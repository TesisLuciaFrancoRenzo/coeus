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
 * Estructura de datos utilizada para almacenar cálculos temporales y optimizar tiempos de resultados de una red neuronal genérica.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NeuralNetworkCache {

    private final List< Layer > layers;

    /**
     * Nueva estructura de datos para almacenar cálculos temporales y optimizaciones sobre redes neuronales genéricas.
     *
     * @param layerQuantity cantidad de capas que posee la red neuronal
     */
    public
    NeuralNetworkCache( final int layerQuantity ) {
        super();
        layers = new ArrayList<>(layerQuantity);
        for ( int i = 0; i < layerQuantity; i++ ) {
            layers.add(null);
        }
    }

    /**
     * Obtiene la capa número {@code layerIndex}.
     *
     * @param layerIndex numero de la capa a obtener.
     *
     * @return la capa número {@code layerIndex}.
     */
    public
    Layer getLayer( final int layerIndex ) {
        return layers.get(layerIndex);
    }

    /**
     * @return índice de la ultima capa (capa de salida)
     */
    public
    int getOutputLayerIndex() {
        return layers.size() - 1;
    }

    /**
     * @param layerIndex índice de una capa.
     *
     * @return true si es la penúltima capa.
     */
    public
    boolean isNextToLastLayer( final int layerIndex ) {
        return layerIndex == ( getOutputLayerIndex() - 1 );
    }

    /**
     * @param layerIndex indica de una capa
     *                   <p>
     *
     * @return true si es la última capa (capa de salida).
     */
    public
    boolean isOutputLayer( final int layerIndex ) {
        return layerIndex == ( layers.size() - 1 );
    }

    /**
     * Agrega una capa en la posición indicada dentro de ésta estructura.
     *
     * @param layerIndex índice destino de la nueva capa.
     * @param layer      nueva capa.
     */
    public
    void setLayer(
            final int layerIndex,
            final Layer layer
    ) {
        layers.set(layerIndex, layer);
    }
}
