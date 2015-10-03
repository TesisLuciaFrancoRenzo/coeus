/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucy
 */
public class IProblemStateTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public IProblemStateTest() {
    }

    @Before
    public void setUp() {
    }

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
        IState afterState = null;
        IProblemState instance = new IProblemStateImpl();
        List<StateProbability> expResult = null;
        List<StateProbability> result = instance.listAllPossibleNextTurnStateFromAfterstate(afterState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IProblemStateImpl implements IProblemState {

        @Override
        public IState computeAfterState(IState turnInitialState, IAction action) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            return null;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
