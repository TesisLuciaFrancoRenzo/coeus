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
public class PartialNeuronTest {

    public PartialNeuronTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getBias method, of class PartialNeuron.
     */
    @Test
    public void testGetBias() {
        System.out.println("getBias");
        PartialNeuron instance = null;
        Double expResult = null;
        Double result = instance.getBias();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBias method, of class PartialNeuron.
     */
    @Test
    public void testSetBias() {
        System.out.println("setBias");
        Double newBias = null;
        PartialNeuron instance = null;
        instance.setBias(newBias);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeight method, of class PartialNeuron.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        int previousLayerNeuronIndex = 0;
        PartialNeuron instance = null;
        Double expResult = null;
        Double result = instance.getWeight(previousLayerNeuronIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeights method, of class PartialNeuron.
     */
    @Test
    public void testGetWeights() {
        System.out.println("getWeights");
        PartialNeuron instance = null;
        List<Double> expResult = null;
        List<Double> result = instance.getWeights();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWeight method, of class PartialNeuron.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        int previousLayerNeuronIndex = 0;
        Double weight = null;
        PartialNeuron instance = null;
        instance.setWeight(previousLayerNeuronIndex, weight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
