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
package ar.edu.unrc.tdlearning.perceptron.ntuple.elegibilitytrace;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ValueUsagePairTest {

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
    public ValueUsagePairTest() {
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
     * Test of getUsagesLeft method, of class ValueUsagePair.
     */
    @Test
    public void testGetUsagesLeft() {
        System.out.println("getUsagesLeft");
        ValueUsagePair instance = new ValueUsagePair();
        int expResult = 0;
        int result = instance.getUsagesLeft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class ValueUsagePair.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        ValueUsagePair instance = new ValueUsagePair();
        double expResult = 0.0;
        double result = instance.getValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class ValueUsagePair.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        ValueUsagePair instance = new ValueUsagePair();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsagesLeft method, of class ValueUsagePair.
     */
    @Test
    public void testSetUsagesLeft() {
        System.out.println("setUsagesLeft");
        int usagesLeft = 0;
        ValueUsagePair instance = new ValueUsagePair();
        instance.setUsagesLeft(usagesLeft);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValue method, of class ValueUsagePair.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        double value = 0.0;
        ValueUsagePair instance = new ValueUsagePair();
        instance.setValue(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of use method, of class ValueUsagePair.
     */
    @Test
    public void testUse() {
        System.out.println("use");
        ValueUsagePair instance = new ValueUsagePair();
        instance.use();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
