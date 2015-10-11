/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.perceptrons;

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
public class LayerTest {

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
    public LayerTest() {
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
     * Test of getNeuron method, of class Layer.
     */
    @Test
    public void testGetNeuron() {
        System.out.println("getNeuron");
        int neuronIndex = 0;
        Layer instance = new Layer(1);
        PartialNeuron expResult = new Neuron(1, 1);
        instance.setNeuron(0, expResult);

        PartialNeuron result = instance.getNeuron(neuronIndex);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNeurons method, of class Layer.
     */
    @Test
    public void testGetNeurons() {
        System.out.println("getNeurons");
        Layer instance = new Layer(2);
        instance.setNeuron(0, new Neuron(1, 1));
        List<PartialNeuron> expResult = new ArrayList<>(1);
        expResult.add(new Neuron(1, 1));
        expResult.add(new Neuron(1, 1));
        List<PartialNeuron> result = instance.getNeurons();
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of setNeuron method, of class Layer.
     */
    @Test
    public void testSetNeuron() {
        System.out.println("setNeuron");
        int neuronIndex = 0;
        PartialNeuron neuron = new Neuron(1, 1);
        Layer instance = new Layer(1);
        instance.setNeuron(neuronIndex, neuron);
        assertEquals(instance.getNeuron(0), neuron);
    }
}
