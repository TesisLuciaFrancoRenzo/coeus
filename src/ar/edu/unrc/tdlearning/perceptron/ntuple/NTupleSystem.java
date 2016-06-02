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
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
     * @param mapSamplePointStates <p>
     * @return
     */
    public static int calculateLocalIndex(int nTupleIndex, int[] nTuplesLenght, IStateNTuple state, Map<SamplePointState, Integer> mapSamplePointStates) {
        SamplePointState[] ntuple = state.getNTuple(nTupleIndex);
        int index = 0;
        for ( int j = 0; j < nTuplesLenght[nTupleIndex]; j++ ) {
            SamplePointState object = ntuple[j];
            Integer sampleIndex = mapSamplePointStates.get(object);
            int size = mapSamplePointStates.size();
            int pow = (int) Math.pow(size, j);
            index += mapSamplePointStates.get(ntuple[j]) * (int) Math.pow(mapSamplePointStates.size(), j); //FIXME hacer comparables?
        }
        return index;
    }

    private final Function<Double, Double> activationFunction;
    private final boolean concurrency;
    private final Function<Double, Double> derivatedActivationFunction;

    private double[] lut;
    private final Map<SamplePointState, Integer> mapSamplePointStates;
    private final int[] nTuplesLenght;
    private final int[] nTuplesWeightQuantity;
    private final int[] nTuplesWeightQuantityIndex;

    /**
     *
     * @param allSamplePointStates
     * @param nTuplesLenght
     * @param activationFunction
     * @param derivatedActivationFunction
     * @param concurrency
     */
    public NTupleSystem(List<SamplePointState> allSamplePointStates, int[] nTuplesLenght, Function<Double, Double> activationFunction, Function<Double, Double> derivatedActivationFunction, boolean concurrency) {
        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i < allSamplePointStates.size(); i++ ) {
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }
        int lutSize = 0;
        nTuplesWeightQuantity = new int[nTuplesLenght.length];
        nTuplesWeightQuantityIndex = new int[nTuplesLenght.length];
        nTuplesWeightQuantityIndex[0] = 0;
        for ( int i = 0; i < nTuplesLenght.length; i++ ) {
            nTuplesWeightQuantity[i] = (int) Math.pow(mapSamplePointStates.size(), nTuplesLenght[i]);
            lutSize += nTuplesWeightQuantity[i];
            if ( i > 0 ) {
                nTuplesWeightQuantityIndex[i] = nTuplesWeightQuantityIndex[i - 1] + nTuplesWeightQuantity[i - 1];
            }
        }
        lut = new double[lutSize];
        this.nTuplesLenght = nTuplesLenght;
        this.activationFunction = activationFunction;
        this.derivatedActivationFunction = derivatedActivationFunction;
        this.concurrency = concurrency;
    }

    /**
     *
     * @param currentWeightIndex
     * @param correction
     */
    public void addCorrectionToWeight(int currentWeightIndex, double correction) {
        lut[currentWeightIndex] += correction;
    }

    /**
     * @return the activationFunction
     */
    public Function<Double, Double> getActivationFunction() {
        return activationFunction;
    }

    /**
     *
     * @param state <p>
     * @return
     */
    public ComplexNTupleComputation getComplexComputation(IStateNTuple state) {
        IntStream stream = IntStream
                .range(0, nTuplesLenght.length);
        if ( concurrency ) {
            stream = stream.parallel();
        } else {
            stream = stream.sequential();
        }
        int[] indexes = new int[nTuplesLenght.length];
        double sum = stream.mapToDouble(v -> {
            indexes[v] = nTuplesWeightQuantityIndex[v] + calculateLocalIndex(v, getnTuplesLenght(), state, getMapSamplePointStates());
            return lut[indexes[v]];
        }).sum();

//        double sum2 = 0d;
//        for ( int v = 0; v < nTuplesLenght.length; v++ ) {
//            indexes[v] = nTuplesWeightQuantityIndex[v] + calculateLocalIndex(v, getnTuplesLenght(), state, getMapSamplePointStates());
//            sum2 += lut[indexes[v]];
//        }
        ComplexNTupleComputation output = new ComplexNTupleComputation();
        output.setIndexes(indexes);
        output.setOutput(getActivationFunction().apply(sum));
        output.setDerivatedOutput(getDerivatedActivationFunction().apply(output.getOutput()));
        return output;
    }

    /**
     *
     * @param state <p>
     * @return
     */
    public Double getComputation(IStateNTuple state) {
        IntStream stream = IntStream
                .range(0, nTuplesLenght.length);
        if ( concurrency ) {
            stream = stream.parallel();
        } else {
            stream = stream.sequential();
        }
        double sum = stream.mapToDouble(v -> {
            return lut[nTuplesWeightQuantityIndex[v] + calculateLocalIndex(v, getnTuplesLenght(), state, getMapSamplePointStates())];
        }).sum();

//        double sum = 0d;
//        for ( int v = 0; v < nTuplesLenght.length; v++ ) {
//            sum += lut[nTuplesWeightQuantityIndex[v] + calculateLocalIndex(v, getnTuplesLenght(), state, getMapSamplePointStates())];
//        }
        return getActivationFunction().apply(sum);
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
     * @param weightsFile <p>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(File weightsFile) throws IOException, ClassNotFoundException {
        FileInputStream f_in = new FileInputStream(weightsFile);
        load(f_in);
    }

    /**
     *
     * @param weightsFile <p>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(InputStream weightsFile) throws IOException, ClassNotFoundException {
        if ( weightsFile == null ) {
            throw new IllegalArgumentException("weightsFile can't be null");
        }

        // descomprimimos
        GZIPInputStream gz = new GZIPInputStream(weightsFile);

        // leemos le objeto utilizando ObjectInputStream
        ObjectInputStream obj_in = new ObjectInputStream(gz);

        // creamos el objeto
        Object obj = obj_in.readObject();

        //intentamos cargarlo en la variable correspondiente
        if ( obj instanceof double[] ) {
            this.lut = (double[]) obj;
        } else {
            throw new IllegalArgumentException("Formato de archivo no soportado");
        }
    }

    /**
     *
     */
    public void reset() {
        IntStream
                .range(0, lut.length)
                .parallel()
                .forEach(weightIndex -> {
                    lut[weightIndex] = (Math.random() * 2d - 1d);
                });
    }

    /**
     *
     * @param lutFile <p>
     * @throws IOException
     */
    public void save(File lutFile) throws IOException {
        //definimos el stream de salida
        FileOutputStream fout = new FileOutputStream(lutFile);
        //comprimimos
        GZIPOutputStream gz = new GZIPOutputStream(fout);
        //escribimos el archivo el objeto
        try ( ObjectOutputStream oos = new ObjectOutputStream(gz) ) {
            oos.writeObject(lut);
        }
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
