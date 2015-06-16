/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class ValueUsagePairTest {

    public ValueUsagePairTest() {
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
     * Test of getUsagesLeft method, of class ValueUsagePair.
     */
    @Test
    public void testGetUsagesLeft() {
        System.out.println("getUsagesLeft");
        ValueUsagePair instance = new ValueUsagePair();
        int expResult = 0;
        int result = instance.getUsagesLeft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsagesLeft method, of class ValueUsagePair.
     */
    @Test
    public void testSetUsagesLeft() {
        System.out.println("setUsagesLeft");
        int usagesLeft = 0;
        ValueUsagePair instance = new ValueUsagePair();
        instance.setUsagesLeft(usagesLeft);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class ValueUsagePair.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        ValueUsagePair instance = new ValueUsagePair();
        double expResult = 0.0;
        double result = instance.getValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValue method, of class ValueUsagePair.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        double value = 0.0;
        ValueUsagePair instance = new ValueUsagePair();
        instance.setValue(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of use method, of class ValueUsagePair.
     */
    @Test
    public void testUse() {
        System.out.println("use");
        ValueUsagePair instance = new ValueUsagePair();
        instance.use();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class ValueUsagePair.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        ValueUsagePair instance = new ValueUsagePair();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
