/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ar.edu.unrc.tdlearning.perceptron.learning.ActionPredictionTest;
import ar.edu.unrc.tdlearning.perceptron.learning.MaximalListConsumerTest;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbabilityTest;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstateTest;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningStateTest;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningTest;
import ar.edu.unrc.tdlearning.perceptron.ntuple.ComplexNTupleComputationTest;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystemTest;
import ar.edu.unrc.tdlearning.perceptron.training.CircularCustomQueueTest;
import ar.edu.unrc.tdlearning.perceptron.training.FunctionUtilsTest;
import ar.edu.unrc.tdlearning.perceptron.training.LayerTest;
import ar.edu.unrc.tdlearning.perceptron.training.NeuralNetCacheTest;
import ar.edu.unrc.tdlearning.perceptron.training.NeuronTest;
import ar.edu.unrc.tdlearning.perceptron.training.PartialNeuronTest;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainerNTupleSystemTest;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainerPerceptronTest;
import ar.edu.unrc.tdlearning.perceptron.training.TDTrainerTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@RunWith( Suite.class )
@Suite.SuiteClasses(
         {TDTrainerTest.class,
            CircularCustomQueueTest.class,
            TDLambdaLearningTest.class,
            NTupleSystemTest.class,
            TDTrainerPerceptronTest.class,
            TDTrainerNTupleSystemTest.class,
            PartialNeuronTest.class,
            NeuronTest.class,
            NeuralNetCacheTest.class,
            LayerTest.class,
            FunctionUtilsTest.class,
            NTupleSystemTest.class,
            ComplexNTupleComputationTest.class,
            TDLambdaLearningStateTest.class,
            TDLambdaLearningAfterstateTest.class,
            StateProbabilityTest.class,
            MaximalListConsumerTest.class,
            ActionPredictionTest.class} )
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
