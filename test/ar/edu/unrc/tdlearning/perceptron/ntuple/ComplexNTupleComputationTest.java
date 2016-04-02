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
package ar.edu.unrc.tdlearning.perceptron.ntuple;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ComplexNTupleComputationTest {

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
    public ComplexNTupleComputationTest() {
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
     * Test of getDerivatedOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testGetDerivatedOutput() {
        System.out.println("getDerivatedOutput");
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        double expResult = 0.0;
        double result = instance.getDerivatedOutput();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndexes method, of class ComplexNTupleComputation.
     */
    @Test
    public void testGetIndexes() {
        System.out.println("getIndexes");
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        int[] expResult = null;
        int[] result = instance.getIndexes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testGetOutput() {
        System.out.println("getOutput");
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        double expResult = 0.0;
        double result = instance.getOutput();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDerivatedOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testSetDerivatedOutput() {
        System.out.println("setDerivatedOutput");
        double derivatedOutput = 0.0;
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        instance.setDerivatedOutput(derivatedOutput);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIndexes method, of class ComplexNTupleComputation.
     */
    @Test
    public void testSetIndexes() {
        System.out.println("setIndexes");
        int[] indexes = null;
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        instance.setIndexes(indexes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOutput method, of class ComplexNTupleComputation.
     */
    @Test
    public void testSetOutput() {
        System.out.println("setOutput");
        double output = 0.0;
        ComplexNTupleComputation instance = new ComplexNTupleComputation();
        instance.setOutput(output);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
