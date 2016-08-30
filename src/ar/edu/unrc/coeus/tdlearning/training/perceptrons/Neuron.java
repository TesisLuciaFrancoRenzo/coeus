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
package ar.edu.unrc.coeus.tdlearning.training.perceptrons;

import java.util.ArrayList;
import java.util.List;

/**
 * Neurona para una red neuronal genérica.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Neuron {

    private final List<Double> deltas;
    private Double derivatedOutput;
    private Double output;
    private final List<Double> weights;

    /**
     * Neurona para una red neuronal genérica.
     *
     * @param weightsQuantity           cantidad de pesos a la que está conectada.
     * @param outputLayerNeuronQuantity cantidad de neuronas de la capa de salida.
     */
    public Neuron(final int weightsQuantity,
            final int outputLayerNeuronQuantity) {
        if ( weightsQuantity > 0 ) {
            weights = new ArrayList(weightsQuantity + 1);
            for ( int i = 0; i < weightsQuantity + 1; i++ ) {
                weights.add(null);
            }
        } else {
            weights = null;
        }
        if ( outputLayerNeuronQuantity > 0 ) {
            deltas = new ArrayList<>(outputLayerNeuronQuantity);
            for ( int i = 0; i < outputLayerNeuronQuantity; i++ ) {
                deltas.add(null);
            }
        } else {
            deltas = null;
        }
    }

    /**
     * Borra los delta de la neurona.
     */
    public void clearDeltas() {
        for ( int i = 0; i < deltas.size(); i++ ) {
            deltas.set(i, null);
        }
    }

    /**
     * @return el bias, null si no tiene.
     */
    public Double getBias() {
        return weights.get(getWeights().size() - 1);
    }

    /**
     * Establece un nuevo bias.
     *
     * @param newBias valor del nuevo bias.
     */
    public void setBias(final Double newBias) {
        weights.set(getWeights().size() - 1, newBias);
    }

    /**
     * Obtiene el delta correspondiente a la neurona de salida {@code outputNeuronIndex}.
     *
     * @param outputNeuronIndex neurona de salida.
     *
     * @return delta.
     */
    public Double getDelta(final int outputNeuronIndex) {
        return deltas.get(outputNeuronIndex);
    }

    /**
     * @return todos los deltas.
     */
    public List<Double> getDeltas() {
        return deltas;
    }

    /**
     * @return derivada de la salida.
     */
    public Double getDerivatedOutput() {
        return derivatedOutput;
    }

    /**
     * @param derivatedOutput nueva derivada de la salida a establecer.
     */
    public void setDerivatedOutput(final Double derivatedOutput) {
        this.derivatedOutput = derivatedOutput;
    }

    /**
     * @return salida de la neurona.
     */
    public Double getOutput() {
        return output;
    }

    /**
     * @param output nueva salida de la neurona a establecer.
     */
    public void setOutput(final Double output) {
        this.output = output;
    }

    /**
     * @param previousLayerNeuronIndex índice de la neurona de la capa anterior.
     *
     * @return peso de la conexión desde ésta neurona hasta la número {@code previousLayerNeuronIndex} en la capa
     *         anterior.
     */
    public Double getWeight(final int previousLayerNeuronIndex) {
        return weights.get(previousLayerNeuronIndex);
    }

    /**
     * @return todos los pesos de ésta neurona con las de la capa anterior.
     */
    public List<Double> getWeights() {
        return weights;
    }

    /**
     * Establece un nuevo delta asociada a la neurona con índice {@code outputNeuronIndex} de la capa de salida.
     *
     * @param outputNeuronIndex índice de la neurona de salida.
     * @param delta             nuevo delta.
     */
    public void setDelta(
            final int outputNeuronIndex,
            final Double delta
    ) {
        deltas.set(outputNeuronIndex, delta);
    }

    /**
     * Establece un nuevo peso asociada a la neurona con índice {@code previousLayerNeuronIndex} de la capa anterior.
     *
     * @param previousLayerNeuronIndex índice de la neurona de la capa anterior.
     * @param weight                   nuevo peso.
     */
    public void setWeight(
            final int previousLayerNeuronIndex,
            final Double weight
    ) {
        weights.set(previousLayerNeuronIndex, weight);
    }

}
