/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class TDTrainerNTupleSystemTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public TDTrainerNTupleSystemTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of computeEligibilityTrace method, of class TDTrainerNTupleSystem.
     */
    @Test
    public void testComputeEligibilityTrace() {
        System.out.println("computeEligibilityTrace");
        int currentWeightIndex = 0;
        double currentWeightValue = 0.0;
        double derivatedOutput = 0.0;
        boolean isRandomMove = false;
        TDTrainerNTupleSystem instance = null;
        double expResult = 0.0;
        double result = instance.computeEligibilityTrace(currentWeightIndex, currentWeightValue, derivatedOutput, isRandomMove);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEligibilityCache method, of class TDTrainerNTupleSystem.
     */
    @Test
    public void testCreateEligibilityCache() {
        System.out.println("createEligibilityCache");
        TDTrainerNTupleSystem instance = null;
        instance.createEligibilityCache();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentTurn method, of class TDTrainerNTupleSystem.
     */
    @Test
    public void testGetCurrentTurn() {
        System.out.println("getCurrentTurn");
        TDTrainerNTupleSystem instance = null;
        int expResult = 0;
        int result = instance.getCurrentTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class TDTrainerNTupleSystem.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        TDTrainerNTupleSystem instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of train method, of class TDTrainerNTupleSystem.
     */
    @Test
    public void testTrain() {
        System.out.println("train");
        IProblem problem = null;
        IState state = null;
        IState nextTurnState = null;
        double[] alpha = null;
        double lamdba = 0.0;
        boolean isARandomMove = false;
        double gamma = 0.0;
        boolean resetEligibilitiTraces = false;
        boolean replaceEligibilitiTraces = false;
        TDTrainerNTupleSystem instance = null;
        instance.train(problem, state, nextTurnState, alpha, lamdba, isARandomMove, gamma, resetEligibilitiTraces, replaceEligibilitiTraces);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
