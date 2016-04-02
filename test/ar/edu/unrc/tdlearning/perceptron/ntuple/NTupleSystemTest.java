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
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.io.File;
import java.util.Map;
import java.util.function.Function;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NTupleSystemTest {

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
    public NTupleSystemTest() {
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
     * Test of addCorrectionToWeight method, of class NTupleSystem.
     */
    @Test
    public void testAddCorrectionToWeight() {
        System.out.println("addCorrectionToWeight");
        int currentWeightIndex = 0;
        double correction = 0.0;
        NTupleSystem instance = null;
        instance.addCorrectionToWeight(currentWeightIndex, correction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateIndex method, of class NTupleSystem.
     */
    @Test
    public void testCalculateIndex() {
        System.out.println("testCalculateIndex");
        int nTupleIndex = 0;

        IStateNTuple state = new DummyState();
        int[] nTuplesLenght = null;
        Map<SamplePointState, Integer> mapSamplePointStates = null;
        int expResult = 0;
        int result = NTupleSystem.calculateIndex(nTupleIndex, nTuplesLenght, state, mapSamplePointStates);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivationFunction method, of class NTupleSystem.
     */
    @Test
    public void testGetActivationFunction() {
        System.out.println("getActivationFunction");
        NTupleSystem instance = null;
        Function<Double, Double> expResult = null;
        Function<Double, Double> result = instance.getActivationFunction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComplexComputation method, of class NTupleSystem.
     */
    @Test
    public void testGetComplexComputation() {
        System.out.println("getComplexComputation");
        IStateNTuple state = null;
        NTupleSystem instance = null;
        IsolatedComputation<ComplexNTupleComputation> expResult = null;
        IsolatedComputation<ComplexNTupleComputation> result = instance.getComplexComputation(state);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComputation method, of class NTupleSystem.
     */
    @Test
    public void testGetComputation() {
        System.out.println("getComputation");
        IStateNTuple state = null;
        NTupleSystem instance = null;
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.getComputation(state);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDerivatedActivationFunction method, of class NTupleSystem.
     */
    @Test
    public void testGetDerivatedActivationFunction() {
        System.out.println("getDerivatedActivationFunction");
        NTupleSystem instance = null;
        Function<Double, Double> expResult = null;
        Function<Double, Double> result = instance.getDerivatedActivationFunction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLut method, of class NTupleSystem.
     */
    @Test
    public void testGetLut() {
        System.out.println("getLut");
        NTupleSystem instance = null;
        double[] expResult = null;
        double[] result = instance.getLut();
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMapSamplePointStates method, of class NTupleSystem.
     */
    @Test
    public void testGetMapSamplePointStates() {
        System.out.println("getMapSamplePointStates");
        NTupleSystem instance = null;
        Map<SamplePointState, Integer> expResult = null;
        Map<SamplePointState, Integer> result = instance.getMapSamplePointStates();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getnTuplesLenght method, of class NTupleSystem.
     */
    @Test
    public void testGetnTuplesLenght() {
        System.out.println("getnTuplesLenght");
        NTupleSystem instance = null;
        int[] expResult = null;
        int[] result = instance.getnTuplesLenght();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getnTuplesWeightQuantity method, of class NTupleSystem.
     */
    @Test
    public void testGetnTuplesWeightQuantity() {
        System.out.println("getnTuplesWeightQuantity");
        NTupleSystem instance = null;
        int[] expResult = null;
        int[] result = instance.getnTuplesWeightQuantity();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of load method, of class NTupleSystem.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
        File weightsFile = null;
        NTupleSystem instance = null;
        instance.load(weightsFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class NTupleSystem.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        NTupleSystem instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class NTupleSystem.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        File lutFile = null;
        NTupleSystem instance = null;
        instance.save(lutFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWeight method, of class NTupleSystem.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        int index = 0;
        double value = 0.0;
        NTupleSystem instance = null;
        instance.setWeight(index, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWeights method, of class NTupleSystem.
     */
    @Test
    public void testSetWeights() {
        System.out.println("setWeights");
        double[] value = null;
        NTupleSystem instance = null;
        instance.setWeights(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private static class DummyState implements IStateNTuple  {

        public DummyState () {
        }

        @Override
        public IState getCopy() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public SamplePointState[] getNTuple(int nTupleIndex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getStateReward(int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isTerminalState() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
