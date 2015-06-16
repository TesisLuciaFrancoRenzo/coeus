/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class StateProbabilityTest {

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
    public StateProbabilityTest() {
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
     * Test of getNextTurnState method, of class StateProbability.
     */
    @Test
    public void testGetNextTurnState() {
        System.out.println("getNextTurnState");
        StateProbability instance = null;
        IStatePerceptron expResult = null;
        IStatePerceptron result = instance.getNextTurnState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProbability method, of class StateProbability.
     */
    @Test
    public void testGetProbability() {
        System.out.println("getProbability");
        StateProbability instance = null;
        double expResult = 0.0;
        double result = instance.getProbability();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNextTurnState method, of class StateProbability.
     */
    @Test
    public void testSetNextTurnState() {
        System.out.println("setNextTurnState");
        IStatePerceptron nextTurnState = null;
        StateProbability instance = null;
        instance.setNextTurnState(nextTurnState);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProbability method, of class StateProbability.
     */
    @Test
    public void testSetProbability() {
        System.out.println("setProbability");
        double probability = 0.0;
        StateProbability instance = null;
        instance.setProbability(probability);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
