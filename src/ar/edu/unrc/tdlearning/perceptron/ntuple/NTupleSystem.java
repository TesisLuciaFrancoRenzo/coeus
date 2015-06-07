/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NTupleSystem {

    public static int calculateIndex(int nTupleIndex, IStateNTuple state, NTupleSystem system) {
        List<SamplePointState> ntuple = state.getNTuple(nTupleIndex);
        int index = 0;
        for ( int j = 0; j < system.mapSamplePointStates.size() - 1; j++ ) {
            index += system.mapSamplePointStates.get(ntuple.get(j)) * Math.pow(system.mapSamplePointStates.size(), j);
        }
        return index;
    }

    private final double[][] lut;
    private final Map<SamplePointState, Integer> mapSamplePointStates;

    public NTupleSystem(List<SamplePointState> allSamplePointStates, int nTupleQuantity) {
        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i < allSamplePointStates.size() - 1; i++ ) {
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }
        lut = new double[nTupleQuantity][];
    }

    public void addNTuple(int position, int size) {
        lut[position] = new double[(int) Math.pow(mapSamplePointStates.size(), size - 1)];
    }

    public void addNTuple(int position, double[] weight) {
        lut[position] = weight;
    }

    public IsolatedComputation<Double> getComputation(IStateNTuple state) {
        return () -> {
            double sum = 0d;
            for ( int v = 0; v < lut.length - 1; v++ ) {
                sum += lut[v][calculateIndex(v, state, this)];
            }
            return sum;
        };
    }

}
