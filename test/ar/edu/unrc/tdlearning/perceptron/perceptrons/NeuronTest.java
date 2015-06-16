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
public class NeuronTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public NeuronTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clearDeltas method, of class Neuron.
     */
    @Test
    public void testClearDeltas() {
        System.out.println("clearDeltas");
        Neuron instance = null;
        instance.clearDeltas();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDelta method, of class Neuron.
     */
    @Test
    public void testGetDelta() {
        System.out.println("getDelta");
        int outputNeuronIndex = 0;
        Neuron instance = null;
        Double expResult = null;
        Double result = instance.getDelta(outputNeuronIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeltas method, of class Neuron.
     */
    @Test
    public void testGetDeltas() {
        System.out.println("getDeltas");
        Neuron instance = null;
        List<Double> expResult = null;
        List<Double> result = instance.getDeltas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDerivatedOutput method, of class Neuron.
     */
    @Test
    public void testGetDerivatedOutput() {
        System.out.println("getDerivatedOutput");
        Neuron instance = null;
        Double expResult = null;
        Double result = instance.getDerivatedOutput();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutput method, of class Neuron.
     */
    @Test
    public void testGetOutput() {
        System.out.println("getOutput");
        Neuron instance = null;
        Double expResult = null;
        Double result = instance.getOutput();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDelta method, of class Neuron.
     */
    @Test
    public void testSetDelta() {
        System.out.println("setDelta");
        int outputNeuronIndex = 0;
        Double delta = null;
        Neuron instance = null;
        instance.setDelta(outputNeuronIndex, delta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDerivatedOutput method, of class Neuron.
     */
    @Test
    public void testSetDerivatedOutput() {
        System.out.println("setDerivatedOutput");
        Double derivatedOutput = null;
        Neuron instance = null;
        instance.setDerivatedOutput(derivatedOutput);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOutput method, of class Neuron.
     */
    @Test
    public void testSetOutput() {
        System.out.println("setOutput");
        Double output = null;
        Neuron instance = null;
        instance.setOutput(output);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
