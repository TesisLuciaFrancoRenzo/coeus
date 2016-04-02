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
package ar.edu.unrc.tdlearning.perceptron.interfaces;

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
public class IStateTest {

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
    public IStateTest() {
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
     * Test of getCopy method, of class IState.
     */
    @Test
    public void testGetCopy() {
        System.out.println("getCopy");
        IState instance = new IStateImpl();
        IState expResult = null;
        IState result = instance.getCopy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStateReward method, of class IState.
     */
    @Test
    public void testGetStateReward() {
        System.out.println("getStateReward");
        int outputNeuron = 0;
        IState instance = new IStateImpl();
        double expResult = 0.0;
        double result = instance.getStateReward(outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTerminalState method, of class IState.
     */
    @Test
    public void testIsTerminalState() {
        System.out.println("isTerminalState");
        IState instance = new IStateImpl();
        boolean expResult = false;
        boolean result = instance.isTerminalState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class IStateImpl implements IState {

        @Override
        public IState getCopy() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        /**
         *
         * @param outputNeuron <p>
         * @return
         */
        @Override
        public double getStateReward(int outputNeuron) {
            return 0.0;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean isTerminalState() {
            return false;
        }
    }

}
