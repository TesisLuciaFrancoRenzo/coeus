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
 * Capa de una red neuronal genérica.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class Layer {

    private final List<Neuron> neurons;

    /**
     * Capa de una red neuronal genérica.
     *
     * @param neuronQuantityInLayer cantidad de neuronas en la capa.
     */
    public
    Layer(final int neuronQuantityInLayer) {
        neurons = new ArrayList<>(neuronQuantityInLayer);
        for (int i = 0; i < neuronQuantityInLayer; i++) {
            neurons.add(null);
        }
    }

    /**
     * Obtiene una neurona dentro de una capa.
     *
     * @param neuronIndex índice de la neurona dentro de la capa.
     *
     * @return neurona
     */
    public
    Neuron getNeuron(final int neuronIndex) {
        return neurons.get(neuronIndex);
    }

    /**
     * @return todas las neuronas de la capa.
     */
    public
    List<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * Cambia una neurona dentro de la capa por otra.
     *
     * @param neuronIndex índice de la neurona a cambiar.
     * @param neuron      nueva neurona.
     */
    public
    void setNeuron(
            final int neuronIndex,
            final Neuron neuron
    ) {
        neurons.set(neuronIndex, neuron);
    }

}
