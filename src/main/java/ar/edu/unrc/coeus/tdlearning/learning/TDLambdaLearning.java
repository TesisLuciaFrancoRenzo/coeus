/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.coeus.tdlearning.learning;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.interfaces.*;
import ar.edu.unrc.coeus.tdlearning.training.TDTrainerNTupleSystem;
import ar.edu.unrc.coeus.tdlearning.training.TDTrainerPerceptron;
import ar.edu.unrc.coeus.tdlearning.training.Trainer;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.coeus.utils.MaximalActionPredictionConsumer;
import ar.edu.unrc.coeus.utils.StatisticCalculator;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Este método de aprendizaje de TD lambda learning necesita Trazas de elegibilidad como método de asignación de crédito temporal, el cual puede ser
 * ajustado a preferencia. Utiliza redes neuronales para recordar los patrones aprendidos durante la solución del problema.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TDLambdaLearning {

    public static final int    MAX_ELIGIBILITY_TRACE_LENGTH = 500;
    public static final double MAX_LAMBDA_GAMMA             = 0.99;
    public static final double THRESHOLD_LAMBDA_GAMMA       = 0.001;
    private final boolean                 canCollectStatistics;
    private final boolean[]               concurrencyInLayer;
    private final double[]                currentAlpha;
    private final double                  gamma;
    private final double[]                initialAlpha;
    private final double                  lambda;
    private final ELearningStyle          learningStyle;
    private final NTupleSystem            nTupleSystem;
    private final ENeuralNetworkType      neuralNetworkType;
    private final INeuralNetworkInterface perceptronInterface;
    private final Random                  random;
    private final boolean                 replaceEligibilityTraces;
    private int                        alphaAnnealingT                    = 0;
    private boolean                    computeParallelBestPossibleAction  = false;
    private double                     currentExplorationRate             = 0.0;
    private int                        eligibilityTraceLength             = 0;
    private EExplorationRateAlgorithms explorationRate                    = null;
    private double                     explorationRateFinalValue          = 0.0;
    private int                        explorationRateFinishInterpolation = 0;
    private double                     explorationRateInitialValue        = 0.0;
    private int                        explorationRateStartInterpolation  = 0;
    private ELearningRateAdaptation    learningRateAdaptation             = null;
    private int randomChoiceCounter;
    private StatisticCalculator statisticsBestPossibleActionTimes = null;
    private StatisticCalculator statisticsTrainingTimes           = null;
    private Trainer             trainer                           = null;

    /**
     * Algoritmo de entrenamiento de redes neuronales genéricas con soporte multicapa, mediante TD Learning.
     *
     * @param learningStyle            tipo de aprendizaje utilizado.
     * @param perceptronInterface      red neuronal que se desea entrenar, la cual implementa la interfaz {@code INeuralNetworkInterface}, permitiendo
     *                                 así el acceso a su representación interna.
     * @param lambda                   escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param replaceEligibilityTraces true si se utiliza el método de reemplazo de trazas de elegibilidad
     * @param alpha                    tasa de aprendizaje para cada capa. Si es null, se inicializa cada alpha con la formula 1/num_neuronas de la
     *                                 capa anterior.
     * @param gamma                    tasa de descuento, entre [0,1].
     * @param concurrencyInLayer       true en las capas que se deben computar concurrentemente.
     * @param collectStatistics        true guarda estadísticas relevante a los tiempos de cálculo.
     */
    public
    TDLambdaLearning(
            final INeuralNetworkInterface perceptronInterface,
            final ELearningStyle learningStyle,
            final double[] alpha,
            final double lambda,
            final boolean replaceEligibilityTraces,
            final double gamma,
            final boolean[] concurrencyInLayer,
            final Random random,
            final boolean collectStatistics
    ) {
        super();
        if ( perceptronInterface == null ) {
            throw new IllegalArgumentException("perceptronInterface can't be null");
        }
        if ( concurrencyInLayer == null ) {
            throw new IllegalArgumentException("concurrencyInLayer can't be null");
        }
        if ( learningStyle == null ) {
            throw new IllegalArgumentException("learningStyle can't be null");
        }
        if ( learningStyle == ELearningStyle.STATE ) {
            throw new IllegalArgumentException("learningStyle = STATE is not implemented, yet");
        }
        this.random = random;
        this.learningStyle = learningStyle;

        if ( alpha == null ) {
            initialAlpha = new double[perceptronInterface.getLayerQuantity()];
            for ( int i = 0; i < perceptronInterface.getLayerQuantity(); i++ ) {
                if ( perceptronInterface.getNeuronQuantityInLayer(i) <= 0 ) {
                    throw new IllegalArgumentException("the layer " + i + " must have 1 or more neurons");
                }
                initialAlpha[i] = 1.0d / perceptronInterface.getNeuronQuantityInLayer(i);
            }
        } else {
            if ( alpha.length != perceptronInterface.getLayerQuantity() ) {
                throw new IllegalArgumentException("alpha.length=" + alpha.length + " must be equal to perceptronInterface.getLayerQuantity()=" +
                                                   perceptronInterface.getLayerQuantity());
            }
            initialAlpha = alpha;
        }

        if ( ( perceptronInterface.getLayerQuantity() != concurrencyInLayer.length ) ||
             ( perceptronInterface.getLayerQuantity() != initialAlpha.length ) ) {
            throw new IllegalArgumentException("alpha.length, concurrencyInLayer.length and perceptronInterface.getLayerQuantity() must be the same");
        }

        currentAlpha = new double[perceptronInterface.getLayerQuantity()];
        System.arraycopy(initialAlpha, 0, currentAlpha, 0, initialAlpha.length);
        this.lambda = lambda;
        this.replaceEligibilityTraces = replaceEligibilityTraces;
        this.gamma = gamma;
        neuralNetworkType = ENeuralNetworkType.PERCEPTRON;
        this.perceptronInterface = perceptronInterface;
        nTupleSystem = null;
        this.concurrencyInLayer = concurrencyInLayer;
        canCollectStatistics = collectStatistics;

        if ( collectStatistics ) {
            statisticsBestPossibleActionTimes = new StatisticCalculator();
            statisticsTrainingTimes = new StatisticCalculator();
        }
        randomChoiceCounter = 0;
    }


    /**
     * Algoritmo de entrenamiento de redes neuronales NTupla, mediante TD Learning.
     *
     * @param nTupleSystem             red NTupla a entrenar.
     * @param learningStyle            tipo de aprendizaje utilizado.
     * @param lambda                   escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param eligibilityTraceLength   longitud de la traza de elegibilidad. Si el valor es negativo, Se computará una longitud dinámicamente.
     * @param replaceEligibilityTraces true si se utiliza el método de reemplazo de trazas de elegibilidad
     * @param alpha                    tasa de aprendizaje. Si es null, se inicializa cada alpha con la formula 1/num_neuronas de la capa anterior.
     * @param gamma                    tasa de descuento entre [0,1].
     * @param concurrencyInLayer       true en las capas que se deben computar concurrentemente.
     * @param collectStatistics        true guarda estadísticas relevante a los tiempos de cálculo.
     */
    public
    TDLambdaLearning(
            final NTupleSystem nTupleSystem,
            final ELearningStyle learningStyle,
            final Double alpha,
            final double lambda,
            final int eligibilityTraceLength,
            final boolean replaceEligibilityTraces,
            final double gamma,
            final boolean[] concurrencyInLayer,
            final Random random,
            final boolean collectStatistics
    ) {
        super();
        if ( nTupleSystem == null ) {
            throw new IllegalArgumentException("nTupleSystem can't be null");
        }

        if ( concurrencyInLayer == null ) {
            throw new IllegalArgumentException("concurrencyInLayer can't be null");
        }

        if ( learningStyle == null ) {
            throw new IllegalArgumentException("learningStyle can't be null");
        }
        if ( learningStyle == ELearningStyle.STATE ) {
            throw new IllegalArgumentException("learningStyle = STATE is not implemented, yet");
        }
        this.random = random;
        this.learningStyle = learningStyle;

        if ( alpha == null ) {
            initialAlpha = new double[2];
            initialAlpha[0] = 1.0d / nTupleSystem.getNTuplesLength().length;
            initialAlpha[1] = 1.0d; //No se usa
        } else {
            initialAlpha = new double[2];
            initialAlpha[0] = alpha;
            initialAlpha[1] = alpha; //No se usa
        }

        if ( ( concurrencyInLayer.length != 2 ) || ( initialAlpha.length != 2 ) ) {
            throw new IllegalArgumentException("alpha.length, concurrencyInLayer.length and perceptronInterface.getLayerQuantity() must be the same");
        }

        currentAlpha = new double[2];
        System.arraycopy(initialAlpha, 0, currentAlpha, 0, initialAlpha.length);
        this.lambda = lambda;
        this.eligibilityTraceLength = ( eligibilityTraceLength < 0 ) ? calculateBestEligibilityTraceLength(lambda, gamma) : eligibilityTraceLength;
        this.replaceEligibilityTraces = replaceEligibilityTraces;
        this.gamma = gamma;
        neuralNetworkType = ENeuralNetworkType.N_TUPLE;
        perceptronInterface = null;
        this.nTupleSystem = nTupleSystem;
        this.concurrencyInLayer = concurrencyInLayer;
        canCollectStatistics = collectStatistics;

        if ( collectStatistics ) {
            statisticsBestPossibleActionTimes = new StatisticCalculator();
            statisticsTrainingTimes = new StatisticCalculator();
        }
        randomChoiceCounter = 0;
    }

    /**
     * Calcula un valor de "enfriamiento" o "recocido" (nombrado diferente dependiendo la bibliografía) sobre el valor inicial {@code
     * initialAlphaValue} en el tiempo {@code t}.
     *
     * @param initialAlphaValue valor inicial.
     * @param t                 valor de {@code T} actual.
     * @param T                 valor que indica el momento en el que {@code initialAlphaValue} disminuye hasta {@code initialAlphaValue}/2.
     *
     * @return alpha en el tiempo {@code t}.
     */
    public static
    double calculateAnnealing(
            final double initialAlphaValue,
            final int t,
            final int T
    ) {
        if ( ( initialAlphaValue > 1.0d ) || ( initialAlphaValue < 0.0d ) ) {
            throw new IllegalArgumentException("initialAlphaValue=" + initialAlphaValue + " is out of range from a valid [0..1]");
        }
        return initialAlphaValue / ( 1.0d + ( t / ( (double) T ) ) );
    }

    /**
     * Heurística que ayuda a establecer la longitud de la traza de elegibilidad según los valores de {@code lambda}.
     *
     * @param lambda escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param gamma  tasa de descuento, entre [0,1].
     *
     * @return valor óptimo para longitud de la traza de elegibilidad.
     */
    public static
    Integer calculateBestEligibilityTraceLength(
            final double lambda,
            final double gamma
    ) {
        if ( ( lambda > 1.0d ) || ( lambda < 0.0d ) || ( gamma > 1.0d ) || ( gamma < 0.0d ) ) {
            throw new IllegalArgumentException("lambda and gamma must be values from 0 to 1");
        }
        final double lambdaGamma = lambda * gamma;
        if ( lambdaGamma == 0.0d ) {
            return 0;
        }
        if ( lambdaGamma > MAX_LAMBDA_GAMMA ) {
            return Integer.MAX_VALUE;
        }
        int          length    = 0;
        final double threshold = THRESHOLD_LAMBDA_GAMMA * lambdaGamma;
        while ( true ) {
            final double pow = Math.pow(lambdaGamma, length);
            if ( pow < threshold ) {
                return length;
            }
            length++;
            if ( length >= MAX_ELIGIBILITY_TRACE_LENGTH ) {
                return Integer.MAX_VALUE;
            }
        }
    }

    /**
     * Dada la Interpolación Lineal entre los puntos (-Infinito, {@code initialValue}) , ({@code startInterpolation}, {@code initialValue}), ({@code
     * finishInterpolation}, {@code finalValue}) y (Infinito, {@code finalValue}) calcula el valor en el tiempo {@code t}.
     *
     * @param t                   tiempo actual.
     * @param initialValue        valor inicial.
     * @param finalValue          valor final.
     * @param startInterpolation  tiempo {@code t} donde se comienza a disminuir el valor {@code initialValue}
     * @param finishInterpolation tiempo {@code t} donde deja de disminuir el valor {@code initialValue}
     *
     * @return
     */
    public static
    double calculateLinearInterpolation(
            final int t,
            final double initialValue,
            final double finalValue,
            final double startInterpolation,
            final double finishInterpolation
    ) {
        final double currentValue;
        if ( t < startInterpolation ) {
            currentValue = initialValue;
        } else if ( t > finishInterpolation ) {
            currentValue = finalValue;
        } else {
            currentValue =
                    ( ( ( t - startInterpolation ) / ( finishInterpolation - startInterpolation ) ) * ( finalValue - initialValue ) ) + initialValue;
        }
        return currentValue;
    }

    /**
     * Computa la mejor {@code IAction} posible a realizar dado un {@code IState} utilizando la red neuronal. No invocar sobre estados finales.
     *
     * @param problem                                problema a resolver.
     * @param learningStyle                          estilo de aprendizaje utilizado.
     * @param turnInitialState                       estado del problema al comienzo del turno actual.
     * @param allPossibleActionsFromTurnInitialState todas las posibles acciones que {@code actor} puede tomar en {@code turnInitialState}.
     * @param actor                                  actor en el turno actual.
     * @param computeParallelBestPossibleAction      true si la solución se debe computar concurrentemente.
     * @param bestPossibleActionTimes                para almacenar estadísticas de tiempos demorados. Si no se desea utilizar, debe ser null.
     *
     * @return la mejor {@code IAction} de todas las posibles para el {@code actor} en el {@code turnInitialState} actual.
     */
    public static
    ActionPrediction computeBestPossibleAction(
            final IProblemRunner problem,
            final ELearningStyle learningStyle,
            final IState turnInitialState,
            final Collection< IAction > allPossibleActionsFromTurnInitialState,
            final IActor actor,
            final boolean computeParallelBestPossibleAction,
            final Random random,
            final StatisticCalculator bestPossibleActionTimes
    ) {
        long time = 0L;
        if ( bestPossibleActionTimes != null ) {
            time = System.currentTimeMillis();
        }
        final Stream< IAction > stream = computeParallelBestPossibleAction
                                         ? allPossibleActionsFromTurnInitialState.parallelStream()
                                         : allPossibleActionsFromTurnInitialState.stream();
        final List< ActionPrediction > bestActions = stream.map(possibleAction -> {
            switch ( learningStyle ) {
                case AFTER_STATE:
                    return evaluateAfterState(problem, turnInitialState, possibleAction, actor);
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }
        }).collect(MaximalActionPredictionConsumer::new, MaximalActionPredictionConsumer::accept, MaximalActionPredictionConsumer::combine).getList();
        final ActionPrediction bestAction = bestActions.get(randomBetween(0, bestActions.size() - 1, random));
        if ( bestPossibleActionTimes != null ) {
            time = System.currentTimeMillis() - time;
            bestPossibleActionTimes.addSample(time);
        }
        return bestAction;
    }

    /**
     * Calcula una predicción de la recompensa final del juego utilizando un {@code IState} obtenido tras de aplicar {@code action} en {@code
     * turnInitialState}.
     *
     * @param problem          problema a resolver.
     * @param turnInitialState estado del problema al comienzo del turno.
     * @param action           acción a tomar.
     * @param actor            actor en el turno actual.
     *
     * @return Tupla {@code ActionPrediction} que contiene 2 elementos: la acción a tomar {@code action}, y la predicción de la recompensa final del
     * juego asociada a dicha acción.
     */
    public static
    ActionPrediction evaluateAfterState(
            final IProblemRunner problem,
            final IState turnInitialState,
            final IAction action,
            final IActor actor
    ) {
        final IState   afterState   = problem.computeAfterState(turnInitialState, action);
        final Object[] output       = problem.evaluateBoardWithPerceptron(afterState);
        final int      outputLength = output.length;
        for ( int i = 0; i < outputLength; i++ ) {
            output[i] = problem.deNormalizeValueFromPerceptronOutput(output[i]) + afterState.getStateReward(i);
        }
        return new ActionPrediction(action, problem.computeNumericRepresentationFor(output, actor), afterState);
    }

    /**
     * Entrena de una red neuronal mediante la experiencia de la solución de un turno del problema.
     *
     * @param problem                           problema a resolver.
     * @param trainer                           método de entrenamiento elegido, según el tipo de red neuronal.
     * @param afterState                        estado de transición luego de aplicar la mejor {@code action} al estado {@code turnInitialState}.
     * @param nextTurnState                     estado del problema en el próximo turno, luego de aplicar las acciones/efectos no determinísticos.
     * @param currentAlpha                      alpha actual.
     * @param concurrencyInLayer                capas que deben ser computadas concurrentemente.
     * @param computeParallelBestPossibleAction true si se deben evaluar las mejores acciones concurrentemente.
     * @param bestPossibleActionTimes           tiempos de respuestas al evaluar las mejores acciones posibles, para realizar estadísticas. Debe ser
     *                                          null si no se utiliza.
     * @param trainingTimes                     tiempos de respuestas al entrenar la red neuronal, para realizar estadísticas. Debe ser null si no se
     *                                          utiliza.
     *
     * @currentState
     */
    public static
    void learnEvaluationAfterState(
            final IProblemToTrain problem,
            final Trainer trainer,
            final IState afterState,
            final IState nextTurnState,
            final double[] currentAlpha,
            final boolean[] concurrencyInLayer,
            final boolean computeParallelBestPossibleAction,
            final Random random,
            final StatisticCalculator bestPossibleActionTimes,
            final StatisticCalculator trainingTimes
    ) {
        long time = 0L;
        if ( nextTurnState.isTerminalState() ) {
            // Si nextTurnState es un estado final, no podemos calcular el
            // bestActionForNextTurn.
            // Teóricamente la evaluación obtenida por el perceptronInterface
            // en el ultimo afterState, debería ser el resultado final real del
            // juego, por lo tanto entrenamos el ultimo afterState para que
            // prediga el final del problema
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis();
            }
            // V (s') ← V (s') + α(rFinal − V (s'))
            // (matemática sin trazas de elegibilidad)
            trainer.train(problem, afterState, nextTurnState, currentAlpha, concurrencyInLayer);
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis() - time;
                trainingTimes.addSample(time);
            }
        } else {
            // Evaluamos cada acción posible aplicada al estado nextState y
            // elegimos la mejor acción basada las predicciones del problema
            final List< IAction > possibleActionsNextTurn = problem.listAllPossibleActions(nextTurnState);
            final ActionPrediction bestActionForNextTurn = computeBestPossibleAction(problem, ELearningStyle.AFTER_STATE,
                    nextTurnState,
                    possibleActionsNextTurn,
                    problem.getActorToTrain(),
                    computeParallelBestPossibleAction,
                    random,
                    bestPossibleActionTimes);
            // Aplicamos la acción 'bestActionForNextTurn' al estado (turno)
            // siguiente 'nextState', y obtenemos el estado de transición
            // (determinístico) del próximo estado (turno).
            final IState afterStateNextTurn = bestActionForNextTurn.getAfterState();

            if ( trainingTimes != null ) {
                time = System.currentTimeMillis();
            }
            // V (s') ← V (s') + α(rnext + V (s'next) − V (s'))
            // (matemática sin trazas de elegibilidad)
            trainer.train(problem, afterState, afterStateNextTurn, currentAlpha, concurrencyInLayer);
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis() - time;
                trainingTimes.addSample(time);
            }
        }
    }

    /**
     * Genera un numero al azar entre los limites {@code a} y {@code a}, incluyendo a estos.
     *
     * @param minValue limite inferior (inclusive)
     * @param maxValue limite superior (inclusive)
     *
     * @return número aleatorio entre a y b
     */
    public static
    int randomBetween(
            final int minValue,
            final int maxValue,
            final Random random
    ) {
        if ( minValue > maxValue ) {
            throw new IllegalArgumentException("error: b must be greater or equal than a");
        } else if ( minValue == maxValue ) {
            return minValue;
        } else {
            return random.nextInt(( maxValue - minValue ) + 1) + minValue;
        }
    }

    /**
     * @return true si se esta recolectando estadísticas sobre los tiempos de cómputos de la librería.
     */
    public
    boolean canCollectStatistics() {
        return canCollectStatistics;
    }

    /**
     * @return valor T ({@code alphaAnnealingT}) que indica el momento en el entrenamiento que alpha = initialAlpha/2 utilizado en {@code
     * calculateAnnealing}.
     */
    public
    int getAlphaAnnealingT() {
        return alphaAnnealingT;
    }

    /**
     * @return tiempos de demora al calcular la mejor posible acción, útil para estadísticas.
     */
    public
    double getBestPossibleActionTimesAverage() {
        return statisticsBestPossibleActionTimes.getAverage();
    }

    /**
     * @return el alpha en el turno actual, de cada capa.
     */
    public
    double[] getCurrentAlpha() {
        return currentAlpha;
    }

    public
    double getCurrentExplorationRate() {
        return currentExplorationRate;
    }

    /**
     * @return tiempos de demora al entrenar la red neuronal, útil para estadísticas.
     */
    public
    int getRandomChoicesCounter() {
        return randomChoiceCounter;
    }

    /**
     * @return tiempos de demora al entrenar la red neuronal, útil para estadísticas.
     */
    public
    double getTrainingTimesAverage() {
        return statisticsTrainingTimes.getAverage();
    }

    /**
     * @return true si se esta calculando la mejor acción concurrentemente.
     */
    public
    boolean isComputeParallelBestPossibleAction() {
        return computeParallelBestPossibleAction;
    }

    /**
     * Establece si el cálculo de la mejor acción debe realizarse concurrentemente.
     *
     * @param computeParallelBestPossibleAction true si las evaluaciones deben ejecutarse en paralelo.
     */
    public
    void setComputeParallelBestPossibleAction(
            final boolean computeParallelBestPossibleAction
    ) {
        this.computeParallelBestPossibleAction = computeParallelBestPossibleAction;
    }

    /**
     * Si mantenemos el valor de alpha fijo, las fluctuaciones de la red previenen que en lugar de converger en un mínimo local, terminemos danzando
     * al azar alrededor. Para alcanzar el mínimo, y quedarnos ahí, debemos utilizar técnicas de templado (disminuir gradualmente) la tasa de
     * aprendizaje "alpha". Una técnica simple es la de mantener constante alpha durante un {@code alphaAnnealingT} entrenamientos, permitiendo a la
     * red encontrar el mínimo local, antes de empezar a disminuir muy lentamente, lo cual esta demostrado por teoría que garantiza convergencia en un
     * mínimo. Los valores de {@code T} se determinan por prueba y error.
     *
     * @param alphaAnnealingT valor T ({@code alphaAnnealingT}) que indica el momento en el entrenamiento que alpha = initialAlpha/2 utilizado en
     *                        {@code calculateAnnealing}.
     */
    public
    void setAnnealingLearningRate( final int alphaAnnealingT ) {
        if ( alphaAnnealingT < 0 ) {
            throw new IllegalArgumentException("alphaAnnealingT must be greater or equal to 0");
        }
        learningRateAdaptation = ELearningRateAdaptation.ANNEALING;
        this.alphaAnnealingT = alphaAnnealingT;
    }

    /**
     * Establece una tasa de exploración fija. El valor elegido es la probabilidad de que el movimiento elegido sea al azar, en lugar de calculado
     * mediante la red neuronal.
     *
     * @param value tasa de exploración.
     */
    public
    void setFixedExplorationRate( final double value ) {
        if ( ( value < 0.0d ) || ( value > 1.0d ) ) {
            throw new IllegalArgumentException("value debe estar en el intervalo [0,1]");
        }
        explorationRate = EExplorationRateAlgorithms.FIXED;
        explorationRateInitialValue = value;
    }

    /**
     * Establece constante de aprendizaje, no variable con el tiempo.
     */
    public
    void setFixedLearningRate() {
        learningRateAdaptation = ELearningRateAdaptation.FIXED;
        alphaAnnealingT = 0;
    }

    /**
     * Establece una tasa de exploración variable con el tiempo, la cual comienza a decrementarse en el turno {@code startDecrementing} y finaliza en
     * {@code finishDecrementing}.
     *
     * @param initialValue
     * @param startDecrementing
     * @param finalValue
     * @param finishDecrementing
     */
    public
    void setLinearExplorationRate(
            final double initialValue,
            final int startDecrementing,
            final double finalValue,
            final int finishDecrementing
    ) {
        if ( ( initialValue < 0.0d ) || ( initialValue > 1.0d ) ) {
            throw new IllegalArgumentException("initialValue debe estar en el intervalo [0,1]");
        }
        if ( ( finalValue < 0.0d ) || ( finalValue > 1.0d ) ) {
            throw new IllegalArgumentException("finalValue debe estar en el intervalo [0,1]");
        }
        explorationRate = EExplorationRateAlgorithms.LINEAR;
        explorationRateInitialValue = initialValue;
        explorationRateStartInterpolation = startDecrementing;
        explorationRateFinalValue = finalValue;
        explorationRateFinishInterpolation = finishDecrementing;
    }

    /**
     * Entrena la red neuronal con lo que aprende de la experiencia de resolver una sola vez {@code problem} de comienzo a fin, durante 1 o mas turnos
     * hasta alcanzar su objetivo o fracasar.
     *
     * @param problem                          problema a resolver
     * @param solveAndTrainOnceExecutionNumber cantidad de veces que se ejecutó {@code solveAndTrainOnce}
     */
    public
    Double solveAndTrainOnce(
            final IProblemToTrain problem,
            final int solveAndTrainOnceExecutionNumber
    ) {
        if ( learningRateAdaptation == null ) {
            throw new IllegalArgumentException("learningRateAdaptation can't be null");
        }
        if ( explorationRate == null ) {
            throw new IllegalArgumentException("explorationRate can't be null");
        }
        if ( solveAndTrainOnceExecutionNumber < 0 ) {
            throw new IllegalArgumentException("solveAndTrainOnceExecutionNumber must be grater or equal to 0");
        }
        //inicializamos las constantes de aprendizaje
        switch ( learningRateAdaptation ) {
            case FIXED:
                // ignorar las actualizaciones y dejar las alphas sin modificar
                break;
            case ANNEALING:
                // Ajustamos las alphas según el método de annealing
                // µ(t) = µ(0)/(1 + t/T)
                IntStream rangeStream = IntStream.range(0, currentAlpha.length);
                rangeStream = ( currentAlpha.length > 100 ) ? rangeStream.parallel() : rangeStream.sequential();
                rangeStream.forEach(index -> currentAlpha[index] =
                        calculateAnnealing(initialAlpha[index], solveAndTrainOnceExecutionNumber, alphaAnnealingT));
                break;
        }

        //inicializamos el factor de exploración
        switch ( explorationRate ) {
            case FIXED:
                // factor constante
                currentExplorationRate = explorationRateInitialValue;
                break;
            case LINEAR:
                //factor ajustado linealmente entre dos puntos
                currentExplorationRate = calculateLinearInterpolation(solveAndTrainOnceExecutionNumber,
                        explorationRateInitialValue,
                        explorationRateFinalValue,
                        explorationRateStartInterpolation,
                        explorationRateFinishInterpolation);
                break;
            default:
                throw new IllegalStateException("unknown explorationRate");
        }
        assert ( currentExplorationRate >= 0.0d ) && ( currentExplorationRate <= 1.0d );
        randomChoiceCounter = 0;

        // Inicializamos el problema y las variables del entrenador, o reutilizamos
        // el ultimo Trainer para reciclar sus variables
        if ( trainer == null ) {
            switch ( neuralNetworkType ) {
                case PERCEPTRON:
                    trainer = new TDTrainerPerceptron(perceptronInterface, lambda, replaceEligibilityTraces, gamma);
                    break;
                case N_TUPLE:
                    trainer = new TDTrainerNTupleSystem(nTupleSystem, eligibilityTraceLength, lambda, replaceEligibilityTraces,

                            gamma);
                    break;
            }
        } else {
            trainer.reset();
        }

        IState                    turnInitialState = problem.initialize(problem.getActorToTrain());
        long                      startTime        = 0L;
        final StatisticCalculator timePerGame      = new StatisticCalculator();
        long                      currentTurn      = 1L;

        while ( !turnInitialState.isTerminalState() ) {
            if ( canCollectStatistics ) {
                startTime = System.currentTimeMillis();
            }

            final List< IAction > possibleActions = problem.listAllPossibleActions(turnInitialState);
            final IState          afterState;
            if ( ( currentExplorationRate > 0.0d ) && problem.canExploreThisTurn(currentTurn) && ( random.nextDouble() <= currentExplorationRate ) ) {
                // elegimos una acción al azar
                final IAction bestAction = possibleActions.get(randomBetween(0, possibleActions.size() - 1, random));
                // aplicamos la acción 'bestAction' al estado actual 'currentState',
                // y obtenemos su estado de transición determinístico.
                afterState = problem.computeAfterState(turnInitialState, bestAction);
                randomChoiceCounter++;
            } else {
                // evaluamos cada acción aplicada al estado inicial y elegimos la mejor
                // de éstas basada en las predicciones de recompensas final del problema
                final ActionPrediction bestActionPrediction = computeBestPossibleAction(problem,
                        learningStyle,
                        turnInitialState,
                        possibleActions,
                        problem.getActorToTrain(),
                        computeParallelBestPossibleAction,
                        random,
                        statisticsBestPossibleActionTimes);
                // aplicamos la acción 'bestAction' al estado actual 'currentState',
                // y obtenemos su estado de transición determinístico.
                afterState = bestActionPrediction.getAfterState();
            }

            // hacemos que el problema aplique la acción 'bestAction' de la red neuronal,
            // y retorne el estado del turno siguiente, luego de aplicar acciones
            // no determinísticas pertinentes para terminar el turno
            final IState nextTurnState = problem.computeNextTurnStateFromAfterState(afterState);

            // hacemos efectivo los cambios realizados por la IA en la lógica del problema
            problem.setCurrentState(nextTurnState);

            // entrenamos el problema para que recuerde las predicciones de t+1
            // (que en este caso es nextState o el afterState dependiendo de las
            // implementaciones de esta clase abstracta). Si es un movimiento al
            // azar se actualizan trazas pero no se actualizan pesos
            switch ( learningStyle ) {
                case AFTER_STATE:
                    learnEvaluationAfterState(problem,
                            trainer,
                            afterState,
                            nextTurnState,
                            currentAlpha,
                            concurrencyInLayer,
                            computeParallelBestPossibleAction,
                            random,
                            statisticsBestPossibleActionTimes,
                            statisticsTrainingTimes);
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }

            // recordamos el nuevo estado del problema luego de aplicar todas
            // las acciones necesarias para avanzar en la solución del problema
            turnInitialState = nextTurnState;
            currentTurn++;

            if ( canCollectStatistics ) {
                timePerGame.addSample(System.currentTimeMillis() - startTime);
            }
        }

        return canCollectStatistics ? timePerGame.getAverage() : null;
    }

}
