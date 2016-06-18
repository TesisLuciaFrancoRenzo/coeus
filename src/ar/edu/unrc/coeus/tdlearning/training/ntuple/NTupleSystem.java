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
package ar.edu.unrc.coeus.tdlearning.training.ntuple;

import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
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
     * Dado la NTupla número {@code nTupleIndex} extraída de {@code state}, esta función calcula a
     * que peso corresponde dentro de la red neuronal.
     *
     * @param nTupleIndex          NTupla observada en {@code state}.
     * @param nTuplesLenght        longitudes de las NTuplas.
     * @param state                estado del cual se extrae la NTupla.
     * @param mapSamplePointStates mapa de los posibles valores dentro de una muestra de NTupla,
     *                             asociado a su índice.
     *
     * @return peso correspondiente en la red neuronal de la NTupla numero {@code nTupleIndex}
     *         dentro de {@code state}.
     */
    public static int calculateLocalIndex(
            final int nTupleIndex,
            final int[] nTuplesLenght,
            final IStateNTuple state,
            final Map<SamplePointState, Integer> mapSamplePointStates
    ) {
        SamplePointState[] ntuple = state.getNTuple(nTupleIndex);
        int index = 0;
        for ( int j = 0; j < nTuplesLenght[nTupleIndex]; j++ ) {
//            SamplePointState object = ntuple[j];
//            Integer sampleIndex = mapSamplePointStates.get(object);
//            int size = mapSamplePointStates.size();
//            int pow = (int) Math.pow(size, j);
            index += mapSamplePointStates.get(ntuple[j]) * (int) Math.pow(
                    mapSamplePointStates.size(), j); //FIXME hacer comparables?
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
     * Red neuronal optimizada para usos de NTuplas.
     *
     * @param allSamplePointStates        todos los posibles valores dentro de una muestra de
     *                                    NTupla.
     * @param nTuplesLenght               longitudes de las NTuplas.
     * @param activationFunction          función de activación.
     * @param derivatedActivationFunction derivada de la función de activación.
     * @param concurrency                 true si se permite concurrencia en los cálculos.
     */
    public NTupleSystem(
            final List<SamplePointState> allSamplePointStates,
            final int[] nTuplesLenght,
            final Function<Double, Double> activationFunction,
            final Function<Double, Double> derivatedActivationFunction,
            final boolean concurrency
    ) {
        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i < allSamplePointStates.size(); i++ ) {
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }
        int lutSize = 0;
        nTuplesWeightQuantity = new int[nTuplesLenght.length];
        nTuplesWeightQuantityIndex = new int[nTuplesLenght.length];
        nTuplesWeightQuantityIndex[0] = 0;
        for ( int i = 0; i < nTuplesLenght.length; i++ ) {
            nTuplesWeightQuantity[i] = (int) Math.pow(mapSamplePointStates.
                    size(), nTuplesLenght[i]);
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
     * Añade {@code correction} al peso con el índice {@code currentWeightIndex} dentro de la red
     * neuronal.
     *
     * @param currentWeightIndex índice del peso en la red neuronal.
     * @param correction         valor a añadir.
     */
    public void addCorrectionToWeight(final int currentWeightIndex,
            final double correction) {
        lut[currentWeightIndex] += correction;
    }

    /**
     * @return función de activación.
     */
    public Function<Double, Double> getActivationFunction() {
        return activationFunction;
    }

    /**
     * Se utiliza la red neuronal para evaluar un {@code state} y obtener una predicción.
     *
     * @param state estado a evaluar.
     *
     * @return predicción de a red neuronal y los cálculos temporales utilizados en el proceso.
     */
    public ComplexNTupleComputation getComplexComputation(final IStateNTuple state) {
        IntStream stream = IntStream
                .range(0, nTuplesLenght.length);
        if ( concurrency ) {
            stream = stream.parallel();
        } else {
            stream = stream.sequential();
        }
        int[] indexes = new int[nTuplesLenght.length];
        double sum = stream.mapToDouble(v ->
                {
                    indexes[v] = nTuplesWeightQuantityIndex[v]
                            + calculateLocalIndex(v, getNTuplesLenght(), state,
                                    getMapSamplePointStates());
                    return lut[indexes[v]];
                }).sum();
        ComplexNTupleComputation output = new ComplexNTupleComputation();
        output.setIndexes(indexes);
        output.setOutput(getActivationFunction().apply(sum));
        output.setDerivatedOutput(
                getDerivatedActivationFunction().apply(output.getOutput()));
        return output;
    }

    /**
     * Se utiliza la red neuronal para evaluar un {@code state} y obtener una predicción.
     *
     * @param state estado a evaluar.
     *
     * @return predicción de a red neuronal.
     */
    public Double getComputation(final IStateNTuple state) {
        IntStream stream = IntStream
                .range(0, nTuplesLenght.length);
        if ( concurrency ) {
            stream = stream.parallel();
        } else {
            stream = stream.sequential();
        }
        double sum = stream.mapToDouble(v ->
                {
                    return lut[nTuplesWeightQuantityIndex[v]
                            + calculateLocalIndex(v, getNTuplesLenght(), state,
                                    getMapSamplePointStates())];
                }).sum();
        return getActivationFunction().apply(sum);
    }

    /**
     * @return derivada de la función de activación.
     */
    public Function<Double, Double> getDerivatedActivationFunction() {
        return derivatedActivationFunction;
    }

    /**
     * @return tabla lut.
     */
    public double[] getLut() {
        return lut;
    }

    /**
     * @return mapa de los posibles valores dentro de una muestra de NTupla, asociado a su índice.
     */
    public Map<SamplePointState, Integer> getMapSamplePointStates() {
        return mapSamplePointStates;
    }

    /**
     * Cambia todos los pesos de la red neuronal con los valores de {@code value}
     *
     * @param value nuevos valores para los pesos de la red neuronal.
     */
    public void setWeights(final double[] value) {
        lut = value;
    }

    /**
     * @return las longitudes de las NTuplas.
     */
    public int[] getNTuplesLenght() {
        return nTuplesLenght;
    }

    /**
     * @return cantidad de pesos en cada NTupla posible.
     */
    public int[] getNTuplesWeightQuantity() {
        return nTuplesWeightQuantity;
    }

    /**
     * Carga una red neuronal desde un archivo.
     *
     * @param weightsFile archivo con los pesos de la red neuronal.
     *
     * @throws IOException            no se puede leer el archivo.
     * @throws ClassNotFoundException no se encuentran las clases adecuadas para cargar la red.
     */
    public void load(final File weightsFile) throws IOException,
            ClassNotFoundException {
        FileInputStream f_in = new FileInputStream(weightsFile);
        load(f_in);
    }

    /**
     * Carga una red neuronal desde un archivo.
     *
     * @param weightsInputStream stream con los pesos de la red neuronal.
     *
     * @throws IOException            no se puede leer el archivo.
     * @throws ClassNotFoundException no se encuentran las clases adecuadas para cargar la red.
     */
    public void load(final InputStream weightsInputStream) throws IOException,
            ClassNotFoundException {
        if ( weightsInputStream == null ) {
            throw new IllegalArgumentException("weightsFile can't be null");
        }

        // descomprimimos
        GZIPInputStream gz = new GZIPInputStream(weightsInputStream);

        // leemos le objeto utilizando ObjectInputStream
        ObjectInputStream obj_in = new ObjectInputStream(gz);

        // creamos el objeto
        Object obj = obj_in.readObject();

        //intentamos cargarlo en la variable correspondiente
        if ( obj instanceof double[] ) {
            this.lut = (double[]) obj;
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    /**
     * Inicializa los valores de los pesos de la red neuronal con numero al azar.
     */
    public void randomize() {
        IntStream
                .range(0, lut.length)
                .parallel()
                .forEach(weightIndex ->
                        {
                            lut[weightIndex] = (Math.random() * 2d - 1d);
                        });
    }

    /**
     * Salva en un archivo {@code lutFile} los pesos de la red neuronal.
     *
     * @param lutFile archivo destino.
     *
     * @throws IOException no se puede guardar en ese archivo.
     */
    public void save(final File lutFile) throws IOException {
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
     * Cambia el valor del peso de la red neuronal con el índice {@code index} al valor
     * {@code value}.
     *
     * @param index índice del peso a modificar.
     * @param value nuevo valor del peso a modificar.
     */
    public void setWeight(final int index,
            final double value) {
        lut[index] = value;
    }

}
