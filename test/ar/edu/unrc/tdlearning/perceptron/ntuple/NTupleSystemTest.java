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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    private final List<SamplePointState> allSamplePointStates;
    private final HashMap<SamplePointState, Integer> mapSamplePointStates;
    private int[] nTuplesLenght;
    private final IStateNTuple state;

    private final double[] weights;

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
        state = (int nTuple) -> {
            switch ( nTuple ) {
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

    @Before
    public void setUp() {
    }

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
    }
//
//    /**
//     * Test of getWeight method, of class NTupleSystem.
//     */
//    @Test
//    public void testGetWeight() {
//        System.out.println("getWeight");
//        int layerIndex = 0;
//        int neuronIndex = 0;
//        int neuronIndexPreviousLayer = 0;
//        NTupleSystem instance = null;
//        double expResult = 0.0;
//        double result = instance.getWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//
//    /**
//     * Test of setWeight method, of class NTupleSystem.
//     */
//    @Test
//    public void testSetWeight() {
//        System.out.println("setWeight");
//        int layerIndex = 0;
//        int neuronIndex = 0;
//        int neuronIndexPreviousLayer = 0;
//        double correctedWeight = 0.0;
//        NTupleSystem instance = null;
//        instance.setWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer, correctedWeight);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
