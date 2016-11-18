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

import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.utils.MaximalActionPredictionConsumer;
import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class MaximalListConsumerTest {

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
     * Test of accept method, of class MaximalActionPredictionConsumer.
     */
    @Test
    public
    void testAccept() {
        System.out.println("accept");
        //lista vacía
        ActionPrediction actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        MaximalActionPredictionConsumer instance = new MaximalActionPredictionConsumer();
        instance.accept(actionPrediction);
        assertThat(instance.getList().size(), is(1));

        //lista con un elemento
        actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        instance.accept(actionPrediction);
        assertThat(instance.getList().size(), is(2));
        //lista con un valor numérico mas grande que el que esta en la lista.

        actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        instance = new MaximalActionPredictionConsumer();
        instance.accept(actionPrediction);
        assertThat(instance.getList().size(), is(1));
    }

    /**
     * Test of combine method, of class MaximalActionPredictionConsumer.
     */
    @Test
    public
    void testCombine() {
        System.out.println("combine");
        //lista vacía
        MaximalActionPredictionConsumer other    = new MaximalActionPredictionConsumer();
        MaximalActionPredictionConsumer instance = new MaximalActionPredictionConsumer();
        instance.combine(other);
        assertThat(instance.getList().size(), is(0));
        //lista con un elemento
        other = new MaximalActionPredictionConsumer();
        ActionPrediction actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        other.accept(actionPrediction);
        instance = new MaximalActionPredictionConsumer();
        instance.accept(actionPrediction);
        instance.combine(other);
        assertThat(instance.getList().size(), is(2));
        //con un elemento mas grande
        actionPrediction = new ActionPrediction(new IAction() {
        }, 3d);
        other.accept(actionPrediction);
        instance = new MaximalActionPredictionConsumer();
        instance.accept(actionPrediction);
        instance.combine(other);
        assertThat(instance.getList().size(), is(2));
        //con un elemento mas chico
        actionPrediction = new ActionPrediction(new IAction() {
        }, 1d);
        other.accept(actionPrediction);
        instance = new MaximalActionPredictionConsumer();
        instance.accept(actionPrediction);
        instance.combine(other);
        assertThat(instance.getList().size(), is(1));
    }

}
