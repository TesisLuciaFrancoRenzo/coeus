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

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblemToTrain;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import java.util.ArrayList;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public final class BestOf3one2one implements IProblemToTrain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
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
