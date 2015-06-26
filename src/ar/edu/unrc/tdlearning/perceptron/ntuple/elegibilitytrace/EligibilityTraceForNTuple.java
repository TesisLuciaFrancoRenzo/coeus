/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace;

import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author franco
 */
public class EligibilityTraceForNTuple {

    private final ValueUsagePair[] eligibilityTrace;
    private final double gamma;
    private final double lambda;
    private final int maxEligibilityTraceLenght;
    private final NTupleSystem nTupleSystem;
    private final Set<Integer> usedTraces;

    /**
     *
     * @param nTupleSystem
     * @param gamma
     * @param lambda
     * @param maxEligibilityTraceLenght
     */
    public EligibilityTraceForNTuple(NTupleSystem nTupleSystem, double gamma, double lambda, int maxEligibilityTraceLenght) {
        this.nTupleSystem = nTupleSystem;
        eligibilityTrace = new ValueUsagePair[nTupleSystem.getLut().length];
        for ( int i = 0; i < eligibilityTrace.length; i++ ) {
            eligibilityTrace[i] = new ValueUsagePair();
        }
        usedTraces = new HashSet<>(nTupleSystem.getnTuplesLenght().length);
        this.maxEligibilityTraceLenght = maxEligibilityTraceLenght;
        this.gamma = gamma;
        this.lambda = lambda;
    }

    /**
     *
     * @param tDError
     */
    public void processNotUsedTraces(double tDError) {
        if ( this.lambda > 0 ) {
            Iterator<Integer> it = usedTraces.iterator();
            while ( it.hasNext() ) {
                Integer traceIndex = it.next();
                ValueUsagePair trace = eligibilityTrace[traceIndex];
                trace.use();
                if ( trace.getUsagesLeft() <= 0 ) {
                    it.remove();
                    trace.reset();
                } else {
                    if ( trace.getUsagesLeft() != maxEligibilityTraceLenght ) {
                        trace.setValue(trace.getValue() * lambda * gamma);//reutilizamos las viejas trazas, ajustandola al tiempo actual
                        if ( tDError != 0 ) {
                            nTupleSystem.addCorrectionToWeight(traceIndex, tDError * trace.getValue()); //falta la multiplicacion por la salida de la neurona de entrada, pero al ser 1 se ignora
                        }
                    }
                }
            }
        }
    }
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        EligibilityTraceForNTuple e = new EligibilityTraceForNTuple(5, 10, 1, 0.7, 3, false, false);
//        //turno 1
//        e.compute(1, 0.5, 0.1, false);
//        e.compute(4, 0.2, 0.2, false);
//        e.processNotUsedTraces();
//        //turno 2
//        e.compute(1, 0.5, 0.1, false);
//        e.processNotUsedTraces();
//        //turno 3
//        e.compute(4, 0.5, 0.1, false);
//        e.compute(2, 0.2, 0.2, false);
//        e.processNotUsedTraces();
//        //turno 4
//        e.processNotUsedTraces();
//        e.processNotUsedTraces();
//        e.processNotUsedTraces();
//        e.processNotUsedTraces();
//        e.processNotUsedTraces();
//    }

    /**
     *
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
     *
     * @param weightIndex
     */
    public void reset(int weightIndex) {
        eligibilityTrace[weightIndex].reset();
        usedTraces.remove(weightIndex);
    }

    /**
     *
     * @param weightIndex
     * @param derivatedOutput
     */
    public synchronized void updateTrace(int weightIndex, double derivatedOutput) {
        ValueUsagePair trace = eligibilityTrace[weightIndex];
        trace.setValue(derivatedOutput); //falta la multiplicacion por la neurona de entrada
        //TODO hacer el otro metodo de calcular trazas, como en perceptrones
        trace.setUsagesLeft(maxEligibilityTraceLenght + 1);
        usedTraces.add(weightIndex);
    }

}
