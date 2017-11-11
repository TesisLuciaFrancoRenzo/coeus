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

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class IProblemTest {

    /**
     * Test of computeAfterState method, of class IProblemToTrain.
     */
    @Test
    public
    void testComputeAfterState() {
        System.out.println("computeAfterState");
        final Action       action    = new Action(0, 0);
        final BoardOne2one newBoard  = new BoardOne2one();
        final BoardOne2one newBoard1 = newBoard.getCopy();
        newBoard1.board[action.getX()][action.getX()] = true;
        final IProblemToTrain instance = new IProblemImpl();
        final IState          result   = instance.computeAfterState(newBoard, action);
        assertThat(newBoard1.board[0][0], is(( (BoardOne2one) result ).board[0][0]));
    }

    /**
     * @author lucia bressan, franco pellegrini, renzo bianchini
     */
    public static
    class Action
            implements IAction {

        private int x;
        private int y;

        public
        Action(
                final int x,
                final int y
        ) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x
         */
        public
        int getX() {
            return x;
        }

        /**
         * @return the y
         */
        public
        int getY() {
            return y;
        }

        @Override
        public
        String toString() {
            return "(" + x + ',' + y + ')';
        }
    }

    /**
     *
     */
    public static
    class BoardOne2one
            implements IStatePerceptron {

        /**
         *
         */
        public final boolean[][] board = { { false, false }, { false, false } };

        /**
         * @return the board
         */
        public
        boolean[][] getBoard() {
            return board;
        }

        @Override
        public
        BoardOne2one getCopy() {
            final BoardOne2one newBoard = new BoardOne2one();
            newBoard.board[0][0] = board[0][0];
            newBoard.board[1][0] = board[1][0];
            newBoard.board[0][1] = board[0][1];
            newBoard.board[1][1] = board[1][1];
            return newBoard;
        }

        int getScore() {
            int count = 0;
            count += ( board[0][0] ) ? 10 : 0;
            count += ( board[0][1] ) ? 1 : 0;
            count += ( board[1][0] ) ? 2 : 0;
            count += ( board[1][1] ) ? 3 : 0;
            return count;
        }

        @Override
        public
        double getStateReward( final int outputNeuron ) {
            return getScore();
        }

        @Override
        public
        boolean isTerminalState() {
            int count = 0;
            count += ( board[0][0] ) ? 1 : 0;
            count += ( board[0][1] ) ? 1 : 0;
            count += ( board[1][0] ) ? 1 : 0;
            count += ( board[1][1] ) ? 1 : 0;
            assert count != 4;
            return count == 3;
        }

        @Override
        public
        String toString() {
            return "Board{" + "board=\n" + board[0][0] + ',' + board[0][1] + '\n' + board[1][0] + ',' + board[1][1] + '}';
        }

        @Override
        public
        Double translateToPerceptronInput( final int neuronIndex ) {
            if ( board[0][0] && ( neuronIndex == 0 ) ) {
                return 1.0d;
            }
            if ( board[0][1] && ( neuronIndex == 1 ) ) {
                return 1.0d;
            }
            if ( board[1][0] && ( neuronIndex == 2 ) ) {
                return 1.0d;
            }
            if ( board[1][1] && ( neuronIndex == 3 ) ) {
                return 1.0d;
            }
            return 0.0d;
        }
    }

    /**
     *
     */
    public static
    class IProblemImpl
            implements IProblemToTrain {

        @Override
        public
        boolean canExploreThisTurn( final long currentTurn ) {
            return true;
        }

        @Override
        public
        IState computeAfterState(
                final IState turnInitialState,
                final IAction action
        ) {
            final BoardOne2one boardCast = (BoardOne2one) turnInitialState;
            boardCast.board[( (Action) action ).getX()][( (Action) action ).getY()] = true;
            return boardCast;

        }

        @Override
        public
        IState computeNextTurnStateFromAfterState( final IState afterState ) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public
        Double computeNumericRepresentationFor( final Object[] output ) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public
        double deNormalizeValueFromPerceptronOutput( final Object value ) {
            return 0.0;
        }

        @Override
        public
        Object[] evaluateStateWithPerceptron( final IState state ) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public
        IState initialize() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public
        ArrayList< IAction > listAllPossibleActions( final IState turnInitialState ) {
            return null;
        }

        @Override
        public
        double normalizeValueToPerceptronOutput( final Object value ) {
            return 0.0;
        }

        @Override
        public
        void setCurrentState( final IState nextTurnState ) {
        }
    }

    /**
     *
     */
    public
    class BestOf3one2one
            implements IProblemToTrain {

        private BoardOne2one currentBoard;
        private BasicNetwork encogPerceptron;

        public
        BestOf3one2one( final BasicNetwork encogPerceptron ) {
            this.encogPerceptron = encogPerceptron;
            //initializeEncogPerceptron();
            resetGame();
        }

        @Override
        public
        boolean canExploreThisTurn( final long currentTurn ) {
            return true;
        }

        @Override
        public
        IState computeAfterState(
                final IState turnInitialState,
                final IAction action
        ) {
            final BoardOne2one afterState = ( (BoardOne2one) turnInitialState ).getCopy();
            afterState.getBoard()[( (Action) action ).getX()][( (Action) action ).getY()] = true;
            return afterState;
        }

        @Override
        public
        IState computeNextTurnStateFromAfterState( final IState afterState ) {
            return afterState.getCopy();
        }

        @Override
        public
        Double computeNumericRepresentationFor(
                final Object[] output
        ) {
            assert output.length == 1;
            return (Double) output[0];
        }

        @Override
        public
        double deNormalizeValueFromPerceptronOutput( final Object value ) {
            if ( currentBoard.getBoard()[0][0] && currentBoard.getBoard()[0][1] && currentBoard.getBoard()[1][0] && !currentBoard.getBoard()[1][1] ) {
                return 13.0d;
            }
            if ( currentBoard.getBoard()[0][0] && !currentBoard.getBoard()[0][1] && currentBoard.getBoard()[1][0] && currentBoard.getBoard()[1][1] ) {
                return 15.0d;
            }
            if ( !currentBoard.getBoard()[0][0] && currentBoard.getBoard()[0][1] && currentBoard.getBoard()[1][0] && currentBoard.getBoard()[1][1] ) {
                return 6.0d;
            }
            if ( currentBoard.getBoard()[0][0] && currentBoard.getBoard()[0][1] && !currentBoard.getBoard()[1][0] && currentBoard.getBoard()[1][1] ) {
                return 14.0d;
            }
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public
        Object[] evaluateStateWithPerceptron( final IState state ) {
            final double[] inputs = new double[4];
            for ( int i = 0; i < 4; i++ ) {
                inputs[i] = ( (IStatePerceptron) state ).translateToPerceptronInput(i);
            }
            final MLData   inputData = new BasicMLData(inputs);
            final MLData   output    = ( encogPerceptron ).compute(inputData);
            final Double[] out       = new Double[output.getData().length];
            for ( int i = 0; i < output.size(); i++ ) {
                out[i] = output.getData()[i];
            }
            return out;
        }

        /**
         * @return the encogPerceptron
         */
        public
        BasicNetwork getEncogPerceptron() {
            return encogPerceptron;
        }

        /**
         * @param encogPerceptron the encogPerceptron to set
         */
        public
        void setEncogPerceptron( final BasicNetwork encogPerceptron ) {
            this.encogPerceptron = encogPerceptron;
        }

        @Override
        public
        IState initialize() {
            //initializeEncogPerceptron();
            return currentBoard.getCopy();
        }

        /**
         *
         */
        public
        void initializeEncogPerceptron() {
            encogPerceptron = new BasicNetwork();
            encogPerceptron.addLayer(new BasicLayer(null, true, 4));
            encogPerceptron.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2));
            encogPerceptron.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
            encogPerceptron.getStructure().finalizeStructure();
            encogPerceptron.reset();
        }

        @Override
        public
        ArrayList< IAction > listAllPossibleActions( final IState turnInitialState ) {
            final ArrayList< IAction > actions = new ArrayList<>();
            assert !turnInitialState.isTerminalState();
            final boolean[][] board = ( (BoardOne2one) turnInitialState ).getBoard();
            if ( !board[0][0] ) {
                actions.add(new Action(0, 0));
            }
            if ( !board[0][1] ) {
                actions.add(new Action(0, 1));
            }
            if ( !board[1][0] ) {
                actions.add(new Action(1, 0));
            }
            if ( !board[1][1] ) {
                actions.add(new Action(1, 1));
            }
            assert !actions.isEmpty();
            return actions;
        }

        @Override
        public
        double normalizeValueToPerceptronOutput( final Object value ) {
            final NormalizedField normOutput = new NormalizedField(NormalizationAction.Normalize, null, 15.0, 6.0, 1.0, 0);
            if ( currentBoard.getBoard()[0][0] && currentBoard.getBoard()[0][1] && currentBoard.getBoard()[1][0] && !currentBoard.getBoard()[1][1] ) {
                return normOutput.normalize(13.0);
            }
            if ( currentBoard.getBoard()[0][0] && !currentBoard.getBoard()[0][1] && currentBoard.getBoard()[1][0] && currentBoard.getBoard()[1][1] ) {
                return normOutput.normalize(15.0);
            }
            if ( !currentBoard.getBoard()[0][0] && currentBoard.getBoard()[0][1] && currentBoard.getBoard()[1][0] && currentBoard.getBoard()[1][1] ) {
                return normOutput.normalize(6.0);
            }
            if ( currentBoard.getBoard()[0][0] && currentBoard.getBoard()[0][1] && !currentBoard.getBoard()[1][0] && currentBoard.getBoard()[1][1] ) {
                return normOutput.normalize(14.0);
            }
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private
        void resetGame() {
            currentBoard = new BoardOne2one();
        }

        @Override
        public
        void setCurrentState( final IState nextTurnState ) {
            currentBoard = (BoardOne2one) nextTurnState;
        }
    }

}
