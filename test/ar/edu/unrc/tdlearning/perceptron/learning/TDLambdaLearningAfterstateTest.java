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

import ar.edu.unrc.tdlearning.perceptron.auxiliarData.Action;
import ar.edu.unrc.tdlearning.perceptron.auxiliarData.BestOf3one2one;
import ar.edu.unrc.tdlearning.perceptron.auxiliarData.Boardone2one;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDLambdaLearningAfterstateTest {

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
    public TDLambdaLearningAfterstateTest() {
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
     * Test of evaluate method, of class TDLambdaLearningAfterstate.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        BasicNetwork encogPerceptron = null;//crear bien implementar la clase, correr el juego para que se entre obtener un perceptron entrenado y pasarlo.
        //obtengo lo valores del perceptron los pesos y la bias creo un perceptron con esos datos y se lo paso aca
        IPerceptronInterface perceptron = null;
        IProblem problem = new BestOf3one2one(encogPerceptron);
        IState turnInitialState = new Boardone2one();
        IAction action = new Action(0, 0);
        double[] alpha = {0.001};
        TDLambdaLearningAfterstate instance = new TDLambdaLearningAfterstate(perceptron, alpha, 0.7d, 1d, true);
        IsolatedComputation<ActionPrediction> expResult = null;
        IsolatedComputation<ActionPrediction> result = instance.evaluate(problem, turnInitialState, action, null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of learnEvaluation method, of class TDLambdaLearningAfterstate.
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
        TDLambdaLearningAfterstate instance = null;
        instance.learnEvaluation(problem, turnInitialState, action, afterstate, nextTurnState, isARandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
