package com.type2labs.undersea.utilities;

public class Preconditions {

    public static boolean isPositive(double value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }

        return true;
    }

    public static boolean isNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }

        return true;
    }

    public static boolean inRange(double min, double max, double value, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }

        return true;
    }

}
