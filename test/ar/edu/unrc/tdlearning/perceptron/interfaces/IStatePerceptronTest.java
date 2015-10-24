/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import static junit.framework.Assert.assertEquals;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class IStatePerceptronTest {

    public static NormalizedField normOutput = new NormalizedField(NormalizationAction.Normalize,
            null, 15, 6, 1, 0);
    private final boolean[][] board = {{false, false}, {false, false}, {false, false}, {false, false}};

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
    public IStatePerceptronTest() {
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
     * Test of translateToPerceptronInput method, of class IStatePerceptron.
     */
    @Test
    public void testTranslateToPerceptronInput() {
        System.out.println("translateToPerceptronInput");
        int neuronIndex = 2;
        IStatePerceptron instance = new IStatePerceptronImpl();
        IsolatedComputation<Double> expResult = () -> {
            return 0d;
        };
        IsolatedComputation<Double> result = instance.translateToPerceptronInput(neuronIndex);
        assertEquals(expResult.compute(), result.compute());
    }

    /**
     *
     */
    public class IStatePerceptronImpl implements IStatePerceptron {

        @Override
        public IState getCopy() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getStateReward(int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isTerminalState() {
            int count = 0;
            count += (board[0][0]) ? 1 : 0;
            count += (board[0][1]) ? 1 : 0;
            count += (board[1][0]) ? 1 : 0;
            count += (board[1][1]) ? 1 : 0;
            assert count != 4;
            return count == 3;
        }

        @Override
        public IsolatedComputation<Double> translateToPerceptronInput(int neuronIndex) {
            if ( board[0][0] == true && neuronIndex == 0 ) {
                return () -> 1d;
            }
            if ( board[0][1] == true && neuronIndex == 1 ) {
                return () -> 1d;
            }
            if ( board[1][0] == true && neuronIndex == 2 ) {
                return () -> 1d;
            }
            if ( board[1][1] == true && neuronIndex == 3 ) {
                return () -> 1d;
            }
            return () -> 0d;
        }

    }

}
