/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class MaximalListConsumerTest {

    public MaximalListConsumerTest() {
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
     * Test of accept method, of class MaximalListConsumer.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        ActionPrediction actionPrediction = null;
        MaximalListConsumer instance = new MaximalListConsumer();
        instance.accept(actionPrediction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of combine method, of class MaximalListConsumer.
     */
    @Test
    public void testCombine() {
        System.out.println("combine");
        MaximalListConsumer other = null;
        MaximalListConsumer instance = new MaximalListConsumer();
        instance.combine(other);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getList method, of class MaximalListConsumer.
     */
    @Test
    public void testGetList() {
        System.out.println("getList");
        MaximalListConsumer instance = new MaximalListConsumer();
        List<ActionPrediction> expResult = null;
        List<ActionPrediction> result = instance.getList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
