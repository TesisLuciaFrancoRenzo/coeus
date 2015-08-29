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
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.ArrayList;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

public final class BestOf3one2one implements IProblem {

    private BasicNetwork encogPerceptron;
    private Boardone2one currentBoard;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public BestOf3one2one() {
        initializeEncogPerceptron();
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
        if ( getBoard()[0][0] == true && getBoard()[0][1] == true && getBoard()[1][0] == true && getBoard()[1][1] == false ) {
            return normOutput.normalize(13);
        }
        if ( getBoard()[0][0] == true && getBoard()[0][1] == false && getBoard()[1][0] == true && getBoard()[1][1] == true ) {
            return normOutput.normalize(15);
        }
        if ( getBoard()[0][0] == false && getBoard()[0][1] == true && getBoard()[1][0] == true && getBoard()[1][1] == true ) {
            return normOutput.normalize(6);
        }
        if ( getBoard()[0][0] == true && getBoard()[0][1] == true && getBoard()[1][0] == false && getBoard()[1][1] == true ) {
            return normOutput.normalize(14);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public IPrediction evaluateBoardWithPerceptron(IState state) {
//        //dependiendo de que tipo de red neuronal utilizamos, evaluamos las entradas y calculamos una salida
//        if ( getEncogPerceptron() != null ) {
//            double[] inputs = new double[4];
//            for ( int i = 0; i < 4; i++ ) {
//                inputs[i] = state.translateToPerceptronInput(i); //input
//            }
//            MLData inputData = new BasicMLData(inputs);
//            MLData output = getEncogPerceptron().compute(inputData);
//            double out = output.getData(0);
//            return new Prediction(out);
//        } else {
//            throw new UnsupportedOperationException("only encog is implemented");
//        }
//    }
    @Override
    public IActor getActorToTrain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getScore() {
        return this.currentBoard.getScore();
    }

//    @Override
//    public IState initialize() {
//        resetGame();
//        return currentBoard;
//    }
    @Override
    public IState initialize(IActor actor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void resetGame() {
        currentBoard = new Boardone2one();
    }

}
