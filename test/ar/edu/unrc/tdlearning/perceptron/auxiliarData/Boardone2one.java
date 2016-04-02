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
package ar.edu.unrc.tdlearning.perceptron.auxiliarData;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Boardone2one implements IStatePerceptron {

    /**
     *
     */
    public static NormalizedField normOutput = new NormalizedField(NormalizationAction.Normalize,
            null, 15, 6, 1, 0);

    /**
     *
     */
    public boolean[][] board = {{false, false}, {false, false}};

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
    public IsolatedComputation<Double> translateToPerceptronInput(int neuronIndex) {
        if ( getBoard()[0][0] == true && neuronIndex == 0 ) {
            return () -> 1d;
        }
        if ( getBoard()[0][1] == true && neuronIndex == 1 ) {
            return () -> 1d;
        }
        if ( getBoard()[1][0] == true && neuronIndex == 2 ) {
            return () -> 1d;
        }
        if ( getBoard()[1][1] == true && neuronIndex == 3 ) {
            return () -> 1d;
        }
        return () -> 0d;
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
