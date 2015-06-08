/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NTupleSystem implements IPerceptronInterface {

    public static int calculateIndex(int nTupleIndex, int[] nTuplesLenght, IStateNTuple state, Map<SamplePointState, Integer> mapSamplePointStates) {
        SamplePointState[] ntuple = state.getNTuple(nTupleIndex);
        int index = 0;
        for ( int j = 0; j < nTuplesLenght[nTupleIndex]; j++ ) {
//            SamplePointState object = ntuple[j];
//            Integer sampleIndex = mapSamplePointStates.get(object);
//            int size = mapSamplePointStates.size();
//            int pow = (int) Math.pow(size, j);
            index += mapSamplePointStates.get(ntuple[j]) * (int) Math.pow(mapSamplePointStates.size(), j);
        }
        return index;
    }

    private final Function<Double, Double> activationFunction;
    private final Function<Double, Double> derivatedActivationFunction;

    private double[] lut;
    private final Map<SamplePointState, Integer> mapSamplePointStates;
    private final int[] nTuplesLenght;
    private final int[] nTuplesWeightQuantity;

    /**
     *
     * @param allSamplePointStates
     * @param nTuplesLenght
     * @param activationFunction
     * @param derivatedActivationFunction
     */
    public NTupleSystem(List<SamplePointState> allSamplePointStates, int[] nTuplesLenght, Function<Double, Double> activationFunction, Function<Double, Double> derivatedActivationFunction) {
        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i < allSamplePointStates.size(); i++ ) {
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }
        int lutSize = 0;
        nTuplesWeightQuantity = new int[nTuplesLenght.length];
        for ( int i = 0; i < nTuplesLenght.length; i++ ) {
            nTuplesWeightQuantity[i] = (int) Math.pow(mapSamplePointStates.size(), nTuplesLenght[i]);
            lutSize += nTuplesWeightQuantity[i];
        }
        lut = new double[lutSize];
        this.nTuplesLenght = nTuplesLenght;
        this.activationFunction = activationFunction;
        this.derivatedActivationFunction = derivatedActivationFunction;
    }

    @Override
    public Function<Double, Double> getActivationFunction(int layerIndex) {
        return activationFunction;
    }

    @Override
    public double getBias(int layerIndex, int neuronIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IsolatedComputation<Double> getComputation(IStateNTuple state) {
        return () -> {
            double sum = 0d;
            int lastFirstIndex = 0;
            for ( int v = 0; v < nTuplesLenght.length; v++ ) {
                sum += lut[lastFirstIndex + calculateIndex(v, nTuplesLenght, state, mapSamplePointStates)];
                lastFirstIndex += nTuplesWeightQuantity[v];
            }
            return activationFunction.apply(sum);
        };
    }

    @Override
    public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
        return derivatedActivationFunction;
    }

    @Override
    public int getLayerQuantity() {
        return 2;
    }

    @Override
    public int getNeuronQuantityInLayer(int layerIndex) {
        if ( layerIndex == 0 ) {
            return lut.length;
        } else {
            return 1;
        }
    }

    @Override
    public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
        return lut[neuronIndexPreviousLayer];
    }

    public void setWeights(double[] weights) {
        if ( lut.length != weights.length ) {
            throw new IllegalArgumentException("la cantidad de pesos de weights no corresponde a la cantidad de pesos en la tabla de lut");
        }
        this.lut = weights;
    }

    @Override
    public boolean hasBias(int layerIndex) {
        return false;
    }

    @Override
    public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
        lut[neuronIndexPreviousLayer] = correctedWeight;
    }
}
