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

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NeuralNetCacheTest {

    /**
     * Test of getLayer method, of class NeuralNetworkCache.
     */
    @Test
    public
    void testGetLayer() {
        System.out.println("getLayer");
        final int                layerIndex = 0;
        final NeuralNetworkCache instance   = new NeuralNetworkCache(1);
        final Layer              expResult  = new Layer(1);
        instance.setLayer(layerIndex, expResult);
        final Layer result = instance.getLayer(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getNeuron method, of class NeuralNetworkCache.
     */
    @Test
    public
    void testGetNeuron() {
        System.out.println("getNeuron");
        final int                layerIndex  = 0;
        final int                neuronIndex = 0;
        final NeuralNetworkCache instance    = new NeuralNetworkCache(1);
        final Layer              layer       = new Layer(1);
        final Neuron             expResult   = new Neuron(1, 1);
        layer.setNeuron(neuronIndex, expResult);
        instance.setLayer(layerIndex, layer);
        final Neuron result = instance.getLayer(layerIndex).getNeuron(neuronIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getOutputLayerIndex method, of class NeuralNetworkCache.
     */
    @Test
    public
    void testGetOutputLayerIndex() {
        System.out.println("getOutputLayerIndex");
        final NeuralNetworkCache instance = new NeuralNetworkCache(1);
        final Layer              layer    = new Layer(1);
        instance.setLayer(0, layer);
        instance.isOutputLayer(1);
        final int expResult = 0;
        final int result    = instance.getOutputLayerIndex();
        assertThat(result, is(expResult));
    }

    /**
     * Test of isNextToLastLayer method, of class NeuralNetworkCache.
     */
    @Test
    public
    void testIsNextToLastLayer() {
        System.out.println("isNextToLastLayer");
        final int                layerIndex = 0;
        final NeuralNetworkCache instance   = new NeuralNetworkCache(2);
        instance.setLayer(layerIndex, new Layer(1));
        instance.setLayer(1, new Layer(1));
        final boolean expResult = true;
        System.out.println("isNextToLastLayer" + instance.getOutputLayerIndex());
        final boolean result = instance.isNextToLastLayer(0);
        assertThat(result, is(expResult));
    }

    /**
     * Test of isOutputLayer method, of class NeuralNetworkCache.
     */
    @Test
    public
    void testIsOutputLayer() {
        System.out.println("isOutputLayer");
        final int                layerIndex = 0;
        final NeuralNetworkCache instance   = new NeuralNetworkCache(2);
        instance.setLayer(layerIndex, new Layer(1));
        instance.setLayer(1, new Layer(1));
        final boolean expResult = true;
        final boolean result    = instance.isOutputLayer(1);
        assertThat(result, is(expResult));
    }

    /**
     * Test of setLayer method, of class NeuralNetworkCache.
     */
    @Test
    public
    void testSetLayer() {
        System.out.println("setLayer");
        final int                layerIndex = 0;
        final NeuralNetworkCache instance   = new NeuralNetworkCache(2);
        final Layer              layer      = new Layer(1);
        instance.setLayer(layerIndex, layer);
        instance.setLayer(1, new Layer(1));
        assertThat(instance.getLayer(layerIndex), is(layer));
    }
}
