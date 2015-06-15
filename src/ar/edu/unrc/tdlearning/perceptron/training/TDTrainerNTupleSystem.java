/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import ar.edu.unrc.tdlearning.perceptron.ntuple.ComplexNTupleComputation;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace.EligibilityTraceForNTuple;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainerNTupleSystem implements ITrainer {

    private EligibilityTraceForNTuple eligibilityTrace;

    /**
     * constante de tasa de aprendizaje
     */
    protected double alpha;
    /**
     * turno actual
     */
    protected int currentTurn;

    /**
     *
     */
    protected double gamma;

    /**
     * Constante que se encuentra en el intervalo [0,1]
     */
    protected double lambda;

    /**
     *
     */
    protected final NTupleSystem nTupleSystem;

    /**
     *
     */
    protected double nextTurnOutputs;

    /**
     *
     */
    protected boolean replaceEligibilitiTraces;

    /**
     *
     */
    protected boolean resetEligibilitiTraces;
    /**
     * Vector de errores TD para la capa de salida, comparando el turno actual
     * con el siguiente
     */
    protected double tDError;

    /**
     * @param lambda                    constante que se encuentra en el
     *                                  intervalo [0,1]
     * @param maxEligibilityTraceLenght
     * @param gamma                     tasa de descuento
     * @param resetEligibilitiTraces    permite resetear las trazas de
     *                                  elegibilidad en caso de movimientos al
     *                                  azar
     * @param replaceEligibilitiTraces  permite reemplazar las trazas en caso de
     *                                  que el peso sea 0, para que cada vez
     *                                  tenga menos influencia en lso calculos
     * @param nTupleSystem
     */
    public TDTrainerNTupleSystem(NTupleSystem nTupleSystem, int maxEligibilityTraceLenght, double lambda, double gamma, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces) {
        this.nTupleSystem = nTupleSystem;
        this.lambda = lambda;
        this.gamma = gamma;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        this.replaceEligibilitiTraces = replaceEligibilitiTraces;
        if ( lambda != 0 ) {
            eligibilityTrace = new EligibilityTraceForNTuple(
                    nTupleSystem.getnTuplesLenght().length,
                    nTupleSystem.getLut().length, gamma, lambda,
                    maxEligibilityTraceLenght, resetEligibilitiTraces, replaceEligibilitiTraces
            );
        }
        currentTurn = 1;
    }

    /**
     * @return the currentTurn
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public void reset() {
        currentTurn = 1;
    }

    /**
     * Entrenamos la red neuronal con un turno. Incluye la actualizacion de las
     * bias. Es necesario invocar el metodo {@code train} desde el turno 1, esto
     * significa que si llamamos a este metodo desde el turno 6, primero hay que
     * llamarlo desde el tunro 5, y para llamarlo desde el turno 5, primero hay
     * que invocarlo desde el turno 4, etc.
     * <p>
     * @param state         estado del problema en el turno {@code currentTurn}
     * @param nextTurnState estado del problema en el turno que sigue de
     *                      {@code currentTurn}
     * <p>
     * @param alpha         constante de tasa de aprendizaje
     * @param isARandomMove <p>
     */
    @Override
    public void train(IProblem problem, IState state, IState nextTurnState, double[] alpha, boolean isARandomMove) {

        //computamos
        ComplexNTupleComputation normalizedStateOutput = nTupleSystem.getComplexComputation((IStateNTuple) state).compute();
        Double normalizedNextTurnStateOutput = nTupleSystem.getComputation((IStateNTuple) nextTurnState).compute();

        //FIXME y la derivada de la funcion de activacion?
        double output = normalizedStateOutput.getOutput();
        double derivatedOutput = normalizedStateOutput.getDerivatedOutput();
        double nextTurnOutput = normalizedNextTurnStateOutput;
        double nextTurnStateBoardReward = problem.normalizeValueToPerceptronOutput(nextTurnState.getStateReward(0));

        //calculamos el TDerror
        if ( !nextTurnState.isTerminalState() ) {
            tDError = alpha[0] * (nextTurnStateBoardReward + gamma * nextTurnOutput - output);
        } else {
            double finalReward = problem.normalizeValueToPerceptronOutput(problem.getFinalReward(0));
            tDError = alpha[0] * (finalReward - output);
        }

        if ( tDError != 0 ) {
            IntStream
                    .range(0, normalizedStateOutput.getIndexes().length)
                    //.parallel()
                    .forEach(index -> {
                        int weightIndex = normalizedStateOutput.getIndexes()[index];
                        double oldWeight = this.nTupleSystem.getLut()[weightIndex];
                        double newDiferential;
                        if ( lambda == 0 ) {
                            newDiferential = tDError * derivatedOutput;
                        } else {
                            newDiferential = tDError * eligibilityTrace.compute(weightIndex, oldWeight, derivatedOutput, isARandomMove);
                        }
                        nTupleSystem.setWeight(weightIndex, oldWeight + newDiferential);
                    });
        }
        if ( lambda != 0 ) {
            this.eligibilityTrace.processNotUsedTraces();
        }
        currentTurn++;
    }

}
