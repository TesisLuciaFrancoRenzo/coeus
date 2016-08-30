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
 * Algoritmos de entrenamiento para redes neuronales de tipo NTuplas.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainerNTupleSystem extends Trainer {

    /**
     * traza de elegibilidad.
     */
    private EligibilityTraceForNTuple eligibilityTrace;
    /**
     * red neuronal a entrenar.
     */
    private final NTupleSystem nTupleSystem;

    /**
     * Inicializa el algoritmo que entrena sistemas NTuplas.
     *
     * @param lambda                    escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre
     *                                  [0,1].
     * @param maxEligibilityTraceLenght
     * @param gamma                     tasa de descuento, entre [0,1].
     * @param replaceEligibilityTraces  true si se permite reiniciar las trazas de elegibilidad en caso de movimientos
     *                                  al azar durante el entrenamiento.
     * @param nTupleSystem
     */
    public TDTrainerNTupleSystem(
            final NTupleSystem nTupleSystem,
            final int maxEligibilityTraceLenght,
            final double lambda,
            final double gamma,
            final boolean replaceEligibilityTraces
    ) {
        this.nTupleSystem = nTupleSystem;
        this.lambda = lambda;
        this.gamma = gamma;
        this.replaceEligibilityTraces = replaceEligibilityTraces;
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
        ComplexNTupleComputation normalizedStateOutput = nTupleSystem.
                getComplexComputation((IStateNTuple) state);
        double output = normalizedStateOutput.getOutput();
        double derivatedOutput = normalizedStateOutput.getDerivatedOutput();
        double nextTurnOutput = nTupleSystem.getComputation(
                (IStateNTuple) nextTurnState);
        double nextTurnStateBoardReward = problem.
                normalizeValueToPerceptronOutput(nextTurnState.getStateReward(0));

        //calculamos el TDerror
        double error;
        if ( !nextTurnState.isTerminalState() ) {
            //falta la multiplicacion por la neurona de entrada, pero al ser 1 se ignora
            error = alpha[0] * (nextTurnStateBoardReward + gamma * nextTurnOutput - output) * derivatedOutput;
        } else {
            //falta la multiplicacion por la neurona de entrada, pero al ser 1 se ignora
            double finalReward = problem.normalizeValueToPerceptronOutput(
                    problem.getFinalReward(nextTurnState, 0));
            error = alpha[0] * (gamma * finalReward - output) * derivatedOutput;
        }

        boolean needToReset = isARandomMove && replaceEligibilityTraces;

        int weightIndex;
        for ( int index = 0; index < normalizedStateOutput.getIndexes().length;
                index++ ) {
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
