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
import ar.edu.unrc.coeus.tdlearning.utils.MaximalActionPredictionConsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.random;

/**
 * Este método de aprendizaje de TD lambda learning necesita Trazas de elegibilidad como método de asignación de crédito
 * temporal, el cual puede ser ajustado a preferencia. Utiliza redes neuronales para recordar los patrones aprendidos
 * durante la solución del problema.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TDLambdaLearning {

    private final boolean[]               concurrencyInLayer;
    private final ENeuralNetworkType      neuralNetworkType;
    private final INeuralNetworkInterface perceptronInterface;
    private       int                     alphaAnnealingT;
    private       boolean                 canCollectStatistics;
    private boolean computeParallelBestPossibleAction = false;
    private double[]                   currentAlpha;
    private EExplorationRateAlgorithms explorationRate;
    private double                     explorationRateFinalValue;
    private int                        explorationRateFinishInterpolation;
    private double                     explorationRateInitialValue;
    private int                        explorationRateStartInterpolation;
    private double                     gamma;
    private double[]                   initialAlpha;
    private double                     lambda;
    private ELearningRateAdaptation    learningRateAdaptation;
    private ELearningStyle             learningStyle;
    private NTupleSystem               nTupleSystem;
    private boolean                    replaceEligibilityTraces;
    private LinkedList<Long>           statisticsBestPossibleActionTimes;
    private LinkedList<Long>           statisticsTrainingTimes;
    private Trainer                    trainer;

    /**
     * Algoritmo de entrenamiento de redes neuronales genéricas con soporte multicapa, mediante TD Learning.
     *
     * @param learningStyle            tipo de aprendizaje utilizado.
     * @param perceptronInterface      red neuronal que se desea entrenar, la cual implementa la interfaz {@code INeuralNetworkInterface}, permitiendo
     *                                 así el acceso a su representación interna.
     * @param lambda                   escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param alpha                    tasa de aprendizaje para cada capa. Si es null, se inicializa cada alpha con la formula 1/num_neuronas de la
     *                                 capa anterior.
     * @param gamma                    tasa de descuento, entre [0,1].
     * @param concurrencyInLayer       true en las capas que se deben computar concurrentemente.
     * @param replaceEligibilityTraces true si se permite reiniciar las trazas de elegibilidad en caso de movimientos al azar durante el
     *                                 entrenamiento.
     * @param collectStatistics        true guarda estadísticas relevante a los tiempos de cálculo.
     */
    public
    TDLambdaLearning(
            final INeuralNetworkInterface perceptronInterface,
            final ELearningStyle learningStyle,
            final double[] alpha,
            final double lambda,
            final double gamma,
            final boolean[] concurrencyInLayer,
            final boolean replaceEligibilityTraces,
            final boolean collectStatistics
    ) {
        if (perceptronInterface == null) {
            throw new IllegalArgumentException("perceptronInterface can't be null");
        }
        if (concurrencyInLayer == null) {
            throw new IllegalArgumentException("concurrencyInLayer can't be null");
        }
        if (learningStyle == null) {
            throw new IllegalArgumentException("learningStyle can't be null");
        } else if (learningStyle == ELearningStyle.state) {
            throw new IllegalArgumentException("learningStyle = state is not implemented, yet");
        }

        this.learningStyle = learningStyle;

        if (alpha == null) {
            initialAlpha = new double[perceptronInterface.getLayerQuantity()];
            for (int i = 0; i < perceptronInterface.getLayerQuantity(); i++) {
                if (perceptronInterface.getNeuronQuantityInLayer(i) <= 0) {
                    throw new IllegalArgumentException("the layer " + i + " must have 1 or more neurons");
                }
                initialAlpha[i] = 1d / perceptronInterface.getNeuronQuantityInLayer(i);
            }
        } else {
            if (alpha.length != perceptronInterface.getLayerQuantity()) {
                throw new IllegalArgumentException("alpha.length=" +
                                                   alpha.length +
                                                   " must be equal to perceptronInterface.getLayerQuantity()=" +
                                                   perceptronInterface.getLayerQuantity());
            }
            this.initialAlpha = alpha;
        }

        if (perceptronInterface.getLayerQuantity() != concurrencyInLayer.length || perceptronInterface.getLayerQuantity() != initialAlpha.length) {
            throw new IllegalArgumentException("alpha.length, concurrencyInLayer.length and perceptronInterface.getLayerQuantity() must be the same");
        }

        this.currentAlpha = new double[perceptronInterface.getLayerQuantity()];
        System.arraycopy(initialAlpha, 0, currentAlpha, 0, initialAlpha.length);
        this.lambda = lambda;
        this.gamma = gamma;
        this.neuralNetworkType = ENeuralNetworkType.perceptron;
        this.perceptronInterface = perceptronInterface;
        this.nTupleSystem = null;
        this.concurrencyInLayer = concurrencyInLayer;
        this.replaceEligibilityTraces = replaceEligibilityTraces;
        this.canCollectStatistics = collectStatistics;

        if (collectStatistics) {
            this.statisticsBestPossibleActionTimes = new LinkedList<>();
            this.statisticsTrainingTimes = new LinkedList<>();
        }
    }

    /**
     * Algoritmo de entrenamiento de redes neuronales NTupla, mediante TD Learning.
     *
     * @param nTupleSystem             red NTupla a entrenar.
     * @param learningStyle            tipo de aprendizaje utilizado.
     * @param lambda                   escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param alpha                    tasa de aprendizaje. Si es null, se inicializa cada alpha con la formula 1/num_neuronas de la capa anterior.
     * @param gamma                    tasa de descuento entre [0,1].
     * @param concurrencyInLayer       true en las capas que se deben computar concurrentemente.
     * @param replaceEligibilityTraces permite reiniciar las trazas de elegibilidad en caso de movimientos al azar durante el entrenamiento.
     * @param collectStatistics        true guarda estadísticas relevante a los tiempos de cálculo.
     */
    public
    TDLambdaLearning(
            final NTupleSystem nTupleSystem,
            final ELearningStyle learningStyle,
            final Double alpha,
            final double lambda,
            final double gamma,
            final boolean[] concurrencyInLayer,
            final boolean replaceEligibilityTraces,
            final boolean collectStatistics
    ) {
        if (nTupleSystem == null) {
            throw new IllegalArgumentException("nTupleSystem can't be null");
        }

        if (concurrencyInLayer == null) {
            throw new IllegalArgumentException("concurrencyInLayer can't be null");
        }

        if (learningStyle == null) {
            throw new IllegalArgumentException("learningStyle can't be null");
        } else if (learningStyle == ELearningStyle.state) {
            throw new IllegalArgumentException("learningStyle = state is not implemented, yet");
        }

        this.learningStyle = learningStyle;

        if (alpha == null) {
            initialAlpha = new double[2];
            initialAlpha[0] = 1d / nTupleSystem.getNTuplesLength().length;
            initialAlpha[1] = 1d; //No se usa
        } else {
            initialAlpha = new double[2];
            initialAlpha[0] = alpha;
            initialAlpha[1] = alpha; //No se usa
        }

        if (2 != concurrencyInLayer.length || 2 != initialAlpha.length) {
            throw new IllegalArgumentException("alpha.length, concurrencyInLayer.length and perceptronInterface.getLayerQuantity() must be the same");
        }

        this.currentAlpha = new double[2];
        System.arraycopy(initialAlpha, 0, currentAlpha, 0, initialAlpha.length);
        this.lambda = lambda;
        this.gamma = gamma;
        this.neuralNetworkType = ENeuralNetworkType.nTuple;
        this.perceptronInterface = null;
        this.nTupleSystem = nTupleSystem;
        this.concurrencyInLayer = concurrencyInLayer;
        this.replaceEligibilityTraces = replaceEligibilityTraces;
        this.canCollectStatistics = collectStatistics;

        if (collectStatistics) {
            this.statisticsBestPossibleActionTimes = new LinkedList<>();
            this.statisticsTrainingTimes = new LinkedList<>();
        }
    }

    /**
     * Calcula un valor de "enfriamiento" o "recocido" (nombrado diferente dependiendo la bibliografía) sobre el valor
     * inicial {@code initialValue} en el tiempo {@code t}.
     *
     * @param initialValue valor inicial.
     * @param t            valor de {@code T} actual.
     * @param T            valor que indica el momento en el que {@code initialValue} disminuye hasta {@code initialValue}/2.
     *
     * @return alpha en el tiempo {@code t}.
     */
    public static
    double calculateAnnealing(
            final double initialValue,
            final int t,
            final int T
    ) {
        if (initialValue > 1 || initialValue < 0) {
            throw new IllegalArgumentException("initialValue=" + initialValue + " is out of range from a valid [0..1]");
        }
        return initialValue / (1d + (t / ((double) T)));
    }

    /**
     * Heurística que ayuda a establecer la longitud de la traza de elegibilidad según los valores de {@code lambda}.
     *
     * @param lambda escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     *
     * @return valor óptimo para longitud de la traza de elegibilidad.
     */
    public static
    Integer calculateBestEligibilityTraceLength(final double lambda) {
        if (lambda > 1 || lambda < 0) {
            throw new IllegalArgumentException("lambda=" +
                                               lambda +
                                               " is out of range from a valid [0..1]"); //FIXME asegurar qeu todas las excepciones estén en ingles
        } else if (lambda > 0.99) {
            return Integer.MAX_VALUE;
        } else if (lambda == 0) {
            return 0;
        }
        int length = 0;
        while (true) {
            double pow       = Math.pow(lambda, length);
            double threshold = 0.001 * lambda;
            if (pow < threshold) {
                return length;
            }
            length++;
            if (length >= 500) {
                return Integer.MAX_VALUE;
            }
        }
    }

    /**
     * Dada la Interpolación Lineal entre los puntos (-Infinito, {@code initialValue}) , ({@code startInterpolation},
     * {@code initialValue}), ({@code finishInterpolation}, {@code finalValue}) y (Infinito, {@code finalValue}) calcula
     * el valor en el tiempo {@code t}.
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
        if (t < startInterpolation) {
            currentValue = initialValue;
        } else if (t > finishInterpolation) {
            currentValue = finalValue;
        } else {
            currentValue = ((t - startInterpolation) / (finishInterpolation - startInterpolation)) * (finalValue - initialValue) + initialValue;
        }
        return currentValue;
    }

    /**
     * Computa la mejor {@code IAction} posible a realizar dado un {@code IState} utilizando la red neuronal. No invocar
     * sobre estados finales.
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
    IAction computeBestPossibleAction(
            final IProblemRunner problem,
            final ELearningStyle learningStyle,
            final IState turnInitialState,
            final List<IAction> allPossibleActionsFromTurnInitialState,
            final IActor actor,
            final boolean computeParallelBestPossibleAction,
            final LinkedList<Long> bestPossibleActionTimes
    ) {
        final Stream<IAction> stream;
        long                  time = 0;
        if (bestPossibleActionTimes != null) {
            time = System.currentTimeMillis();
        }
        if (computeParallelBestPossibleAction) {
            stream = allPossibleActionsFromTurnInitialState.parallelStream();
        } else {
            stream = allPossibleActionsFromTurnInitialState.stream();
        }
        List<ActionPrediction> bestActions = stream.map(possibleAction -> {
            switch (learningStyle) {
                case afterState: {
                    return evaluateAfterState(problem, turnInitialState, possibleAction, actor);
                }
                default: {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            }
        }).collect(MaximalActionPredictionConsumer::new, MaximalActionPredictionConsumer::accept, MaximalActionPredictionConsumer::combine).getList();
        IAction bestAction = bestActions.get(randomBetween(0, bestActions.size() - 1)).getAction();
        if (bestPossibleActionTimes != null) {
            time = System.currentTimeMillis() - time;
            bestPossibleActionTimes.add(time);
        }
        return bestAction;
    }

    /**
     * Calcula una predicción de la recompensa final del juego utilizando un {@code IState} obtenido tras de aplicar
     * {@code action} en {@code turnInitialState}.
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
        final IState   afterState = problem.computeAfterState(turnInitialState, action);
        final Object[] output     = problem.evaluateBoardWithPerceptron(afterState);
        for (int i = 0; i < output.length; i++) {
            output[i] = problem.deNormalizeValueFromPerceptronOutput(output[i]) + afterState.
                                                                                                    getStateReward(i);
        }
        return new ActionPrediction(action, problem.computeNumericRepresentationFor(output, actor));
    }

    /**
     * Entrena de una red neuronal mediante la experiencia de la solución de un turno del problema.
     *
     * @param problem                           problema a resolver.
     * @param trainer                           método de entrenamiento elegido, según el tipo de red neuronal.
     * @param afterState                        estado de transición luego de aplicar la mejor {@code action} al estado {@code turnInitialState}.
     * @param nextTurnState                     estado del problema en el próximo turno, luego de aplicar las acciones/efectos no determinísticos.
     * @param isARandomMove                     true si el movimiento actual fue elegido al azar.
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
            final boolean isARandomMove,
            final double[] currentAlpha,
            final boolean[] concurrencyInLayer,
            final boolean computeParallelBestPossibleAction,
            final LinkedList<Long> bestPossibleActionTimes,
            final LinkedList<Long> trainingTimes
    ) {
        long time = 0;
        if (!nextTurnState.isTerminalState()) {
            // Evaluamos cada acción posible aplicada al estado nextState y
            // elegimos la mejor acción basada las predicciones del problema
            final List<IAction> possibleActionsNextTurn = problem.
                                                                         listAllPossibleActions(nextTurnState);
            final IAction bestActionForNextTurn = computeBestPossibleAction(problem,
                    ELearningStyle.afterState,
                    nextTurnState,
                    possibleActionsNextTurn,
                    problem.getActorToTrain(),
                    computeParallelBestPossibleAction,
                    bestPossibleActionTimes
            );
            // Aplicamos la acción 'bestActionForNextTurn' al estado (turno)
            // siguiente 'nextState', y obtenemos el estado de transición
            // (determinístico) del próximo estado (turno).
            final IState afterStateNextTurn = problem.computeAfterState(nextTurnState, bestActionForNextTurn);
            // V (s') ← V (s') + α(rnext + V (s'next) − V (s'))
            // (matemática sin trazas de elegibilidad)
            if (trainingTimes != null) {
                time = System.currentTimeMillis();
            }
            trainer.train(problem, afterState, afterStateNextTurn, currentAlpha, concurrencyInLayer, isARandomMove);
            if (trainingTimes != null) {
                time = System.currentTimeMillis() - time;
                trainingTimes.add(time);
            }
        } else {
            // Si nextTurnState es un estado final, no podemos calcular el
            // bestActionForNextTurn.
            // Teóricamente la evaluación obtenida por el perceptronInterface
            // en el ultimo afterState, debería ser el resultado final real del
            // juego, por lo tanto entrenamos el ultimo afterState para que
            // prediga el final del problema
            if (trainingTimes != null) {
                time = System.currentTimeMillis();
            }
            trainer.train(problem, afterState, nextTurnState, currentAlpha, concurrencyInLayer, isARandomMove);
            if (trainingTimes != null) {
                time = System.currentTimeMillis() - time;
                trainingTimes.add(time);
            }
        }
    }

    /**
     * Genera un numero al azar entre los limites {@code a} y {@code a}, incluyendo a estos.
     *
     * @param a limite inferior (inclusive)
     * @param b limite superior (inclusive)
     *
     * @return número aleatorio entre a y b
     */
    public static
    int randomBetween(
            final int a,
            final int b
    ) {
        if (a > b) {
            throw new IllegalArgumentException("error: b must be greater or equal than a");
        } else if (a == b) {
            return a;
        } else {
            return a + (int) ((b - a + 1d) * random());
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
     * @return el alpha en el turno actual, de cada capa.
     */
    public
    double[] getCurrentAlpha() {
        return currentAlpha;
    }

    /**
     * @return tiempos de demora al calcular la mejor posible acción, útil para estadísticas.
     */
    public
    LinkedList<Long> getStatisticsBestPossibleActionTimes() {
        return statisticsBestPossibleActionTimes;
    }

    /**
     * @return tiempos de demora al entrenar la red neuronal, útil para estadísticas.
     */
    public
    LinkedList<Long> getStatisticsTrainingTimes() {
        return statisticsTrainingTimes;
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
     * Si mantenemos el valor de alpha fijo, las fluctuaciones de la red previenen que en lugar de converger en un
     * mínimo local, terminemos danzando al azar alrededor. Para alcanzar el mínimo, y quedarnos ahí, debemos utilizar
     * técnicas de templado (disminuir gradualmente) la tasa de aprendizaje "alpha". Una técnica simple es la de
     * mantener constante alpha durante un {@code alphaAnnealingT} entrenamientos, permitiendo a la red encontrar el
     * mínimo local, antes de empezar a disminuir muy lentamente, lo cual esta demostrado por teoría que garantiza
     * convergencia en un mínimo. Los valores de {@code T} se determinan por prueba y error.
     *
     * @param alphaAnnealingT valor T ({@code alphaAnnealingT}) que indica el momento en el entrenamiento que alpha = initialAlpha/2 utilizado en
     *                        {@code calculateAnnealing}.
     */
    public
    void setAnnealingLearningRate(final int alphaAnnealingT) {
        if (alphaAnnealingT < 0) {
            throw new IllegalArgumentException("alphaAnnealingT must be greater or equal to 0");
        }
        this.learningRateAdaptation = ELearningRateAdaptation.annealing;
        this.alphaAnnealingT = alphaAnnealingT;
    }

    /**
     * Establece una tasa de exploración fija. El valor elegido es la probabilidad de que el movimiento elegido sea al
     * azar, en lugar de calculado mediante la red neuronal.
     *
     * @param value tasa de exploración.
     */
    public
    void setFixedExplorationRate(final double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("value debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.fixed;
        this.explorationRateInitialValue = value;
    }

    /**
     * Establece constante de aprendizaje, no variable con el tiempo.
     */
    public
    void setFixedLearningRate() {
        this.learningRateAdaptation = ELearningRateAdaptation.fixed;
        this.alphaAnnealingT = 0;
    }

    /**
     * Establece una tasa de exploración variable con el tiempo, la cual comienza a decrementarse en el turno
     * {@code startDecrementing} y finaliza en {@code finishDecrementing}.
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
        if (initialValue < 0 || initialValue > 1) {
            throw new IllegalArgumentException("initialValue debe estar en el intervalo [0,1]");
        }
        if (finalValue < 0 || finalValue > 1) {
            throw new IllegalArgumentException("finalValue debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.linear;
        this.explorationRateInitialValue = initialValue;
        this.explorationRateStartInterpolation = startDecrementing;
        this.explorationRateFinalValue = finalValue;
        this.explorationRateFinishInterpolation = finishDecrementing;
    }

    /**
     * Entrena la red neuronal con lo que aprende de la experiencia de resolver una sola vez {@code problem} de comienzo
     * a fin, durante 1 o mas turnos hasta alcanzar su objetivo o fracasar.
     *
     * @param problem     problema a resolver
     * @param currentTurn cantidad de veces que se ejecutó {@code solveAndTrainOnce}
     */
    public
    void solveAndTrainOnce(
            final IProblemToTrain problem,
            final int currentTurn
    ) {
        if (learningRateAdaptation == null) {
            throw new IllegalArgumentException("learningRateAdaptation can't be null");
        }
        if (explorationRate == null) {
            throw new IllegalArgumentException("explorationRate can't be null");
        }
        if (currentTurn < 0) {
            throw new IllegalArgumentException("currentTurn must be grater or equal to 0");
        }
        //inicializamos las constantes de aprendizaje
        switch (this.learningRateAdaptation) {
            case fixed: {
                // ignorar las actualizaciones y dejar las alphas sin modificar
                break;
            }
            case annealing: {
                // Ajustamos las alphas según el método de annealing
                // µ(t) = µ(0)/(1 + t/T)
                IntStream rangeStream = IntStream.range(0, currentAlpha.length);
                if (this.currentAlpha.length > 100) {
                    rangeStream = rangeStream.parallel();
                } else {
                    rangeStream = rangeStream.sequential();
                }
                rangeStream.forEach(index -> currentAlpha[index] = calculateAnnealing(initialAlpha[index], currentTurn, alphaAnnealingT));
                break;
            }
        }

        double currentExplorationRate = 0;
        //inicializamos el factor de exploración
        switch (explorationRate) {
            case fixed: {
                // factor constante
                currentExplorationRate = explorationRateInitialValue;
                break;
            }
            case linear: {
                //factor ajustado linealmente entre dos puntos
                currentExplorationRate = calculateLinearInterpolation(currentTurn,
                        explorationRateInitialValue,
                        explorationRateFinalValue,
                        explorationRateStartInterpolation,
                        explorationRateFinishInterpolation
                );
                break;
            }
        }
        assert currentExplorationRate >= 0 && currentExplorationRate <= 1;

        // Inicializamos el problema y las variables del entrenador, o reutilizamos
        // el ultimo Trainer para reciclar sus variables
        if (trainer == null) {
            switch (neuralNetworkType) {
                case perceptron: {
                    trainer = new TDTrainerPerceptron(perceptronInterface, lambda, gamma, replaceEligibilityTraces);
                    break;
                }
                case nTuple: {
                    trainer = new TDTrainerNTupleSystem(nTupleSystem,
                            calculateBestEligibilityTraceLength(lambda),
                            lambda,
                            gamma,
                            replaceEligibilityTraces
                    );
                    break;
                }
            }
        } else {
            trainer.reset();
        }

        IState  turnInitialState = problem.initialize(problem.getActorToTrain());
        boolean randomChoice     = false;

        while (!turnInitialState.isTerminalState()) {

            // calculamos todas las acciones posibles para el estado inicial
            final IAction bestAction;

            if (currentExplorationRate > 0) {
                randomChoice = Math.random() <= currentExplorationRate;
            }

            final List<IAction> possibleActions = problem.listAllPossibleActions(turnInitialState);
            if (!randomChoice) {
                // evaluamos cada acción aplicada al estado inicial y elegimos la mejor
                // de éstas basada en las predicciones de recompensas final del problema
                bestAction = computeBestPossibleAction(problem,
                        learningStyle,
                        turnInitialState,
                        possibleActions,
                        problem.getActorToTrain(),
                        computeParallelBestPossibleAction,
                        statisticsBestPossibleActionTimes
                );
            } else {
                bestAction = possibleActions.get(randomBetween(0, possibleActions.size() - 1));
            }

            // aplicamos la acción 'bestAction' al estado actual 'currentState',
            // y obtenemos su estado de transición determinístico.
            final IState afterState = problem.computeAfterState(turnInitialState, bestAction);

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
            switch (learningStyle) {
                case afterState: {
                    learnEvaluationAfterState(problem,
                            trainer,
                            afterState,
                            nextTurnState, randomChoice,
                            currentAlpha,
                            concurrencyInLayer,
                            computeParallelBestPossibleAction,
                            statisticsBestPossibleActionTimes,
                            statisticsTrainingTimes
                    );
                    break;
                }
                default: {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            }

            // recordamos el nuevo estado del problema luego de aplicar todas
            // las acciones necesarias para avanzar en la solución del problema
            turnInitialState = nextTurnState;
        }
    }

}
