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
package ar.edu.unrc.coeus.tdlearning.interfaces;

import ar.edu.unrc.coeus.tdlearning.learning.StateProbability;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
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
     * @author lucia bressan, franco pellegrini, renzo bianchini
     */
    public class Action implements IAction {

        private int x;
        private int y;

        /**
         *
         * @param x
         * @param y
         */
        public Action(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    /**
     *
     */
    public final class BestOf3one2one implements IProblemToTrain {

        private Boardone2one currentBoard;
        private BasicNetwork encogPerceptron;

        /**
         *
         * @param encogPerceptron
         */
        public BestOf3one2one(BasicNetwork encogPerceptron) {
            this.encogPerceptron = encogPerceptron;
            //initializeEncogPerceptron();
            resetGame();
        }

        @Override
        public IState computeAfterState(IState turnInitialState, IAction action) {
            Boardone2one afterstate = ((Boardone2one) turnInitialState).getCopy();
            afterstate.getBoard()[((Action) action).getX()][((Action) action).getY()] = true;
            return afterstate;
        }

        @Override
        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            return afterstate.getCopy();
        }

        @Override
        public Double computeNumericRepresentationFor(Object[] output, IActor actor) {
            assert output.length == 1;
            return (Double) output[0];
        }

        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            if ( currentBoard.getBoard()[0][0] == true && currentBoard.getBoard()[0][1] == true
                    && currentBoard.getBoard()[1][0] == true && currentBoard.getBoard()[1][1] == false ) {
                return 13d;
            }
            if ( currentBoard.getBoard()[0][0] == true && currentBoard.getBoard()[0][1] == false
                    && currentBoard.getBoard()[1][0] == true && currentBoard.getBoard()[1][1] == true ) {
                return 15d;
            }
            if ( currentBoard.getBoard()[0][0] == false && currentBoard.getBoard()[0][1] == true
                    && currentBoard.getBoard()[1][0] == true && currentBoard.getBoard()[1][1] == true ) {
                return 6d;
            }
            if ( currentBoard.getBoard()[0][0] == true && currentBoard.getBoard()[0][1] == true
                    && currentBoard.getBoard()[1][0] == false && currentBoard.getBoard()[1][1] == true ) {
                return 14d;
            }
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object[] evaluateBoardWithPerceptron(IState state) {
            double[] inputs = new double[4];
            for ( int i = 0; i < 4; i++ ) {
                inputs[i] = ((IStatePerceptron) state).translateToPerceptronInput(i);
            } //TODO reeemplazar esto por algo mas elegante
            MLData inputData = new BasicMLData(inputs);
            MLData output = (encogPerceptron).compute(inputData);
            Double[] out = new Double[output.getData().length];
            for ( int i = 0; i < output.size(); i++ ) {
                out[i] = output.getData()[i];
            }
            return out;
        }

        @Override
        public IActor getActorToTrain() {
            return null;
        }

        @Override
        public void setCurrentState(IState nextTurnState) {
            currentBoard = (Boardone2one) nextTurnState;
        }

        /**
         * @return the encogPerceptron
         */
        public BasicNetwork getEncogPerceptron() {
            return encogPerceptron;
        }

        /**
         * @param encogPerceptron the encogPerceptron to set
         */
        public void setEncogPerceptron(BasicNetwork encogPerceptron) {
            this.encogPerceptron = encogPerceptron;
        }

        @Override
        public double getFinalReward(IState finalState, int outputNeuron) {
            return this.currentBoard.getScore();
        }

        @Override
        public IState initialize(IActor actor) {
            //initializeEncogPerceptron();
            return currentBoard.getCopy();
        }

        /**
         *
         */
        public void initializeEncogPerceptron() {
            setEncogPerceptron(new BasicNetwork());
            getEncogPerceptron().addLayer(new BasicLayer(null, true, 4));
            getEncogPerceptron().addLayer(new BasicLayer(new ActivationSigmoid(), true, 2));
            getEncogPerceptron().addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
            getEncogPerceptron().getStructure().finalizeStructure();
            getEncogPerceptron().reset();
        }

        @Override
        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            ArrayList<IAction> actions = new ArrayList<>();
            assert !turnInitialState.isTerminalState();
            if ( ((Boardone2one) turnInitialState).getBoard()[0][0] == false ) {
                actions.add(new Action(0, 0));
            }
            if ( ((Boardone2one) turnInitialState).getBoard()[0][1] == false ) {
                actions.add(new Action(0, 1));
            }
            if ( ((Boardone2one) turnInitialState).getBoard()[1][0] == false ) {
                actions.add(new Action(1, 0));
            }
            if ( ((Boardone2one) turnInitialState).getBoard()[1][1] == false ) {
                actions.add(new Action(1, 1));
            }
            assert !actions.isEmpty();
            return actions;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            NormalizedField normOutput = new NormalizedField(NormalizationAction.Normalize, null, 15, 6, 1, 0);
            if ( currentBoard.getBoard()[0][0] == true && currentBoard.getBoard()[0][1] == true
                    && currentBoard.getBoard()[1][0] == true && currentBoard.getBoard()[1][1] == false ) {
                return normOutput.normalize(13);
            }
            if ( currentBoard.getBoard()[0][0] == true && currentBoard.getBoard()[0][1] == false
                    && currentBoard.getBoard()[1][0] == true && currentBoard.getBoard()[1][1] == true ) {
                return normOutput.normalize(15);
            }
            if ( currentBoard.getBoard()[0][0] == false && currentBoard.getBoard()[0][1] == true
                    && currentBoard.getBoard()[1][0] == true && currentBoard.getBoard()[1][1] == true ) {
                return normOutput.normalize(6);
            }
            if ( currentBoard.getBoard()[0][0] == true && currentBoard.getBoard()[0][1] == true
                    && currentBoard.getBoard()[1][0] == false && currentBoard.getBoard()[1][1] == true ) {
                return normOutput.normalize(14);
            }
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void resetGame() {
            currentBoard = new Boardone2one();
        }
    }

    /**
     *
     */
    public class Boardone2one implements IStatePerceptron {

        /**
         *
         */
        public boolean[][] board = {{false, false}, {false, false}};
        /**
         *
         */
        public NormalizedField normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, 15, 6, 1, 0);

        /**
         * @return the board
         */
        public boolean[][] getBoard() {
            return board;
        }

        @Override
        public Boardone2one getCopy() {
            Boardone2one newBoard = new Boardone2one();
            newBoard.board[0][0] = this.board[0][0];
            newBoard.board[1][0] = this.board[1][0];
            newBoard.board[0][1] = this.board[0][1];
            newBoard.board[1][1] = this.board[1][1];
            return newBoard;
        }

        @Override
        public double getStateReward(int outputNeuron) {
            return getScore();
        }

        @Override
        public boolean isTerminalState() {
            int count = 0;
            count += (getBoard()[0][0]) ? 1 : 0;
            count += (getBoard()[0][1]) ? 1 : 0;
            count += (getBoard()[1][0]) ? 1 : 0;
            count += (getBoard()[1][1]) ? 1 : 0;
            assert count != 4;
            return count == 3;
        }

        @Override
        public String toString() {
            return "Board{" + "board=\n" + getBoard()[0][0] + "," + getBoard()[0][1] + "\n" + getBoard()[1][0] + "," + getBoard()[1][1] + '}';
        }

        @Override
        public Double translateToPerceptronInput(int neuronIndex) {
            if ( getBoard()[0][0] == true && neuronIndex == 0 ) {
                return 1d;
            }
            if ( getBoard()[0][1] == true && neuronIndex == 1 ) {
                return 1d;
            }
            if ( getBoard()[1][0] == true && neuronIndex == 2 ) {
                return 1d;
            }
            if ( getBoard()[1][1] == true && neuronIndex == 3 ) {
                return 1d;
            }
            return 0d;
        }

        //    @Override
//    public double translateRewordToNormalizedPerceptronOutputFrom(int neuronIndex) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public double translateThisFinalStateToPerceptronOutput(int neuronIndex) {
//        //  |5|10|
//        //  |5|10|
//        if ( getBoard()[0][0] == true && getBoard()[0][1] == true && getBoard()[1][0] == true && getBoard()[1][1] == false ) {
//            return normOutput.normalize(13);
//        }
//        if ( getBoard()[0][0] == true && getBoard()[0][1] == false && getBoard()[1][0] == true && getBoard()[1][1] == true ) {
//            return normOutput.normalize(15);
//        }
//        if ( getBoard()[0][0] == false && getBoard()[0][1] == true && getBoard()[1][0] == true && getBoard()[1][1] == true ) {
//            return normOutput.normalize(6);
//        }
//        if ( getBoard()[0][0] == true && getBoard()[0][1] == true && getBoard()[1][0] == false && getBoard()[1][1] == true ) {
//            return normOutput.normalize(14);
//        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public double translateToPerceptronInput(int neuronIndex) {
//        if ( getBoard()[0][0] == true && neuronIndex == 0 ) {
//            return 1;
//        }
//        if ( getBoard()[0][1] == true && neuronIndex == 1 ) {
//            return 1;
//        }
//        if ( getBoard()[1][0] == true && neuronIndex == 2 ) {
//            return 1;
//        }
//        if ( getBoard()[1][1] == true && neuronIndex == 3 ) {
//            return 1;
//        }
//        return 0;
//    }
        int getScore() {
            int count = 0;
            count += (getBoard()[0][0] == true) ? 10 : 0;
            count += (getBoard()[0][1] == true) ? 1 : 0;
            count += (getBoard()[1][0] == true) ? 2 : 0;
            count += (getBoard()[1][1] == true) ? 3 : 0;
            return count;
        }
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
