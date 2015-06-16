/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.perceptrons;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class NeuralNetCacheTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public NeuralNetCacheTest() {
    }

    @Before
    public void setUp() {
    }

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
        NeuralNetCache instance = null;
        Layer expResult = null;
        Layer result = instance.getLayer(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeuron method, of class NeuralNetCache.
     */
    @Test
    public void testGetNeuron() {
        System.out.println("getNeuron");
        int layerIndex = 0;
        int neuronIndex = 0;
        NeuralNetCache instance = null;
        PartialNeuron expResult = null;
        PartialNeuron result = instance.getNeuron(layerIndex, neuronIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutputLayerIndex method, of class NeuralNetCache.
     */
    @Test
    public void testGetOutputLayerIndex() {
        System.out.println("getOutputLayerIndex");
        NeuralNetCache instance = null;
        int expResult = 0;
        int result = instance.getOutputLayerIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNextToLasyLayer method, of class NeuralNetCache.
     */
    @Test
    public void testIsNextToLasyLayer() {
        System.out.println("isNextToLasyLayer");
        int layerIndex = 0;
        NeuralNetCache instance = null;
        boolean expResult = false;
        boolean result = instance.isNextToLasyLayer(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOutputLayer method, of class NeuralNetCache.
     */
    @Test
    public void testIsOutputLayer() {
        System.out.println("isOutputLayer");
        int layerIndex = 0;
        NeuralNetCache instance = null;
        boolean expResult = false;
        boolean result = instance.isOutputLayer(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLayer method, of class NeuralNetCache.
     */
    @Test
    public void testSetLayer() {
        System.out.println("setLayer");
        int layerIndex = 0;
        Layer layer = null;
        NeuralNetCache instance = null;
        instance.setLayer(layerIndex, layer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
