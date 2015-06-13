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
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;

/**
 * Esta clase implementa TD lambda learning (lambda = trazas de elegibilidad),
 * con la tecnica Afterstate, la cual hace que el perceptronInterface recuerde
 * los estados intermedios de los turnos (la parte deterministica de las
 * acciones) para hacer las predicciones
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDLambdaLearningAfterstate extends TDLambdaLearning {

    /**
     *
     * @param perceptron               implementacion de la interfaz entre
     *                                 nuestra red neuronal y el
     *                                 perceptronInterface que utilizara el
     *                                 problema. Este puede estar implementado
     *                                 con cualquier libreria o codigo.
     * @param lamdba                   constante que se encuentra en el
     *                                 intervalo [0,1]
     * @param alpha                    constante de tasa de aprendizaje
     * @param accumulativePredicition  true si se esta utilizando el metodo
     *                                 acumulativo de prediccion en TDLearning
     * @param gamma                    tasa de descuento
     * @param resetEligibilitiTraces   permite resetear las trazas de
     *                                 elegibilidad en caso de movimientos al
     *                                 azar
     * @param replaceEligibilitiTraces permite reemplazar las trazas en caso de
     *                                 que el peso sea 0, para que cada vez
     *                                 tenga menos influencia en lso calculos
     */
    public TDLambdaLearningAfterstate(IPerceptronInterface perceptron, double[] alpha, double lamdba, boolean accumulativePredicition, double gamma, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces) {
        super(perceptron, alpha, lamdba, accumulativePredicition, gamma, resetEligibilitiTraces, replaceEligibilitiTraces);
    }

    /**
     *
     * @param perceptron
     * @param alpha
     * @param lamdba
     * @param accumulativePredicition
     * @param gamma
     * @param resetEligibilitiTraces
     * @param replaceEligibilitiTraces
     */
    public TDLambdaLearningAfterstate(NTupleSystem perceptron, Double alpha, double lamdba, boolean accumulativePredicition, double gamma, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces) {
        super(perceptron, alpha, lamdba, accumulativePredicition, gamma, resetEligibilitiTraces, replaceEligibilitiTraces);
    }

    @Override
    protected IsolatedComputation<ActionPrediction> evaluate(IProblem problem, IState turnInitialState, IAction action) {
        return () -> {
            IState afterstate = problem.computeAfterState(turnInitialState, action);
            Double[] output = problem.evaluateBoardWithPerceptron(afterstate).compute();
            for ( int i = 0; i < output.length; i++ ) {
                output[i] = problem.denormalizeValueFromPerceptronOutput(output[i]);
                //if ( this.lamdba == 0 ) {
                output[i] += afterstate.getStateReward(i);
                //}
            }
            return new ActionPrediction(action, output, problem.computeNumericRepresentationFor(output).compute());
        };
    }

    @Override
    protected void learnEvaluation(IProblem problem, IState turnInitialState, IAction action, IState afterstate, IState nextTurnState, boolean isARandomMove) {
        if ( !nextTurnState.isTerminalState() ) {
            // evaluamos cada accion posible aplicada al estado nextState y elegimos la mejor
            // accion basada las predicciones del problema
            IAction bestActionForNextTurn = computeBestPossibleAction(problem, nextTurnState).compute();
            // aplicamos la accion 'bestActionForNextTurn' al estado (turno) siguiente 'nextState',
            // y obtenemos el estado de transicion (deterministico) del proximo estado (turno).
            IState afterStateNextTurn = problem.computeAfterState(nextTurnState, bestActionForNextTurn);
            //V (s') ← V (s') + α(rnext + V (s'next) − V (s'))      -> matematica sin trazas de elegibilidad
            trainer.train(problem, afterstate, afterStateNextTurn, getCurrentAlpha(), lamdba, isARandomMove, gamma, resetEligibilitiTraces, replaceEligibilitiTraces);
        } else {
            // Si nextTurnState es un estado final, no podemos calcular el bestActionForNextTurn.
            // Teoricamente la evaluacion obtenida por el perceptronInterface en el ultimo afterstate,
            // deberia ser el resultado final real del juego, por lo tanto entrenamos el ultimo
            // afterstate para que prediga el final del problema
            //TODO verificar que este correctamente y concuerde con la teoria http://www.bkgm.com/articles/tesauro/tdl.html#h1:temporal_difference_learning
            trainer.train(problem, afterstate, nextTurnState, getCurrentAlpha(), lamdba, isARandomMove, gamma, resetEligibilitiTraces, replaceEligibilitiTraces); //TODO revisar aca, puede estar el error
        }
    }

}
