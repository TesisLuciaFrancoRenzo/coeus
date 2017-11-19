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
package ar.edu.unrc.coeus.tdlearning.training;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.interfaces.IProblemToTrain;
import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStatePerceptron;
import ar.edu.unrc.coeus.tdlearning.training.perceptrons.Layer;
import ar.edu.unrc.coeus.tdlearning.training.perceptrons.NeuralNetworkCache;
import ar.edu.unrc.coeus.utils.FunctionUtils;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@SuppressWarnings( { "PointlessArithmeticExpression", "AnonymousInnerClassMayBeStatic" } )
public
class TDTrainerTest {

    /**
     *
     */
    public static final boolean DEBUG = false;

    /**
     *
     */
    public static Function< Double, Double > activationFunctionHidden;

    /**
     *
     */
    public static Function< Double, Double > activationFunctionOutput;

    /**
     *
     */
    public static Function< Double, Double > derivedActivationFunctionHidden;

    /**
     *
     */
    public static Function< Double, Double > derivedActivationFunctionOutput;

    /**
     *
     */
    @BeforeAll
    public static
    void setUpClass() {
        activationFunctionOutput = FunctionUtils.SIGMOID;
        derivedActivationFunctionOutput = FunctionUtils.SIGMOID_DERIVED;
        activationFunctionHidden = FunctionUtils.SIGMOID;
        derivedActivationFunctionHidden = FunctionUtils.SIGMOID_DERIVED;
    }

    /**
     * Test básico con caso de prueba numero 1 informe, class TDTrainerPerceptron.
     */
    @Test
    public
    void testCase1() {
        final BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        neuralNetwork.
                addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        neuralNetwork.getStructure().finalizeStructure();

        //configuramos el contenido de los pesos y bias
        neuralNetwork.setWeight(0, 0, 0, 0.28);
        final double expectedFinalWeight = 1.242203935267051e-9 + 0.28; //resultado obtenido mediante cálculos manuales de w(k,l)
        neuralNetwork.setWeight(1, 0, 0, 0.5);
        neuralNetwork.setWeight(2, 0, 0, 1.5);

        //configuramos las bias
        neuralNetwork.setWeight(0, 1, 0, 0.3);
        neuralNetwork.setWeight(1, 1, 0, 2.5);
        final double expectedFinalBias = 5.945588987834763e-9 + 2.5; //resultado obtenido mediante cálculos manuales de w(k2,j1)
        neuralNetwork.setWeight(2, 1, 0, 3.8);

        final double    input1      = 2.0; //entrada del perceptron en el tiempo t
        final double    input1Tp1   = 5.0; //entrada del perceptron en el tiempo t+1
        final double    lambda      = 0.8;
        final double[]  alpha       = { 0.5, 0.5, 0.5 };
        final boolean[] concurrency = { false, false, false, false };

        final IStatePerceptron stateT = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1;
            }
        };

        final IStatePerceptron stateTp1 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1;
            }
        };

        final INeuralNetworkInterface perceptronInterface = new INeuralNetworkInterface() {

            @Override
            public
            Function< Double, Double > getActivationFunction( final int layerIndex ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range. Index = " + layerIndex);
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public
            double getBias(
                    final int layerIndex,
                    final int neuronIndex
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex);
            }

            @Override
            public
            Function< Double, Double > getDerivedActivationFunction(
                    final int layerIndex
            ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return derivedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivedActivationFunctionHidden;
                }
            }

            @Override
            public
            int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public
            int getNeuronQuantityInLayer( final int layerIndex ) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public
            double getWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public
            boolean hasBias( final int layerIndex ) {
                return true;
            }

            @Override
            public
            void setBias(
                    final int layerIndex,
                    final int neuronIndex,
                    final double correctedBias
            ) {
                neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
            }

            @Override
            public
            void setWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer,
                    final double correctedWeight
            ) {
                neuralNetwork.
                        setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        final IProblemToTrain problem = new IProblemToTrain() {

            @Override
            public
            boolean canExploreThisTurn( final long currentTurn ) {
                return true;
            }

            @Override
            public
            IState computeAfterState(
                    final IState turnInitialState,
                    final IAction action
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            IState computeNextTurnStateFromAfterState( final IState afterState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            Double computeNumericRepresentationFor(
                    final Object[] output
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double deNormalizeValueFromPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            Object[] evaluateStateWithPerceptron( final IState state ) {
                final double[] inputs = new double[neuralNetwork.
                        getLayerNeuronCount(0)];
                for ( int i = 0; i < neuralNetwork.getLayerNeuronCount(0); i++ ) {
                    inputs[i] = ( (IStatePerceptron) state ).
                            translateToPerceptronInput(i);
                }

                final MLData   inputData = new BasicMLData(inputs);
                final MLData   output    = neuralNetwork.compute(inputData);
                final Double[] out       = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }

            @Override
            public
            IState initialize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            ArrayList< IAction > listAllPossibleActions(
                    final IState turnInitialState
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double normalizeValueToPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            void setCurrentState( final IState nextTurnState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input     = { input1 }; //entrada del perceptron en el tiempo t
        final double[] inputTp1  = { input1Tp1 }; //entrada del perceptron en el tiempo t+1
        MLData         inputData = new BasicMLData(input);
        MLData         output    = neuralNetwork.compute(inputData);

        final double[] expResultArrayT = { 0.9946114783313552 };
        double[]       resultArray     = output.getData();
        assertThat(expResultArrayT, is(resultArray));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        output = neuralNetwork.compute(inputData);

        final double[] expResultArrayTP1 = { 0.9946401272292515 };
        resultArray = output.getData();
        assertThat(expResultArrayTP1, is(resultArray));

        //entrenamos
        final TDTrainerPerceptron trainer = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
        trainer.train(problem, stateT, stateTp1, alpha, concurrency);

        final double calculatedFinalWeight = neuralNetwork.getWeight(0, 0, 0);
        final double calculatedFinalBias   = neuralNetwork.getWeight(1, 1, 0);

        assertThat("Nuevo peso para el caso de prueba 1", expectedFinalWeight, is(calculatedFinalWeight));
        assertThat("Nuevo bias para el caso de prueba 1", expectedFinalBias, is(calculatedFinalBias));
    }

    /**
     * Test básico con caso de prueba numero 2 informe, class TDTrainerPerceptron.
     */
    @Test
    public
    void testCase2() {
        final BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, true, 2));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2));
        neuralNetwork.
                addLayer(new BasicLayer(new ActivationSigmoid(), false, 2));
        neuralNetwork.getStructure().finalizeStructure();

        //configuramos el contenido de los pesos
        neuralNetwork.setWeight(0, 0, 0, 0.3);
        neuralNetwork.setWeight(0, 0, 1, 0.2);
        final double expectedFinalWeight = -3.080410860239348e-4 + 0.2; //resultado obtenido mediante cálculos manuales de w(j2,k1)
        neuralNetwork.setWeight(0, 1, 0, 0.1);
        neuralNetwork.setWeight(0, 1, 1, 0.9);

        neuralNetwork.setWeight(1, 0, 0, 0.4);
        neuralNetwork.setWeight(1, 0, 1, 0.5);
        neuralNetwork.setWeight(1, 1, 0, 0.6);
        neuralNetwork.setWeight(1, 1, 1, 0.7);

        //configuramos el contenido de las bias
        neuralNetwork.setWeight(0, 2, 0, 0.81);
        neuralNetwork.setWeight(0, 2, 1, 0.22);
        final double expectedFinalBias = -3.850513575299185e-4 + 0.22; //resultado obtenido mediante cálculos manuales de w(j2,k3)
        neuralNetwork.setWeight(1, 2, 0, 0.11);
        neuralNetwork.setWeight(1, 2, 1, 0.55);

        final double[]  input       = { 0.8, 1.5 }; //entrada del perceptron en el tiempo t
        final double[]  inputTp1    = { 0.3, 0.4 }; //entrada del perceptron en el tiempo t+1
        final double    lambda      = 0.8;
        final double[]  alpha       = { 0.5, 0.5 };
        final boolean[] concurrency = { false, false, false };

        final IStatePerceptron stateT = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input[neuronIndex];
            }
        };

        final IStatePerceptron stateTp1 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return inputTp1[neuronIndex];
            }
        };

        final INeuralNetworkInterface perceptronInterface = new INeuralNetworkInterface() {

            @Override
            public
            Function< Double, Double > getActivationFunction( final int layerIndex ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public
            double getBias(
                    final int layerIndex,
                    final int neuronIndex
            ) {
                if ( hasBias(layerIndex) ) {
                    return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.
                            getLayerNeuronCount(layerIndex - 1), neuronIndex);
                } else {
                    throw new IllegalStateException("No hay bias en la capa " + layerIndex);
                }
            }

            @Override
            public
            Function< Double, Double > getDerivedActivationFunction(
                    final int layerIndex
            ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return derivedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivedActivationFunctionHidden;
                }
            }

            @Override
            public
            int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public
            int getNeuronQuantityInLayer( final int layerIndex ) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public
            double getWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public
            boolean hasBias( final int layerIndex ) {
                return neuralNetwork.isLayerBiased(layerIndex - 1);
            }

            @Override
            public
            void setBias(
                    final int layerIndex,
                    final int neuronIndex,
                    final double correctedBias
            ) {
                if ( hasBias(layerIndex) ) {
                    neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.
                            getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
                } else {
                    throw new IllegalStateException("No hay bias en la capa " + layerIndex);
                }
            }

            @Override
            public
            void setWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer,
                    final double correctedWeight
            ) {
                neuralNetwork.
                        setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        final IProblemToTrain problem = new IProblemToTrain() {

            @Override
            public
            boolean canExploreThisTurn( final long currentTurn ) {
                return true;
            }

            @Override
            public
            IState computeAfterState(
                    final IState turnInitialState,
                    final IAction action
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            IState computeNextTurnStateFromAfterState( final IState afterState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            Double computeNumericRepresentationFor(
                    final Object[] output
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double deNormalizeValueFromPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            Object[] evaluateStateWithPerceptron( final IState state ) {
                final double[] inputs = new double[neuralNetwork.
                        getLayerNeuronCount(0)];
                for ( int i = 0; i < neuralNetwork.getLayerNeuronCount(0); i++ ) {
                    inputs[i] = ( (IStatePerceptron) state ).translateToPerceptronInput(i);
                }

                final MLData   inputData = new BasicMLData(inputs);
                final MLData   output    = neuralNetwork.compute(inputData);
                final Double[] out       = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }

            @Override
            public
            IState initialize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            ArrayList< IAction > listAllPossibleActions(
                    final IState turnInitialState
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double normalizeValueToPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            void setCurrentState( final IState nextTurnState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        MLData inputData = new BasicMLData(input);
        MLData output    = neuralNetwork.compute(inputData);

        final double[] expResultArrayT = { 0.7164779076006158, 0.8218381521799242 };
        assertThat(output.getData(), is(expResultArrayT));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        output = neuralNetwork.compute(inputData);

        final double[] expResultArrayTp1 = { 0.6879369497348741, 0.7970369750469807 };
        assertThat(output.getData(), is(expResultArrayTp1));

        // Verificamos que las fNet de las cache sean iguales a lo calculado por encog
        final Class[] argTypes = { IStatePerceptron.class, NeuralNetworkCache.class };
        Method        method;

        try {
            method = TDTrainerPerceptron.class.getDeclaredMethod("createCache", argTypes);
            NeuralNetworkCache cache = null;
            try {
                method.setAccessible(true);
                final Object[]            args       = { stateT, null };
                final TDTrainerPerceptron trainer2   = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
                final Field               alphaField = TDTrainerPerceptron.class.getDeclaredField("alpha");
                alphaField.setAccessible(true);
                alphaField.set(trainer2, alpha);
                final Field problemField = TDTrainerPerceptron.class.getDeclaredField("problem");
                problemField.setAccessible(true);
                problemField.set(trainer2, problem);
                final Field concurrencyInLayerField = TDTrainerPerceptron.class.getDeclaredField("concurrencyInLayer");
                concurrencyInLayerField.setAccessible(true);
                concurrencyInLayerField.set(trainer2, concurrency);

                cache = (NeuralNetworkCache) method.invoke(trainer2, args);
            } catch ( NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ex ) {
                fail(ex.getLocalizedMessage());
            }
            if ( cache != null ) {
                final Layer    lastLayer    = cache.getLayer(cache.getOutputLayerIndex());
                final double[] cacheOutputs = { lastLayer.getNeuron(0).getOutput(), lastLayer.getNeuron(1).getOutput() };
                assertThat(cacheOutputs, is(expResultArrayT));
            } else {
                fail("cache=null");
            }
        } catch ( NoSuchMethodException | SecurityException ex ) {
            fail("No se encontró el método:" + ex.getLocalizedMessage());
        }

        try {
            method = TDTrainerPerceptron.class.getDeclaredMethod("createCache", argTypes);
            NeuralNetworkCache cache = null;
            try {
                method.setAccessible(true);
                final Object[]            args       = { stateTp1, null };
                final TDTrainerPerceptron trainer2   = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
                final Field               alphaField = TDTrainerPerceptron.class.getDeclaredField("alpha");
                alphaField.setAccessible(true);
                alphaField.set(trainer2, alpha);
                final Field problemField = TDTrainerPerceptron.class.getDeclaredField("problem");
                problemField.setAccessible(true);
                problemField.set(trainer2, problem);
                final Field concurrencyInLayerField = TDTrainerPerceptron.class.getDeclaredField("concurrencyInLayer");
                concurrencyInLayerField.setAccessible(true);
                concurrencyInLayerField.set(trainer2, concurrency);

                cache = (NeuralNetworkCache) method.invoke(trainer2, args);
            } catch ( NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ex ) {
                fail(ex.getLocalizedMessage());
            }
            if ( cache != null ) {
                final Layer    lastLayer    = cache.getLayer(cache.getOutputLayerIndex());
                final double[] cacheOutputs = { lastLayer.getNeuron(0).getOutput(), lastLayer.getNeuron(1).getOutput() };
                assertThat(cacheOutputs, is(expResultArrayTp1));
            } else {
                fail("cache=null");
            }
        } catch ( NoSuchMethodException | SecurityException ex ) {
            fail("No se encontró el método:" + ex.getLocalizedMessage());
        }

        //entrenamos
        final TDTrainerPerceptron trainer = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
        trainer.train(problem, stateT, stateTp1, alpha, concurrency);

        final double calculatedFinalWeight = neuralNetwork.getWeight(0, 0, 1);
        final double calculatedFinalBias   = neuralNetwork.getWeight(0, 2, 1);

        assertThat("Nuevo peso para el caso de prueba 2", expectedFinalWeight, is(calculatedFinalWeight));
        assertThat("Nuevo bias para el caso de prueba 2", expectedFinalBias, is(calculatedFinalBias));
    }

    /**
     * Test básico con caso de prueba numero 2 informe, class TDTrainerPerceptron. Se utilizaron las mismas formulas pero el campo de las bias se
     * forzó el valor cero.
     */
    @Test
    public
    void testCase2WithoutBias() {
        final BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, false, 2));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 2));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 2));
        neuralNetwork.getStructure().finalizeStructure();

        //configuramos el contenido de los pesos
        neuralNetwork.setWeight(0, 0, 0, 0.3);
        neuralNetwork.setWeight(0, 0, 1, 0.2);
        final double expectedFinalWeight = -6.119615371341125e-4 + 0.2; //resultado obtenido mediante cálculos manuales de w(j2,k1)
        neuralNetwork.setWeight(0, 1, 0, 0.1);
        neuralNetwork.setWeight(0, 1, 1, 0.9);

        neuralNetwork.setWeight(1, 0, 0, 0.4);
        neuralNetwork.setWeight(1, 0, 1, 0.5);
        neuralNetwork.setWeight(1, 1, 0, 0.6);
        neuralNetwork.setWeight(1, 1, 1, 0.7);

        final double[]  input       = { 0.8, 1.5 }; //entrada del perceptron en el tiempo t
        final double[]  inputTp1    = { 0.3, 0.4 }; //entrada del perceptron en el tiempo t+1
        final double    lambda      = 0.8;
        final double[]  alpha       = { 0.5, 0.5 };
        final boolean[] concurrency = { false, false, false };

        final IStatePerceptron stateT = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input[neuronIndex];
            }
        };

        final IStatePerceptron stateTp1 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return inputTp1[neuronIndex];
            }
        };

        final INeuralNetworkInterface perceptronInterface = new INeuralNetworkInterface() {

            @Override
            public
            Function< Double, Double > getActivationFunction( final int layerIndex ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public
            double getBias(
                    final int layerIndex,
                    final int neuronIndex
            ) {
                throw new IllegalAccessError();
            }

            @Override
            public
            Function< Double, Double > getDerivedActivationFunction(
                    final int layerIndex
            ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return derivedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivedActivationFunctionHidden;
                }
            }

            @Override
            public
            int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public
            int getNeuronQuantityInLayer( final int layerIndex ) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public
            double getWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public
            boolean hasBias( final int layerIndex ) {
                return false;
            }

            @Override
            public
            void setBias(
                    final int layerIndex,
                    final int neuronIndex,
                    final double correctedBias
            ) {
                throw new IllegalAccessError();
            }

            @Override
            public
            void setWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer,
                    final double correctedWeight
            ) {
                neuralNetwork.
                        setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        final IProblemToTrain problem = new IProblemToTrain() {

            @Override
            public
            boolean canExploreThisTurn( final long currentTurn ) {
                return true;
            }

            @Override
            public
            IState computeAfterState(
                    final IState turnInitialState,
                    final IAction action
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            IState computeNextTurnStateFromAfterState( final IState afterState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            Double computeNumericRepresentationFor(
                    final Object[] output
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double deNormalizeValueFromPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            Object[] evaluateStateWithPerceptron( final IState state ) {
                final double[] inputs = new double[neuralNetwork.getLayerNeuronCount(0)];
                for ( int i = 0; i < neuralNetwork.getLayerNeuronCount(0); i++ ) {
                    inputs[i] = ( (IStatePerceptron) state ).translateToPerceptronInput(i);
                }

                final MLData   inputData = new BasicMLData(inputs);
                final MLData   output    = neuralNetwork.compute(inputData);
                final Double[] out       = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }

            @Override
            public
            IState initialize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            ArrayList< IAction > listAllPossibleActions(
                    final IState turnInitialState
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double normalizeValueToPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            void setCurrentState( final IState nextTurnState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        MLData inputData = new BasicMLData(input);
        MLData output    = neuralNetwork.compute(inputData);

        final double[] expResultArrayT = { 0.6747942572811074, 0.7050544341127909 };
        assertThat(output.getData(), is(expResultArrayT));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        output = neuralNetwork.compute(inputData);

        final double[] expResultArrayTp1 = { 0.6399326115933596, 0.6656699788589633 };
        assertThat(output.getData(), is(expResultArrayTp1));

        // Verificamos que las fNet de las cache sean iguales a lo calculado por encog
        final Class[] argTypes = { IStatePerceptron.class, NeuralNetworkCache.class };
        Method        method;

        try {
            method = TDTrainerPerceptron.class.getDeclaredMethod("createCache", argTypes);
            NeuralNetworkCache cache = null;
            try {
                method.setAccessible(true);
                final Object[]            args       = { stateT, null };
                final TDTrainerPerceptron trainer2   = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
                final Field               alphaField = TDTrainerPerceptron.class.getDeclaredField("alpha");
                alphaField.setAccessible(true);
                alphaField.set(trainer2, alpha);
                final Field problemField = TDTrainerPerceptron.class.getDeclaredField("problem");
                problemField.setAccessible(true);
                problemField.set(trainer2, problem);
                final Field concurrencyInLayerField = TDTrainerPerceptron.class.getDeclaredField("concurrencyInLayer");
                concurrencyInLayerField.setAccessible(true);
                concurrencyInLayerField.set(trainer2, concurrency);

                cache = (NeuralNetworkCache) method.invoke(trainer2, args);
            } catch ( NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ex ) {
                fail(ex.getLocalizedMessage());
            }
            if ( cache != null ) {
                final Layer    lastLayer    = cache.getLayer(cache.getOutputLayerIndex());
                final double[] cacheOutputs = { lastLayer.getNeuron(0).getOutput(), lastLayer.getNeuron(1).getOutput() };
                assertThat(cacheOutputs, is(expResultArrayT));
            } else {
                fail("cache=null");
            }
        } catch ( NoSuchMethodException | SecurityException ex ) {
            fail("No se encontró el método:" + ex.getLocalizedMessage());
        }

        try {
            method = TDTrainerPerceptron.class.getDeclaredMethod("createCache", argTypes);
            NeuralNetworkCache cache = null;
            try {
                method.setAccessible(true);
                final Object[]            args       = { stateTp1, null };
                final TDTrainerPerceptron trainer2   = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
                final Field               alphaField = TDTrainerPerceptron.class.getDeclaredField("alpha");
                alphaField.setAccessible(true);
                alphaField.set(trainer2, alpha);
                final Field problemField = TDTrainerPerceptron.class.getDeclaredField("problem");
                problemField.setAccessible(true);
                problemField.set(trainer2, problem);
                final Field concurrencyInLayerField = TDTrainerPerceptron.class.getDeclaredField("concurrencyInLayer");
                concurrencyInLayerField.setAccessible(true);
                concurrencyInLayerField.set(trainer2, concurrency);

                cache = (NeuralNetworkCache) method.invoke(trainer2, args);
            } catch ( NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ex ) {
                fail(ex.getLocalizedMessage());
            }
            if ( cache != null ) {
                final Layer    lastLayer    = cache.getLayer(cache.getOutputLayerIndex());
                final double[] cacheOutputs = { lastLayer.getNeuron(0).getOutput(), lastLayer.getNeuron(1).getOutput() };
                assertThat(cacheOutputs, is(expResultArrayTp1));
            } else {
                fail("cache=null");
            }
        } catch ( NoSuchMethodException | SecurityException ex ) {
            fail("No se encontró el método:" + ex.getLocalizedMessage());
        }

        //entrenamos
        final TDTrainerPerceptron trainer = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
        trainer.train(problem, stateT, stateTp1, alpha, concurrency);

        final double calculatedFinalWeight = neuralNetwork.getWeight(0, 0, 1);

        assertThat("Nuevo peso para el caso de prueba 2 (sin bias)", expectedFinalWeight, is(calculatedFinalWeight));
    }

    /**
     *
     */
    @Test
    public
    void testCaseEligibilityTrace() {
        final BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        neuralNetwork.getStructure().finalizeStructure();

        final double[] wKJ   = new double[4];
        final double[] wJI   = new double[4];
        final double[] biasJ = new double[4];
        final double[] biasI = new double[4];

        wKJ[0] = 0.1;
        wJI[0] = 0.33;
        biasJ[0] = 0.9;
        biasI[0] = 0.1;

        //configuramos el contenido de los pesos y bias
        neuralNetwork.setWeight(0, 0, 0, wKJ[0]);
        neuralNetwork.setWeight(1, 0, 0, wJI[0]);

        //configuramos las bias
        neuralNetwork.setWeight(0, 1, 0, biasJ[0]);
        neuralNetwork.setWeight(1, 1, 0, biasI[0]);

        final double    input1      = 0.22; //entrada del perceptron en el tiempo t
        final double    input1Tp1   = 0.31; //entrada del perceptron en el tiempo t+1
        final double    lambda      = 0.7;
        final double[]  alpha       = { 0.5, 0.5 };
        final boolean[] concurrency = { false, false, false };

        final IStatePerceptron stateT = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1;
            }
        };

        final IStatePerceptron stateTp1 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1;
            }
        };

        final INeuralNetworkInterface perceptronInterface = new INeuralNetworkInterface() {

            @Override
            public
            Function< Double, Double > getActivationFunction( final int layerIndex ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public
            double getBias(
                    final int layerIndex,
                    final int neuronIndex
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.
                        getLayerNeuronCount(layerIndex - 1), neuronIndex);
            }

            @Override
            public
            Function< Double, Double > getDerivedActivationFunction(
                    final int layerIndex
            ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return derivedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivedActivationFunctionHidden;
                }
            }

            @Override
            public
            int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public
            int getNeuronQuantityInLayer( final int layerIndex ) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public
            double getWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public
            boolean hasBias( final int layerIndex ) {
                return true;
            }

            @Override
            public
            void setBias(
                    final int layerIndex,
                    final int neuronIndex,
                    final double correctedBias
            ) {
                neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.
                        getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
            }

            @Override
            public
            void setWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer,
                    final double correctedWeight
            ) {
                neuralNetwork.
                        setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        final IProblemToTrain problem = new IProblemToTrain() {

            @Override
            public
            boolean canExploreThisTurn( final long currentTurn ) {
                return true;
            }

            @Override
            public
            IState computeAfterState(
                    final IState turnInitialState,
                    final IAction action
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            IState computeNextTurnStateFromAfterState( final IState afterState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            Double computeNumericRepresentationFor(
                    final Object[] output
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double deNormalizeValueFromPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            Object[] evaluateStateWithPerceptron( final IState state ) {
                final double[] inputs = new double[neuralNetwork.
                        getLayerNeuronCount(0)];
                for ( int i = 0; i < neuralNetwork.getLayerNeuronCount(0); i++ ) {
                    inputs[i] = ( (IStatePerceptron) state ).
                            translateToPerceptronInput(i);
                }

                final MLData   inputData = new BasicMLData(inputs);
                final MLData   output    = neuralNetwork.compute(inputData);
                final Double[] out       = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }

            @Override
            public
            IState initialize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            ArrayList< IAction > listAllPossibleActions(
                    final IState turnInitialState
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double normalizeValueToPerceptronOutput( final Object value ) {
                return (double) value;

            }

            @Override
            public
            void setCurrentState( final IState nextTurnState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        final double[] fNetK = new double[4];
        final double[] fNetJ = new double[4];
        final double[] fNetI = new double[4];

        final double[] deltaII = new double[4];
        final double[] deltaIJ = new double[4];

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input     = { input1 }; //entrada del perceptron en el tiempo t
        final double[] inputTp1  = { input1Tp1 }; //entrada del perceptron en el tiempo t+1
        MLData         inputData = new BasicMLData(input);
        MLData         output    = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[0] = input1;
        fNetJ[0] = FunctionUtils.SIGMOID.apply(( fNetK[0] * wKJ[0] ) + biasJ[0]);
        fNetI[0] = FunctionUtils.SIGMOID.apply(( fNetJ[0] * wJI[0] ) + biasI[0]);

        final double[] expResultArrayT = { fNetI[0] };
        double[]       resultArray     = output.getData();
        assertThat(expResultArrayT, is(resultArray));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[1] = input1Tp1;
        fNetJ[1] = FunctionUtils.SIGMOID.apply(( fNetK[1] * wKJ[0] ) + biasJ[0]);
        fNetI[1] = FunctionUtils.SIGMOID.apply(( fNetJ[1] * wJI[0] ) + biasI[0]);

        final double[] expResultArrayTP1 = { fNetI[1] };
        resultArray = output.getData();
        assertThat(expResultArrayTP1, is(resultArray));

        //---------- entrenamos---------------------------------------
        final TDTrainerPerceptron trainer = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
        trainer.train(problem, stateT, stateTp1, alpha, concurrency);

        wJI[1] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[1] = neuralNetwork.getWeight(0, 0, 0);
        biasJ[1] = neuralNetwork.getWeight(0, 1, 0);
        biasI[1] = neuralNetwork.getWeight(1, 1, 0);

        //calculamos valores que deberían resultar
        deltaII[0] = FunctionUtils.SIGMOID_DERIVED.apply(fNetI[0]);
        deltaIJ[0] = deltaII[0] * FunctionUtils.SIGMOID_DERIVED.
                apply(fNetJ[0]) * wJI[0];

        //W(k,J)
        double expectedNewWKJ = ( alpha[0] * ( fNetI[1] - fNetI[0] ) * deltaIJ[0] * fNetK[0] ) + wKJ[0];
        assertThat("expectedNewWKJ primera actualización", expectedNewWKJ, is(wKJ[1]));

        //W(J,I)
        double expectedNewWJI = ( alpha[0] * ( fNetI[1] - fNetI[0] ) * deltaII[0] * fNetJ[0] ) + wJI[0];
        assertThat("expectedNewWKJ primera actualización", expectedNewWJI, is(wJI[1]));

        //bias(j)
        double expectedNewBiasJ = ( alpha[0] * ( fNetI[1] - fNetI[0] ) * deltaIJ[0] * 1.0 ) + biasJ[0];
        assertThat("expectedNewBiasJ primera actualización", expectedNewBiasJ, is(biasJ[1]));

        //bias(I)
        double expectedNewBiasI = ( alpha[0] * ( fNetI[1] - fNetI[0] ) * deltaII[0] * 1.0 ) + biasI[0];
        assertThat("expectedNewBiasI primera actualización", expectedNewBiasI, is(biasI[1]));

        //=============== calculamos un segundo turno ==============================
        final double input1Tp1_2 = 0.44; //entrada del perceptron en el tiempo t+1

        final IStatePerceptron stateT_2 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1;
            }

        };

        final IStatePerceptron stateTp1_2 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1_2;
            }

        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input_2    = { input1Tp1 }; //entrada del perceptron en el tiempo t
        final double[] inputTp1_2 = { input1Tp1_2 }; //entrada del perceptron en el tiempo t+1
        inputData = new BasicMLData(input_2);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[1] = input1Tp1;
        fNetJ[1] = FunctionUtils.SIGMOID.apply(( fNetK[1] * wKJ[1] ) + biasJ[1]);
        fNetI[1] = FunctionUtils.SIGMOID.apply(( fNetJ[1] * wJI[1] ) + biasI[1]);

        final double[] expResultArrayT_2 = { fNetI[1] };
        resultArray = output.getData();
        assertThat(expResultArrayT_2, is(resultArray));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1_2);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[2] = input1Tp1_2;
        fNetJ[2] = FunctionUtils.SIGMOID.apply(( fNetK[2] * wKJ[1] ) + biasJ[1]);
        fNetI[2] = FunctionUtils.SIGMOID.apply(( fNetJ[2] * wJI[1] ) + biasI[1]);

        final double[] expResultArrayTP1_2 = { fNetI[2] };
        resultArray = output.getData();
        assertThat(expResultArrayTP1_2, is(resultArray));

        //---------- entrenamos---------------------------------------
        trainer.train(problem, stateT_2, stateTp1_2, alpha, concurrency);

        wJI[2] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[2] = neuralNetwork.getWeight(0, 0, 0);
        biasJ[2] = neuralNetwork.getWeight(0, 1, 0);
        biasI[2] = neuralNetwork.getWeight(1, 1, 0);

        //calculamos valores que deberían resultar
        deltaII[1] = FunctionUtils.SIGMOID_DERIVED.apply(fNetI[1]);
        deltaIJ[1] = deltaII[1] * FunctionUtils.SIGMOID_DERIVED.
                apply(fNetJ[1]) * wJI[1];

        double error = alpha[0] * ( fNetI[2] - fNetI[1] ); //error = (double) 2.1083069642169328E-5
        //W(k,J)
        double traceT0 = deltaIJ[0] * fNetK[0]; //trazaT0 = (double) 0.003592589632991708
        double traceT1 = deltaIJ[1] * fNetK[1]; //trazaT1 = (double) 0.005042135188521667
        double sum =
                ( Math.pow(lambda, ( 2 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 2 - 2 )) * traceT1 ); //sumatoria = (double) 0.007556947931615862
        expectedNewWKJ = ( error * sum ) + wKJ[1]; //expectedNewWKJ = (double) 0.10000021201860962
        assertThat("expectedNewWKJ segunda actualización", expectedNewWKJ, is(wKJ[2]));

        //W(J,I)
        traceT0 = deltaII[0] * fNetJ[0]; //trazaT0 = (double) 0.17390479362330252
        traceT1 = deltaII[1] * fNetJ[1]; //trazaT1 = (double) 0.17433161534722577
        sum = ( Math.pow(lambda, ( 2 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 2 - 2 )) * traceT1 ); //sumatoria = (double) 0.29606497088353756
        expectedNewWJI = ( error * sum ) + wJI[1]; //expectedNewWJI = (double) 0.33000879273802003
        assertThat("expectedNewWJI segunda actualización", expectedNewWJI, is(wJI[2]));

        //bias(j)
        traceT0 = deltaIJ[0]; //trazaT0 = (double) 0.016329952877235036
        traceT1 = deltaIJ[1]; //trazaT1 = (double) 0.016264952221037635
        sum = ( Math.pow(lambda, ( 2 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 2 - 2 )) * traceT1 ); //sumatoria = (double) 0.02769591923510216
        expectedNewBiasJ = ( error * sum ) + biasJ[1]; //expectedNewBiasJ = (double) 0.9000008234374944
        assertThat("expectedNewBiasJ segunda actualización", expectedNewBiasJ, is(biasJ[2]));

        //bias(I)
        traceT0 = deltaII[0]; //trazaT0 = (double) 0.24307069499125544
        traceT1 = deltaII[1]; //trazaT1 = (double) 0.24304603463087834
        sum = ( Math.pow(lambda, ( 2 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 2 - 2 )) * traceT1 ); //sumatoria = (double) 0.4131955211247571
        expectedNewBiasI = ( error * sum ) + biasI[1]; //expectedNewBiasI = (double) 0.10001227671277943
        assertThat("expectedNewBiasI segunda actualización", expectedNewBiasI, is(biasI[2]));

        //=============== calculamos un tercer turno ==============================
        final double input1Tp1_3 = 0.01; //entrada del perceptron en el tiempo t+1

        final IStatePerceptron stateT_3 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1_2;
            }

        };

        final IStatePerceptron stateTp1_3 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1_3;
            }

        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input_3 = { input1Tp1_2 }; //entrada del perceptron en el tiempo t
        inputData = new BasicMLData(input_3);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[2] = input1Tp1_2;
        fNetJ[2] = FunctionUtils.SIGMOID.apply(( fNetK[2] * wKJ[2] ) + biasJ[2]);
        fNetI[2] = FunctionUtils.SIGMOID.apply(( fNetJ[2] * wJI[2] ) + biasI[2]);

        final double[] expResultArrayT_3 = { fNetI[2] };
        resultArray = output.getData();
        assertThat(expResultArrayT_3, is(resultArray));

        //calculamos valores que deberían resultar
        final Object[] evalOutput = problem.evaluateStateWithPerceptron(stateTp1_3);
        for ( int i = 0; i < evalOutput.length; i++ ) {
            evalOutput[i] = problem.deNormalizeValueFromPerceptronOutput(evalOutput[i]);
        }
        final double fNetIFinal = (double) evalOutput[0];

        //---------- entrenamos---------------------------------------
        trainer.train(problem, stateT_3, stateTp1_3, alpha, concurrency);

        wJI[3] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[3] = neuralNetwork.getWeight(0, 0, 0);
        biasJ[3] = neuralNetwork.getWeight(0, 1, 0);
        biasI[3] = neuralNetwork.getWeight(1, 1, 0);

        //calculamos valores que deberían resultar
        deltaII[2] = FunctionUtils.SIGMOID_DERIVED.apply(fNetI[2]);
        deltaIJ[2] = deltaII[2] * FunctionUtils.SIGMOID_DERIVED.
                apply(fNetJ[2]) * wJI[2];

        error = alpha[0] * ( fNetIFinal - fNetI[2] ); //

        //W(k,J)
        traceT0 = deltaIJ[0] * fNetK[0]; //
        traceT1 = deltaIJ[1] * fNetK[1]; //
        double traceT2 = deltaIJ[2] * fNetK[2]; //
        sum = ( Math.pow(lambda, ( 3 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 3 - 2 )) * traceT1 ) + ( Math.pow(lambda, ( 3 - 3 )) * traceT2 ); //
        expectedNewWKJ = ( error * sum ) + wKJ[2]; //
        assertThat("expectedNewWKJ tercera actualización", expectedNewWKJ, is(wKJ[3]));

        //W(J,I)
        traceT0 = deltaII[0] * fNetJ[0]; //
        traceT1 = deltaII[1] * fNetJ[1]; //
        traceT2 = deltaII[2] * fNetJ[2]; //
        sum = ( Math.pow(lambda, ( 3 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 3 - 2 )) * traceT1 ) + ( Math.pow(lambda, ( 3 - 3 )) * traceT2 ); //
        expectedNewWJI = ( error * sum ) + wJI[2]; //
        assertThat("expectedNewWJI tercera actualización", expectedNewWJI, is(wJI[3]));

        //bias(j)
        traceT0 = deltaIJ[0]; //
        traceT1 = deltaIJ[1]; //
        traceT2 = deltaIJ[2]; //
        sum = ( Math.pow(lambda, ( 3 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 3 - 2 )) * traceT1 ) + ( Math.pow(lambda, ( 3 - 3 )) * traceT2 ); //
        expectedNewBiasJ = ( error * sum ) + biasJ[2]; //
        assertThat("expectedNewBiasJ tercera actualización", expectedNewBiasJ, is(biasJ[3]));

        //bias(I)
        traceT0 = deltaII[0]; //
        traceT1 = deltaII[1]; //
        traceT2 = deltaII[2]; //
        sum = ( Math.pow(lambda, ( 3 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 3 - 2 )) * traceT1 ) + ( Math.pow(lambda, ( 3 - 3 )) * traceT2 ); //
        expectedNewBiasI = ( error * sum ) + biasI[2]; //
        assertThat("expectedNewBiasI tercera actualización", expectedNewBiasI, is(biasI[3]));
    }

    /**
     *
     */
    @Test
    public
    void testCaseEligibilityTraceWithoutBias() {
        final BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, false, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        neuralNetwork.getStructure().finalizeStructure();

        final double[] wKJ = new double[4];
        final double[] wJI = new double[4];

        wKJ[0] = 0.1;
        wJI[0] = 0.33;

        //configuramos el contenido de los pesos y bias
        neuralNetwork.setWeight(0, 0, 0, wKJ[0]);
        neuralNetwork.setWeight(1, 0, 0, wJI[0]);

        final double    input1      = 0.22; //entrada del perceptron en el tiempo t
        final double    input1Tp1   = 0.31; //entrada del perceptron en el tiempo t+1
        final double    lambda      = 0.7;
        final double[]  alpha       = { 0.5, 0.5 };
        final boolean[] concurrency = { false, false, false };

        final IStatePerceptron stateT = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1;
            }
        };

        final IStatePerceptron stateTp1 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1;
            }
        };

        final INeuralNetworkInterface perceptronInterface = new INeuralNetworkInterface() {

            @Override
            public
            Function< Double, Double > getActivationFunction( final int layerIndex ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public
            double getBias(
                    final int layerIndex,
                    final int neuronIndex
            ) {
                throw new IllegalAccessError();
            }

            @Override
            public
            Function< Double, Double > getDerivedActivationFunction(
                    final int layerIndex
            ) {
                if ( ( layerIndex < 0 ) || ( layerIndex >= getLayerQuantity() ) ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == ( getLayerQuantity() - 1 ) ) {
                    //ultima capa
                    return derivedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivedActivationFunctionHidden;
                }
            }

            @Override
            public
            int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public
            int getNeuronQuantityInLayer( final int layerIndex ) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public
            double getWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer
            ) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public
            boolean hasBias( final int layerIndex ) {
                return false;
            }

            @Override
            public
            void setBias(
                    final int layerIndex,
                    final int neuronIndex,
                    final double correctedBias
            ) {
                throw new IllegalAccessError();
            }

            @Override
            public
            void setWeight(
                    final int layerIndex,
                    final int neuronIndex,
                    final int neuronIndexPreviousLayer,
                    final double correctedWeight
            ) {
                neuralNetwork.setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        final IProblemToTrain problem = new IProblemToTrain() {

            @Override
            public
            boolean canExploreThisTurn( final long currentTurn ) {
                return true;
            }

            @Override
            public
            IState computeAfterState(
                    final IState turnInitialState,
                    final IAction action
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            IState computeNextTurnStateFromAfterState( final IState afterState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            Double computeNumericRepresentationFor(
                    final Object[] output
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double deNormalizeValueFromPerceptronOutput( final Object value ) {
                return (double) value;
            }

            @Override
            public
            Object[] evaluateStateWithPerceptron( final IState state ) {
                final double[] inputs = new double[neuralNetwork.getLayerNeuronCount(0)];
                for ( int i = 0; i < neuralNetwork.getLayerNeuronCount(0); i++ ) {
                    inputs[i] = ( (IStatePerceptron) state ).translateToPerceptronInput(i);
                }

                final MLData   inputData = new BasicMLData(inputs);
                final MLData   output    = neuralNetwork.compute(inputData);
                final Double[] out       = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }

            @Override
            public
            IState initialize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            ArrayList< IAction > listAllPossibleActions(
                    final IState turnInitialState
            ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double normalizeValueToPerceptronOutput( final Object value ) {
                return (double) value;

            }

            @Override
            public
            void setCurrentState( final IState nextTurnState ) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        final double[] fNetK = new double[4];
        final double[] fNetJ = new double[4];
        final double[] fNetI = new double[4];

        final double[] deltaII = new double[4];
        final double[] deltaIJ = new double[4];

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input     = { input1 }; //entrada del perceptron en el tiempo t
        final double[] inputTp1  = { input1Tp1 }; //entrada del perceptron en el tiempo t+1
        MLData         inputData = new BasicMLData(input);
        MLData         output    = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[0] = input1;
        fNetJ[0] = FunctionUtils.SIGMOID.apply(fNetK[0] * wKJ[0]);
        fNetI[0] = FunctionUtils.SIGMOID.apply(fNetJ[0] * wJI[0]);

        final double[] expResultArrayT = { fNetI[0] };
        double[]       resultArray     = output.getData();
        assertThat(expResultArrayT, is(resultArray));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[1] = input1Tp1;
        fNetJ[1] = FunctionUtils.SIGMOID.apply(fNetK[1] * wKJ[0]);
        fNetI[1] = FunctionUtils.SIGMOID.apply(fNetJ[1] * wJI[0]);

        final double[] expResultArrayTP1 = { fNetI[1] };
        resultArray = output.getData();
        assertThat(expResultArrayTP1, is(resultArray));

        //---------- entrenamos---------------------------------------
        final TDTrainerPerceptron trainer = new TDTrainerPerceptron(perceptronInterface, lambda, false, 1.0d);
        trainer.train(problem, stateT, stateTp1, alpha, concurrency);

        wJI[1] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[1] = neuralNetwork.getWeight(0, 0, 0);

        //calculamos valores que deberían resultar
        deltaII[0] = FunctionUtils.SIGMOID_DERIVED.apply(fNetI[0]);
        deltaIJ[0] = deltaII[0] * FunctionUtils.SIGMOID_DERIVED.apply(fNetJ[0]) * wJI[0];

        //W(k,J)
        double expectedNewWKJ = ( alpha[0] * ( fNetI[1] - fNetI[0] ) * deltaIJ[0] * fNetK[0] ) + wKJ[0];
        assertThat("expectedNewWKJ primera actualización", expectedNewWKJ, is(wKJ[1]));

        //W(J,I)
        double expectedNewWJI = ( alpha[0] * ( fNetI[1] - fNetI[0] ) * deltaII[0] * fNetJ[0] ) + wJI[0];
        assertThat("expectedNewWKJ primera actualización", expectedNewWJI, is(wJI[1]));

        //=============== calculamos un segundo turno ==============================
        final double input1Tp1_2 = 0.44; //entrada del perceptron en el tiempo t+1

        final IStatePerceptron stateT_2 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1;
            }

        };

        final IStatePerceptron stateTp1_2 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1_2;
            }

        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input_2    = { input1Tp1 }; //entrada del perceptron en el tiempo t
        final double[] inputTp1_2 = { input1Tp1_2 }; //entrada del perceptron en el tiempo t+1
        inputData = new BasicMLData(input_2);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[1] = input1Tp1;
        fNetJ[1] = FunctionUtils.SIGMOID.apply(fNetK[1] * wKJ[1]);
        fNetI[1] = FunctionUtils.SIGMOID.apply(fNetJ[1] * wJI[1]);

        final double[] expResultArrayT_2 = { fNetI[1] };
        resultArray = output.getData();
        assertThat(expResultArrayT_2, is(resultArray));

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1_2);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[2] = input1Tp1_2;
        fNetJ[2] = FunctionUtils.SIGMOID.apply(fNetK[2] * wKJ[1]);
        fNetI[2] = FunctionUtils.SIGMOID.apply(fNetJ[2] * wJI[1]);

        final double[] expResultArrayTP1_2 = { fNetI[2] };
        resultArray = output.getData();
        assertThat(expResultArrayTP1_2, is(resultArray));

        //---------- entrenamos---------------------------------------
        trainer.train(problem, stateT_2, stateTp1_2, alpha, concurrency);

        wJI[2] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[2] = neuralNetwork.getWeight(0, 0, 0);

        //calculamos valores que deberían resultar
        deltaII[1] = FunctionUtils.SIGMOID_DERIVED.apply(fNetI[1]);
        deltaIJ[1] = deltaII[1] * FunctionUtils.SIGMOID_DERIVED.apply(fNetJ[1]) * wJI[1];

        double error = alpha[0] * ( fNetI[2] - fNetI[1] ); //error = (double) 2.1083069642169328E-5
        //W(k,J)
        double traceT0 = deltaIJ[0] * fNetK[0]; //trazaT0 = (double) 0.003592589632991708
        double traceT1 = deltaIJ[1] * fNetK[1]; //trazaT1 = (double) 0.005042135188521667
        double sum =
                ( Math.pow(lambda, ( 2 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 2 - 2 )) * traceT1 ); //sumatoria = (double) 0.007556947931615862
        expectedNewWKJ = ( error * sum ) + wKJ[1]; //expectedNewWKJ = (double) 0.10000021201860962
        assertThat("expectedNewWKJ segunda actualización", expectedNewWKJ, is(wKJ[2]));

        //W(J,I)
        traceT0 = deltaII[0] * fNetJ[0]; //trazaT0 = (double) 0.17390479362330252
        traceT1 = deltaII[1] * fNetJ[1]; //trazaT1 = (double) 0.17433161534722577
        sum = ( Math.pow(lambda, ( 2 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 2 - 2 )) * traceT1 ); //sumatoria = (double) 0.29606497088353756
        expectedNewWJI = ( error * sum ) + wJI[1]; //expectedNewWJI = (double) 0.33000879273802003
        assertThat("expectedNewWJI segunda actualización", expectedNewWJI, is(wJI[2]));

        //=============== calculamos un tercer turno ==============================
        final double input1Tp1_3 = 0.01; //entrada del perceptron en el tiempo t+1

        final IStatePerceptron stateT_3 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1_2;
            }

        };

        final IStatePerceptron stateTp1_3 = new IStatePerceptron() {
            @Override
            public
            IState getCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public
            double getStateReward( final int outputNeuron ) {
                return 0;
            }

            @Override
            public
            boolean isTerminalState() {
                return false;
            }

            @Override
            public
            Double translateToPerceptronInput( final int neuronIndex ) {
                return input1Tp1_3;
            }

        };

        // testeamos que la salida es la esperada. Los cálculos se han realizado
        // manualmente y corresponden al caso de prueba numero 2 del informe.
        // testeamos la salida de t
        final double[] input_3 = { input1Tp1_2 }; //entrada del perceptron en el tiempo t
        inputData = new BasicMLData(input_3);
        output = neuralNetwork.compute(inputData);

        //calculamos valores que deberían resultar
        fNetK[2] = input1Tp1_2;
        fNetJ[2] = FunctionUtils.SIGMOID.apply(fNetK[2] * wKJ[2]);
        fNetI[2] = FunctionUtils.SIGMOID.apply(fNetJ[2] * wJI[2]);

        final double[] expResultArrayT_3 = { fNetI[2] };
        resultArray = output.getData();
        assertThat(expResultArrayT_3, is(resultArray));

        //calculamos valores que deberían resultar
        final Object[] evalOutput = problem.evaluateStateWithPerceptron(stateTp1_3);
        for ( int i = 0; i < evalOutput.length; i++ ) {
            evalOutput[i] = problem.deNormalizeValueFromPerceptronOutput(evalOutput[i]);
        }
        final double fNetIFinal = (double) evalOutput[0];

        //---------- entrenamos---------------------------------------
        trainer.train(problem, stateT_3, stateTp1_3, alpha, concurrency);

        wJI[3] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[3] = neuralNetwork.getWeight(0, 0, 0);

        //calculamos valores que deberían resultar
        deltaII[2] = FunctionUtils.SIGMOID_DERIVED.apply(fNetI[2]);
        deltaIJ[2] = deltaII[2] * FunctionUtils.SIGMOID_DERIVED.apply(fNetJ[2]) * wJI[2];

        error = alpha[0] * ( fNetIFinal - fNetI[2] ); //

        //W(k,J)
        traceT0 = deltaIJ[0] * fNetK[0]; //
        traceT1 = deltaIJ[1] * fNetK[1]; //
        double traceT2 = deltaIJ[2] * fNetK[2]; //
        sum = ( Math.pow(lambda, ( 3 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 3 - 2 )) * traceT1 ) + ( Math.pow(lambda, ( 3 - 3 )) * traceT2 ); //
        expectedNewWKJ = ( error * sum ) + wKJ[2]; //
        assertThat("expectedNewWKJ tercera actualización", wKJ[3], is(expectedNewWKJ));

        //W(J,I)
        traceT0 = deltaII[0] * fNetJ[0]; //
        traceT1 = deltaII[1] * fNetJ[1]; //
        traceT2 = deltaII[2] * fNetJ[2]; //
        sum = ( Math.pow(lambda, ( 3 - 1 )) * traceT0 ) + ( Math.pow(lambda, ( 3 - 2 )) * traceT1 ) + ( Math.pow(lambda, ( 3 - 3 )) * traceT2 ); //
        expectedNewWJI = ( error * sum ) + wJI[2]; //
        assertThat("expectedNewWJI tercera actualización", wJI[3], is(expectedNewWJI));
    }

}
