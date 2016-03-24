/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.auxiliarData.Boardone2one;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class IProblemStateTest {

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
    public IProblemStateTest() {
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
     * Test of listAllPossibleNextTurnStateFromAfterstate method, of class
     * IProblemState.
     */
    @Test
    public void testListAllPossibleNextTurnStateFromAfterstate() {
        System.out.println("listAllPossibleNextTurnStateFromAfterstate");
        List<StateProbability> expResult = new ArrayList<>(4);
        Boardone2one newBoard = new Boardone2one();
        double probability = 1 / 4;
        Boardone2one newBoard1 = newBoard.getCopy();
        Boardone2one newBoard2 = newBoard.getCopy();
        Boardone2one newBoard3 = newBoard.getCopy();
        Boardone2one newBoard4 = newBoard.getCopy();

        newBoard1.board[0][0] = true;
        newBoard2.board[1][0] = true;
        newBoard3.board[0][1] = true;
        newBoard4.board[1][1] = true;

        expResult.add(new StateProbability((IStatePerceptron) newBoard1, probability));
        expResult.add(new StateProbability((IStatePerceptron) newBoard2, probability));
        expResult.add(new StateProbability((IStatePerceptron) newBoard3, probability));
        expResult.add(new StateProbability((IStatePerceptron) newBoard4, probability));

        IProblemState instance = new IProblemStateImpl();

        List<StateProbability> result = instance.listAllPossibleNextTurnStateFromAfterstate(newBoard);
        System.out.println("listAllPossibleNextTurnStateFromAfterstate" + expResult.get(0).toString());
        //assertEquals(expResult.get(0), result.get(0));
    }

    /**
     *
     */
    public class IProblemStateImpl implements IProblemState {

        Boardone2one newBoard;

        /**
         *
         */
        public IProblemStateImpl() {
            this.newBoard = new Boardone2one();
            newBoard.board[0][0] = false;
            newBoard.board[1][0] = false;
            newBoard.board[0][1] = false;
            newBoard.board[1][1] = false;

        }

        @Override
        public IState computeAfterState(IState turnInitialState, IAction action) {
            return null;
        }

        @Override
        public IState computeNextTurnStateFromAfterstate(IState afterstate) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IActor getActorToTrain() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setCurrentState(IState nextTurnState) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getFinalReward(IState finalState, int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IState initialize(IActor actor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(IState afterState) {
            List<StateProbability> output = new ArrayList<>(4);
            Boardone2one newBoard1 = null, newBoard2 = null, newBoard3 = null, newBoard4 = null;
            double probability = 1 / 4;
            if ( newBoard.board[0][0] == false ) {
                newBoard1 = newBoard.getCopy();
                newBoard1.board[0][0] = true;
                output.add(new StateProbability((IStatePerceptron) newBoard1, probability));
            }
            if ( newBoard.board[1][0] == false ) {
                newBoard2 = newBoard.getCopy();
                newBoard1.board[1][0] = true;
                output.add(new StateProbability((IStatePerceptron) newBoard2, probability));
            }
            if ( newBoard.board[0][1] == false ) {
                newBoard3 = newBoard.getCopy();
                newBoard1.board[0][1] = true;
                output.add(new StateProbability((IStatePerceptron) newBoard3, probability));
            }
            if ( newBoard.board[1][1] == false ) {
                newBoard4 = newBoard.getCopy();
                newBoard1.board[1][1] = true;
                output.add(new StateProbability((IStatePerceptron) newBoard4, probability));
            }

            return output;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
