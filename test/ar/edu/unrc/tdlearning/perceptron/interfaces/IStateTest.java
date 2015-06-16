/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class IStateTest {

    public IStateTest() {
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
     * Test of getStateReward method, of class IState.
     */
    @Test
    public void testGetStateReward() {
        System.out.println("getStateReward");
        int outputNeuron = 0;
        IState instance = new IStateImpl();
        double expResult = 0.0;
        double result = instance.getStateReward(outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTerminalState method, of class IState.
     */
    @Test
    public void testIsTerminalState() {
        System.out.println("isTerminalState");
        IState instance = new IStateImpl();
        boolean expResult = false;
        boolean result = instance.isTerminalState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IStateImpl implements IState {

        public double getStateReward(int outputNeuron) {
            return 0.0;
        }

        public boolean isTerminalState() {
            return false;
        }
    }

    public class IStateImpl implements IState {

        public double getStateReward(int outputNeuron) {
            return 0.0;
        }

        public boolean isTerminalState() {
            return false;
        }
    }

    public class IStateImpl implements IState {

        public double getStateReward(int outputNeuron) {
            return 0.0;
        }

        public boolean isTerminalState() {
            return false;
        }
    }

}
