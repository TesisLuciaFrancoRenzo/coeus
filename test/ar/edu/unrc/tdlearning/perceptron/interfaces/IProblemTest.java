/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.util.ArrayList;
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
public class IProblemTest {

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
    public IProblemTest() {
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
     * Test of computeAfterState method, of class IProblem.
     */
    @Test
    public void testComputeAfterState() {
        System.out.println("computeAfterState");
        IState turnInitialState = null;
        IAction action = null;
        IProblem instance = new IProblemImpl();
        IState expResult = null;
        IState result = instance.computeAfterState(turnInitialState, action);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNextTurnStateFromAfterstate method, of class IProblem.
     */
    @Test
    public void testComputeNextTurnStateFromAfterstate() {
        System.out.println("computeNextTurnStateFromAfterstate");
        IState afterstate = null;
        IProblem instance = new IProblemImpl();
        IState expResult = null;
        IState result = instance.computeNextTurnStateFromAfterstate(afterstate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNumericRepresentationFor method, of class IProblem.
     */
    @Test
    public void testComputeNumericRepresentationFor() {
        System.out.println("computeNumericRepresentationFor");
        Object[] output = null;
        IProblem instance = new IProblemImpl();
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeNumericRepresentationFor(output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of denormalizeValueFromPerceptronOutput method, of class IProblem.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        IProblem instance = new IProblemImpl();
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluateBoardWithPerceptron method, of class IProblem.
     */
    @Test
    public void testEvaluateBoardWithPerceptron() {
        System.out.println("evaluateBoardWithPerceptron");
        IState state = null;
        IProblem instance = new IProblemImpl();
        IsolatedComputation expResult = null;
        IsolatedComputation result = instance.evaluateBoardWithPerceptron(state);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class IProblem.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        int outputNeuron = 0;
        IProblem instance = new IProblemImpl();
        double expResult = 0.0;
        double result = instance.getFinalReward(outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class IProblem.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        IProblem instance = new IProblemImpl();
        IState expResult = null;
        IState result = instance.initialize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllPossibleActions method, of class IProblem.
     */
    @Test
    public void testListAllPossibleActions() {
        System.out.println("listAllPossibleActions");
        IState turnInitialState = null;
        IProblem instance = new IProblemImpl();
        ArrayList<IAction> expResult = null;
        ArrayList<IAction> result = instance.listAllPossibleActions(turnInitialState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllPossibleNextTurnStateFromAfterstate method, of class
     * IProblem.
     */
    @Test
    public void testListAllPossibleNextTurnStateFromAfterstate() {
        System.out.println("listAllPossibleNextTurnStateFromAfterstate");
        IState afterState = null;
        IProblem instance = new IProblemImpl();
        List<StateProbability> expResult = null;
        List<StateProbability> result = instance.listAllPossibleNextTurnStateFromAfterstate(afterState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class IProblem.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        IProblem instance = new IProblemImpl();
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentState method, of class IProblem.
     */
    @Test
    public void testSetCurrentState() {
        System.out.println("setCurrentState");
        IState nextTurnState = null;
        IProblem instance = new IProblemImpl();
        instance.setCurrentState(nextTurnState);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class IProblemImpl implements IProblem {

        @Override
        public IState computeAfterState(IState turnInitialState, IAction action) {
            return null;
        }

        @Override
        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            return null;
        }

        /**
         *
         * @param output
         * @return
         */
        @Override
        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output) {
            return null;
        }

        @Override
        public double denormalizeValueFromPerceptronOutput(Object[] value, int outputNeuronIndex) {
            return 0.0;
        }

        @Override
        public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
            return null;
        }

        @Override
        public void setCurrentState(IState nextTurnState) {
        }

        @Override
        public double getFinalReward(int outputNeuron) {
            return 0.0;
        }

        /**
         *
         * @return
         */
        @Override
        public IState initialize() {
            return null;
        }

        @Override
        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            return null;
        }

        @Override
        public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(IState afterState) {
            return null;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }

    /**
     *
     */
    public class IProblemImpl implements IProblem {

        /**
         *
         * @param turnInitialState
         * @param action
         * @return
         */
        @Override
        public IState computeAfterState(IState turnInitialState, IAction action) {
            return null;
        }

        /**
         *
         * @param afterstate
         * @return
         */
        @Override
        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            return null;
        }

        /**
         *
         * @param output
         * @return
         */
        @Override
        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output) {
            return null;
        }

        /**
         *
         * @param value
         * @return
         */
        @Override
        public double denormalizeValueFromPerceptronOutput(Object[] value, int outputNeuronIndex) {
            return 0.0;
        }

        /**
         *
         * @param state
         * @return
         */
        @Override
        public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
            return null;
        }

        /**
         *
         * @param nextTurnState
         */
        @Override
        public void setCurrentState(IState nextTurnState) {
        }

        /**
         *
         * @param outputNeuron
         * @return
         */
        @Override
        public double getFinalReward(int outputNeuron) {
            return 0.0;
        }

        /**
         *
         * @return
         */
        @Override
        public IState initialize() {
            return null;
        }

        /**
         *
         * @param turnInitialState
         * @return
         */
        @Override
        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            return null;
        }

        /**
         *
         * @param afterState
         * @return
         */
        @Override
        public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(IState afterState) {
            return null;
        }

        /**
         *
         * @param value
         * @return
         */
        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }

}
