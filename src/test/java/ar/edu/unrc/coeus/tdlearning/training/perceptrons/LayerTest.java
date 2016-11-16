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
import static org.junit.Assert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class LayerTest {

    /**
     *
     */
    public
    LayerTest() {
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
     * Test of getNeuron method, of class Layer.
     */
    @Test
    public
    void testGetNeuron() {
        System.out.println("getNeuron");
        int    neuronIndex = 0;
        Layer  instance    = new Layer(1);
        Neuron expResult   = new Neuron(1, 1);
        instance.setNeuron(0, expResult);

        Neuron result = instance.getNeuron(neuronIndex);
        assertThat(result, is(expResult));
    }

    /**
     * Test of getNeurons method, of class Layer.
     */
    @Test
    public
    void testGetNeurons() {
        System.out.println("getNeurons");
        Layer instance = new Layer(2);
        instance.setNeuron(0, new Neuron(1, 1));
        List<Neuron> expResult = new ArrayList<>(1);
        expResult.add(new Neuron(1, 1));
        expResult.add(new Neuron(1, 1));
        List<Neuron> result = instance.getNeurons();
        assertThat(result.size(), is(expResult.size()));
    }

    /**
     * Test of setNeuron method, of class Layer.
     */
    @Test
    public
    void testSetNeuron() {
        System.out.println("setNeuron");
        int    neuronIndex = 0;
        Neuron neuron      = new Neuron(1, 1);
        Layer  instance    = new Layer(1);
        instance.setNeuron(neuronIndex, neuron);
        assertThat(instance.getNeuron(0), is(neuron));
    }
}
