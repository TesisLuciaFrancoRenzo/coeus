/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import ar.edu.unrc.tdlearning.perceptron.ntuple.ComplexNTupleComputation;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainerNTupleSystem implements ITrainer {

    /**
     */
    private double[] elegibilityTraces; //TODO optimizar, no inicializar si no se usa
    /**
     *
     */
    private double[] momentumCache;
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
     *
     * @param nTupleSystem
     */
    public TDTrainerNTupleSystem(NTupleSystem nTupleSystem) {
        this.nTupleSystem = nTupleSystem;
        currentTurn = 1;
    }

    @Override
    public void createEligibilityCache() {
        elegibilityTraces = new double[nTupleSystem.getLut().length];
    }

    @Override
    public void createMomentumCache() {
        momentumCache = new double[nTupleSystem.getLut().length];
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
     * @param turnCurrentState         estado del problema en el turno
     *                                 {@code currentTurn}
     * @param nextTurnState            estado del problema en el turno que sigue
     *                                 de {@code currentTurn}
     * @param lamdba                   constante que se encuentra en el
     *                                 intervalo [0,1]
     * @param alpha                    constante de tasa de aprendizaje
     * @param isARandomMove
     * @param gamma                    tasa de descuento
     * @param momentum                 0 <= m < 1
     * @param resetEligibilitiTraces   permite resetear las trazas de
     *                                 elegibilidad en caso de movimientos al
     *                                 azar
     * @param replaceEligibilitiTraces permite reemplazar las trazas en caso de
     *                                 que el peso sea 0, para que cada vez
     *                                 tenga menos influencia en lso calculos
     */
    @Override
    public void train(IState turnCurrentState, IState nextTurnState, double[] alpha, double lamdba, boolean isARandomMove, double gamma, double momentum, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces) {
        this.lambda = lamdba;
        this.gamma = gamma;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        this.replaceEligibilitiTraces = replaceEligibilitiTraces;

        // reciclamos caches
        if ( currentTurn == 1 ) {
            if ( lambda > 0 ) {
                for ( int i = 0; i < this.elegibilityTraces.length; i++ ) {
                    elegibilityTraces[i] = 0;
                }
            }
        }

        //computamos turnCurrentState
        ComplexNTupleComputation turnCurrentStateOutputs = this.nTupleSystem.getComplexComputation((IStateNTuple) turnCurrentState).compute();

        //calculamos el TDerror
        double targetOutput;
        if ( !nextTurnState.isTerminalState() ) {
            targetOutput = gamma * nTupleSystem.getComplexComputation((IStateNTuple) nextTurnState).compute().getOutput();
        } else {
            targetOutput = nextTurnState.translateRewardToNormalizedPerceptronOutput(); //TODO revisar esto! hay que agregar el puntaje actual?????? o el FINAL???
        }
        tDError = targetOutput - turnCurrentStateOutputs.getOutput();

        IntStream
                .range(0, turnCurrentStateOutputs.getIndexes().length)
                //.parallel()
                .forEach(weightIndex -> {
                    int currentWeightIndex = turnCurrentStateOutputs.getIndexes()[weightIndex];
                    double oldWeight = this.nTupleSystem.getLut()[currentWeightIndex];
                    if ( !isARandomMove || nextTurnState.isTerminalState() ) {
                        //calculamos el nuevo valor para el peso o bias, sumando la correccion adecuada a su valor anterior

                        double newDiferential = alpha[0] * tDError
                        * computeEligibilityTrace(currentWeightIndex, oldWeight, turnCurrentStateOutputs.getDerivatedOutput(), isARandomMove);
                        //FIXME problemas en las trazas de eligibilidad que no se calculan? hay que actualizarlas?
                        if ( momentum > 0 ) {
                            newDiferential += momentum * momentumCache[currentWeightIndex];
                        }

                        //actualizamos el peso en la red neuronal original
                        nTupleSystem.setWeight(currentWeightIndex, oldWeight + newDiferential);
                        if ( momentum > 0 ) {
                            momentumCache[currentWeightIndex] = newDiferential;
                        }
                    } else {
                        computeEligibilityTrace(currentWeightIndex, oldWeight, turnCurrentStateOutputs.getDerivatedOutput(), isARandomMove);
                    }
                });

        currentTurn++;
    }

    /**
     * Computo de la traza de elegibilidad para el estado actual
     * <p>
     * @param currentWeightIndex
     * @param currentWeightValue ultimo valor que tenia el peso
     * <p>
     * @param derivatedOutput
     * @param isRandomMove       true si el ultimo movimiento fue elegido al
     *                           azar
     * <p>
     * @return un valor correspondiente a la formula "e" de la teoria
     */
    protected double computeEligibilityTrace(int currentWeightIndex, double currentWeightValue, double derivatedOutput, boolean isRandomMove) {

        if ( this.lambda > 0 ) {
            if ( isRandomMove && resetEligibilitiTraces ) {
                this.elegibilityTraces[currentWeightIndex] = 0d;
                return 0d;
            } else {
                double newEligibilityTrace;
                if ( currentWeightValue == 0 && replaceEligibilitiTraces ) {
                    newEligibilityTrace = 0;
                } else {
                    newEligibilityTrace = elegibilityTraces[currentWeightIndex] * lambda * gamma; //reutilizamos las viejas trazas
                }
                newEligibilityTrace += derivatedOutput;
                elegibilityTraces[currentWeightIndex] = newEligibilityTrace;
                return newEligibilityTrace;
            }
        } else {
            return derivatedOutput;
        }
    }
}
