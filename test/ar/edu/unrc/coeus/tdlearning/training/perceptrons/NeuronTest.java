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

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NeuronTest {

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
    public NeuronTest() {
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
     * Test of clearDeltas method, of class Neuron.
     */
    @Test
    public void testClearDeltas() {
        System.out.println("clearDeltas");
        Neuron instance = new Neuron(1, 1);
        instance.clearDeltas();
        Double result = instance.getDelta(0);
        Double expResult = null;
        assertEquals(expResult, result);
    }

    /**
     * Test of getDelta method, of class Neuron.
     */
    @Test
    public void testGetDelta() {
        System.out.println("getDelta");
        int outputNeuronIndex = 0;
        Neuron instance = new Neuron(1, 1);
        instance.setDelta(0, 1d);
        Double expResult = 1d;
        Double result = instance.getDelta(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeltas method, of class Neuron.
     */
    @Test
    public void testGetDeltas() {
        System.out.println("getDeltas");
        Neuron instance = new Neuron(2, 2);
        List<Double> expResult = new ArrayList<>(2);
        expResult.add(null);
        expResult.add(null);
        List<Double> result = instance.getDeltas();
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of getDerivatedOutput method, of class Neuron.
     */
    @Test
    public void testGetDerivatedOutput() {
        System.out.println("getDerivatedOutput");
        Neuron instance = new Neuron(1, 1);
        instance.setDerivatedOutput(0.33);
        Double expResult = 0.33;
        Double result = instance.getDerivatedOutput();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutput method, of class Neuron.
     */
    @Test
    public void testGetOutput() {
        System.out.println("getOutput");
        Neuron instance = new Neuron(1, 1);
        instance.setOutput(0.5);
        Double expResult = 0.5;
        Double result = instance.getOutput();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDelta method, of class Neuron.
     */
    @Test
    public void testSetDelta() {
        System.out.println("setDelta");
        int outputNeuronIndex = 0;
        Double delta = 0.33;
        Neuron instance = new Neuron(1, 1);
        instance.setDelta(outputNeuronIndex, delta);
        assertEquals(instance.getDeltas().get(outputNeuronIndex), delta);
    }

    /**
     * Test of setDerivatedOutput method, of class Neuron.
     */
    @Test
    public void testSetDerivatedOutput() {
        System.out.println("setDerivatedOutput");
        Double derivatedOutput = 0.365;
        Neuron instance = new Neuron(1, 1);
        instance.setDerivatedOutput(derivatedOutput);
        assertEquals(instance.getDerivatedOutput(), derivatedOutput);
    }

    /**
     * Test of setOutput method, of class Neuron.
     */
    @Test
    public void testSetOutput() {
        System.out.println("setOutput");
        Double output = 0.1111;
        Neuron instance = new Neuron(1, 1);
        instance.setOutput(output);
        assertEquals(instance.getOutput(), output);
    }

}
