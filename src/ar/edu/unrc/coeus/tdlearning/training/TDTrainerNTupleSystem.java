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
package ar.edu.unrc.coeus.tdlearning.training;

import ar.edu.unrc.coeus.tdlearning.interfaces.IProblemToTrain;
import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.ComplexNTupleComputation;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.elegibilitytrace.EligibilityTraceForNTuple;

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
     * Vector de errores TD para la capa de salida, comparando el turno actual
     * con el siguiente
     */
    protected double error;

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
     * @param lambda                    constante que se encuentra en el
     *                                  intervalo [0,1]
     * @param maxEligibilityTraceLenght
     * @param gamma                     tasa de descuento
     * @param resetEligibilitiTraces    permite resetear las trazas de
     *                                  elegibilidad en caso de movimientos al
     *                                  azar
     * @param nTupleSystem
     */
    public TDTrainerNTupleSystem(
            final NTupleSystem nTupleSystem,
            final int maxEligibilityTraceLenght,
            final double lambda,
            final double gamma,
            final boolean resetEligibilitiTraces) {
        this.nTupleSystem = nTupleSystem;
        this.lambda = lambda;
        this.gamma = gamma;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        if ( lambda != 0 ) {
            eligibilityTrace = new EligibilityTraceForNTuple(
                    nTupleSystem, gamma, lambda,
                    maxEligibilityTraceLenght
            );
        }
    }

    @Override
    public void reset() {
        if ( lambda != 0 ) {
            eligibilityTrace.reset();
        }
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
    public void train(
            final IProblemToTrain problem,
            final IState state,
            final IState nextTurnState,
            final double[] alpha,
            final boolean[] concurrencyInLayer,
            final boolean isARandomMove
    ) {

        //computamos
        ComplexNTupleComputation normalizedStateOutput = nTupleSystem.getComplexComputation((IStateNTuple) state);

        double output = normalizedStateOutput.getOutput();
        double derivatedOutput = normalizedStateOutput.getDerivatedOutput();
        double nextTurnOutput = nTupleSystem.getComputation((IStateNTuple) nextTurnState);
        double nextTurnStateBoardReward = problem.normalizeValueToPerceptronOutput(nextTurnState.getStateReward(0));

        //calculamos el TDerror
        if ( !nextTurnState.isTerminalState() ) {
            //falta la multiplicacion por la neurona de entrada, pero al ser 1 se ignora
            error = alpha[0] * (nextTurnStateBoardReward + gamma * nextTurnOutput - output) * derivatedOutput;
        } else {
            //falta la multiplicacion por la neurona de entrada, pero al ser 1 se ignora
            double finalReward = problem.normalizeValueToPerceptronOutput(problem.getFinalReward(nextTurnState, 0));
            error = alpha[0] * (gamma * finalReward - output) * derivatedOutput;
        }

        boolean needToReset = isARandomMove && resetEligibilitiTraces;

        int weightIndex;
        for ( int index = 0; index < normalizedStateOutput.getIndexes().length; index++ ) {
            weightIndex = normalizedStateOutput.getIndexes()[index];
            if ( (!isARandomMove || nextTurnState.isTerminalState()) && error != 0 ) {
                nTupleSystem.addCorrectionToWeight(weightIndex, error);
            }
            if ( lambda != 0 ) {
                if ( needToReset ) {
                    eligibilityTrace.reset(weightIndex);
                } else {
                    eligibilityTrace.updateTrace(weightIndex, derivatedOutput);
                }
            }
        }
        if ( lambda != 0 ) {
            this.eligibilityTrace.processNotUsedTraces(error);
        }
    }

}
