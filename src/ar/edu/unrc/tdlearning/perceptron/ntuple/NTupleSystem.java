/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NTupleSystem {

    /**
     *
     * @param nTupleIndex
     * @param nTuplesLenght
     * @param state
     * @param mapSamplePointStates
     * @return
     */
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

    /**
     * @return the activationFunction
     */
    public Function<Double, Double> getActivationFunction() {
        return activationFunction;
    }

    /**
     *
     * @param state
     * @return
     */
    public IsolatedComputation<ComplexNTupleComputation> getComplexComputation(IStateNTuple state) {
        return () -> {
            double sum = 0d;
            int lastFirstIndex = 0;
            int[] indexes = new int[getnTuplesLenght().length];
            for ( int v = 0; v < getnTuplesLenght().length; v++ ) {
                indexes[v] = lastFirstIndex + calculateIndex(v, getnTuplesLenght(), state, getMapSamplePointStates());
                sum += getLut()[indexes[v]];
                lastFirstIndex += getnTuplesWeightQuantity()[v];
            }
            ComplexNTupleComputation output = new ComplexNTupleComputation();
            output.setIndexes(indexes);
            output.setOutput(getActivationFunction().apply(sum));
            output.setDerivatedOutput(getDerivatedActivationFunction().apply(output.getOutput()));
            return output;
        };
    }

    /**
     *
     * @param state
     * @return
     */
    public IsolatedComputation<Double> getComputation(IStateNTuple state) {
        return () -> {
            double sum = 0d;
            int lastFirstIndex = 0;
            for ( int v = 0; v < getnTuplesLenght().length; v++ ) {
                sum += getLut()[lastFirstIndex + calculateIndex(v, getnTuplesLenght(), state, getMapSamplePointStates())];
                lastFirstIndex += getnTuplesWeightQuantity()[v];
            }
            return getActivationFunction().apply(sum);
        };
    }

    /**
     * @return the derivatedActivationFunction
     */
    public Function<Double, Double> getDerivatedActivationFunction() {
        return derivatedActivationFunction;
    }

    /**
     * @return the lut
     */
    public double[] getLut() {
        return lut;
    }

    /**
     * @return the mapSamplePointStates
     */
    public Map<SamplePointState, Integer> getMapSamplePointStates() {
        return mapSamplePointStates;
    }

    /**
     *
     * @param value
     */
    public void setWeights(double[] value) {
        lut = value;
    }

    /**
     * @return the nTuplesLenght
     */
    public int[] getnTuplesLenght() {
        return nTuplesLenght;
    }

    /**
     * @return the nTuplesWeightQuantity
     */
    public int[] getnTuplesWeightQuantity() {
        return nTuplesWeightQuantity;
    }

    /**
     *
     * @param weightsFile
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(File weightsFile) throws IOException, ClassNotFoundException {
        // Read from disk using FileInputStream
        FileInputStream f_in = new FileInputStream(weightsFile);

        // Read object using ObjectInputStream
        ObjectInputStream obj_in
                = new ObjectInputStream(f_in);

        // Read an object
        Object obj = obj_in.readObject();

        if ( obj instanceof double[] ) {
            this.lut = (double[]) obj;
        }
    }

    /**
     *
     */
    public void reset() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //TODO implementar
    }

    /**
     *
     * @param lutFile
     * @throws IOException
     */
    public void save(File lutFile) throws IOException {
        FileOutputStream fout = new FileOutputStream(lutFile);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(lut);
    }

    /**
     *
     * @param index
     * @param value
     */
    public void setWeight(int index, double value) {
        lut[index] = value;
    }

}
