/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
class Neuron {

    private final List<Double> deltas;

    private Double derivatedOutput;
    private Double output;
    private final List<Double> weights;

    Neuron(int weightsQuantity, int outputLayerNeuronQuantity) {
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

    void clearDeltas() {
        for ( int i = 0; i < deltas.size(); i++ ) {
            deltas.set(i, null);
        }
    }

    /**
     * @return the bias, null si no tiene bias
     */
    Double getBias() {
        return getWeights().get(getWeights().size() - 1);
    }

    /**
     * @param newBias
     */
    void setBias(Double newBias) {
        getWeights().set(getWeights().size() - 1, newBias);
    }

    Double getDelta(int outputNeuronIndex) {
        return getDeltas().get(outputNeuronIndex);
    }

    /**
     * @return the deltas
     */
    List<Double> getDeltas() {
        return deltas;
    }

    /**
     * @return the derivatedOutput
     */
    Double getDerivatedOutput() {
        return derivatedOutput;
    }

    /**
     * @param derivatedOutput the derivatedOutput to set
     */
    void setDerivatedOutput(Double derivatedOutput) {
        this.derivatedOutput = derivatedOutput;
    }

    /**
     * @return the output
     */
    Double getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    void setOutput(Double output) {
        this.output = output;
    }

    /**
     * @param previousLayerNeuronIndex <p>
     * @return the weights
     */
    Double getWeight(int previousLayerNeuronIndex) {
        return getWeights().get(previousLayerNeuronIndex);
    }

    /**
     * @return the weights
     */
    List<Double> getWeights() {
        return weights;
    }

    void setDelta(int outputNeuronIndex, Double delta) {
        getDeltas().set(outputNeuronIndex, delta);
    }

    /**
     * @param previousLayerNeuronIndex
     * @param weight
     */
    void setWeight(int previousLayerNeuronIndex, Double weight) {
        getWeights().set(previousLayerNeuronIndex, weight);
    }

}
