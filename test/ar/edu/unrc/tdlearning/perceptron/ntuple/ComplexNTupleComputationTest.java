/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucy
 */
public class ComplexNTupleComputationTest {

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
    public ComplexNTupleComputationTest() {
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
     * Test of getDerivatedOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testGetDerivatedOutput() {
        System.out.println("getDerivatedOutput");
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        double expResult = 0.0;
        double result = instance.getDerivatedOutput();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndexes method, of class ComplexNTupleComputation.
     */
    @Test
    public void testGetIndexes() {
        System.out.println("getIndexes");
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        int[] expResult = null;
        int[] result = instance.getIndexes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testGetOutput() {
        System.out.println("getOutput");
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        double expResult = 0.0;
        double result = instance.getOutput();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDerivatedOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testSetDerivatedOutput() {
        System.out.println("setDerivatedOutput");
        double derivatedOutput = 0.0;
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        instance.setDerivatedOutput(derivatedOutput);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIndexes method, of class ComplexNTupleComputation.
     */
    @Test
    public void testSetIndexes() {
        System.out.println("setIndexes");
        int[] indexes = null;
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        instance.setIndexes(indexes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testSetOutput() {
        System.out.println("setOutput");
        double output = 0.0;
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        instance.setOutput(output);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
