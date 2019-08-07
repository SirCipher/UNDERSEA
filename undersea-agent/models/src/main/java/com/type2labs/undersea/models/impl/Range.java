package com.type2labs.undersea.models.impl;

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