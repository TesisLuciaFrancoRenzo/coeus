/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import java.util.function.Function;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TDTrainerTest {

    /**
     *
     */
    public static final boolean DEBUG = false;

    /**
     *
     */
    public static Function<Double, Double> activationFunctionHidden;

    /**
     *
     */
    public static Function<Double, Double> activationFunctionOutput;

    /**
     *
     */
    public static Function<Double, Double> derivatedActivationFunctionHidden;

    /**
     *
     */
    public static Function<Double, Double> derivatedActivationFunctionOutput;

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
        activationFunctionOutput = FunctionUtils.sigmoid;
        derivatedActivationFunctionOutput = FunctionUtils.derivatedSigmoid;

        activationFunctionHidden = FunctionUtils.sigmoid;
        derivatedActivationFunctionHidden = FunctionUtils.derivatedSigmoid;
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
    public TDTrainerTest() {
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
     * Test basico con caso de prueba numero 1 informe, class TDTrainer.
     */
    @Test
    public void testCase1() {
        BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        neuralNetwork.getStructure().finalizeStructure();

        //configuramos el contenido de los pesos y bias
        neuralNetwork.setWeight(0, 0, 0, 0.28);
        double expectedFinalWeight = 1.242203935267051e-9 + 0.28; //resultado obtenido mediante calculos manuales de w(k,l)
        neuralNetwork.setWeight(1, 0, 0, 0.5);
        neuralNetwork.setWeight(2, 0, 0, 1.5);

        //configuramos las bias
        neuralNetwork.setWeight(0, 1, 0, 0.3);
        neuralNetwork.setWeight(1, 1, 0, 2.5);
        double expectedFinalBias = 5.945588987834763e-9 + 2.5; //resultado obtenido mediante calculos manuales de w(k2,j1)
        neuralNetwork.setWeight(2, 1, 0, 3.8);

        double input1 = 2; //entrada del perceptron en el tiempo t
        double input1Tp1 = 5; //entrada del perceptron en el tiempo t+1
        double lambda = 0.8;
        double alpha = 0.5;

        IState stateT = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1;
            }
        };

        IState stateTp1 = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1Tp1;
            }
        };

        IPerceptronInterface perceptronInterface = new IPerceptronInterface() {

            @Override
            public Function<Double, Double> getActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public double getBias(int layerIndex, int neuronIndex) {
                return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex);
            }

            @Override
            public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return derivatedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivatedActivationFunctionHidden;
                }
            }

            @Override
            public int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public int getNeuronQuantityInLayer(int layerIndex) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public boolean hasBias(int layerIndex) {
                return true; //FIXME hacer que consulte realmente si tiene bias la capa o no
            }

            @Override
            public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
                neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
            }

            @Override
            public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
                neuralNetwork.setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe.
        // testeamos la salida de t
        double[] input = {input1}; //entrada del perceptron en el tiempo t
        double[] inputTp1 = {input1Tp1}; //entrada del perceptron en el tiempo t+1
        MLData inputData = new BasicMLData(input);
        MLData outut = neuralNetwork.compute(inputData);

        double[] expResultArrayt = {0.9946114783313552};
        double[] resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArrayt, resultArray, 0.0000000000000001);

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        outut = neuralNetwork.compute(inputData);

        double[] expResultArraytp1 = {0.9946401272292515};
        resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArraytp1, resultArray, 0.0000000000000001);

        //entrenamos
        TDTrainer trainer = new TDTrainer(perceptronInterface);
        trainer.train(stateT, stateTp1, alpha, lambda, false);

        double calculatedFinalWeight = neuralNetwork.getWeight(0, 0, 0);
        double calculatedFinalBias = neuralNetwork.getWeight(1, 1, 0);
        if ( DEBUG ) {
            System.out.println("\n*** =========== Cache del caso de prueba 1 =========== ****");
            trainer.printLastCache();
            System.out.println("\n*** =========== Red neuronal actualizada del caso de prueba 1  =========== ****");
            neuralNetwork.toString(); //FIXME deberia imprimir la red neuronal de encog
        }
        assertEquals("Nuevo peso para el caso de prueba 1", expectedFinalWeight, calculatedFinalWeight, 0.0000000000000001);
        assertEquals("Nuevo bias para el caso de prueba 1", expectedFinalBias, calculatedFinalBias, 0.0000000000000001);
    }

    /**
     * Test basico con caso de prueba numero 2 informe, class TDTrainer.
     */
    @Test
    public void testCase2() {
        BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, true, 2));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 2));
        neuralNetwork.getStructure().finalizeStructure();

        //configuramos el contenido de los pesos
        neuralNetwork.setWeight(0, 0, 0, 0.3);
        neuralNetwork.setWeight(0, 0, 1, 0.2);
        double expectedFinalWeight = -3.080410860239348e-4 + 0.2; //resultado obtenido mediante calculos manuales de w(j2,k1)
        neuralNetwork.setWeight(0, 1, 0, 0.1);
        neuralNetwork.setWeight(0, 1, 1, 0.9);

        neuralNetwork.setWeight(1, 0, 0, 0.4);
        neuralNetwork.setWeight(1, 0, 1, 0.5);
        neuralNetwork.setWeight(1, 1, 0, 0.6);
        neuralNetwork.setWeight(1, 1, 1, 0.7);

        //configuramos el contenido de las bias
        neuralNetwork.setWeight(0, 2, 0, 0.81);
        neuralNetwork.setWeight(0, 2, 1, 0.22);
        double expectedFinalBias = -3.850513575299185e-4 + 0.22; //resultado obtenido mediante calculos manuales de w(j2,k3)
        neuralNetwork.setWeight(1, 2, 0, 0.11);
        neuralNetwork.setWeight(1, 2, 1, 0.55);

        double[] input = {0.8, 1.5}; //entrada del perceptron en el tiempo t
        double[] inputTp1 = {0.3, 0.4}; //entrada del perceptron en el tiempo t+1
        double lambda = 0.8;
        double alpha = 0.5;

        IState stateT = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input[neuronIndex];
            }
        };

        IState stateTp1 = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return inputTp1[neuronIndex];
            }
        };

        IPerceptronInterface perceptronInterface = new IPerceptronInterface() {

            @Override
            public Function<Double, Double> getActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public double getBias(int layerIndex, int neuronIndex) {
                return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex);
            }

            @Override
            public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return derivatedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivatedActivationFunctionHidden;
                }
            }

            @Override
            public int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public int getNeuronQuantityInLayer(int layerIndex) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public boolean hasBias(int layerIndex) {
                return true; //FIXME hacer que consulte realmente si tiene bias la capa o no
            }

            @Override
            public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
                neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
            }

            @Override
            public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
                neuralNetwork.setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe.
        // testeamos la salida de t
        MLData inputData = new BasicMLData(input);
        MLData outut = neuralNetwork.compute(inputData);

        double[] expResultArrayt = {0.7164779076006158, 0.8218381521799242};
        double[] resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArrayt, resultArray, 0.0000000000000001);

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        outut = neuralNetwork.compute(inputData);

        double[] expResultArraytp1 = {0.6879369497348741, 0.7970369750469807};
        resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArraytp1, resultArray, 0.0000000000000001);

        //entrenamos
        TDTrainer trainer = new TDTrainer(perceptronInterface);
        trainer.train(stateT, stateTp1, alpha, lambda, false);

        double calculatedFinalWeight = neuralNetwork.getWeight(0, 0, 1);
        double calculatedFinalBias = neuralNetwork.getWeight(0, 2, 1);

        if ( DEBUG ) {
            System.out.println("\n**** =========== Cache del caso de prueba 2 =========== ****");
            trainer.printLastCache();
            System.out.println("\n*** =========== Red neuronal actualizada del caso de prueba 2 =========== ****");
            neuralNetwork.toString(); //FIXME arreglar el grafico de la red neuronal
        }

        assertEquals("Nuevo peso para el caso de prueba 2", expectedFinalWeight, calculatedFinalWeight, 0.0000000000000001);
        assertEquals("Nueva bias para el caso de prueba 2", expectedFinalBias, calculatedFinalBias, 0.0000000000000001);
    }

    /**
     * Test basico con caso de prueba numero 1 informe, class TDTrainer.
     */
    @Test
    public void testCaseEligibilityTrace() {
        BasicNetwork neuralNetwork = new BasicNetwork();

        neuralNetwork.addLayer(new BasicLayer(null, true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        neuralNetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        neuralNetwork.getStructure().finalizeStructure();

        double[] wKJ = new double[4];
        double[] wJI = new double[4];
        double[] biasJ = new double[4];
        double[] biasI = new double[4];

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

        double input1 = 0.22; //entrada del perceptron en el tiempo t
        double input1Tp1 = 0.31; //entrada del perceptron en el tiempo t+1
        double lambda = 0.7;
        double alpha = 0.1;

        IState stateT = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1;
            }

        };

        IState stateTp1 = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1Tp1;
            }

        };

        IPerceptronInterface perceptronInterface = new IPerceptronInterface() {

            @Override
            public Function<Double, Double> getActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return activationFunctionOutput;
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public double getBias(int layerIndex, int neuronIndex) {
                return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex);
            }

            @Override
            public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return derivatedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivatedActivationFunctionHidden;
                }
            }

            @Override
            public int getLayerQuantity() {
                return neuralNetwork.getLayerCount();
            }

            @Override
            public int getNeuronQuantityInLayer(int layerIndex) {
                return neuralNetwork.getLayerNeuronCount(layerIndex);
            }

            @Override
            public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
                return neuralNetwork.getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public boolean hasBias(int layerIndex) {
                return true; //FIXME hacer que consulte realmente si tiene bias la capa o no
            }

            @Override
            public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
                neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
            }

            @Override
            public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
                neuralNetwork.setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };

        double[] fnetK = new double[4];
        double[] fnetJ = new double[4];
        double[] fnetI = new double[4];

        double[] deltaII = new double[4];
        double[] deltaIJ = new double[4];

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe.
        // testeamos la salida de t
        double[] input = {input1}; //entrada del perceptron en el tiempo t
        double[] inputTp1 = {input1Tp1}; //entrada del perceptron en el tiempo t+1
        MLData inputData = new BasicMLData(input);
        MLData outut = neuralNetwork.compute(inputData);

        //calculamos valores que deberian resultar
        fnetK[0] = input1;
        fnetJ[0] = FunctionUtils.sigmoid.apply(fnetK[0] * wKJ[0] + biasJ[0]);
        fnetI[0] = FunctionUtils.sigmoid.apply(fnetJ[0] * wJI[0] + biasI[0]);

        double[] expResultArrayt = {fnetI[0]};
        double[] resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArrayt, resultArray, 0.0000000000000001);

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1);
        outut = neuralNetwork.compute(inputData);

        //calculamos valores que deberian resultar
        fnetK[1] = input1Tp1;
        fnetJ[1] = FunctionUtils.sigmoid.apply(fnetK[1] * wKJ[0] + biasJ[0]);
        fnetI[1] = FunctionUtils.sigmoid.apply(fnetJ[1] * wJI[0] + biasI[0]);

        double[] expResultArraytp1 = {fnetI[1]};
        resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArraytp1, resultArray, 0.0000000000000001);

        //---------- entrenamos---------------------------------------
        TDTrainer trainer = new TDTrainer(perceptronInterface);
        trainer.train(stateT, stateTp1, alpha, lambda, false);

        wJI[1] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[1] = neuralNetwork.getWeight(0, 0, 0);
        biasJ[1] = neuralNetwork.getWeight(0, 1, 0);
        biasI[1] = neuralNetwork.getWeight(1, 1, 0);

        //calculamos valores que deberian resultar
        deltaII[0] = FunctionUtils.derivatedSigmoid.apply(fnetI[0]);
        deltaIJ[0] = deltaII[0] * FunctionUtils.derivatedSigmoid.apply(fnetJ[0]) * wJI[0];

        //W(k,J)
        double expectedNewWKJ = alpha * (fnetI[1] - fnetI[0])
                * deltaIJ[0] * fnetK[0] + wKJ[0];
        assertEquals("expectedNewWKJ primera actualizacion", expectedNewWKJ, wKJ[1], 0.0000000000000001);

        //W(J,I)
        double expectedNewWJI = alpha * (fnetI[1] - fnetI[0])
                * deltaII[0] * fnetJ[0] + wJI[0];
        assertEquals("expectedNewWJI primera actualizacion", expectedNewWJI, wJI[1], 0.0000000000000001);

        //bias(j)
        double expectedNewBiasJ = alpha * (fnetI[1] - fnetI[0])
                * deltaIJ[0] * 1 + biasJ[0];
        assertEquals("expectedNewBiasJ primera actualizacion", expectedNewBiasJ, biasJ[1], 0.0000000000000001);

        //bias(I)
        double expectedNewBiasI = alpha * (fnetI[1] - fnetI[0])
                * deltaII[0] * 1 + biasI[0];
        assertEquals("expectedNewBiasI primera actualizacion", expectedNewBiasI, biasI[1], 0.0000000000000001);

        //=============== calculamos un segundo turno ==============================
        double input1_2 = input1Tp1; //entrada del perceptron en el tiempo t (es t+1 anterior)
        double input1Tp1_2 = 0.44; //entrada del perceptron en el tiempo t+1

        IState stateT_2 = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1_2;
            }

        };

        IState stateTp1_2 = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1Tp1_2;
            }

        };

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe.
        // testeamos la salida de t
        double[] input_2 = {input1_2}; //entrada del perceptron en el tiempo t
        double[] inputTp1_2 = {input1Tp1_2}; //entrada del perceptron en el tiempo t+1
        inputData = new BasicMLData(input_2);
        outut = neuralNetwork.compute(inputData);

        //calculamos valores que deberian resultar
        fnetK[1] = input1_2;
        fnetJ[1] = FunctionUtils.sigmoid.apply(fnetK[1] * wKJ[1] + biasJ[1]);
        fnetI[1] = FunctionUtils.sigmoid.apply(fnetJ[1] * wJI[1] + biasI[1]);

        double[] expResultArrayt_2 = {fnetI[1]};
        resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArrayt_2, resultArray, 0.0000000000000001);

        // testeamos la salida de t+1
        inputData = new BasicMLData(inputTp1_2);
        outut = neuralNetwork.compute(inputData);

        //calculamos valores que deberian resultar
        fnetK[2] = input1Tp1_2;
        fnetJ[2] = FunctionUtils.sigmoid.apply(fnetK[2] * wKJ[1] + biasJ[1]);
        fnetI[2] = FunctionUtils.sigmoid.apply(fnetJ[2] * wJI[1] + biasI[1]);

        double[] expResultArraytp1_2 = {fnetI[2]};
        resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArraytp1_2, resultArray, 0.0000000000000001);

        //---------- entrenamos---------------------------------------
        trainer.train(stateT_2, stateTp1_2, alpha, lambda, false);

        wJI[2] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[2] = neuralNetwork.getWeight(0, 0, 0);
        biasJ[2] = neuralNetwork.getWeight(0, 1, 0);
        biasI[2] = neuralNetwork.getWeight(1, 1, 0);

        //calculamos valores que deberian resultar
        deltaII[1] = FunctionUtils.derivatedSigmoid.apply(fnetI[1]);
        deltaIJ[1] = deltaII[1] * FunctionUtils.derivatedSigmoid.apply(fnetJ[1]) * wJI[1];

        double error = alpha * (fnetI[2] - fnetI[1]); //error = (double) 2.1083069642169328E-5
        //W(k,J)
        double trazat0 = deltaIJ[0] * fnetK[0]; //trazat0 = (double) 0.003592589632991708
        double trazat1 = deltaIJ[1] * fnetK[1]; //trazat1 = (double) 0.005042135188521667
        double sumatoria = (Math.pow(lambda, 2 - 1) * trazat0) + (Math.pow(lambda, 2 - 2) * trazat1); //sumatoria = (double) 0.007556947931615862
        expectedNewWKJ = error * sumatoria + wKJ[1]; //expectedNewWKJ = (double) 0.10000021201860962
        assertEquals("expectedNewWKJ segunda actualizacion", expectedNewWKJ, wKJ[2], 0.0000000000000001);

        //W(J,I)
        trazat0 = deltaII[0] * fnetJ[0]; //trazat0 = (double) 0.17390479362330252
        trazat1 = deltaII[1] * fnetJ[1]; //trazat1 = (double) 0.17433161534722577
        sumatoria = (Math.pow(lambda, 2 - 1) * trazat0) + (Math.pow(lambda, 2 - 2) * trazat1); //sumatoria = (double) 0.29606497088353756
        expectedNewWJI = error * sumatoria + wJI[1]; //expectedNewWJI = (double) 0.33000879273802003
        assertEquals("expectedNewWJI segunda actualizacion", expectedNewWJI, wJI[2], 0.0000000000000001);

        //bias(j)
        trazat0 = deltaIJ[0]; //trazat0 = (double) 0.016329952877235036
        trazat1 = deltaIJ[1]; //trazat1 = (double) 0.016264952221037635
        sumatoria = (Math.pow(lambda, 2 - 1) * trazat0) + (Math.pow(lambda, 2 - 2) * trazat1); //sumatoria = (double) 0.02769591923510216
        expectedNewBiasJ = error * sumatoria + biasJ[1]; //expectedNewBiasJ = (double) 0.9000008234374944
        assertEquals("expectedNewBiasJ segunda actualizacion", expectedNewBiasJ, biasJ[2], 0.0000000000000001);

        //bias(I)
        trazat0 = deltaII[0]; //trazat0 = (double) 0.24307069499125544
        trazat1 = deltaII[1]; //trazat1 = (double) 0.24304603463087834
        sumatoria = (Math.pow(lambda, 2 - 1) * trazat0) + (Math.pow(lambda, 2 - 2) * trazat1); //sumatoria = (double) 0.4131955211247571
        expectedNewBiasI = error * sumatoria + biasI[1]; //expectedNewBiasI = (double) 0.10001227671277943
        assertEquals("expectedNewBiasI segunda actualizacion", expectedNewBiasI, biasI[2], 0.0000000000000001);

        //=============== calculamos un tercer turno ==============================
        double input1_3 = input1Tp1_2; //entrada del perceptron en el tiempo t (es t+1 anterior)
        double input1Tp1_3 = 0.01; //entrada del perceptron en el tiempo t+1

        IState stateT_3 = new IState() {

            @Override
            public boolean isTerminalState() {
                return false;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1_3;
            }

        };

        IState stateTp1_3 = new IState() {

            @Override
            public boolean isTerminalState() {
                return true;
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateToPerceptronInput(int neuronIndex) {
                return input1Tp1_3;
            }

        };

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe.
        // testeamos la salida de t
        double[] input_3 = {input1_3}; //entrada del perceptron en el tiempo t
        inputData = new BasicMLData(input_3);
        outut = neuralNetwork.compute(inputData);

        //calculamos valores que deberian resultar
        fnetK[2] = input1_3;
        fnetJ[2] = FunctionUtils.sigmoid.apply(fnetK[2] * wKJ[2] + biasJ[2]);
        fnetI[2] = FunctionUtils.sigmoid.apply(fnetJ[2] * wJI[2] + biasI[2]);

        double[] expResultArrayt_3 = {fnetI[2]};
        resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArrayt_3, resultArray, 0.0000000000000001);

        //calculamos valores que deberian resultar
        double fnetIFinal = stateTp1_3.translateThisFinalStateToPerceptronOutput(0);

        //---------- entrenamos---------------------------------------
        trainer.train(stateT_3, stateTp1_3, alpha, lambda, false);

        wJI[3] = neuralNetwork.getWeight(1, 0, 0);
        wKJ[3] = neuralNetwork.getWeight(0, 0, 0);
        biasJ[3] = neuralNetwork.getWeight(0, 1, 0);
        biasI[3] = neuralNetwork.getWeight(1, 1, 0);

        //calculamos valores que deberian resultar
        deltaII[2] = FunctionUtils.derivatedSigmoid.apply(fnetI[2]);
        deltaIJ[2] = deltaII[2] * FunctionUtils.derivatedSigmoid.apply(fnetJ[2]) * wJI[2];

        error = alpha * (fnetIFinal - fnetI[2]); //

        //W(k,J)
        trazat0 = deltaIJ[0] * fnetK[0]; //
        trazat1 = deltaIJ[1] * fnetK[1]; //
        double trazat2 = deltaIJ[2] * fnetK[2]; //
        sumatoria = (Math.pow(lambda, 3 - 1) * trazat0) + (Math.pow(lambda, 3 - 2) * trazat1) + (Math.pow(lambda, 3 - 3) * trazat2); //
        expectedNewWKJ = error * sumatoria + wKJ[2]; //
        assertEquals("expectedNewWKJ tercera actualizacion", expectedNewWKJ, wKJ[3], 0.0000000000000001);

        //W(J,I)
        trazat0 = deltaII[0] * fnetJ[0]; //
        trazat1 = deltaII[1] * fnetJ[1]; //
        trazat2 = deltaII[2] * fnetJ[2]; //
        sumatoria = (Math.pow(lambda, 3 - 1) * trazat0) + (Math.pow(lambda, 3 - 2) * trazat1) + (Math.pow(lambda, 3 - 3) * trazat2); //
        expectedNewWJI = error * sumatoria + wJI[2]; //
        assertEquals("expectedNewWJI tercera actualizacion", expectedNewWJI, wJI[3], 0.0000000000000001);

        //bias(j)
        trazat0 = deltaIJ[0]; //
        trazat1 = deltaIJ[1]; //
        trazat2 = deltaIJ[2]; //
        sumatoria = (Math.pow(lambda, 3 - 1) * trazat0) + (Math.pow(lambda, 3 - 2) * trazat1) + (Math.pow(lambda, 3 - 3) * trazat2); //
        expectedNewBiasJ = error * sumatoria + biasJ[2]; //
        assertEquals("expectedNewBiasJ tercera actualizacion", expectedNewBiasJ, biasJ[3], 0.0000000000000001);

        //bias(I)
        trazat0 = deltaII[0]; //
        trazat1 = deltaII[1]; //
        trazat2 = deltaII[2]; //
        sumatoria = (Math.pow(lambda, 3 - 1) * trazat0) + (Math.pow(lambda, 3 - 2) * trazat1) + (Math.pow(lambda, 3 - 3) * trazat2); //
        expectedNewBiasI = error * sumatoria + biasI[2]; //
        assertEquals("expectedNewBiasI tercera actualizacion", expectedNewBiasI, biasI[3], 0.0000000000000001);
    }
}
