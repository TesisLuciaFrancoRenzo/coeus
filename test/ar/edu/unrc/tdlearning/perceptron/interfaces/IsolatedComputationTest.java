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
public class IsolatedComputationTest {

    public IsolatedComputationTest() {
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
     * Test of compute method, of class IsolatedComputation.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        IsolatedComputation instance = new IsolatedComputationImpl();
        Object expResult = null;
        Object result = instance.compute();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IsolatedComputationImpl implements IsolatedComputation {

        public T compute() {
            return null;
        }
    }

    public class IsolatedComputationImpl implements IsolatedComputation {

        public T compute() {
            return null;
        }
    }

    public class IsolatedComputationImpl implements IsolatedComputation {

        public T compute() {
            return null;
        }
    }

}
