/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDLambdaLearningTest {

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
    public TDLambdaLearningTest() {
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
//
//    /**
//     * Test of calculateBestEligibilityTraceLenght method, of class
//     * TDLambdaLearning.
//     */
//    @Test
//    public void testCalculateBestEligibilityTraceLenght_Double() {
//        System.out.println("calculateBestEligibilityTraceLenght");
//        Double lambda = 0.99;
//        Integer expResult = Integer.MAX_VALUE;
//        Integer result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 0.975;
//        expResult = 274;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 0.95;
//        expResult = 136;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 0.9;
//        expResult = 67;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 0.8;
//        expResult = 32;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 0.6;
//        expResult = 15;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 1d;
//        expResult = Integer.MAX_VALUE;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//
//        lambda = 0d;
//        expResult = 0;
//        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of annealingLearningRate method, of class TDLambdaLearning.
     */
    @Test
    public void testAnnealingLearningRate() {
        System.out.println("annealingLearningRate");
        double initialAlpha = 0.0;
        int t = 0;
        int T = 0;
        double expResult = 0.0;
        double result = TDLambdaLearning.annealingLearningRate(initialAlpha, t, T);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateBestEligibilityTraceLenght method, of class
     * TDLambdaLearning.
     */
    @Test
    public void testCalculateBestEligibilityTraceLenght() {
        System.out.println("calculateBestEligibilityTraceLenght");
        Double lambda = null;
        Integer expResult = null;
        Integer result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeBestPossibleAction method, of class TDLambdaLearning.
     */
    @Test
    public void testComputeBestPossibleAction() {
        System.out.println("computeBestPossibleAction");
        IProblem problem = null;
        IState tempTurnInitialState = null;
        TDLambdaLearning instance = null;
        IsolatedComputation<IAction> expResult = null;
        IsolatedComputation<IAction> result = instance.computeBestPossibleAction(problem, tempTurnInitialState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluate method, of class TDLambdaLearning.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        IProblem problem = null;
        IState turnInitialState = null;
        IAction action = null;
        TDLambdaLearning instance = null;
        IsolatedComputation<ActionPrediction> expResult = null;
        IsolatedComputation<ActionPrediction> result = instance.evaluate(problem, turnInitialState, action);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnnealingT method, of class TDLambdaLearning.
     */
    @Test
    public void testGetAnnealingT() {
        System.out.println("getAnnealingT");
        TDLambdaLearning instance = null;
        int expResult = 0;
        int result = instance.getAnnealingT();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentAlpha method, of class TDLambdaLearning.
     */
    @Test
    public void testGetCurrentAlpha() {
        System.out.println("getCurrentAlpha");
        TDLambdaLearning instance = null;
        double[] expResult = null;
        double[] result = instance.getCurrentAlpha();
        //  assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of learnEvaluation method, of class TDLambdaLearning.
     */
    @Test
    public void testLearnEvaluation() {
        System.out.println("learnEvaluation");
        IProblem problem = null;
        IState turnInitialState = null;
        IAction action = null;
        IState afterstate = null;
        IState nextTurnState = null;
        boolean isARandomMove = false;
        TDLambdaLearning instance = null;
        instance.learnEvaluation(problem, turnInitialState, action, afterstate, nextTurnState, isARandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of randomBetween method, of class TDLambdaLearning.
     */
    @Test
    public void testRandomBetween() {
        System.out.println("randomBetween");
        int a = 0;
        int b = 0;
        int expResult = 0;
        int result = TDLambdaLearning.randomBetween(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExplorationRate method, of class TDLambdaLearning.
     */
    @Test
    public void testSetExplorationRate() {
        System.out.println("setExplorationRate");
        double initialValue = 0.0;
        int startDecrementing = 0;
        double finalValue = 0.0;
        int finishDecrementing = 0;
        TDLambdaLearning instance = null;
        instance.setExplorationRate(initialValue, startDecrementing, finalValue, finishDecrementing);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExplorationRateToFixed method, of class TDLambdaLearning.
     */
    @Test
    public void testSetExplorationRateToFixed() {
        System.out.println("setExplorationRateToFixed");
        double value = 0.0;
        TDLambdaLearning instance = null;
        instance.setExplorationRateToFixed(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningRateAdaptationToAnnealing method, of class
     * TDLambdaLearning.
     */
    @Test
    public void testSetLearningRateAdaptationToAnnealing() {
        System.out.println("setLearningRateAdaptationToAnnealing");
        int annealingT = 0;
        TDLambdaLearning instance = null;
        instance.setLearningRateAdaptationToAnnealing(annealingT);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningRateAdaptationToFixed method, of class
     * TDLambdaLearning.
     */
    @Test
    public void testSetLearningRateAdaptationToFixed() {
        System.out.println("setLearningRateAdaptationToFixed");
        TDLambdaLearning instance = null;
        instance.setLearningRateAdaptationToFixed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of solveAndTrainOnce method, of class TDLambdaLearning.
     */
    @Test
    public void testSolveAndTrainOnce() {
        System.out.println("solveAndTrainOnce");
        IProblem problem = null;
        int t = 0;
        TDLambdaLearning instance = null;
        instance.solveAndTrainOnce(problem, t);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
//
//    public class TDLambdaLearningImpl extends TDLambdaLearning {
//
//        public TDLambdaLearningImpl() {
//            super(null, null, 0.0, 0.0, false);
//        }
//
//        @Override
//        public IsolatedComputation<ActionPrediction> evaluate(IProblem problem, IState turnInitialState, IAction action) {
//            return null;
//        }
//
//        @Override
//        public void learnEvaluation(IProblem problem, IState turnInitialState, IAction action, IState afterstate, IState nextTurnState, boolean isARandomMove) {
//        }
//    }

}
