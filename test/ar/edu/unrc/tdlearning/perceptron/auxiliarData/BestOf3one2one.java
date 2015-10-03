/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.auxiliarData;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
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
 * @author renzo
 */
public final class BestOf3one2one implements IProblem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    private Boardone2one currentBoard;
    private BasicNetwork encogPerceptron;

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
        return ((Boardone2one) afterstate).getCopy();
    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
        return () -> {
            assert output.length == 1;
            return (Double) output[0];
        };
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
    public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
        return () -> {
            double[] inputs = new double[4];
            for ( int i = 0; i < 4; i++ ) {
                inputs[i] = ((IStatePerceptron) state).translateToPerceptronInput(i).compute();
            } //TODO reeemplazar esto por algo mas elegante
            MLData inputData = new BasicMLData(inputs);
            MLData output = (encogPerceptron).compute(inputData);
            Double[] out = new Double[output.getData().length];
            for ( int i = 0; i < output.size(); i++ ) {
                out[i] = output.getData()[i];
            }
            return out;
        };
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

//    public int getScore() {
//        return this.currentBoard.getScore();
//    }
//    @Override
//    public IState initialize() {
//        resetGame();
//        return currentBoard;
//    }
    @Override
    public IState initialize(IActor actor) {
        //initializeEncogPerceptron();
        return currentBoard.getCopy();
    }

//    @Override
//    public double randomMoveProbability() {
//        return 0.5;
//    }
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
        assert !((Boardone2one) turnInitialState).isTerminalState();
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
