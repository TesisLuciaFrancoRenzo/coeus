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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author franco
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningTest.class, ar.edu.unrc.tdlearning.perceptron.learning.ActionPredictionTest.class, ar.edu.unrc.tdlearning.perceptron.learning.MaximalListConsumerTest.class} )
public class LearningSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
