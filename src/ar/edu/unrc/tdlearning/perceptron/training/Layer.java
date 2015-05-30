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
class Layer {

    private final List<Neuron> neurons;

    Layer(int neuronQuantityInLayer) {
        neurons = new ArrayList<>(neuronQuantityInLayer);
        for ( int i = 0; i < neuronQuantityInLayer; i++ ) {
            neurons.add(null);
        }

    }

    /**
     * @param neuronIndex <p>
     * @return the neurons
     */
    Neuron getNeuron(int neuronIndex) {
        return neurons.get(neuronIndex);
    }

    /**
     * @return the neurons
     */
    List<Neuron> getNeurons() {
        return neurons;
    }

    void setNeuron(int neuronIndex, Neuron neuron) {
        neurons.set(neuronIndex, neuron);
    }

}
