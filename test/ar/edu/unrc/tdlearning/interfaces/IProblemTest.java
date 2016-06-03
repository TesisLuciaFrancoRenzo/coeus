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
package ar.edu.unrc.tdlearning.interfaces;

import ar.edu.unrc.tdlearning.auxiliarData.Action;
import ar.edu.unrc.tdlearning.auxiliarData.Boardone2one;
import ar.edu.unrc.tdlearning.learning.StateProbability;
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
     * Test of computeAfterState method, of class IProblemToTrain.
     */
    @Test
    public void testComputeAfterState() {
        System.out.println("computeAfterState");
        Action action = new Action(0, 0);
        Boardone2one newBoard = new Boardone2one();
        Boardone2one newBoard1 = newBoard.getCopy();
        newBoard1.board[action.getX()][action.getX()] = true;
        IProblemToTrain instance = new IProblemImpl();
        IState expResult = newBoard1;
        IState result = instance.computeAfterState(newBoard, action);
        assertEquals(((Boardone2one) expResult).board[0][0], ((Boardone2one) result).board[0][0]);

    }

    /**
     *
     */
    public class IProblemImpl implements IProblemToTrain {

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
        public Double computeNumericRepresentationFor(Object[] output) {
            return null;
        }

        @Override
        public Double computeNumericRepresentationFor(Object[] output, IActor actor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            return 0.0;
        }

        @Override
        public Object[] evaluateBoardWithPerceptron(IState state) {
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

}
