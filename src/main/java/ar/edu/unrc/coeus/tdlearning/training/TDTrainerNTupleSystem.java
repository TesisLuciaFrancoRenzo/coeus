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
public
class TDTrainerNTupleSystem
        extends Trainer {

    /**
     * traza de elegibilidad.
     */
    private final EligibilityTraceForNTuple eligibilityTrace;
    /**
     * red neuronal a entrenar.
     */
    private final NTupleSystem              nTupleSystem;

    /**
     * Inicializa el algoritmo que entrena sistemas NTuplas.
     *
     * @param lambda                    escala de tiempo del decaimiento exponencial de la traza de elegibilidad, entre [0,1].
     * @param maxEligibilityTraceLength
     * @param gamma                     tasa de descuento, entre [0,1].
     * @param replaceEligibilityTraces  true si se permite reiniciar las trazas de elegibilidad en caso de movimientos al azar durante el
     *                                  entrenamiento.
     * @param nTupleSystem
     */
    public
    TDTrainerNTupleSystem(
            final NTupleSystem nTupleSystem,
            final int maxEligibilityTraceLength,
            final double lambda,
            final double gamma,
            final boolean replaceEligibilityTraces
    ) {
        this.nTupleSystem = nTupleSystem;
        this.lambda = lambda;
        this.gamma = gamma;
        this.replaceEligibilityTraces = replaceEligibilityTraces;
        if (lambda != 0) {
            eligibilityTrace = new EligibilityTraceForNTuple(nTupleSystem, gamma, lambda, maxEligibilityTraceLength);
        } else {
            eligibilityTrace = null;
        }
    }

    @Override
    public
    void reset() {
        if (lambda != 0) {
            eligibilityTrace.reset();
        }
    }

    @Override
    public
    void train(
            final IProblemToTrain problem,
            final IState state,
            final IState nextTurnState,
            final double[] alpha,
            final boolean[] concurrencyInLayer,
            final boolean isARandomMove
    ) {
        //computamos
        final ComplexNTupleComputation normalizedStateOutput    = nTupleSystem.getComplexComputation((IStateNTuple) state);
        final double                   output                   = normalizedStateOutput.getOutput();
        final double                   derivedOutput            = normalizedStateOutput.getDerivedOutput();
        final double                   nextTurnOutput           = nTupleSystem.getComputation((IStateNTuple) nextTurnState);
        final double                   nextTurnStateBoardReward = problem.normalizeValueToPerceptronOutput(nextTurnState.getStateReward(0));

        //calculamos el TDError
        final double partialError;
        final double tdError;
        if (!nextTurnState.isTerminalState()) {
            //falta la multiplicación por la neurona de entrada, pero al ser 1 se ignora
            tdError = nextTurnStateBoardReward + gamma * nextTurnOutput - output;
        } else {
            //falta la multiplicación por la neurona de entrada, pero al ser 1 se ignora
            final double finalReward = problem.normalizeValueToPerceptronOutput(problem.getFinalReward(nextTurnState, 0));
            tdError = gamma * finalReward - output;
        }
        partialError = alpha[0] * tdError;//* (derivedOutput);

        if (lambda > 0 && isARandomMove && replaceEligibilityTraces) {
            eligibilityTrace.reset();
        } else {
            for (int weightIndex = 0; weightIndex < normalizedStateOutput.getIndexes().length; weightIndex++) {
                final int    activeIndex = normalizedStateOutput.getIndexes()[weightIndex];
                final double currentEligibilityTrace;

                if (lambda > 0) {
                    if (isARandomMove && !replaceEligibilityTraces) {
                        currentEligibilityTrace = eligibilityTrace.updateTrace(activeIndex, 0);
                    } else {
                        currentEligibilityTrace = eligibilityTrace.updateTrace(activeIndex, derivedOutput);
                    }
                } else {
                    currentEligibilityTrace = derivedOutput;
                }

                final double finalError = partialError * currentEligibilityTrace;

                if ((!isARandomMove || nextTurnState.isTerminalState()) && finalError != 0d) {
                    nTupleSystem.addCorrectionToWeight(activeIndex, finalError);
                }
            }
            if (lambda > 0) {
                eligibilityTrace.processNotUsedTraces(partialError);
            }
        }
    }

}
