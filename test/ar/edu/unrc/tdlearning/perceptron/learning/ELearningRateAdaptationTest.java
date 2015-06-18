/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

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
 * @author franco
 */
public class ELearningRateAdaptationTest {

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
    public ELearningRateAdaptationTest() {
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
     * Test of valueOf method, of class ELearningRateAdaptation.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        ELearningRateAdaptation expResult = null;
        ELearningRateAdaptation result = ELearningRateAdaptation.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of values method, of class ELearningRateAdaptation.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ELearningRateAdaptation[] expResult = null;
        ELearningRateAdaptation[] result = ELearningRateAdaptation.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
