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
public class EligibilityTraceForNTupleTest {

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
    public EligibilityTraceForNTupleTest() {
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
     * Test of processNotUsedTraces method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testProcessNotUsedTraces() {
        System.out.println("processNotUsedTraces");
        double tDError = 0.0;
        EligibilityTraceForNTuple instance = null;
        instance.processNotUsedTraces(tDError);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        EligibilityTraceForNTuple instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testReset_0args() {
        System.out.println("reset");
        EligibilityTraceForNTuple instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testReset_int() {
        System.out.println("reset");
        int weightIndex = 0;
        EligibilityTraceForNTuple instance = null;
        instance.reset(weightIndex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTrace method, of class EligibilityTraceForNTuple.
     */
    @Test
    public void testUpdateTrace() {
        System.out.println("updateTrace");
        int weightIndex = 0;
        double derivatedOutput = 0.0;
        EligibilityTraceForNTuple instance = null;
        instance.updateTrace(weightIndex, derivatedOutput);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
