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
package ar.edu.unrc.coeus.tdlearning.learning;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TDLambdaLearningTest {

    /**
     *
     */
    public
    TDLambdaLearningTest() {
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
     * Test of calculateBestEligibilityTraceLength method, of class TDLambdaLearning.
     */
    @Test
    public
    void testCalculateBestEligibilityTraceLength() {
        System.out.println("calculateBestEligibilityTraceLength");
        Double  lambda    = 0.7;
        Integer expResult = 21;
        Integer result    = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        System.out.println("result " + result);
        assertThat(result, is(expResult));
    }

    /**
     * Test of calculateBestEligibilityTraceLength method, of class TDLambdaLearning.
     */
    @Test
    public
    void testCalculateBestEligibilityTraceLength_Double() {
        System.out.println("calculateBestEligibilityTraceLength");
        Double  lambda    = 0.99;
        Integer expResult = Integer.MAX_VALUE;
        Integer result    = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 0.975;
        expResult = 274;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 0.95;
        expResult = 136;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 0.9;
        expResult = 67;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 0.8;
        expResult = 32;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 0.6;
        expResult = 15;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 1d;
        expResult = Integer.MAX_VALUE;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));

        lambda = 0d;
        expResult = 0;
        result = TDLambdaLearning.calculateBestEligibilityTraceLength(lambda, 1);
        assertThat(result, is(expResult));
    }

    /**
     * Test of calculateLinearInterpolation method, of class TDLambdaLearning.
     */
    @Test
    public
    void testCalculateLinearInterpolation_Ascending() {
        System.out.println("testCalculateLinearInterpolation");
        int    t                   = -50;
        double initialValue        = 0d;
        double finalValue          = 10d;
        double startInterpolation  = 0d;
        double finishInterpolation = 10d;
        double expResult           = initialValue;
        double result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 0;
        expResult = initialValue;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 3;
        expResult = 3d;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 5;
        expResult = 5d;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 8;
        expResult = 8d;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 10;
        expResult = finalValue;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 100;
        expResult = finalValue;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));
    }

    /**
     * Test of calculateLinearInterpolation method, of class TDLambdaLearning.
     */
    @Test
    public
    void testCalculateLinearInterpolation_Descending() {
        System.out.println("testCalculateLinearInterpolation");
        int    t                   = -50;
        double initialValue        = 10d;
        double finalValue          = 0d;
        double startInterpolation  = 0d;
        double finishInterpolation = 10d;
        double expResult           = initialValue;
        double result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 0;
        expResult = initialValue;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 3;
        expResult = 7d;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 5;
        expResult = 5d;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 8;
        expResult = 2d;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 10;
        expResult = finalValue;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));

        t = 100;
        expResult = finalValue;
        result = TDLambdaLearning.calculateLinearInterpolation(t, initialValue, finalValue, startInterpolation, finishInterpolation);
        System.out.println("result=" + result);
        assertThat(result, is(expResult));
    }

    /**
     * Test of RandomBetween method, of class TDLambdaLearning.
     */
    @Test
    public
    void testRandomBetween() {
        System.out.println("randomBetween");
        boolean expResult = true;
        boolean result1   = false;
        boolean result2   = false;
        for ( int i = 0; i < 500; i++ ) {
            int value = TDLambdaLearning.randomBetween(5, 6);
            if ( value == 6 ) {
                result2 = true;
            }
            if ( value == 5 ) {
                result1 = true;
            }
        }
        System.out.println("results 1=" + result1 + " 2=" + result2);
        boolean finalResult = result1 && result2;
        assertThat(finalResult, is(expResult));
    }

}
