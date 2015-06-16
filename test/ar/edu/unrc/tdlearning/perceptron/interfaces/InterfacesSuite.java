/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.interfaces;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author franco
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {ar.edu.unrc.tdlearning.perceptron.interfaces.IActionTest.class, ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptronTest.class, ar.edu.unrc.tdlearning.perceptron.interfaces.IProblemTest.class, ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterfaceTest.class, ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputationTest.class, ar.edu.unrc.tdlearning.perceptron.interfaces.IStateTest.class, ar.edu.unrc.tdlearning.perceptron.interfaces.IStateNTupleTest.class} )
public class InterfacesSuite {

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

}
