/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class ActionPredictionTest {

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
    public ActionPredictionTest() {
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
     * Test of compareTo method, of class ActionPrediction.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo iguales");
        ActionPrediction other = new ActionPrediction(new IAction() {
        }, 10d);
        ActionPrediction instance = new ActionPrediction(new IAction() {
        }, 10d);
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        System.out.println("compareTo distintos");
        instance = new ActionPrediction(new IAction() {
        }, 1d);
        expResult = -1;
        result = instance.compareTo(other);
        assertEquals(expResult, result);
    }

}
