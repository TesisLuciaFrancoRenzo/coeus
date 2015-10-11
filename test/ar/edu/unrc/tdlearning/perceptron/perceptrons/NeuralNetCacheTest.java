/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.perceptrons;

import static junit.framework.Assert.assertEquals;
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
     * Test of getLayer method, of class NeuralNetCache.
     */
    @Test
    public void testGetLayer() {
        System.out.println("getLayer");
        int layerIndex = 0;
        NeuralNetCache instance = new NeuralNetCache(1);
        Layer expResult = new Layer(1);
        instance.setLayer(layerIndex, expResult);
        Layer result = instance.getLayer(layerIndex);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNeuron method, of class NeuralNetCache.
     */
    @Test
    public void testGetNeuron() {
        System.out.println("getNeuron");
        int layerIndex = 0;
        int neuronIndex = 0;
        NeuralNetCache instance = new NeuralNetCache(1);
        Layer layer = new Layer(1);
        PartialNeuron expResult = new Neuron(1, 1);
        layer.setNeuron(neuronIndex, expResult);
        instance.setLayer(layerIndex, layer);
        PartialNeuron result = instance.getLayer(layerIndex).getNeuron(neuronIndex);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutputLayerIndex method, of class NeuralNetCache.
     */
    @Test
    public void testGetOutputLayerIndex() {
        System.out.println("getOutputLayerIndex");
        NeuralNetCache instance = new NeuralNetCache(1);
        Layer layer = new Layer(1);
        instance.setLayer(0, layer);
        instance.isOutputLayer(1);
        int expResult = 0;
        int result = instance.getOutputLayerIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of isNextToLasyLayer method, of class NeuralNetCache.
     */
    @Test
    public void testIsNextToLasyLayer() {
        System.out.println("isNextToLasyLayer");
        int layerIndex = 0;
        NeuralNetCache instance = new NeuralNetCache(2);
        instance.setLayer(layerIndex, new Layer(1));
        instance.setLayer(1, new Layer(1));
        boolean expResult = true;
        System.out.println("isNextToLasyLayer" + instance.getOutputLayerIndex());
        boolean result = instance.isNextToLasyLayer(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOutputLayer method, of class NeuralNetCache.
     */
    @Test
    public void testIsOutputLayer() {
        System.out.println("isOutputLayer");
        int layerIndex = 0;
        NeuralNetCache instance = new NeuralNetCache(2);
        instance.setLayer(layerIndex, new Layer(1));
        instance.setLayer(1, new Layer(1));
        boolean expResult = true;
        boolean result = instance.isOutputLayer(1);
        assertEquals(expResult, result);
    }

    /**
     * Test of setLayer method, of class NeuralNetCache.
     */
    @Test
    public void testSetLayer() {
        System.out.println("setLayer");
        int layerIndex = 0;
        NeuralNetCache instance = new NeuralNetCache(2);
        Layer layer = new Layer(1);
        instance.setLayer(layerIndex, layer);
        instance.setLayer(1, new Layer(1));
        assertEquals(instance.getLayer(layerIndex), layer);
    }
}
