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
package ar.edu.unrc.tdlearning.learning;

import ar.edu.unrc.tdlearning.interfaces.IAction;
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
public class ActionPredictionTest {

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
    public ActionPredictionTest() {
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
     * Test of compareTo method, of class ActionPrediction.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo iguales");
        ActionPrediction other = new ActionPrediction(new IAction() {
        }, 10d);
        ActionPrediction instance = new ActionPrediction(new IAction() {
        }, 10d);
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        System.out.println("compareTo distintos");
        instance = new ActionPrediction(new IAction() {
        }, 1d);
        expResult = -1;
        result = instance.compareTo(other);
        assertEquals(expResult, result);
    }

}
