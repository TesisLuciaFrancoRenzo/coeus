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
package ar.edu.unrc.tdlearning.perceptron.perceptrons;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Neuron extends PartialNeuron {

    private final List<Double> deltas;

    private Double derivatedOutput;
    private Double output;

    /**
     *
     * @param weightsQuantity
     * @param outputLayerNeuronQuantity
     */
    public Neuron(int weightsQuantity, int outputLayerNeuronQuantity) {
        super(weightsQuantity, outputLayerNeuronQuantity);
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
     *
     */
    public void clearDeltas() {
        for ( int i = 0; i < deltas.size(); i++ ) {
            deltas.set(i, null);
        }
    }

    /**
     *
     * @param outputNeuronIndex
     *
     * @return
     */
    public Double getDelta(int outputNeuronIndex) {
        return getDeltas().get(outputNeuronIndex);
    }

    /**
     * @return the deltas
     */
    public List<Double> getDeltas() {
        return deltas;
    }

    /**
     * @return the derivatedOutput
     */
    public Double getDerivatedOutput() {
        return derivatedOutput;
    }

    /**
     * @param derivatedOutput the derivatedOutput to set
     */
    public void setDerivatedOutput(Double derivatedOutput) {
        this.derivatedOutput = derivatedOutput;
    }

    /**
     * @return the output
     */
    public Double getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(Double output) {
        this.output = output;
    }

    /**
     *
     * @param outputNeuronIndex
     * @param delta
     */
    public void setDelta(int outputNeuronIndex, Double delta) {
        getDeltas().set(outputNeuronIndex, delta);
    }

}
