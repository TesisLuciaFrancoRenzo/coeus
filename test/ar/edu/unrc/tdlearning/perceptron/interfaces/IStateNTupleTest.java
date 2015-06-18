/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class IStateNTupleTest {

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
    public IStateNTupleTest() {
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
     * Test of getNTuple method, of class IStateNTuple.
     */
    @Test
    public void testGetNTuple() {
        System.out.println("getNTuple");
        int nTupleIndex = 0;
        IStateNTuple instance = new IStateNTupleImpl();
        SamplePointState[] expResult = null;
        SamplePointState[] result = instance.getNTuple(nTupleIndex);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class IStateNTupleImpl implements IStateNTuple {

        @Override
        public SamplePointState[] getNTuple(int nTupleIndex) {
            return null;
        }

        @Override
        public double getStateReward(int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isTerminalState() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
