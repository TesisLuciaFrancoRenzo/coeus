/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.auxiliarData;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author franco
 */
public class Boardone2one implements IState {

    public static NormalizedField normOutput = new NormalizedField(NormalizationAction.Normalize,
            null, 15, 6, 1, 0);
    private boolean[][] board = {{false, false}, {false, false}};

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
