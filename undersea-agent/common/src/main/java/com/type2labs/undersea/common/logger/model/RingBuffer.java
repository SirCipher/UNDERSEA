package com.type2labs.undersea.common.logger.model;

import java.util.Arrays;
import java.util.List;

/**
 * Based on http://tutorials.jenkov.com/java-performance/ring-buffer.html
 */
public class RingBuffer<T> {

    private T[] elements;
    private int capacity = 0;
    private int writePos = 0;
    private int available = 0;

    private RingBuffer(int capacity) {
        this.capacity = capacity;

        @SuppressWarnings("unchecked")
        T[] t = (T[]) new Object[capacity];
        this.elements = t;
    }

    public RingBuffer() {
        this(1000);
    }

    public void reset() {
        this.writePos = 0;
        this.available = 0;
    }

    public int capacity() {
        return this.capacity;
    }

    public int available() {
        return this.available;
    }

    public int remainingCapacity() {
        return this.capacity - this.available;
    }

    public List<T> readBetween(int startIndex){
        T[] ts = Arrays.copyOfRange(elements, startIndex, elements.length);
        return Arrays.asList(ts);
    }

    T read(int index) {
        return elements[index];
    }

    public boolean put(T element) {
        if (available < capacity) {
            if (writePos >= capacity) {
                writePos = 0;
            }

            elements[writePos] = element;
            writePos++;
            available++;
            return true;
        }

        return false;
    }

    public T take() {
        if (available == 0) {
            return null;
        }
        int nextSlot = writePos - available;
        if (nextSlot < 0) {
            nextSlot += capacity;
        }

        T nextObj = elements[nextSlot];
        available--;

        return nextObj;
    }

}