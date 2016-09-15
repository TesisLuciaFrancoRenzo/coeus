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
package ar.edu.unrc.coeus.interfaces;

import ar.edu.unrc.coeus.tdlearning.utils.FunctionUtils;
import java.util.function.Function;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class IPerceptronInterfaceTest {

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
    public IPerceptronInterfaceTest() {
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
     * Test of getActivationFunction method, of class INeuralNetworkInterface.
     */
    @Test
    public void testGetActivationFunction() {
        System.out.println("getActivationFunction");
        int layerIndex = 1;
        Function<Double, Double> sigmoid = FunctionUtils.SIGMOID;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        Function<Double, Double> result = instance.getActivationFunction(
                layerIndex);
        assertThat(result, is(sigmoid));
    }

    /**
     * Test of getBias method, of class INeuralNetworkInterface.
     */
    @Test
    public void testGetBias() {
        System.out.println("getBias");
        int layerIndex = 0;
        int neuronIndex = 0;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        double expResult = 0.56;
        instance.setBias(layerIndex, neuronIndex, 0.56);
        double result = instance.getBias(layerIndex, neuronIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getDerivatedActivationFunction method, of class INeuralNetworkInterface.
     */
    @Test
    public void testGetDerivatedActivationFunction() {
        System.out.println("getDerivatedActivationFunction");
        int layerIndex = 1;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        Function<Double, Double> expResult = FunctionUtils.SIGMOID_DERIVATED;
        Function<Double, Double> result = instance.
                getDerivatedActivationFunction(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getLayerQuantity method, of class INeuralNetworkInterface.
     */
    @Test
    public void testGetLayerQuantity() {
        System.out.println("getLayerQuantity");
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        int expResult = 3;
        int result = instance.getLayerQuantity();
        assertThat(result, is(expResult));

    }

    /**
     * Test of getNeuronQuantityInLayer method, of class INeuralNetworkInterface.
     */
    @Test
    public void testGetNeuronQuantityInLayer() {
        System.out.println("getNeuronQuantityInLayer");
        int layerIndex = 0;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        int expResult = 3;
        int result = instance.getNeuronQuantityInLayer(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getWeight method, of class INeuralNetworkInterface.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        int layerIndex = 0;
        int neuronIndex = 0;
        int neuronIndexPreviousLayer = 0;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        double expResult = 0.3556;
        instance.setWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer,
                expResult);
        double result = instance.getWeight(layerIndex, neuronIndex,
                neuronIndexPreviousLayer);
        assertThat(result, is(expResult));

    }

    /**
     * Test of hasBias method, of class INeuralNetworkInterface.
     */
    @Test
    public void testHasBias() {
        System.out.println("hasBias");
        int layerIndex = 0;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        boolean expResult = true;
        boolean result = instance.hasBias(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of setBias method, of class INeuralNetworkInterface.
     */
    @Test
    public void testSetBias() {
        System.out.println("setBias");
        int layerIndex = 0;
        int neuronIndex = 0;
        double correctedBias = 0.56;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        instance.setBias(layerIndex, neuronIndex, correctedBias);
        assertThat(correctedBias, is(instance.getBias(layerIndex, neuronIndex)));
    }

    /**
     * Test of setWeight method, of class INeuralNetworkInterface.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        int layerIndex = 0;
        int neuronIndex = 0;
        int neuronIndexPreviousLayer = 0;
        double correctedWeight = 0.3556;
        INeuralNetworkInterface instance = new IPerceptronInterfaceImpl();
        instance.setWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer, correctedWeight);
        assertThat(correctedWeight, is(instance.getWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer)));
    }

    /**
     *
     */
    public class IPerceptronInterfaceImpl implements INeuralNetworkInterface {

        double biasB;
        int layerQuantity = 3, neuronQuantityInLayer = 3;
        double weight;

        @Override
        public Function<Double, Double> getActivationFunction(int layerIndex) {
            return FunctionUtils.SIGMOID;
        }

        @Override
        public double getBias(int layerIndex,
                int neuronIndex) {
            return biasB;
        }

        @Override
        public Function<Double, Double> getDerivatedActivationFunction(
                int layerIndex) {
            return FunctionUtils.SIGMOID_DERIVATED;
        }

        @Override
        public int getLayerQuantity() {
            return layerQuantity;
        }

        @Override
        public int getNeuronQuantityInLayer(int layerIndex) {
            return neuronQuantityInLayer;
        }

        @Override
        public double getWeight(int layerIndex,
                int neuronIndex,
                int neuronIndexPreviousLayer) {
            return weight;
        }

        @Override
        public boolean hasBias(int layerIndex) {
            return true;
        }

        @Override
        public void setBias(int layerIndex,
                int neuronIndex,
                double correctedBias) {
            biasB = correctedBias;
        }

        @Override
        public void setWeight(int layerIndex,
                int neuronIndex,
                int neuronIndexPreviousLayer,
                double correctedWeight) {
            weight = correctedWeight;
        }
    }

}
