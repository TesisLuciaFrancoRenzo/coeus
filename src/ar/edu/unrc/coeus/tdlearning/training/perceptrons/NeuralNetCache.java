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
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NeuralNetCache {

    private final List<Layer> layers;

    /**
     *
     * @param layerQuantity cantidad de capas que posee la red neuronal
     */
    public NeuralNetCache(final int layerQuantity) {
        layers = new ArrayList<>(layerQuantity);
        for ( int i = 0; i < layerQuantity; i++ ) {
            layers.add(null);
        }
    }

    /**
     *
     * @param layerIndex <p>
     * @return la capa indicada
     */
    public Layer getLayer(final int layerIndex) {
        return layers.get(layerIndex);
    }

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
    public boolean isNextToLasyLayer(final int layerIndex) {
        return layerIndex == getOutputLayerIndex() - 1;
    }

    /**
     *
     * @param layerIndex indica de una capa
     * <p>
     * @return true si es la ultima capa (capa de salida)
     */
    public boolean isOutputLayer(final int layerIndex) {
        return layerIndex == layers.size() - 1;
    }

    /**
     * Agrega una capa en la posicion indicada
     * <p>
     * @param layerIndex indice del destino de la nueva capa
     * @param layer      nueva capa a añadir a la cache
     */
    public void setLayer(final int layerIndex, final Layer layer) {
        layers.set(layerIndex, layer);
    }
}
