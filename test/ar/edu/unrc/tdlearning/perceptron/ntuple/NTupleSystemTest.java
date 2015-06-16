/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.io.File;
import java.util.Map;
import java.util.function.Function;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class NTupleSystemTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public NTupleSystemTest() {
    }

    @Before
    public void setUp() {
    }

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
        System.out.println("calculateIndex");
        int nTupleIndex = 0;
        int[] nTuplesLenght = null;
        IStateNTuple state = null;
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
        //   assertArrayEquals(expResult, result);
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
        //  assertArrayEquals(expResult, result);
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
        //  assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of load method, of class NTupleSystem.
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

}
