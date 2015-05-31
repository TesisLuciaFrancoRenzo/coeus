/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class CircularCustomQueueTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public CircularCustomQueueTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of enqueue method, of class CircularCustomQueue.
     */
    @Test
    public void testEnqueue() {
        System.out.println("enqueue && get");
        CircularCustomQueue<Integer> instance = new CircularCustomQueue<>();
        instance.enqueue(1);
        int expResult = 1;
        int result = instance.get(0);
        assertEquals(expResult, result);

        instance.enqueue(2);
        expResult = 1;
        result = instance.get(0);
        assertEquals(expResult, result);

        expResult = 2;
        result = instance.get(1);
        assertEquals(expResult, result);

        instance = new CircularCustomQueue<>(2);
        instance.enqueue(1);
        expResult = 1;
        result = instance.get(0);
        assertEquals(expResult, result);

        instance.enqueue(2);
        expResult = 1;
        result = instance.get(0);
        assertEquals(expResult, result);

        expResult = 2;
        result = instance.get(1);
        assertEquals(expResult, result);

        instance.enqueue(3);
        expResult = 2;
        result = instance.get(0);
        assertEquals(expResult, result);

        instance.enqueue(4);
        instance.enqueue(5);
        expResult = 4;
        result = instance.get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of enqueue method, of class CircularCustomQueue.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testGetOutOfBound1() {
        System.out.println("get IndexOutOfBoundsException 1");
        CircularCustomQueue<Integer> instance = new CircularCustomQueue<>();
        instance.enqueue(1);
        instance.get(1);
    }

    /**
     * Test of enqueue method, of class CircularCustomQueue.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testGetOutOfBound2() {
        System.out.println("get IndexOutOfBoundsException 2");
        CircularCustomQueue<Integer> instance = new CircularCustomQueue<>(2);
        instance.enqueue(1);
        instance.get(1);
    }

    /**
     * Test of getRealIndex method, of class CircularCustomQueue.
     */
    @Test
    public void testGetRealIndex() {
        System.out.println("getRealIndex");
        int index = 0;
        int realSize = 20;
        CircularCustomQueue instance = new CircularCustomQueue(4);
        for ( int i = 0; i < realSize; i++ ) {
            instance.enqueue(i);
        }
        int expResult = 16;
        int result = instance.getRealIndex(index, realSize);
        assertEquals(expResult, result);

        index = 3;
        expResult = 19;
        result = instance.getRealIndex(index, realSize);
        assertEquals(expResult, result);

        index = 0;
        realSize = 3;
        instance = new CircularCustomQueue(4);
        for ( int i = 0; i < realSize; i++ ) {
            instance.enqueue(i);
        }
        expResult = 0;
        result = instance.getRealIndex(index, realSize);
        assertEquals(expResult, result);

        index = 2;
        expResult = 2;
        result = instance.getRealIndex(index, realSize);
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class CircularCustomQueue.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        CircularCustomQueue instance = new CircularCustomQueue();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        
        instance = new CircularCustomQueue(5);
        expResult = true;
        result = instance.isEmpty();
        assertEquals(expResult, result);
        
        instance = new CircularCustomQueue();
        instance.enqueue(1);
        expResult = false;
        result = instance.isEmpty();
        assertEquals(expResult, result);
        
        instance = new CircularCustomQueue(3);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(1);
        expResult = false;
        result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of isFull method, of class CircularCustomQueue.
     */
    @Test
    public void testIsFull() {
        System.out.println("isFull");
        CircularCustomQueue instance = new CircularCustomQueue(4);
        boolean expResult = false;
        boolean result = instance.isFull();
        assertEquals(expResult, result);
        
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(1);
        expResult = false;
        result = instance.isFull();
        assertEquals(expResult, result);
        
        instance.enqueue(1);
        expResult = true;
        result = instance.isFull();
        assertEquals(expResult, result);
        
        instance.enqueue(1);
        expResult = true;
        result = instance.isFull();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of size method, of class CircularCustomQueue.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        CircularCustomQueue instance = new CircularCustomQueue();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        
        instance = new CircularCustomQueue();
        instance.enqueue(1);
        instance.enqueue(2);
        expResult = 2;
        result = instance.size();
        assertEquals(expResult, result);
        
        instance.enqueue(1);
        instance.enqueue(2);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(2);
        instance.enqueue(2);
        expResult = 8;
        result = instance.size();
        assertEquals(expResult, result);
        
        instance = new CircularCustomQueue(5);
        instance.enqueue(1);
        instance.enqueue(2);
        expResult = 2;
        result = instance.size();
        assertEquals(expResult, result);
        
        instance.enqueue(1);
        instance.enqueue(2);
        instance.enqueue(1);
        instance.enqueue(1);
        instance.enqueue(2);
        instance.enqueue(2);
        expResult = 5;
        result = instance.size();
        assertEquals(expResult, result);
    }

}
