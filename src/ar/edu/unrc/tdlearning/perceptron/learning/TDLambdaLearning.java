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
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblemRunner;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblemToTrain;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import static ar.edu.unrc.tdlearning.perceptron.learning.EExplorationRateAlgorithms.linear;
import static ar.edu.unrc.tdlearning.perceptron.learning.ELearningRateAdaptation.annealing;
import static ar.edu.unrc.tdlearning.perceptron.learning.ELearningRateAdaptation.fixed;
import static ar.edu.unrc.tdlearning.perceptron.learning.ENeuralNetworkType.ntuple;
import static ar.edu.unrc.tdlearning.perceptron.learning.ENeuralNetworkType.perceptron;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.training.ITrainer;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainerNTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainerPerceptron;
import static java.lang.Math.random;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Este método de aprendizaje de TD lambda learning necesita Trazas de
 * elegibilidad como método de asigancion de crédito temporal. Utiliza
 * Perceptrones para recordar los patrones aprendidos durante la solucion del
 * problema
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDLambdaLearning {

    /**
     * If we keep initialAlpha fixed, however, these same fluctuations prevent
     * the network from ever properly converging to the minimum - instead we end
     * up randomly dancing around it. In order to actually reach the minimum,
     * and stay there, we must anneal (gradually lower) the global learning
     * rate. A simple, non-adaptive annealing schedule for this purpose is the
     * search-then-converge schedule. Its name derives from the fact that it
     * keeps initialAlpha nearly constant for the first T training patterns,
     * allowing the network to find the general location of the minimum, before
     * annealing it at a (very slow) pace that is known from theory to guarantee
     * convergence to the minimum. The characteristic time T of this schedule is
     * a new free parameter that must be determined by trial and error.
     * <p>
     * @param initialAlpha initial learning rate
     * @param t            current T in time
     * @param T            the characteristic time T of this schedule is a new
     *                     free parameter that must be determined by trial and
     *                     error. It specifi when the initialAlpha will become
     *                     initialAlpha/2
     * <p>
     * @return
     */
    public static double annealingLearningRate(double initialAlpha, int t, int T) {
        if ( initialAlpha > 1 || initialAlpha < 0 ) {
            throw new IllegalArgumentException("initialAlpha=" + initialAlpha + " is out of range from a valid [0..1]");
        }
        return initialAlpha / (1d + ((t * 1d) / (T * 1d)));
    }

    /**
     *
     * @param lambda <p>
     * @return
     */
    public static Integer calculateBestEligibilityTraceLenght(Double lambda) {
        if ( lambda > 1 || lambda < 0 ) {
            throw new IllegalArgumentException("lambda=" + lambda + " is out of range from a valid [0..1]");
        } else if ( lambda > 0.99 ) {
            return Integer.MAX_VALUE;
        } else if ( lambda == 0 ) {
            return 0;
        }
        int lenght = 0;
        while ( true ) {
            double pow = Math.pow(lambda, lenght);
            double threshold = 0.001 * lambda;
            if ( pow < threshold ) {
                return lenght;
            }
            lenght++;
            if ( lenght >= 500 ) {
                return Integer.MAX_VALUE;
            }
        }
    }

    //TODO REESCRIBIRRRR ayuda
    /**
     * Computa la mejor accion posible dado un estado inicial. No invocar sobre
     * estados finales o estados que no tiene posibles acciones a realizar.
     * {@code tempTurnInitialState} y todas las posibles acciones a tomar
     * {@code possibleActions}
     * <p>
     * @param problem
     * @param learningStyle
     * @param turnInitialState                       estado inicial
     * <p>
     * <p>
     * @param allPossibleActionsFromTurnInitialState <p>
     * @param player                                 jugador actual
     * <p>
     * @param computeParallelBestPossibleAction
     * @param bestPossibleActionTimes
     *
     * @return la mejor accion de todas
     */
    public static IAction computeBestPossibleAction(
            final IProblemRunner problem,
            final ELearningStyle learningStyle,
            final IState turnInitialState,
            final List<IAction> allPossibleActionsFromTurnInitialState,
            final IActor player,
            final boolean computeParallelBestPossibleAction,
            final LinkedList<Long> bestPossibleActionTimes) {
        Stream<IAction> stream;
        long time = 0;
        if ( bestPossibleActionTimes != null ) {
            time = System.currentTimeMillis();
        }
        if ( computeParallelBestPossibleAction ) {
            stream = allPossibleActionsFromTurnInitialState.parallelStream();
        } else {
            stream = allPossibleActionsFromTurnInitialState.stream();
        }
        List<ActionPrediction> bestActiones
                = stream
                .map(possibleAction -> {
                    switch ( learningStyle ) {
                        case afterState: {
                            return evaluateAfterstate(problem, turnInitialState, possibleAction, player);
                        }
                        default: {
                            throw new UnsupportedOperationException("Not supported yet.");
                        }
                    }
                })
                .collect(MaximalListConsumer::new, MaximalListConsumer::accept, MaximalListConsumer::combine)
                .getList();
        IAction bestAction = bestActiones.get(randomBetween(0, bestActiones.size() - 1)).getAction();
        if ( bestPossibleActionTimes != null ) {
            time = System.currentTimeMillis() - time;
            bestPossibleActionTimes.add(time);
        }
        return bestAction;
    }

    //TODO REESCRIBIRRRR ayuda
    /**
     * Calcula la prediccion del resultado final del juego al aplicar la accion
     * {@code action} en el estado {@code turnInitialState}, utilizando un
     * metodo entre varios dependiendo de la implementacion de IProblemToTrain.
     * Por ejemplo si utilizas una red neuronal esta funcion deberia devolver la
     * salida de la misma. Se debe asegurar que la ejecucion en paralelo de este
     * metodo no cause efectos colaterales (deb ser safethread implementando
     * {@code IsolatedComputation}).
     * <p>
     * @param problem          a resolver
     * @param turnInitialState estado del problema al comienzo del turno
     * @param action           accion a tomar
     * <p>
     * <p>
     * @param player           jugador actual
     * <p>
     * @return Tupla que contiene 2 elementos: la accion {@code action}, y la
     *         prediccion del valor final del juego si aplico {@code action} al
     *         estado {@code turnInitialState}
     */
    public static ActionPrediction evaluateAfterstate(
            final IProblemRunner problem,
            final IState turnInitialState,
            final IAction action,
            final IActor player
    ) {
        IState afterstate = problem.computeAfterState(turnInitialState, action);
        Object[] output = problem.evaluateBoardWithPerceptron(afterstate);
        for ( int i = 0; i < output.length; i++ ) {
            output[i] = problem.denormalizeValueFromPerceptronOutput(output[i]) + afterstate.getStateReward(i);
        }
        return new ActionPrediction(action, problem.computeNumericRepresentationFor(output, player));
    }

    //TODO REESCRIBIRRRR ayuda
    /**
     * Metodo que implementa el entrenamiento de una red mediante la experiencia
     * de la solucion de un paso del problema.
     * <p>
     * @param problem                           a resolver
     * @param trainer
     * @param turnInitialState                  estado del problema al comienzo
     *                                          del turno
     * @param action                            accion a tomar
     * @param afterstate                        estado de transicion de aplicar
     *                                          la accion {@code action} al
     *                                          estado {@code turnInitialState}
     * @param nextTurnState                     estado del problema en el
     *                                          proximo turno, luego de aplicar
     *                                          las acciones/efectos no
     *                                          deterministicos a
     * <p>
     * @param isARandomMove                     <p>
     * @param currentAlpha
     * @param concurrencyInLayer
     * @param computeParallelBestPossibleAction
     * @param bestPossibleActionTimes
     * @param trainingTimes
     *
     * @currentState
     */
    public static void learnEvaluationAfterstate(
            final IProblemToTrain problem,
            final ITrainer trainer,
            final IState turnInitialState,
            final IAction action,
            final IState afterstate,
            final IState nextTurnState,
            final boolean isARandomMove,
            final double[] currentAlpha,
            final boolean[] concurrencyInLayer,
            final boolean computeParallelBestPossibleAction,
            final LinkedList<Long> bestPossibleActionTimes,
            final LinkedList<Long> trainingTimes) {
        long time = 0;
        if ( !nextTurnState.isTerminalState() ) {
            // evaluamos cada accion posible aplicada al estado nextState y elegimos la mejor
            // accion basada las predicciones del problema
            List<IAction> possibleActionsNextTurn = problem.listAllPossibleActions(nextTurnState);
            IAction bestActionForNextTurn = computeBestPossibleAction(
                    problem,
                    ELearningStyle.afterState,
                    nextTurnState,
                    possibleActionsNextTurn,
                    problem.getActorToTrain(),
                    computeParallelBestPossibleAction,
                    bestPossibleActionTimes);
            // aplicamos la accion 'bestActionForNextTurn' al estado (turno) siguiente 'nextState',
            // y obtenemos el estado de transicion (deterministico) del proximo estado (turno).
            IState afterStateNextTurn = problem.computeAfterState(nextTurnState, bestActionForNextTurn);
            //V (s') ← V (s') + α(rnext + V (s'next) − V (s'))      -> matematica sin trazas de elegibilidad
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis();
            }
            trainer.train(problem, afterstate, afterStateNextTurn, currentAlpha, concurrencyInLayer, isARandomMove);
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis() - time;
                trainingTimes.add(time);
            }
        } else {
            // Si nextTurnState es un estado final, no podemos calcular el bestActionForNextTurn.
            // Teoricamente la evaluacion obtenida por el perceptronInterface en el ultimo afterstate,
            // deberia ser el resultado final real del juego, por lo tanto entrenamos el ultimo
            // afterstate para que prediga el final del problema
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis();
            }
            trainer.train(problem, afterstate, nextTurnState, currentAlpha, concurrencyInLayer, isARandomMove);
            if ( trainingTimes != null ) {
                time = System.currentTimeMillis() - time;
                trainingTimes.add(time);
            }
        }
    }

    /**
     * calcula un numero al azar entre los limites dados, inclusive estos.
     * <p>
     * @param a numero desde
     * @param b numero hasta
     * <p>
     * @return aleatorio entre a y b
     * <p>
     */
    public static int randomBetween(final int a, final int b) {
        if ( a > b ) {
            throw new IllegalArgumentException("error: b debe ser mayor que a");
        } else if ( a == b ) {
            return a;
        } else {
            return a + (int) ((b - a + 1d) * random());
        }
    }

    private boolean computeParallelBestPossibleAction = false;
    /**
     * tasa de aprendizaje actual para cada capa de pesos
     */
    private double[] currentAlpha;
    private EExplorationRateAlgorithms explorationRate;
    private double explorationRateFinalValue;
    private int explorationRateFinishDecrementing;
    private double explorationRateInitialValue;
    private int explorationRateStartDecrementing;
    private ELearningStyle learningStyle;
    private NTupleSystem nTupleSystem;
    private final ENeuralNetworkType neuralNetworkType;
    /**
     *
     */
    protected int annealingT;

    /**
     *
     */
    protected LinkedList<Long> bestPossibleActionTimes;

    /**
     *
     */
    protected boolean canCollectStatistics;

    /**
     *
     */
    protected final boolean[] concurrencyInLayer;

    /**
     *
     */
    protected double gamma;
    /**
     * constante de tasa de aprendizaje para cada capa de pesos
     */
    protected double[] initialAlpha;

    /**
     * constante que se encuentra en el intervalo [0,1]
     */
    protected double lambda;

    /**
     *
     */
    protected ELearningRateAdaptation learningRateAdaptation;

    /**
     *
     */
    protected final IPerceptronInterface perceptronInterface;

    /**
     *
     */
    protected boolean resetEligibilitiTraces;

    /**
     *
     */
    protected ITrainer trainer;

    /**
     *
     */
    protected LinkedList<Long> trainingTimes;

    /**
     *
     * @param learningStyle
     * @param perceptronInterface    implementacion de la interfaz entre nuestra
     *                               red neuronal y el perceptronInterface que
     *                               utilizara el problema. Este puede estar
     *                               implementado con cualquier libreria o
     *                               codigo.
     * @param lambda                 constante que se encuentra en el intervalo
     *                               [0,1]
     * @param alpha                  Constantes de tasa de aprendizaje para cada
     *                               capa. Si es null, se inicializa cada
     *                               initialAlpha con la formula 1/num_neuronas
     *                               de la capa anterior.
     * @param gamma                  tasa de descuento
     * @param concurrencyInLayer
     * @param resetEligibilitiTraces permite resetear las trazas de elegibilidad
     *                               en caso de movimientos al azar
     * @param collectStatistics      guarda estadisticas relevante a los tiempos
     *                               de cálculo
     */
    public TDLambdaLearning(
            final IPerceptronInterface perceptronInterface,
            final ELearningStyle learningStyle,
            final double[] alpha,
            final double lambda,
            final double gamma,
            final boolean[] concurrencyInLayer,
            final boolean resetEligibilitiTraces,
            final boolean collectStatistics) {
        if ( perceptronInterface == null ) {
            throw new IllegalArgumentException("perceptronInterface can't be null");
        }
        if ( concurrencyInLayer == null ) {
            throw new IllegalArgumentException("concurrencyInLayer can't be null");
        }
        if ( learningStyle == null ) {
            throw new IllegalArgumentException("learningStyle can't be null");
        } else if ( learningStyle == ELearningStyle.state ) {
            throw new IllegalArgumentException("El estilo de entrenamiento por estado no esta implementado, utilice el metodo after state");
        }

        this.learningStyle = learningStyle;

        if ( alpha == null ) {
            initialAlpha = new double[perceptronInterface.getLayerQuantity()];
            for ( int i = 0; i < perceptronInterface.getLayerQuantity(); i++ ) {
                if ( perceptronInterface.getNeuronQuantityInLayer(i) <= 0 ) {
                    throw new IllegalArgumentException("la capa " + i + " debe tener 1 o mas neuronas");
                }
                initialAlpha[i] = 1d / perceptronInterface.getNeuronQuantityInLayer(i);
            }
        } else {
            if ( alpha.length != perceptronInterface.getLayerQuantity() ) {
                throw new IllegalArgumentException("alpha.length debe ser igual a perceptronInterface.getLayerQuantity() = " + (perceptronInterface.getLayerQuantity() - 1) + " y es = " + alpha.length);
            }
            this.initialAlpha = alpha;
        }

        if ( perceptronInterface.getLayerQuantity() != concurrencyInLayer.length || perceptronInterface.getLayerQuantity() != initialAlpha.length ) {
            throw new IllegalArgumentException("alpha.lenght, concurrencyInLayer.lenght and perceptronInterface.getLayerQuantity() must be the same");
        }

        this.currentAlpha = new double[perceptronInterface.getLayerQuantity()];
        System.arraycopy(initialAlpha, 0, currentAlpha, 0, initialAlpha.length);
        this.lambda = lambda;
        this.gamma = gamma;
        this.neuralNetworkType = ENeuralNetworkType.perceptron;
        this.perceptronInterface = perceptronInterface;
        this.nTupleSystem = null;
        this.concurrencyInLayer = concurrencyInLayer;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        this.canCollectStatistics = collectStatistics;

        if ( collectStatistics ) {
            this.bestPossibleActionTimes = new LinkedList<>();
            this.trainingTimes = new LinkedList<>();
        }
    }

    /**
     *
     * @param learningStyle
     * @param nTupleSystem
     * @param alpha
     * @param lambda
     * @param gamma
     * @param concurrencyInLayer
     * @param resetEligibilitiTraces
     * @param collectStatistics
     */
    public TDLambdaLearning(
            final NTupleSystem nTupleSystem,
            final ELearningStyle learningStyle,
            final Double alpha,
            final double lambda,
            final double gamma,
            final boolean[] concurrencyInLayer,
            final boolean resetEligibilitiTraces,
            final boolean collectStatistics) {
        if ( nTupleSystem == null ) {
            throw new IllegalArgumentException("nTupleSystem can't be null");
        }

        if ( concurrencyInLayer == null ) {
            throw new IllegalArgumentException("concurrencyInLayer can't be null");
        }

        if ( learningStyle == null ) {
            throw new IllegalArgumentException("learningStyle can't be null");
        } else if ( learningStyle == ELearningStyle.state ) {
            throw new IllegalArgumentException("El estilo de entrenamiento por estado no esta implementado, utilice el metodo after state");
        }

        this.learningStyle = learningStyle;

        if ( alpha == null ) {
            initialAlpha = new double[2];
            initialAlpha[0] = 1d / nTupleSystem.getnTuplesLenght().length;
            initialAlpha[1] = 1; //No se usa
        } else {
            initialAlpha = new double[2];
            initialAlpha[0] = alpha;
            initialAlpha[1] = alpha; //No se usa
        }

        if ( 2 != concurrencyInLayer.length || 2 != initialAlpha.length ) {
            throw new IllegalArgumentException("alpha.lenght, concurrencyInLayer.lenght and perceptronInterface.getLayerQuantity() must be the same");
        }

//        if ( this.initialAlpha.length + 1 != concurrencyInLayer.length ) {
//            throw new IllegalArgumentException("alpha.lenght+1 and concurrencyInLayer.lenght must be the same");
//        }
        this.currentAlpha = new double[2];
        System.arraycopy(initialAlpha, 0, currentAlpha, 0, initialAlpha.length);
        this.lambda = lambda;
        this.gamma = gamma;
        this.neuralNetworkType = ENeuralNetworkType.ntuple;
        this.perceptronInterface = null;
        this.nTupleSystem = nTupleSystem;
        this.concurrencyInLayer = concurrencyInLayer;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        this.canCollectStatistics = collectStatistics;

        if ( collectStatistics ) {
            this.bestPossibleActionTimes = new LinkedList<>();
            this.trainingTimes = new LinkedList<>();
        }
    }

    /**
     *
     * @return
     */
    public boolean canCollectStatistics() {
        return canCollectStatistics;
    }

    /**
     * @return the annealingT
     */
    public int getAnnealingT() {
        return annealingT;
    }

    /**
     *
     * @return
     */
    public LinkedList<Long> getBestPossibleActionTimes() {
        return bestPossibleActionTimes;
    }

    /**
     * @return the currentAlpha
     */
    public double[] getCurrentAlpha() {
        return currentAlpha;
    }

    /**
     *
     * @param value
     */
    public void setExplorationRateToFixed(final double value) {
        if ( value < 0 || value > 1 ) {
            throw new IllegalArgumentException("value debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.fixed;
        this.explorationRateInitialValue = value;
    }

    /**
     *
     * @param annealingT
     */
    public void setLearningRateAdaptationToAnnealing(final int annealingT) {
        if ( annealingT < 0 ) {
            throw new IllegalArgumentException("annealingT debe ser un valor mayor a 0");
        }
        this.learningRateAdaptation = ELearningRateAdaptation.annealing;
        this.annealingT = annealingT;
    }

    /**
     *
     * @return
     */
    public LinkedList<Long> getTrainingTimes() {
        return trainingTimes;
    }

    /**
     * @return the computeParallelBestPossibleAction
     */
    public boolean isComputeParallelBestPossibleAction() {
        return computeParallelBestPossibleAction;
    }

    /**
     * Asegurarse que la funcion {@code evaluate} este implementada de modo que
     * pueda ser ejecutada concurrentemente.
     * <p>
     * @param computeParallelBestPossibleAction true si las evaluaciones deben
     *                                          ejecutarse en paralelo.
     */
    public void setComputeParallelBestPossibleAction(final boolean computeParallelBestPossibleAction) {
        this.computeParallelBestPossibleAction = computeParallelBestPossibleAction;
    }

    /**
     *
     * @param initialValue
     * @param startDecrementing
     * @param finalValue
     * @param finishDecrementing
     */
    public void setExplorationRate(
            final double initialValue,
            final int startDecrementing,
            final double finalValue,
            final int finishDecrementing) {
        if ( initialValue < 0 || initialValue > 1 ) {
            throw new IllegalArgumentException("initialValue debe estar en el intervalo [0,1]");
        }
        if ( finalValue < 0 || finalValue > 1 ) {
            throw new IllegalArgumentException("finalValue debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.linear;
        this.explorationRateInitialValue = initialValue;
        this.explorationRateStartDecrementing = startDecrementing;
        this.explorationRateFinalValue = finalValue;
        this.explorationRateFinishDecrementing = finishDecrementing;
    }

    /**
     *
     */
    public void setLearningRateAdaptationToFixed() {
        this.learningRateAdaptation = ELearningRateAdaptation.fixed;
        this.annealingT = 0;
    }

    /**
     * Entrena la Inteligencia Artificial con lo que aprende de la experiencia
     * de resolver una sola vez un {@code problem} de comienzo a fin, durante 1
     * o mas turnos hasta alñcanzar su objetivo o fracasar.
     * <p>
     * @param problem problema a resolver
     * <p>
     * @param t       cantiad de veces qeu se ejecuto el metodo
     *                solveAndTrainOnce
     */
    public void solveAndTrainOnce(final IProblemToTrain problem, final int t) {
        if ( learningRateAdaptation == null ) {
            throw new IllegalArgumentException("learningRateAdaptation can't be null");
        }
        if ( explorationRate == null ) {
            throw new IllegalArgumentException("explorationRate can't be null");
        }
        if ( t < 0 ) {
            throw new IllegalArgumentException("t debe ser un valor mayor a 0");
        }
        //inicializamos las constantes de aprendizaje
        switch ( this.learningRateAdaptation ) {
            case fixed: {
                // ignorar las actualizaciones y dejar las alphas sin modificar
                break;
            }
            case annealing: {
                //ajustamos las alphas segun el metodo de annealing µ(t) = µ(0)/(1 + t/T)
                IntStream rangeStream = IntStream.range(0, currentAlpha.length);
                if ( this.currentAlpha.length > 1_000 ) {
                    rangeStream = rangeStream.parallel();
                } else {
                    rangeStream = rangeStream.sequential();
                }
                rangeStream.forEach(index -> {
                    currentAlpha[index] = annealingLearningRate(this.initialAlpha[index], t, annealingT);
                });
                break;
            }
        }

        double currentExplorationRate = 0;
        //inicializamos el factor de exploracion
        switch ( explorationRate ) {
            case fixed: {
                // factor constante
                currentExplorationRate = explorationRateInitialValue;
                break;
            }
            case linear: {
                //factor ajustado linealmente entre dos puntos
                if ( t < explorationRateStartDecrementing ) {
                    currentExplorationRate = explorationRateInitialValue;
                } else if ( t > explorationRateFinishDecrementing ) {
                    currentExplorationRate = explorationRateFinalValue;
                } else {
                    currentExplorationRate
                            = ((t - explorationRateStartDecrementing) / (explorationRateFinishDecrementing - explorationRateStartDecrementing))
                            * (explorationRateFinalValue - explorationRateInitialValue)
                            + explorationRateInitialValue;
                }
                break;
            }
        }
        assert currentExplorationRate >= 0 && currentExplorationRate <= 1;

        //inicializamos el problema y las variables del entrenador, o reutilizamos el ultimo Trainer para reciclar sus variables
        if ( trainer == null ) {
            switch ( neuralNetworkType ) {
                case perceptron: {
                    trainer = new TDTrainerPerceptron(perceptronInterface, lambda, gamma, resetEligibilitiTraces);
                    break;
                }
                case ntuple: {
                    trainer = new TDTrainerNTupleSystem(nTupleSystem, calculateBestEligibilityTraceLenght(lambda), lambda, gamma, resetEligibilitiTraces);
                    break;
                }
            }
        } else {
            trainer.reset();
        }

        IState turnInitialState = problem.initialize(problem.getActorToTrain());
        boolean randomChoise = false;

        while ( !turnInitialState.isTerminalState() ) {

            // calculamos todas las acciones posibles para el estado inicial
            IAction bestAction;

            if ( currentExplorationRate > 0 ) {
                randomChoise = Math.random() <= currentExplorationRate;
            }

            List<IAction> possibleActions = problem.listAllPossibleActions(turnInitialState);
            if ( !randomChoise ) {
                // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
                // accion basada en las predicciones del problema
                bestAction = computeBestPossibleAction(problem,
                        learningStyle,
                        turnInitialState,
                        possibleActions,
                        problem.getActorToTrain(),
                        computeParallelBestPossibleAction,
                        bestPossibleActionTimes);
            } else {
                bestAction = possibleActions.get(randomBetween(0, possibleActions.size() - 1));
            }

            // aplicamos la accion 'bestAction' al estado actual 'currentState',
            // y obtenemos su estado de transicion deterministico.
            IState afterState = problem.computeAfterState(turnInitialState, bestAction);

            // hacemos que el problema aplique la accion 'bestAction' de la IA,
            // y retorne el estado del turno siguiente, luego de aplicar acciones
            // no deterministicas pertinentes para terminar el turno
            IState nextTurnState = problem.computeNextTurnStateFromAfterstate(afterState);

            // hacemos efectivo los cambios realizados por la IA en la logica del problema
            problem.setCurrentState(nextTurnState);

            // entrenamos el problema para que recuerde las predicciones de t+1
            // (que en este caso es nextState o el afterstate dependiendo de las
            // implementaciones de esta clase abstracta). Si es un movimiento al azar se actualizan trazas pero no se actualizan pesos
            switch ( learningStyle ) {
                case afterState: {
                    learnEvaluationAfterstate(
                            problem,
                            trainer,
                            turnInitialState,
                            bestAction,
                            afterState,
                            nextTurnState,
                            randomChoise,
                            currentAlpha,
                            concurrencyInLayer,
                            computeParallelBestPossibleAction,
                            bestPossibleActionTimes,
                            trainingTimes
                    );
                    break;
                }
                default: {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            }

            // recordamos el nuevo estado del problema luago de aplicar todas
            // las acciones necesarias para avanzar en la solucion del problema
            turnInitialState = nextTurnState;
        }
    }

}
