/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import java.util.ArrayList;
import java.util.List;

/**
 * Util ara el calculo de cache de momentums ya que no es necesario todos los
 * otros valores
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
class PartialNeuron {

    private final List<Double> weights;

    PartialNeuron(int weightsQuantity, int outputLayerNeuronQuantity) {
        if ( weightsQuantity > 0 ) {
            weights = new ArrayList(weightsQuantity + 1);
            for ( int i = 0; i < weightsQuantity + 1; i++ ) {
                weights.add(null);
            }
        } else {
            weights = null;
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

    /**
     * @param previousLayerNeuronIndex
     * @param weight
     */
    void setWeight(int previousLayerNeuronIndex, Double weight) {
        getWeights().set(previousLayerNeuronIndex, weight);
    }

}
