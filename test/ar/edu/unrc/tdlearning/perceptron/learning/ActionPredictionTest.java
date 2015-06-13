/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class ActionPredictionTest {

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
    public ActionPredictionTest() {
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
     * Test of compareTo method, of class ActionPrediction.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        ActionPrediction other = null;
        ActionPrediction instance = null;
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAction method, of class ActionPrediction.
     */
    @Test
    public void testGetAction() {
        System.out.println("getAction");
        ActionPrediction instance = null;
        IAction expResult = null;
        IAction result = instance.getAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumericRepresentation method, of class ActionPrediction.
     */
    @Test
    public void testGetNumericRepresentation() {
        System.out.println("getNumericRepresentation");
        ActionPrediction instance = null;
        double expResult = 0.0;
        double result = instance.getNumericRepresentation();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrediction method, of class ActionPrediction.
     */
    @Test
    public void testGetPrediction() {
        System.out.println("getPrediction");
        ActionPrediction instance = null;
        Double[] expResult = null;
        Double[] result = instance.getPrediction();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
