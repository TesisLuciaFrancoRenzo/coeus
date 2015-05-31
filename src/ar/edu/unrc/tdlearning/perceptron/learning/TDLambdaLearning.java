/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.training.ELearningRateAdaptation;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainer;
import static java.lang.Math.random;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Este método de aprendizaje de TD lambda learning necesita Trazas de
 * elegibilidad como método de asigancion de crédito temporal. Utiliza
 * Perceptrones para recordar los patrones aprendido durante la solucion del
 * problema
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract class TDLambdaLearning {

    protected double gamma;

    protected final boolean accumulativePredicition;

    /**
     * constante de tasa de aprendizaje para cada capa de pesos
     */
    protected double[] initialAlpha;

    /**
     * tasa de aprendizaje actual para cada capa de pesos
     */
    protected double[] currentAlpha;
    private final ELearningRateAdaptation learningRateAdaptation;

    /**
     * constante que se encuentra en el intervalo [0,1]
     */
    protected double lamdba;

    /**
     *
     */
    protected final IPerceptronInterface perceptronInterface;

    /**
     *
     */
    protected TDTrainer trainer;
//
//    public static Integer calculateBestEligibilityTraceLenght(Double lambda, Double threshold) {
//        if ( lambda > 1 || lambda < 0 ) {
//            throw new IllegalArgumentException("lambda=" + lambda + " is out of range from a valid [0..1]");
//        } else if ( lambda > 0.99 ) {
//            return Integer.MAX_VALUE;
//        } else if ( lambda == 0 ) {
//            return 0;
//        }
//        Integer lenght = 2;
//        while ( Math.pow(lambda, lenght - 1) > threshold ) {
//            lenght++;
//            if ( lenght == Integer.MAX_VALUE ) {
//                return Integer.MAX_VALUE;
//            }
//        }
//        return lenght;
//    }
//
//    /**
//     *
//     * @param lambda <p>
//     * @return
//     */
//    public static Integer calculateBestEligibilityTraceLenght(Double lambda) {
//        if ( lambda > 1 || lambda < 0 ) {
//            throw new IllegalArgumentException("lambda=" + lambda + " is out of range from a valid [0..1]");
//        } else if ( lambda > 0.99 ) {
//            return Integer.MAX_VALUE;
//        } else if ( lambda == 0 ) {
//            return 0;
//        }
//        int lenght = 0;
//        while ( true ) {
//            double pow = Math.pow(lambda, lenght);
//            double threshold = 0.001 * lambda;
//            if ( pow < threshold ) {
//                return lenght;
//            }
//            lenght++;
//            if ( lenght >= 500 ) {
//                return Integer.MAX_VALUE;
//            }
//        }
//    }

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
     *                     error
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
     * @param perceptronInterface     implementacion de la interfaz entre
     *                                nuestra red neuronal y el
     *                                perceptronInterface que utilizara el
     *                                problema. Este puede estar implementado
     *                                con cualquier libreria o codigo.
     * @param lamdba                  constante que se encuentra en el intervalo
     *                                [0,1]
     * @param learningRateAdaptation  formula uilizada para ir modificando las
     *                                constantes de aprendizaje alpha a travez
     *                                del tiempo
     * @param alpha                   Constantes de tasa de aprendizaje para
     *                                cada capa. Si es null, se inicializa cada
     *                                initialAlpha con la formula 1/num_neuronas
     *                                de la capa anterior.
     * @param accumulativePredicition true si se esta utilizando el metodo
     *                                acumulativo de prediccion en TDLearning
     * @param gamma                   tasa de descuento
     */
    protected TDLambdaLearning(IPerceptronInterface perceptronInterface, double[] alpha, ELearningRateAdaptation learningRateAdaptation, double lamdba, boolean accumulativePredicition, double gamma) {
        if ( perceptronInterface == null ) {
            throw new IllegalArgumentException("perceptronInterface can't be null");
        }
        if ( learningRateAdaptation == null ) {
            throw new IllegalArgumentException("learningRateAdaptation can't be null");
        }
        if ( alpha == null ) {
            alpha = new double[perceptronInterface.getLayerQuantity() - 1];
            for ( int i = 0; i < perceptronInterface.getLayerQuantity() - 1; i++ ) {
                if ( perceptronInterface.getNeuronQuantityInLayer(i) <= 0 ) {
                    throw new IllegalArgumentException("la capa " + i + " debe tener 1 o mas neuronas");
                }
                alpha[i] = 1d / perceptronInterface.getNeuronQuantityInLayer(i);
            }
        } else {
            this.initialAlpha = alpha;
        }
        this.currentAlpha = new double[perceptronInterface.getLayerQuantity() - 1];
        System.arraycopy(initialAlpha, 0, this.currentAlpha, 0, alpha.length);
        this.learningRateAdaptation = learningRateAdaptation;
        this.lamdba = lamdba;
        this.gamma = gamma;
        this.perceptronInterface = perceptronInterface;
        this.accumulativePredicition = accumulativePredicition;
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
    public static int randomBetween(int a, int b) {
        //TODO verificar covertura de todos los valores en un arreglo, si se elige uno de estos elementos
        if ( a > b ) {
            throw new IllegalArgumentException("error: b debe ser mayor que a");
        } else if ( a == b ) {
            return a;
        } else {
            int tirada = a + (int) ((double) (b - a + 1) * random());
            return tirada;
        }
    }

    /**
     * Computa la mejor accion posible dado un estado inicial. No invocar sobre
     * estados finales o estados que no tiene posibles acciones a realizar.
     * {@code tempTurnInitialState} y todas las posibles acciones a tomar
     * {@code possibleActions}
     * <p>
     * @param problem
     * @param tempTurnInitialState estado inicial
     * <p>
     * <p>
     * @return la mejor accion de todas
     */
    public synchronized IAction computeBestPossibleAction(IProblem problem, IState tempTurnInitialState) {
        List<ActionPrediction> bestActiones
                = problem.listAllPossibleActions(tempTurnInitialState)
                .stream() //FIXME trae problemas??? el evaluate trae problema en concurrencia? efectos colaterales?
                .map(possibleAction -> evaluate(problem, tempTurnInitialState, possibleAction))
                .collect(MaximalListConsumer::new, MaximalListConsumer::accept, MaximalListConsumer::combine)
                .getList();
        return bestActiones.get(randomBetween(0, bestActiones.size() - 1)).getAction();
    }

    /**
     * Entrena la Inteligencia Artificial con lo que aprende de la experiencia
     * de resolver una sola vez un {@code problem} de comienzo a fin, durante 1
     * o mas turnos hasta alñcanzar su objetivo o fracasar.
     * <p>
     * @param problem problema a resolver
     * <p>
     * @param t       numero de problemas resueltos hasta ahora
     * @param T       numero de problemas a resolver en total. T debe ser mayor
     *                que cero.
     */
    public void solveAndTrainOnce(IProblem problem, int t, int T) {
        if ( learningRateAdaptation == null ) {
            throw new IllegalArgumentException("learningRateAdaptation can't be null");
        }
        if ( T <= 0 ) {
            throw new IllegalArgumentException("T no puede ser menor o igual a cero");
        }
        if ( t < 0 || t > T ) {
            throw new IllegalArgumentException("t debe ser un valor entre 0 y T");
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
                if ( this.currentAlpha.length > 50 ) {
                    rangeStream = rangeStream.parallel();
                } else {
                    rangeStream = rangeStream.sequential();
                }
                rangeStream.forEach(index -> {
                    currentAlpha[index] = annealingLearningRate(this.initialAlpha[index], t, T);
                });
                break;
            }
        }

        //inicializamos el problema y las variables del entrenador
        trainer = new TDTrainer(perceptronInterface);

        IState turnInitialState = problem.initialize();
        boolean randomChoise = false;

        while ( !turnInitialState.isTerminalState() ) {

            // calculamos todas las acciones posibles para el estado inicial
            IAction bestAction;

            if ( problem.randomMoveProbability() > 0 ) {
                randomChoise = Math.random() <= problem.randomMoveProbability();
            }

            if ( !randomChoise ) {
                // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
                // accion basada en las predicciones del problema
                IState tempTurnInitialState = turnInitialState; //usado para que la variable sea efectivamente final en los calculos lambda
                bestAction = computeBestPossibleAction(problem, tempTurnInitialState);
            } else {
                List<IAction> possibleActions = problem.listAllPossibleActions(turnInitialState);
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
            learnEvaluation(problem, turnInitialState, bestAction, afterState, nextTurnState, randomChoise);

            // recordamos el nuevo estado del problema luago de aplicar todas
            // las acciones necesarias para avanzar en la solucion del problema
            turnInitialState = nextTurnState;
        }
    }

    /**
     * Calcula la predicciondel resultado final del juego al aplicar la accion
     * {@code action} en el estado {@code turnInitialState}, utilizando un
     * metodo entre varios dependiendo de la implementacion de IProblem. Por
     * ejemplo si utilizas una red neuronal esta funcion deberia devolver la
     * salida de la misma. Se debe asegurar que la ejecucion en paralelo de este
     * metodo no cause efectos colaterales (deb ser safethread).
     * <p>
     * @param problem          a resolver
     * @param turnInitialState estado del problema al comienzo del turno
     * @param action           accion a tomar
     * <p>
     * <p>
     * @return Tupla que contiene 2 elementos: la accion {@code action}, y la
     *         prediccion del valor final del juego si aplico {@code action} al
     *         estado {@code turnInitialState}
     */
    protected abstract ActionPrediction evaluate(IProblem problem, IState turnInitialState, IAction action);

    /**
     * Metodo que implementa el entrenamiento de una red mediante la experiencia
     * de la solucion de un paso del problema.
     * <p>
     * @param problem          a resolver
     * @param turnInitialState estado del problema al comienzo del turno
     * @param action           accion a tomar
     * @param afterstate       estado de transicion de aplicar la accion
     *                         {@code action} al estado {@code turnInitialState}
     * @param nextTurnState    estado del problema en el proximo turno, luego de
     *                         aplicar las acciones/efectos no deterministicos a
     * <p>
     * @param isARandomMove    <p>
     * @currentState
     */
    protected abstract void learnEvaluation(IProblem problem, IState turnInitialState, IAction action, IState afterstate, IState nextTurnState, boolean isARandomMove);
}
