/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public void clearDeltas() {
        for ( int i = 0; i < deltas.size(); i++ ) {
            deltas.set(i, null);
        }
    }

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

    public void setDelta(int outputNeuronIndex, Double delta) {
        getDeltas().set(outputNeuronIndex, delta);
    }

}
