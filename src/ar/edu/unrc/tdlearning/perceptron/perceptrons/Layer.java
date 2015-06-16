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
public class Layer {

    private final List<PartialNeuron> neurons;

    /**
     *
     * @param neuronQuantityInLayer
     */
    public Layer(int neuronQuantityInLayer) {
        neurons = new ArrayList<>(neuronQuantityInLayer);
        for ( int i = 0; i < neuronQuantityInLayer; i++ ) {
            neurons.add(null);
        }

    }

    /**
     * @param neuronIndex <p>
     * @return the neurons
     */
    public PartialNeuron getNeuron(int neuronIndex) {
        return neurons.get(neuronIndex);
    }

    /**
     * @return the neurons
     */
    public List<PartialNeuron> getNeurons() {
        return neurons;
    }

    /**
     *
     * @param neuronIndex
     * @param neuron
     */
    public void setNeuron(int neuronIndex, PartialNeuron neuron) {
        neurons.set(neuronIndex, neuron);
    }

}
