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
public class PartialNeuronTest {

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
    public PartialNeuronTest() {
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
     * Test of getBias method, of class PartialNeuron.
     */
    @Test
    public void testGetBias() {
        System.out.println("getBias");
        PartialNeuron instance = new Neuron(1, 1);
        instance.setBias(1.325);
        Double expResult = 1.325;
        Double result = instance.getBias();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeight method, of class PartialNeuron.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        int previousLayerNeuronIndex = 0;
        PartialNeuron instance = new Neuron(1, 1);
        instance.setWeight(0, 0.356);
        Double expResult = 0.356;
        Double result = instance.getWeight(previousLayerNeuronIndex);
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeights method, of class PartialNeuron.
     */
    @Test
    public void testGetWeights() {
        System.out.println("getWeights");
        PartialNeuron instance = new Neuron(1, 1);
        instance.setWeight(0, 0.3);
        instance.setWeight(1, 0.4);
        List<Double> expResult = new ArrayList(1);
        expResult.add(0.3);
        expResult.add(0.4);
        List<Double> result = instance.getWeights();
        assertEquals(expResult.get(0), result.get(0));
        assertEquals(expResult.get(1), result.get(01));
    }

    /**
     * Test of setBias method, of class PartialNeuron.
     */
    @Test
    public void testSetBias() {
        System.out.println("setBias");
        Double newBias = 0.356;
        PartialNeuron instance = new Neuron(1, 1);
        instance.setBias(newBias);
        assertEquals(instance.getBias(), newBias);

    }

    /**
     * Test of setWeight method, of class PartialNeuron.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        int previousLayerNeuronIndex = 0;
        Double weight = 0.256;
        PartialNeuron instance = new Neuron(1, 1);
        instance.setWeight(previousLayerNeuronIndex, weight);
        assertEquals(instance.getWeight(0), weight);
    }
}
