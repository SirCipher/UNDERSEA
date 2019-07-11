package uuv.dsl.model;

public class Range {
    /**
     * begin
     */
    private double min;

    /**
     * end
     */
    private double max;
    /**
     * percentage
     */
    private Number value;

    public Range(double min, double max, Number value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public Number getValue() {
        return value;
    }

    public String toString() {
        return min + ":" + max + ":" + value;
    }
}