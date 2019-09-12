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

package com.type2labs.undersea.common.agent;

/**
 * Speed range data structure.
 */
public class Range {

    /**
     * begin
     */
    private final double min;

    /**
     * end
     */
    private final double max;

    /**
     * percentage
     */
    private final Number value;

    public Range(double min, double max, Number value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public Number getValue() {
        return value;
    }

    public String toString() {
        return min + ":" + max + ":" + value;
    }
}