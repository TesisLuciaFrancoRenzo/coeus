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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NeuralNetCacheTest {

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    public NeuralNetCacheTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getLayer method, of class NeuralNetworkCache.
     */
    @Test
    public void testGetLayer() {
        System.out.println("getLayer");
        int layerIndex = 0;
        NeuralNetworkCache instance = new NeuralNetworkCache(1);
        Layer expResult = new Layer(1);
        instance.setLayer(layerIndex, expResult);
        Layer result = instance.getLayer(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getNeuron method, of class NeuralNetworkCache.
     */
    @Test
    public void testGetNeuron() {
        System.out.println("getNeuron");
        int layerIndex = 0;
        int neuronIndex = 0;
        NeuralNetworkCache instance = new NeuralNetworkCache(1);
        Layer layer = new Layer(1);
        Neuron expResult = new Neuron(1, 1);
        layer.setNeuron(neuronIndex, expResult);
        instance.setLayer(layerIndex, layer);
        Neuron result = instance.getLayer(layerIndex).getNeuron(
                neuronIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getOutputLayerIndex method, of class NeuralNetworkCache.
     */
    @Test
    public void testGetOutputLayerIndex() {
        System.out.println("getOutputLayerIndex");
        NeuralNetworkCache instance = new NeuralNetworkCache(1);
        Layer layer = new Layer(1);
        instance.setLayer(0, layer);
        instance.isOutputLayer(1);
        int expResult = 0;
        int result = instance.getOutputLayerIndex();
        assertThat(result, is(expResult));
    }

    /**
     * Test of isNextToLastLayer method, of class NeuralNetworkCache.
     */
    @Test
    public void testIsNextToLasyLayer() {
        System.out.println("isNextToLasyLayer");
        int layerIndex = 0;
        NeuralNetworkCache instance = new NeuralNetworkCache(2);
        instance.setLayer(layerIndex, new Layer(1));
        instance.setLayer(1, new Layer(1));
        boolean expResult = true;
        System.out.println("isNextToLasyLayer" + instance.getOutputLayerIndex());
        boolean result = instance.isNextToLastLayer(0);
        assertThat(result, is(expResult));
    }

    /**
     * Test of isOutputLayer method, of class NeuralNetworkCache.
     */
    @Test
    public void testIsOutputLayer() {
        System.out.println("isOutputLayer");
        int layerIndex = 0;
        NeuralNetworkCache instance = new NeuralNetworkCache(2);
        instance.setLayer(layerIndex, new Layer(1));
        instance.setLayer(1, new Layer(1));
        boolean expResult = true;
        boolean result = instance.isOutputLayer(1);
        assertThat(result, is(expResult));
    }

    /**
     * Test of setLayer method, of class NeuralNetworkCache.
     */
    @Test
    public void testSetLayer() {
        System.out.println("setLayer");
        int layerIndex = 0;
        NeuralNetworkCache instance = new NeuralNetworkCache(2);
        Layer layer = new Layer(1);
        instance.setLayer(layerIndex, layer);
        instance.setLayer(1, new Layer(1));
        assertThat(instance.getLayer(layerIndex), is(layer));
    }
}
