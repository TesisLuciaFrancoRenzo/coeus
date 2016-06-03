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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class MaximalListConsumerTest {

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

    List<ActionPrediction> list = new List<ActionPrediction>() {

        @Override
        public boolean add(ActionPrediction e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void add(int index, ActionPrediction element) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean addAll(Collection<? extends ActionPrediction> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean addAll(int index, Collection<? extends ActionPrediction> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ActionPrediction get(int index) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int indexOf(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Iterator<ActionPrediction> iterator() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int lastIndexOf(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ListIterator<ActionPrediction> listIterator() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ListIterator<ActionPrediction> listIterator(int index) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ActionPrediction remove(int index) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ActionPrediction set(int index, ActionPrediction element) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<ActionPrediction> subList(int fromIndex, int toIndex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    /**
     *
     */
    public MaximalListConsumerTest() {
        this.list = new ArrayList<>();
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
     * Test of accept method, of class MaximalListConsumer.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        //lista vacia
        ActionPrediction actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        MaximalListConsumer instance = new MaximalListConsumer();
        instance.accept(actionPrediction);
        assertEquals(1, instance.getList().size());

        //lista con un elemento
        actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        instance.accept(actionPrediction);
        assertEquals(2, instance.getList().size());
        //lista con un valor numerico mas grande que el que esta en la lista.

        actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        instance = new MaximalListConsumer();
        instance.accept(actionPrediction);
        assertEquals(1, instance.getList().size());
    }

    /**
     * Test of combine method, of class MaximalListConsumer.
     */
    @Test
    public void testCombine() {
        System.out.println("combine");
        //lista vacia
        MaximalListConsumer other = new MaximalListConsumer();
        MaximalListConsumer instance = new MaximalListConsumer();
        instance.combine(other);
        assertEquals(0, instance.getList().size());
        //lista con un elemento
        other = new MaximalListConsumer();
        ActionPrediction actionPrediction = new ActionPrediction(new IAction() {
        }, 2d);
        other.accept(actionPrediction);
        instance = new MaximalListConsumer();
        instance.accept(actionPrediction);
        instance.combine(other);
        assertEquals(2, instance.getList().size());
        //con un elemento mas grande
        actionPrediction = new ActionPrediction(new IAction() {
        }, 3d);
        other.accept(actionPrediction);
        instance = new MaximalListConsumer();
        instance.accept(actionPrediction);
        instance.combine(other);
        assertEquals(2, instance.getList().size());
        //con un elemento mas chico
        actionPrediction = new ActionPrediction(new IAction() {
        }, 1d);
        other.accept(actionPrediction);
        instance = new MaximalListConsumer();
        instance.accept(actionPrediction);
        instance.combine(other);
        assertEquals(1, instance.getList().size());
    }

}
