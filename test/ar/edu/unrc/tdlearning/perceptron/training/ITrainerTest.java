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
package ar.edu.unrc.tdlearning.perceptron.training;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
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
public class ITrainerTest {

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
    public ITrainerTest() {
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
     * Test of reset method, of class ITrainer.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        ITrainer instance = new ITrainerImpl();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of train method, of class ITrainer.
     */
    @Test
    public void testTrain() {
        System.out.println("train");
        IProblem problem = null;
        IState state = null;
        IState nextTurnState = null;
        double[] currentAlpha = null;
        boolean aRandomMove = false;
        ITrainer instance = new ITrainerImpl();
        instance.train(problem, state, nextTurnState, currentAlpha, aRandomMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class ITrainerImpl implements ITrainer {

        @Override
        public void reset() {
        }

        @Override
        public void train(IProblem problem, IState state, IState nextTurnState, double[] currentAlpha, boolean aRandomMove) {
        }
    }

}
