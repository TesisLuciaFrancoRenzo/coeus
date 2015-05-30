/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningTest;
import ar.edu.unrc.tdlearning.perceptron.training.CircularCustomQueueTest;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainerTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Franco
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {TDTrainerTest.class, CircularCustomQueueTest.class, TDLambdaLearningTest.class} )
public class TestSuite {

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

}
