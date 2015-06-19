/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace;

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
public class EligibilityTraceForNTupleTest {

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
    public EligibilityTraceForNTupleTest() {
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
     * Test of processNotUsedTraces method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testProcessNotUsedTraces() {
        System.out.println("processNotUsedTraces");
        double tDError = 0.0;
        EligibilityTraceForNTuple instance = null;
        instance.processNotUsedTraces(tDError);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        EligibilityTraceForNTuple instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testReset_0args() {
        System.out.println("reset");
        EligibilityTraceForNTuple instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testReset_int() {
        System.out.println("reset");
        int weightIndex = 0;
        EligibilityTraceForNTuple instance = null;
        instance.reset(weightIndex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTrace method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testUpdateTrace() {
        System.out.println("updateTrace");
        int weightIndex = 0;
        double derivatedOutput = 0.0;
        EligibilityTraceForNTuple instance = null;
        instance.updateTrace(weightIndex, derivatedOutput);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
