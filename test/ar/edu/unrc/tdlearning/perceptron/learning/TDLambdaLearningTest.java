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
package ar.edu.unrc.tdlearning.perceptron.learning;

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
public class TDLambdaLearningTest {

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
    public TDLambdaLearningTest() {
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
     * Test of calculateBestEligibilityTraceLenght method, of class
     * TDLambdaLearning.
     */
    @Test
    public void testCalculateBestEligibilityTraceLenght() {
        System.out.println("calculateBestEligibilityTraceLenght");
        Double lambda = 0.7;
        Integer expResult = 21;
        Integer result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        System.out.println("result " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateBestEligibilityTraceLenght method, of class
     * TDLambdaLearning.
     */
    @Test
    public void testCalculateBestEligibilityTraceLenght_Double() {
        System.out.println("calculateBestEligibilityTraceLenght");
        Double lambda = 0.99;
        Integer expResult = Integer.MAX_VALUE;
        Integer result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 0.975;
        expResult = 274;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 0.95;
        expResult = 136;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 0.9;
        expResult = 67;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 0.8;
        expResult = 32;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 0.6;
        expResult = 15;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 1d;
        expResult = Integer.MAX_VALUE;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);

        lambda = 0d;
        expResult = 0;
        result = TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda);
        assertEquals(expResult, result);
    }

}
