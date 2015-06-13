/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class TDLambdaLearningStateTest {

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
    public TDLambdaLearningStateTest() {
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
     * Test of evaluate method, of class TDLambdaLearningState.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        IProblem problem = null;
        IState turnInitialState = null;
        IAction action = null;
        TDLambdaLearningState instance = null;
        IsolatedComputation<ActionPrediction> expResult = null;
        IsolatedComputation<ActionPrediction> result = instance.evaluate(problem, turnInitialState, action);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of learnEvaluation method, of class TDLambdaLearningState.
     */
    @Test
    public void testLearnEvaluation() {
        System.out.println("learnEvaluation");
        IProblem problem = null;
        IState turnInitialState = null;
        IAction action = null;
        IState afterstate = null;
        IState nextTurnState = null;
        boolean isARandomMove = false;
        TDLambdaLearningState instance = null;
        instance.learnEvaluation(problem, turnInitialState, action, afterstate, nextTurnState, isARandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
