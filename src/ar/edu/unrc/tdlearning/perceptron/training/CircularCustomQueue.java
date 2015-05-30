/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tdlearning.perceptron.training;

import java.util.ArrayList;

/**
 *
 * @author franco
 * @param <T>
 */
public class CircularCustomQueue<T> {

    private static final int defaultCapacity = Integer.MAX_VALUE;
    private boolean infiniteCapacity;
    private ArrayList<T> circularQueue;
    private final int capacity;
    private int size = 0;
    private int rear = 0;

    public CircularCustomQueue() {
        this(defaultCapacity);
    }

    //FIXME que pasa si es infinito?
    public CircularCustomQueue(int capacity) {
        this.capacity = capacity;
        if ( capacity == Integer.MAX_VALUE ) {
            infiniteCapacity = true;
            circularQueue = new ArrayList<>();
        } else {
            infiniteCapacity = false;
            circularQueue = new ArrayList<>(capacity);
            for ( int i = 0; i < capacity; i++ ) {
                circularQueue.add(null); //TODO optimizar!!! si es grande tarda mucho
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void enqueue(T obj) {
        if ( infiniteCapacity ) {
            circularQueue.add(obj);
        } else {
            if ( size == capacity ) {
                size--;
            }
            circularQueue.set(rear, obj);
            rear = (rear + 1) % capacity;
        }
        size++;
    }

    public T get(int index) {
        if ( !infiniteCapacity && index >= size ) {
            throw new IndexOutOfBoundsException();
        }
        if ( size == capacity ) {
            return circularQueue.get((index + rear) % capacity);
        } else {
            return circularQueue.get(index);
        }
    }

    public int getRealIndex(int index, int realSize) {
        return realSize - 1 - (size - 1 - index);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("CircularCustomQueue{CircularCustomQueue=[");
        for ( int i = 0; i < capacity; i++ ) {
            output.append(this.get(i));
            if ( i < capacity - 1 ) {
                output.append(", ");
            }
        }
        output.append("]}");
        return output.toString();
    }

}
