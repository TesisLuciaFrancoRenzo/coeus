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
package ar.edu.unrc.coeus.tdlearning.training.perceptrons;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NeuronTest {

    /**
     *
     */
    public
    NeuronTest() {
    }

    /**
     *
     */
    @BeforeClass
    public static
    void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static
    void tearDownClass() {
    }

    /**
     *
     */
    @Before
    public
    void setUp() {
    }

    /**
     *
     */
    @After
    public
    void tearDown() {
    }

    /**
     * Test of clearGradients method, of class Neuron.
     */
    @Test
    public
    void testClearDeltas() {
        System.out.println("clearGradients");
        Neuron instance = new Neuron(1, 1);
        instance.clearGradients();
        Double result = instance.getGradient(0);
        assertThat(result, nullValue());
    }

    /**
     * Test of getBias method, of class PartialNeuron.
     */
    @Test
    public
    void testGetBias() {
        System.out.println("getBias");
        Neuron instance = new Neuron(1, 1);
        instance.setBias(1.325);
        Double expResult = 1.325;
        Double result    = instance.getBias();
        assertThat(result, is(expResult));
    }

    /**
     * Test of getGradient method, of class Neuron.
     */
    @Test
    public
    void testGetDelta() {
        System.out.println("getGradient");
        int    outputNeuronIndex = 0;
        Neuron instance          = new Neuron(1, 1);
        instance.setGradient(0, 1d);
        Double expResult = 1d;
        Double result    = instance.getGradient(0);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getGradient method, of class Neuron.
     */
    @Test
    public
    void testGetDeltas() {
        System.out.println("getGradient");
        Neuron       instance  = new Neuron(2, 2);
        List<Double> expResult = new ArrayList<>(2);
        expResult.add(null);
        expResult.add(null);
        List<Double> result = instance.getGradient();
        assertThat(result.size(), is(expResult.size()));
    }

    /**
     * Test of getDerivedOutput method, of class Neuron.
     */
    @Test
    public
    void testGetDerivedOutput() {
        System.out.println("getDerivedOutput");
        Neuron instance = new Neuron(1, 1);
        instance.setDerivedOutput(0.33);
        Double expResult = 0.33;
        Double result    = instance.getDerivedOutput();
        assertThat(result, is(expResult));
    }

    /**
     * Test of getOutput method, of class Neuron.
     */
    @Test
    public
    void testGetOutput() {
        System.out.println("getOutput");
        Neuron instance = new Neuron(1, 1);
        instance.setOutput(0.5);
        Double expResult = 0.5;
        Double result    = instance.getOutput();
        assertThat(result, is(expResult));
    }

    /**
     * Test of getWeight method, of class PartialNeuron.
     */
    @Test
    public
    void testGetWeight() {
        System.out.println("getWeight");
        int    previousLayerNeuronIndex = 0;
        Neuron instance                 = new Neuron(1, 1);
        instance.setWeight(0, 0.356);
        Double expResult = 0.356;
        Double result    = instance.getWeight(previousLayerNeuronIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getWeights method, of class PartialNeuron.
     */
    @Test
    public
    void testGetWeights() {
        System.out.println("getWeights");
        Neuron instance = new Neuron(1, 1);
        instance.setWeight(0, 0.3);
        instance.setWeight(1, 0.4);
        List<Double> expResult = new ArrayList<>(1);
        expResult.add(0.3);
        expResult.add(0.4);
        List<Double> result = instance.getWeights();
        assertThat(result.get(0), is(expResult.get(0)));
        assertThat(result.get(1), is(expResult.get(1)));
    }

    /**
     * Test of setBias method, of class PartialNeuron.
     */
    @Test
    public
    void testSetBias() {
        System.out.println("setBias");
        Double newBias  = 0.356;
        Neuron instance = new Neuron(1, 1);
        instance.setBias(newBias);
        assertThat(instance.getBias(), is(newBias));
    }

    /**
     * Test of setGradient method, of class Neuron.
     */
    @Test
    public
    void testSetDelta() {
        System.out.println("setGradient");
        int    outputNeuronIndex = 0;
        Double gradient          = 0.33;
        Neuron instance          = new Neuron(1, 1);
        instance.setGradient(outputNeuronIndex, gradient);
        assertThat(instance.getGradient().get(outputNeuronIndex), is(gradient));
    }

    /**
     * Test of setDerivedOutput method, of class Neuron.
     */
    @Test
    public
    void testSetDerivedOutput() {
        System.out.println("setDerivedOutput");
        Double derivedOutput = 0.365;
        Neuron instance      = new Neuron(1, 1);
        instance.setDerivedOutput(derivedOutput);
        assertThat(instance.getDerivedOutput(), is(derivedOutput));
    }

    /**
     * Test of setOutput method, of class Neuron.
     */
    @Test
    public
    void testSetOutput() {
        System.out.println("setOutput");
        Double output   = 0.1111;
        Neuron instance = new Neuron(1, 1);
        instance.setOutput(output);
        assertThat(instance.getOutput(), is(output));
    }

    /**
     * Test of setWeight method, of class PartialNeuron.
     */
    @Test
    public
    void testSetWeight() {
        System.out.println("setWeight");
        int    previousLayerNeuronIndex = 0;
        Double weight                   = 0.256;
        Neuron instance                 = new Neuron(1, 1);
        instance.setWeight(previousLayerNeuronIndex, weight);
        assertThat(instance.getWeight(0), is(weight));
    }

}
