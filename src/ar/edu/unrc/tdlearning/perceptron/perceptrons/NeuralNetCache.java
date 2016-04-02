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
public class NeuralNetCache {

    private final List<Layer> layers;

    /**
     *
     * @param layerQuantity cantidad de capas que posee la red neuronal
     */
    public NeuralNetCache(int layerQuantity) {
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
    public Layer getLayer(int layerIndex) {
        return layers.get(layerIndex);
    }

//    /**
//     *
//     * @param layerIndex  indica de una capa
//     * @param neuronIndex indice de una neurona
//     * <p>
//     * @return una neurona
//     */
//    public PartialNeuron getNeuron(int layerIndex, int neuronIndex) {
//        return layers.get(layerIndex).getNeuron(neuronIndex); //FIXME sacar esto ya que es menos eficiente
//    }
    /**
     *
     * @return indice de la ultima capa (capa de salida)
     */
    public int getOutputLayerIndex() {
        return layers.size() - 1;
    }

    /**
     *
     * @param layerIndex indice de una capa
     * <p>
     * @return true si es la penultima capa
     */
    public boolean isNextToLasyLayer(int layerIndex) {
        return layerIndex == getOutputLayerIndex() - 1;
    }

    /**
     *
     * @param layerIndex indica de una capa
     * <p>
     * @return true si es la ultima capa (capa de salida)
     */
    public boolean isOutputLayer(int layerIndex) {
        return layerIndex == layers.size() - 1;
    }

    /**
     * Agrega una capa en la posicion indicada
     * <p>
     * @param layerIndex indice del destino de la nueva capa
     * @param layer      nueva capa a añadir a la cache
     */
    public void setLayer(int layerIndex, Layer layer) {
        layers.set(layerIndex, layer);
    }
}
