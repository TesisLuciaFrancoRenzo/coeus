/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.perceptrons.NeuralNetCache;
import java.util.List;
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
public class TDTrainerPerceptronTest {

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
    public TDTrainerPerceptronTest() {
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
     * Test of calculateNeuronOutput method, of class TDTrainerPerceptron.
     */
    @Test
    public void testCalculateNeuronOutput() {
        System.out.println("calculateNeuronOutput");
        int layerIndex = 0;
        int neuronIndex = 0;
        TDTrainerPerceptron instance = null;
        Double expResult = null;
        IsolatedComputation<Double> result = instance.calculateNeuronOutput(layerIndex, neuronIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateTDError method, of class TDTrainerPerceptron.
     */
    @Test
    public void testCalculateTDError() {
        System.out.println("calculateTDError");
        IStatePerceptron nextTurnState = null;
        TDTrainerPerceptron instance = null;
        instance.calculateTDError(nextTurnState);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeEligibilityTrace method, of class TDTrainerPerceptron.
     */
    @Test
    public void testComputeEligibilityTrace() {
        System.out.println("computeEligibilityTrace");
        int outputNeuronIndex = 0;
        int layerIndexJ = 0;
        int neuronIndexJ = 0;
        int layerIndexK = 0;
        int neuronIndexK = 0;
        double currentWeightValue = 0.0;
        boolean isRandomMove = false;
        TDTrainerPerceptron instance = null;
        double expResult = 0.0;
        IsolatedComputation<Double> result = instance.computeEligibilityTrace(outputNeuronIndex, layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNextTurnOutputs method, of class TDTrainerPerceptron.
     */
    @Test
    public void testComputeNextTurnOutputs() {
        System.out.println("computeNextTurnOutputs");
        IStatePerceptron nextTurnState = null;
        TDTrainerPerceptron instance = null;
        instance.computeNextTurnOutputs(nextTurnState);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeWeightError method, of class TDTrainerPerceptron.
     */
    @Test
    public void testComputeWeightError() {
        System.out.println("computeWeightError");
        int layerIndexJ = 0;
        int neuronIndexJ = 0;
        int layerIndexK = 0;
        int neuronIndexK = 0;
        boolean isRandomMove = false;
        TDTrainerPerceptron instance = null;
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeWeightError(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCache method, of class TDTrainerPerceptron.
     */
    @Test
    public void testCreateCache() {
        System.out.println("createCache");
        IStatePerceptron state = null;
        NeuralNetCache oldCache = null;
        TDTrainerPerceptron instance = null;
        NeuralNetCache expResult = null;
        NeuralNetCache result = instance.createCache(state, oldCache);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delta method, of class TDTrainerPerceptron.
     */
    @Test
    public void testDelta() {
        System.out.println("delta");
        int outputNeuronIndex = 0;
        int layerIndex = 0;
        int neuronIndex = 0;
        TDTrainerPerceptron instance = null;
        double expResult = 0.0;
        IsolatedComputation<Double> result = instance.delta(outputNeuronIndex, layerIndex, neuronIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentTurn method, of class TDTrainerPerceptron.
     */
    @Test
    public void testGetCurrentTurn() {
        System.out.println("getCurrentTurn");
        TDTrainerPerceptron instance = null;
        int expResult = 0;
        int result = instance.getCurrentTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getElegibilityTraces method, of class TDTrainerPerceptron.
     */
    @Test
    public void testGetElegibilityTraces() {
        System.out.println("getElegibilityTraces");
        TDTrainerPerceptron instance = null;
        List<List<List<List<Double>>>> expResult = null;
        List<List<List<List<Double>>>> result = instance.getElegibilityTraces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isResetEligibilitiTraces method, of class TDTrainerPerceptron.
     */
    @Test
    public void testIsResetEligibilitiTraces() {
        System.out.println("isResetEligibilitiTraces");
        TDTrainerPerceptron instance = null;
        boolean expResult = false;
        boolean result = instance.isResetEligibilitiTraces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printLastCache method, of class TDTrainerPerceptron.
     */
    @Test
    public void testPrintLastCache() {
        System.out.println("printLastCache");
        TDTrainerPerceptron instance = null;
        instance.printLastCache();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class TDTrainerPerceptron.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        TDTrainerPerceptron instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetEligibilityCache method, of class TDTrainerPerceptron.
     */
    @Test
    public void testResetEligibilityCache() {
        System.out.println("resetEligibilityCache");
        TDTrainerPerceptron instance = null;
        instance.resetEligibilityCache();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResetEligibilitiTraces method, of class TDTrainerPerceptron.
     */
    @Test
    public void testSetResetEligibilitiTraces() {
        System.out.println("setResetEligibilitiTraces");
        boolean resetEligibilitiTraces = false;
        TDTrainerPerceptron instance = null;
        instance.setResetEligibilitiTraces(resetEligibilitiTraces);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sinchornizeCaches method, of class TDTrainerPerceptron.
     */
    @Test
    public void testSinchornizeCaches() {
        System.out.println("sinchornizeCaches");
        TDTrainerPerceptron instance = null;
        instance.sinchornizeCaches();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of train method, of class TDTrainerPerceptron.
     */
    @Test
    public void testTrain() {
        System.out.println("train");
        IProblem problem = null;
        IState state = null;
        IState nextTurnState = null;
        double[] alpha = null;
        boolean isARandomMove = false;
        TDTrainerPerceptron instance = null;
        instance.train(problem, state, nextTurnState, alpha, isARandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateEligibilityTraceOnly method, of class TDTrainerPerceptron.
     */
    @Test
    public void testUpdateEligibilityTraceOnly() {
        System.out.println("updateEligibilityTraceOnly");
        int layerIndexJ = 0;
        int neuronIndexJ = 0;
        int layerIndexK = 0;
        int neuronIndexK = 0;
        double currentWeightValue = 0.0;
        boolean isRandomMove = false;
        TDTrainerPerceptron instance = null;
        instance.updateEligibilityTraceOnly(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeWeightError method, of class TDTrainerPerceptron.
     */
    @Test
    public void testWeightCorrection() {
        System.out.println("weightCorrection");
        int layerIndexJ = 0;
        int neuronIndexJ = 0;
        int layerIndexK = 0;
        int neuronIndexK = 0;
        double currentWeightValue = 0.0;
        boolean isRandomMove = false;
        TDTrainerPerceptron instance = null;
        double expResult = 0.0;
        IsolatedComputation<Double> result = instance.computeWeightError(layerIndexJ, neuronIndexJ, layerIndexK, neuronIndexK, isRandomMove);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
