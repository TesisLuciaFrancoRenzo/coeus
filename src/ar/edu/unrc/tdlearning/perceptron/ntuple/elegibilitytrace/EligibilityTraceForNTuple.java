/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace;

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
    private final boolean replaceEligibilitiTraces;
    private final boolean resetEligibilitiTraces;
    private final Set<Integer> usedTraces;

    /**
     *
     * @param initialCacheSize
     * @param nTupleSystemSize
     * @param gamma
     * @param lambda
     * @param maxEligibilityTraceLenght
     * @param resetEligibilitiTraces
     * @param replaceEligibilitiTraces
     */
    public EligibilityTraceForNTuple(int initialCacheSize, int nTupleSystemSize, double gamma, double lambda,
            int maxEligibilityTraceLenght, boolean resetEligibilitiTraces, boolean replaceEligibilitiTraces) {
        eligibilityTrace = new ValueUsagePair[nTupleSystemSize];
        for ( int i = 0; i < eligibilityTrace.length; i++ ) {
            eligibilityTrace[i] = new ValueUsagePair();
        }
        usedTraces = new HashSet<>(initialCacheSize);
        this.maxEligibilityTraceLenght = maxEligibilityTraceLenght;
        this.gamma = gamma;
        this.lambda = lambda;
        this.resetEligibilitiTraces = resetEligibilitiTraces;
        this.replaceEligibilitiTraces = replaceEligibilitiTraces;
    }

    public void reset() {
        for ( ValueUsagePair trace : eligibilityTrace ) {
            trace.reset();
        }
        usedTraces.clear();
    }

    public synchronized double compute(int currentWeightIndex, double currentWeightValue, double derivatedOutput, boolean isRandomMove) {
        //TODO revisar lo synchronized si es la mejor estrategia... usa o no usa paralelismo?
        if ( this.lambda > 0 ) {
            ValueUsagePair trace = eligibilityTrace[currentWeightIndex];
            if ( isRandomMove && resetEligibilitiTraces ) {
                trace.reset();
                usedTraces.remove(currentWeightIndex);
                return 0d;
            } else {
                double newEligibilityTrace;
                if ( currentWeightValue == 0 && replaceEligibilitiTraces ) {
                    newEligibilityTrace = 0;
                } else {
                    newEligibilityTrace = trace.getValue() * lambda * gamma; //reutilizamos las viejas trazas
                }
                newEligibilityTrace += derivatedOutput;
                trace.setValue(newEligibilityTrace);
                trace.setUsagesLeft(maxEligibilityTraceLenght + 1);
                usedTraces.add(currentWeightIndex);
                return newEligibilityTrace;
            }
        } else {
            return derivatedOutput;
        }
    }

    public void processNotUsedTraces() {
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
                    }
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EligibilityTraceForNTuple e = new EligibilityTraceForNTuple(5, 10, 1, 0.7, 3, false, false);
        //turno 1
        e.compute(1, 0.5, 0.1, false);
        e.compute(4, 0.2, 0.2, false);
        e.processNotUsedTraces();
        //turno 2
        e.compute(1, 0.5, 0.1, false);
        e.processNotUsedTraces();
        //turno 3
        e.compute(4, 0.5, 0.1, false);
        e.compute(2, 0.2, 0.2, false);
        e.processNotUsedTraces();
        //turno 4
        e.processNotUsedTraces();
        e.processNotUsedTraces();
        e.processNotUsedTraces();
        e.processNotUsedTraces();
        e.processNotUsedTraces();
    }

}
