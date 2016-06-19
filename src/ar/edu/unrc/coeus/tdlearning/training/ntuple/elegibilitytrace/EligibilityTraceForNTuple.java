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
package ar.edu.unrc.coeus.tdlearning.training.ntuple.elegibilitytrace;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementación de trazas de elegibilidad para NTuplas.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class EligibilityTraceForNTuple {

    private final ValueUsagePair[] eligibilityTrace;
    private final double gamma;
    private final double lambda;
    private final int maxEligibilityTraceLenght;
    private final NTupleSystem nTupleSystem;
    private final Set<Integer> usedTraces;

    /**
     * Traza de elegibilidad especializada para redes neuronales de tipo NTuplas.
     *
     * @param nTupleSystem              red neuronal utilizada.
     * @param gamma                     tasa de descuento entre [0,1].
     * @param lambda                    escala de tiempo del decaimiento exponencial de la traza de
     *                                  elegibilidad, entre [0,1].
     * @param maxEligibilityTraceLenght longitud máxima de la traza de elegibilidad.
     */
    public EligibilityTraceForNTuple(
            final NTupleSystem nTupleSystem,
            final double gamma,
            final double lambda,
            final int maxEligibilityTraceLenght
    ) {
        this.nTupleSystem = nTupleSystem;
        eligibilityTrace = new ValueUsagePair[nTupleSystem.getLut().length];
        for ( int i = 0; i < eligibilityTrace.length; i++ ) {
            eligibilityTrace[i] = new ValueUsagePair();
        }
        usedTraces = new HashSet<>(nTupleSystem.getNTuplesLenght().length);
        this.maxEligibilityTraceLenght = maxEligibilityTraceLenght;
        this.gamma = gamma;
        this.lambda = lambda;
    }

    /**
     * Ajusta los valores de la traza de elegibilidad en los pesos no actualizados.
     *
     * @param tDError errores calculados en los pesos.
     */
    public void processNotUsedTraces(final double tDError) {
        if ( this.lambda > 0 ) {
            Iterator<Integer> it = usedTraces.iterator();
            while ( it.hasNext() ) {
                Integer traceIndex = it.next();
                ValueUsagePair trace = eligibilityTrace[traceIndex];
                trace.use();
                if ( trace.getUsagesLeft() <= 0 ) {
                    it.remove();
                    trace.reset();
                } else if ( trace.getUsagesLeft() != maxEligibilityTraceLenght ) {
                    trace.setValue(trace.getValue() * lambda * gamma);//reutilizamos las viejas trazas, ajustandola al tiempo actual
                    if ( tDError != 0 ) {
                        nTupleSystem.addCorrectionToWeight(traceIndex,
                                tDError * trace.getValue()); //falta la multiplicacion por la salida de la neurona de entrada, pero al ser 1 se ignora
                    }
                }
            }
        }
    }

    /**
     * Reinicia la traza de elegibilidad completa.
     */
    public void reset() {
        Iterator<Integer> it = usedTraces.iterator();
        while ( it.hasNext() ) {
            Integer traceIndex = it.next();
            eligibilityTrace[traceIndex].reset();
        }
        usedTraces.clear();
    }

    /**
     * Reinicia un elemento de la traza de elegibilidad.
     *
     * @param weightIndex índice del peso de la traza a reiniciar.
     */
    public void reset(final int weightIndex) {
        eligibilityTrace[weightIndex].reset();
        usedTraces.remove(weightIndex);
    }

    /**
     * Actualiza el contenido de la traza de elegibilidad en el índice {@code weightIndex}
     *
     * @param weightIndex     índice del peso a actualizar en la traza.
     * @param derivatedOutput valor de actualización.
     */
    public synchronized void updateTrace(
            final int weightIndex,
            final double derivatedOutput
    ) {
        ValueUsagePair trace = eligibilityTrace[weightIndex];
        trace.setValue(derivatedOutput);
        trace.setUsagesLeft(maxEligibilityTraceLenght + 1);
        usedTraces.add(weightIndex);
    }

}