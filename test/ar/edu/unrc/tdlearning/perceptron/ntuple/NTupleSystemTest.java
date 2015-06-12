/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTuple;
import ar.edu.unrc.tdlearning.perceptron.training.FunctionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static junit.framework.Assert.assertEquals;
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
    private final List<SamplePointState> allSamplePointStates;
    private final HashMap<SamplePointState, Integer> mapSamplePointStates;
    private int[] nTuplesLenght;
    private final IStateNTuple state;

    private final double[] weights;

    /**
     *
     */
    public NTupleSystemTest() {
        allSamplePointStates = new ArrayList<>();
        allSamplePointStates.add(SamplePoint.a); //0
        allSamplePointStates.add(SamplePoint.b); //1
        allSamplePointStates.add(SamplePoint.c); //2

        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i < allSamplePointStates.size(); i++ ) {
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }

        nTuplesLenght = new int[3];
        nTuplesLenght[0] = 1;
        nTuplesLenght[1] = 3;
        nTuplesLenght[2] = 2;
        state = new IStateNTuple() {

            @Override
            public SamplePointState[] getNTuple(int nTupleIndex) {
                switch ( nTupleIndex ) {
                    case 0: {
                        //en este ejemplo el numero indice de la ntupla que se va a solucitar al IState es irrelevante
                        SamplePoint[] ntuple = {SamplePoint.b}; //index = 1
                        return ntuple;
                    }
                    case 1: {
                        //en este ejemplo el numero indice de la ntupla que se va a solucitar al IState es irrelevante
                        SamplePoint[] ntuple = {SamplePoint.c, SamplePoint.a, SamplePoint.b}; //index = 2, 0, 1
                        return ntuple;
                    }
                    case 2: {
                        //en este ejemplo el numero indice de la ntupla que se va a solucitar al IState es irrelevante
                        SamplePoint[] ntuple = {SamplePoint.a, SamplePoint.c}; //index = 0, 2
                        return ntuple;
                    }
                    default: {
                        return null;
                    }
                }
            }

            @Override
            public double getStateReward() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isTerminalState() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };

        int[] nTupleIndexQuiantity = new int[nTuplesLenght.length];
        nTupleIndexQuiantity[0] = (int) Math.pow(allSamplePointStates.size(), nTuplesLenght[0]);
        nTupleIndexQuiantity[1] = (int) Math.pow(allSamplePointStates.size(), nTuplesLenght[1]);
        nTupleIndexQuiantity[2] = (int) Math.pow(allSamplePointStates.size(), nTuplesLenght[2]);

        weights = new double[nTupleIndexQuiantity[0] + nTupleIndexQuiantity[1] + nTupleIndexQuiantity[2]];
        for ( int i = 0; i < nTupleIndexQuiantity[0]; i++ ) {
            weights[i] = 0.1;
        }
        for ( int i = nTupleIndexQuiantity[0]; i < nTupleIndexQuiantity[0] + nTupleIndexQuiantity[1]; i++ ) {
            weights[i] = 0.8;
        }
        for ( int i = nTupleIndexQuiantity[0] + nTupleIndexQuiantity[1]; i < nTupleIndexQuiantity[0] + nTupleIndexQuiantity[1] + nTupleIndexQuiantity[2]; i++ ) {
            weights[i] = 0.5;
        }
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
     * Test of calculateIndex method, of class NTupleSystem.
     */
    @Test
    public void testCalculateIndex() {
        System.out.println("calculateIndex");
        int nTupleIndex = 1; //numero indice de la ntupla que se va a solucitar al IState
        int m = allSamplePointStates.size(); //Every sampling point p can have m different states
        int expResult
                = 2 * (int) Math.pow(m, 0) //indice de SamplePoint.c
                + 0 * (int) Math.pow(m, 1) //indice de SamplePoint.a
                + 1 * (int) Math.pow(m, 2); //indice de SamplePoint.b
        int result = NTupleSystem.calculateIndex(nTupleIndex, nTuplesLenght, state, mapSamplePointStates);
        assertEquals(expResult, result);
    }

    /**
     * Test of getComplexComputation method, of class NTupleSystem.
     */
    @Test
    public void testGetComplexComputation() {
        System.out.println("getComplexComputation");
        NTupleSystem instance = new NTupleSystem(allSamplePointStates, nTuplesLenght, FunctionUtils.sigmoid, FunctionUtils.derivatedSigmoid);
        instance.setWeights(this.weights);
        Double expResult = FunctionUtils.sigmoid.apply(0.8 + 0.5 + 0.1);
        Double result = instance.getComplexComputation(state).compute().getOutput();
        assertEquals(expResult, result, 0.000000000000005);

        expResult = FunctionUtils.derivatedSigmoid.apply(FunctionUtils.sigmoid.apply(0.8 + 0.5 + 0.1));
        result = instance.getComplexComputation(state).compute().getDerivatedOutput();
        assertEquals(expResult, result, 0.000000000000005);

        System.out.println("getComputation");
        instance = new NTupleSystem(allSamplePointStates, nTuplesLenght, FunctionUtils.linear, FunctionUtils.derivatedLinear);
        instance.setWeights(this.weights);
        expResult = 0.8 + 0.5 + 0.1;
        result = instance.getComplexComputation(state).compute().getOutput();
        assertEquals(expResult, result, 0.000000000000005);
    }
//
//    /**
//     * Test of addCorrectionToWeight method, of class NTupleSystem.
//     */
//    @Test
//    public void testAddCorrectionToWeight() {
//        System.out.println("addCorrectionToWeight");
//        int currentWeightIndex = 0;
//        double correction = 0.0;
//        NTupleSystem instance = null;
//        instance.addCorrectionToWeight(currentWeightIndex, correction);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getActivationFunction method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetActivationFunction() {
//        System.out.println("getActivationFunction");
//        NTupleSystem instance = null;
//        Function<Double, Double> expResult = null;
//        Function<Double, Double> result = instance.getActivationFunction();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    /**
//     * Test of getDerivatedActivationFunction method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetDerivatedActivationFunction() {
//        System.out.println("getDerivatedActivationFunction");
//        NTupleSystem instance = null;
//        Function<Double, Double> expResult = null;
//        Function<Double, Double> result = instance.getDerivatedActivationFunction();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLut method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetLut() {
//        System.out.println("getLut");
//        NTupleSystem instance = null;
//        double[] expResult = null;
//        double[] result = instance.getLut();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMapSamplePointStates method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetMapSamplePointStates() {
//        System.out.println("getMapSamplePointStates");
//        NTupleSystem instance = null;
//        Map<SamplePointState, Integer> expResult = null;
//        Map<SamplePointState, Integer> result = instance.getMapSamplePointStates();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setWeights method, of class NTupleSystem.
//     */
//    @Test
//    public void testSetWeights() {
//        System.out.println("setWeights");
//        double[] value = null;
//        NTupleSystem instance = null;
//        instance.setWeights(value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getnTuplesLenght method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetnTuplesLenght() {
//        System.out.println("getnTuplesLenght");
//        NTupleSystem instance = null;
//        int[] expResult = null;
//        int[] result = instance.getnTuplesLenght();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getnTuplesWeightQuantity method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetnTuplesWeightQuantity() {
//        System.out.println("getnTuplesWeightQuantity");
//        NTupleSystem instance = null;
//        int[] expResult = null;
//        int[] result = instance.getnTuplesWeightQuantity();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of load method, of class NTupleSystem.
//     */
//    @Test
//    public void testLoad() throws Exception {
//        System.out.println("load");
//        File weightsFile = null;
//        NTupleSystem instance = null;
//        instance.load(weightsFile);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of reset method, of class NTupleSystem.
//     */
//    @Test
//    public void testReset() {
//        System.out.println("reset");
//        NTupleSystem instance = null;
//        instance.reset();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of save method, of class NTupleSystem.
//     */
//    @Test
//    public void testSave() throws Exception {
//        System.out.println("save");
//        File lutFile = null;
//        NTupleSystem instance = null;
//        instance.save(lutFile);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setWeight method, of class NTupleSystem.
//     */
//    @Test
//    public void testSetWeight() {
//        System.out.println("setWeight");
//        int index = 0;
//        double value = 0.0;
//        NTupleSystem instance = null;
//        instance.setWeight(index, value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of getComputation method, of class NTupleSystem.
     */
    @Test
    public void testGetComputation() {
        System.out.println("getComputation");
        NTupleSystem instance = new NTupleSystem(allSamplePointStates, nTuplesLenght, FunctionUtils.sigmoid, FunctionUtils.derivatedSigmoid);
        instance.setWeights(this.weights);
        Double expResult = FunctionUtils.sigmoid.apply(0.8 + 0.5 + 0.1);
        Double result = instance.getComputation(state).compute();
        assertEquals(expResult, result, 0.000000000000005);
        
        System.out.println("getComputation");
        instance = new NTupleSystem(allSamplePointStates, nTuplesLenght, FunctionUtils.linear, FunctionUtils.derivatedLinear);
        instance.setWeights(this.weights);
        expResult = 0.8 + 0.5 + 0.1;
        result = instance.getComputation(state).compute();
        assertEquals(expResult, result, 0.000000000000005);
    }

}
