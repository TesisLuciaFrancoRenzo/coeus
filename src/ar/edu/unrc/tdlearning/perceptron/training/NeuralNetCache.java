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
public class NeuralNetCache {

    private final List<Layer> layers;

    /**
     *
     * @param layerQuantity cantidad de capas que posee la red neuronal
     */
    NeuralNetCache(int layerQuantity) {
        layers = new ArrayList<>(layerQuantity);
        for ( int i = 0; i < layerQuantity; i++ ) {
            layers.add(null);
        }
    }
//
//    public void printDebug() {
//        for ( int layerIndex = 1; layerIndex < layers.size(); layerIndex++ ) {
//            for ( int neuronIndex = 0; neuronIndex < layers.get(layerIndex).getNeurons().size(); neuronIndex++ ) {
//                Neuron neuron = getNeuron(layerIndex, neuronIndex);
//                System.out.println("------ Neurona " + layerIndex + "," + neuronIndex + " ------");
//                System.out.println("* salida = " + neuron.getOutput());
//                System.out.println("* salida derivada = " + neuron.getDerivatedOutput());
//                System.out.println("* bias = " + neuron.getBias());
//                System.out.print("* pesos = {");
//                for ( int weightIndex = 0; weightIndex < neuron.getWeights().size() - 1; weightIndex++ ) {
//                    System.out.print(neuron.getWeights().get(weightIndex));
//                    if ( weightIndex < neuron.getWeights().size() - 2 ) {
//                        System.out.print(",");
//                    }
//                }
//                System.out.println("}");
//                System.out.print("* deltas = {");
//                for ( int deltaIndex = 0; deltaIndex < neuron.getDeltas().size(); deltaIndex++ ) {
//                    System.out.print(neuron.getDeltas().get(deltaIndex));
//                    if ( deltaIndex < neuron.getDeltas().size() - 1 ) {
//                        System.out.print(",");
//                    }
//                }
//                System.out.println("}");
//
//            }
//        }
//    }

    /**
     *
     * @param layerIndex <p>
     * @return la capa indicada
     */
    Layer getLayer(int layerIndex) {
        return layers.get(layerIndex);
    }

    /**
     *
     * @param layerIndex  indica de una capa
     * @param neuronIndex indice de una neurona
     * <p>
     * @return una neurona
     */
    PartialNeuron getNeuron(int layerIndex, int neuronIndex) {
        return layers.get(layerIndex).getNeuron(neuronIndex); //FIXME sacar esto ya que es menos eficiente
    }

    /**
     *
     * @return indice de la ultima capa (capa de salida)
     */
    int getOutputLayerIndex() {
        return layers.size() - 1;
    }

    /**
     *
     * @param layerIndex indice de una capa
     * <p>
     * @return true si es la penultima capa
     */
    boolean isNextToLasyLayer(int layerIndex) {
        return layerIndex == getOutputLayerIndex() - 1;
    }

    /**
     *
     * @param layerIndex indica de una capa
     * <p>
     * @return true si es la ultima capa (capa de salida)
     */
    boolean isOutputLayer(int layerIndex) {
        return layerIndex == layers.size() - 1;
    }

    /**
     * Agrega una capa en la posicion indicada
     * <p>
     * @param layerIndex indice del destino de la nueva capa
     * @param layer      nueva capa a añadir a la cache
     */
    void setLayer(int layerIndex, Layer layer) {
        layers.set(layerIndex, layer);
    }
}
