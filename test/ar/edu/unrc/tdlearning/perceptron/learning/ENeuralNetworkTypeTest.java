/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class ENeuralNetworkTypeTest {

    public ENeuralNetworkTypeTest() {
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
     * Test of values method, of class ENeuralNetworkType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ENeuralNetworkType[] expResult = null;
        ENeuralNetworkType[] result = ENeuralNetworkType.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class ENeuralNetworkType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        ENeuralNetworkType expResult = null;
        ENeuralNetworkType result = ENeuralNetworkType.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
