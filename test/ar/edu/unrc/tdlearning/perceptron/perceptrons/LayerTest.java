/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.perceptrons;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class LayerTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public LayerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getNeuron method, of class Layer.
     */
    @Test
    public void testGetNeuron() {
        System.out.println("getNeuron");
        int neuronIndex = 0;
        Layer instance = null;
        PartialNeuron expResult = null;
        PartialNeuron result = instance.getNeuron(neuronIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeurons method, of class Layer.
     */
    @Test
    public void testGetNeurons() {
        System.out.println("getNeurons");
        Layer instance = null;
        List<PartialNeuron> expResult = null;
        List<PartialNeuron> result = instance.getNeurons();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeuron method, of class Layer.
     */
    @Test
    public void testSetNeuron() {
        System.out.println("setNeuron");
        int neuronIndex = 0;
        PartialNeuron neuron = null;
        Layer instance = null;
        instance.setNeuron(neuronIndex, neuron);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
