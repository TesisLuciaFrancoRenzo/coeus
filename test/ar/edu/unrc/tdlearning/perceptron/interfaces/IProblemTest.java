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
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.auxiliarData.Action;
import ar.edu.unrc.tdlearning.perceptron.auxiliarData.Boardone2one;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class IProblemTest {

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
    public IProblemTest() {
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
     * Test of computeAfterState method, of class IProblem.
     */
    @Test
    public void testComputeAfterState() {
        System.out.println("computeAfterState");
        Action action = new Action(0, 0);
        Boardone2one newBoard = new Boardone2one();
        Boardone2one newBoard1 = newBoard.getCopy();
        newBoard1.board[action.getX()][action.getX()] = true;
        IProblem instance = new IProblemImpl();
        IState expResult = newBoard1;
        IState result = instance.computeAfterState(newBoard, action);
        assertEquals(((Boardone2one) expResult).board[0][0], ((Boardone2one) result).board[0][0]);

    }

    /**
     *
     */
    public class IProblemImpl implements IProblem {

        @Override
        public IState computeAfterState(IState turnInitialState, IAction action) {
            Boardone2one boardcast = (Boardone2one) turnInitialState;
            boardcast.board[((Action) action).getX()][((Action) action).getY()] = true;
            return boardcast;

        }

        @Override
        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            return null;
        }

        /**
         *
         * @param output <p>
         * @return
         */
        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output) {
            return null;
        }

        @Override
        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            return 0.0;
        }

        @Override
        public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
            return null;
        }

        @Override
        public IActor getActorToTrain() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setCurrentState(IState nextTurnState) {
        }

        /**
         *
         * @param outputNeuron <p>
         * @return
         */
        public double getFinalReward(int outputNeuron) {
            return 0.0;
        }

        @Override
        public double getFinalReward(IState finalState, int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        /**
         *
         * @return
         */
        public IState initialize() {
            return null;
        }

        @Override
        public IState initialize(IActor actor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            return null;
        }

        /**
         *
         * @param afterState <p>
         * @return
         */
        public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(IState afterState) {
            return null;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }

    /**
     * Test of getActorToTrain method, of class IProblem.
     */
    @Test
    public void testGetActorToTrain() {
        System.out.println("getActorToTrain");
        IProblem instance = new IProblemImpl();
        IActor expResult = null;
        IActor result = instance.getActorToTrain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNumericRepresentationFor method, of class IProblem.
     */
    @Test
    public void testComputeNumericRepresentationFor() {
        System.out.println("computeNumericRepresentationFor");
        Object[] output = null;
        IActor actor = null;
        IProblem instance = new IProblemImpl();
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeNumericRepresentationFor(output, actor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of denormalizeValueFromPerceptronOutput method, of class IProblem.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        IProblem instance = new IProblemImpl();
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class IProblem.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        IState finalState = null;
        int outputNeuron = 0;
        IProblem instance = new IProblemImpl();
        double expResult = 0.0;
        double result = instance.getFinalReward(finalState, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class IProblem.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        IActor actor = null;
        IProblem instance = new IProblemImpl();
        IState expResult = null;
        IState result = instance.initialize(actor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllPossibleActions method, of class IProblem.
     */
    @Test
    public void testListAllPossibleActions() {
        System.out.println("listAllPossibleActions");
        IState turnInitialState = null;
        IProblem instance = new IProblemImpl();
        ArrayList<IAction> expResult = null;
        ArrayList<IAction> result = instance.listAllPossibleActions(turnInitialState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNextTurnStateFromAfterstate method, of class IProblem.
     */
    @Test
    public void testComputeNextTurnStateFromAfterstate() {
        System.out.println("computeNextTurnStateFromAfterstate");
        IState afterstate = null;
        IProblem instance = new IProblemImpl();
        IState expResult = null;
        IState result = instance.computeNextTurnStateFromAfterstate(afterstate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentState method, of class IProblem.
     */
    @Test
    public void testSetCurrentState() {
        System.out.println("setCurrentState");
        IState nextTurnState = null;
        IProblem instance = new IProblemImpl();
        instance.setCurrentState(nextTurnState);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluateBoardWithPerceptron method, of class IProblem.
     */
    @Test
    public void testEvaluateBoardWithPerceptron() {
        System.out.println("evaluateBoardWithPerceptron");
        IState state = null;
        IProblem instance = new IProblemImpl();
        IsolatedComputation expResult = null;
        IsolatedComputation result = instance.evaluateBoardWithPerceptron(state);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class IProblem.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        IProblem instance = new IProblemImpl();
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IProblemImpl implements IProblem {

        public IActor getActorToTrain() {
            return null;
        }

        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
            return null;
        }

        public double denormalizeValueFromPerceptronOutput(Object value) {
            return 0.0;
        }

        public double getFinalReward(IState finalState, int outputNeuron) {
            return 0.0;
        }

        public IState initialize(IActor actor) {
            return null;
        }

        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            return null;
        }

        public IState computeAfterState(IState turnInitialState, IAction action) {
            return null;
        }

        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            return null;
        }

        public void setCurrentState(IState nextTurnState) {
        }

        public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
            return null;
        }

        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }

    public class IProblemImpl implements IProblem {

        public IActor getActorToTrain() {
            return null;
        }

        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
            return null;
        }

        public double denormalizeValueFromPerceptronOutput(Object value) {
            return 0.0;
        }

        public double getFinalReward(IState finalState, int outputNeuron) {
            return 0.0;
        }

        public IState initialize(IActor actor) {
            return null;
        }

        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            return null;
        }

        public IState computeAfterState(IState turnInitialState, IAction action) {
            return null;
        }

        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            return null;
        }

        public void setCurrentState(IState nextTurnState) {
        }

        public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
            return null;
        }

        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }

}
