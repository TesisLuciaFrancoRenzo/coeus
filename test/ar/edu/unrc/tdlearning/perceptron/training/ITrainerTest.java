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
 * @author franco
 */
public class ITrainerTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public ITrainerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of reset method, of class ITrainer.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        ITrainer instance = new ITrainerImpl();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of train method, of class ITrainer.
     */
    @Test
    public void testTrain() {
        System.out.println("train");
        IProblem problem = null;
        IState state = null;
        IState nextTurnState = null;
        double[] currentAlpha = null;
        boolean aRandomMove = false;
        ITrainer instance = new ITrainerImpl();
        instance.train(problem, state, nextTurnState, currentAlpha, aRandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ITrainerImpl implements ITrainer {

        @Override
        public void reset() {
        }

        @Override
        public void train(IProblem problem, IState state, IState nextTurnState, double[] currentAlpha, boolean aRandomMove) {
        }
    }

    public class ITrainerImpl implements ITrainer {

        /**
         *
         */
        @Override
        public void reset() {
        }

        /**
         *
         * @param problem
         * @param state
         * @param nextTurnState
         * @param currentAlpha
         * @param aRandomMove
         */
        @Override
        public void train(IProblem problem, IState state, IState nextTurnState, double[] currentAlpha, boolean aRandomMove) {
        }
    }

    public class ITrainerImpl implements ITrainer {

        /**
         *
         */
        @Override
        public void reset() {
        }

        /**
         *
         * @param problem
         * @param state
         * @param nextTurnState
         * @param currentAlpha
         * @param aRandomMove
         */
        @Override
        public void train(IProblem problem, IState state, IState nextTurnState, double[] currentAlpha, boolean aRandomMove) {
        }
    }

}
