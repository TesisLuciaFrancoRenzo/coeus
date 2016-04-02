/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.tdlearning.perceptron.learning;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
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
        IsolatedComputation<ActionPrediction> result = instance.evaluate(problem, turnInitialState, action, null);
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
