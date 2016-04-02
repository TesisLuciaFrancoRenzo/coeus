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
