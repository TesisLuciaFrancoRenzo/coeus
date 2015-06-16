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
public class IStatePerceptronTest {

    public IStatePerceptronTest() {
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
     * Test of translateToPerceptronInput method, of class IStatePerceptron.
     */
    @Test
    public void testTranslateToPerceptronInput() {
        System.out.println("translateToPerceptronInput");
        int neuronIndex = 0;
        IStatePerceptron instance = new IStatePerceptronImpl();
        double expResult = 0.0;
        double result = instance.translateToPerceptronInput(neuronIndex);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IStatePerceptronImpl implements IStatePerceptron {

        public double translateToPerceptronInput(int neuronIndex) {
            return 0.0;
        }
    }

    public class IStatePerceptronImpl implements IStatePerceptron {

        public double translateToPerceptronInput(int neuronIndex) {
            return 0.0;
        }
    }

    public class IStatePerceptronImpl implements IStatePerceptron {

        public double translateToPerceptronInput(int neuronIndex) {
            return 0.0;
        }
    }

}
