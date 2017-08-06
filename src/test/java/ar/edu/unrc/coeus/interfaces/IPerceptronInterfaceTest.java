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

import ar.edu.unrc.coeus.utils.FunctionUtils;
import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class IPerceptronInterfaceTest {

    /**
     * Test of getActivationFunction method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testGetActivationFunction() {
        System.out.println("getActivationFunction");
        final int                        layerIndex = 1;
        final Function< Double, Double > sigmoid    = FunctionUtils.SIGMOID;
        final INeuralNetworkInterface    instance   = new IPerceptronInterfaceImpl();
        final Function< Double, Double > result     = instance.getActivationFunction(layerIndex);
        assertThat(result, is(sigmoid));
    }

    /**
     * Test of getBias method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testGetBias() {
        System.out.println("getBias");
        final int                     layerIndex  = 0;
        final int                     neuronIndex = 0;
        final INeuralNetworkInterface instance    = new IPerceptronInterfaceImpl();
        final double                  expResult   = 0.56;
        instance.setBias(layerIndex, neuronIndex, 0.56);
        final double result = instance.getBias(layerIndex, neuronIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getDerivedActivationFunction method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testGetDerivedActivationFunction() {
        System.out.println("getDerivedActivationFunction");
        final int                        layerIndex = 1;
        final INeuralNetworkInterface    instance   = new IPerceptronInterfaceImpl();
        final Function< Double, Double > expResult  = FunctionUtils.SIGMOID_DERIVED;
        final Function< Double, Double > result     = instance.getDerivedActivationFunction(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getLayerQuantity method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testGetLayerQuantity() {
        System.out.println("getLayerQuantity");
        final INeuralNetworkInterface instance  = new IPerceptronInterfaceImpl();
        final int                     expResult = 3;
        final int                     result    = instance.getLayerQuantity();
        assertThat(result, is(expResult));

    }

    /**
     * Test of getNeuronQuantityInLayer method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testGetNeuronQuantityInLayer() {
        System.out.println("getNeuronQuantityInLayer");
        final int                     layerIndex = 0;
        final INeuralNetworkInterface instance   = new IPerceptronInterfaceImpl();
        final int                     expResult  = 3;
        final int                     result     = instance.getNeuronQuantityInLayer(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getWeight method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testGetWeight() {
        System.out.println("getWeight");
        final int                     layerIndex               = 0;
        final int                     neuronIndex              = 0;
        final int                     neuronIndexPreviousLayer = 0;
        final INeuralNetworkInterface instance                 = new IPerceptronInterfaceImpl();
        final double                  expResult                = 0.3556;
        instance.setWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer, expResult);
        final double result = instance.getWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer);
        assertThat(result, is(expResult));

    }

    /**
     * Test of hasBias method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testHasBias() {
        System.out.println("hasBias");
        int                           layerIndex = 0;
        final INeuralNetworkInterface instance   = new IPerceptronInterfaceImpl();
        boolean                       expResult  = false;
        boolean                       result     = instance.hasBias(layerIndex);
        assertThat(result, is(expResult));

        layerIndex = 1;
        expResult = true;
        result = instance.hasBias(layerIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of setBias method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testSetBias() {
        System.out.println("setBias");
        final int                     layerIndex    = 0;
        final int                     neuronIndex   = 0;
        final double                  correctedBias = 0.56;
        final INeuralNetworkInterface instance      = new IPerceptronInterfaceImpl();
        instance.setBias(layerIndex, neuronIndex, correctedBias);
        assertThat(correctedBias, is(instance.getBias(layerIndex, neuronIndex)));
    }

    /**
     * Test of setWeight method, of class INeuralNetworkInterface.
     */
    @Test
    public
    void testSetWeight() {
        System.out.println("setWeight");
        final int                     layerIndex               = 0;
        final int                     neuronIndex              = 0;
        final int                     neuronIndexPreviousLayer = 0;
        final double                  correctedWeight          = 0.3556;
        final INeuralNetworkInterface instance                 = new IPerceptronInterfaceImpl();
        instance.setWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer, correctedWeight);
        assertThat(correctedWeight, is(instance.getWeight(layerIndex, neuronIndex, neuronIndexPreviousLayer)));
    }

    /**
     *
     */
    public static
    class IPerceptronInterfaceImpl
            implements INeuralNetworkInterface {
        static final int       layerQuantity         = 3;
        static final int       neuronQuantityInLayer = 3;
        final        boolean[] bias                  = { false, true, false, true };
        double biasB  = 0.0;
        double weight = 0.0;

        @Override
        public
        Function< Double, Double > getActivationFunction( final int layerIndex ) {
            return FunctionUtils.SIGMOID;
        }

        @Override
        public
        double getBias(
                final int layerIndex,
                final int neuronIndex
        ) {
            return biasB;
        }

        @Override
        public
        Function< Double, Double > getDerivedActivationFunction(
                final int layerIndex
        ) {
            return FunctionUtils.SIGMOID_DERIVED;
        }

        @Override
        public
        int getLayerQuantity() {
            return layerQuantity;
        }

        @Override
        public
        int getNeuronQuantityInLayer( final int layerIndex ) {
            return neuronQuantityInLayer;
        }

        @Override
        public
        double getWeight(
                final int layerIndex,
                final int neuronIndex,
                final int neuronIndexPreviousLayer
        ) {
            return weight;
        }

        @Override
        public
        boolean hasBias( final int layerIndex ) {
            return bias[layerIndex];
        }

        @Override
        public
        void setBias(
                final int layerIndex,
                final int neuronIndex,
                final double correctedBias
        ) {
            biasB = correctedBias;
        }

        @Override
        public
        void setWeight(
                final int layerIndex,
                final int neuronIndex,
                final int neuronIndexPreviousLayer,
                final double correctedWeight
        ) {
            weight = correctedWeight;
        }
    }

}
