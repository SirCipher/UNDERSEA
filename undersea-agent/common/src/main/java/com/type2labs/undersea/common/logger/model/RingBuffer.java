/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.logger.model;

import java.util.Arrays;
import java.util.List;

/**
 * Based on http://tutorials.jenkov.com/java-performance/ring-buffer.html
 * <p>
 * This is the underlying data storage system for the {@link com.type2labs.undersea.common.logger.LogServiceImpl}. An
 * efficient structure that loops back round once it has hit its maximum capacity. This does (currently) result in
 * data loss at a certain point and it is left as a to do to persist the data up to that point
 */
public class RingBuffer<T> {

    private final T[] elements;
    private int capacity = 0;
    private int writePos = 0;
    private int available = 0;

    public RingBuffer(int capacity) {
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

    public List<T> readBetween(int startIndex, int endIndex) {
        T[] ts = Arrays.copyOfRange(elements, startIndex, endIndex);
        return Arrays.asList(ts);
    }

    public T read(int index) {
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