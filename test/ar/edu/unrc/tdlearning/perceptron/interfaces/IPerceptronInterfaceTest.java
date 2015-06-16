/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import java.util.function.Function;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class IPerceptronInterfaceTest {

    public IPerceptronInterfaceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getBias method, of class IPerceptronInterface.
     */
    @Test
    public void testGetBias() {
        System.out.println("getBias");
        int layerIndex = 0;
        int neuronIndex = 0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        double expResult = 0.0;
        double result = instance.getBias(layerIndex, neuronIndex);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivationFunction method, of class IPerceptronInterface.
     */
    @Test
    public void testGetActivationFunction() {
        System.out.println("getActivationFunction");
        int layerIndex = 0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        Function<Double, Double> expResult = null;
        Function<Double, Double> result = instance.getActivationFunction(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDerivatedActivationFunction method, of class IPerceptronInterface.
     */
    @Test
    public void testGetDerivatedActivationFunction() {
        System.out.println("getDerivatedActivationFunction");
        int layerIndex = 0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        Function<Double, Double> expResult = null;
        Function<Double, Double> result = instance.getDerivatedActivationFunction(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLayerQuantity method, of class IPerceptronInterface.
     */
    @Test
    public void testGetLayerQuantity() {
        System.out.println("getLayerQuantity");
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        int expResult = 0;
        int result = instance.getLayerQuantity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeuronQuantityInLayer method, of class IPerceptronInterface.
     */
    @Test
    public void testGetNeuronQuantityInLayer() {
        System.out.println("getNeuronQuantityInLayer");
        int layerIndex = 0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        int expResult = 0;
        int result = instance.getNeuronQuantityInLayer(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeight method, of class IPerceptronInterface.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        int layerIndex = 0;
        int neuronIndex = 0;
        int neuronIndexPreviousLayer = 0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        double expResult = 0.0;
        double result = instance.getWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasBias method, of class IPerceptronInterface.
     */
    @Test
    public void testHasBias() {
        System.out.println("hasBias");
        int layerIndex = 0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        boolean expResult = false;
        boolean result = instance.hasBias(layerIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBias method, of class IPerceptronInterface.
     */
    @Test
    public void testSetBias() {
        System.out.println("setBias");
        int layerIndex = 0;
        int neuronIndex = 0;
        double correctedBias = 0.0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        instance.setBias(layerIndex, neuronIndex, correctedBias);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWeight method, of class IPerceptronInterface.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        int layerIndex = 0;
        int neuronIndex = 0;
        int neuronIndexPreviousLayer = 0;
        double correctedWeight = 0.0;
        IPerceptronInterface instance = new IPerceptronInterfaceImpl();
        instance.setWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer, correctedWeight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IPerceptronInterfaceImpl implements IPerceptronInterface {

        public double getBias(int layerIndex, int neuronIndex) {
            return 0.0;
        }

        public Function<Double, Double> getActivationFunction(int layerIndex) {
            return null;
        }

        public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
            return null;
        }

        public int getLayerQuantity() {
            return 0;
        }

        public int getNeuronQuantityInLayer(int layerIndex) {
            return 0;
        }

        public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
            return 0.0;
        }

        public boolean hasBias(int layerIndex) {
            return false;
        }

        public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
        }

        public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
        }
    }

    public class IPerceptronInterfaceImpl implements IPerceptronInterface {

        public double getBias(int layerIndex, int neuronIndex) {
            return 0.0;
        }

        public Function<Double, Double> getActivationFunction(int layerIndex) {
            return null;
        }

        public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
            return null;
        }

        public int getLayerQuantity() {
            return 0;
        }

        public int getNeuronQuantityInLayer(int layerIndex) {
            return 0;
        }

        public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
            return 0.0;
        }

        public boolean hasBias(int layerIndex) {
            return false;
        }

        public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
        }

        public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
        }
    }

    public class IPerceptronInterfaceImpl implements IPerceptronInterface {

        public double getBias(int layerIndex, int neuronIndex) {
            return 0.0;
        }

        public Function<Double, Double> getActivationFunction(int layerIndex) {
            return null;
        }

        public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
            return null;
        }

        public int getLayerQuantity() {
            return 0;
        }

        public int getNeuronQuantityInLayer(int layerIndex) {
            return 0;
        }

        public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
            return 0.0;
        }

        public boolean hasBias(int layerIndex) {
            return false;
        }

        public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
        }

        public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
        }
    }

}
