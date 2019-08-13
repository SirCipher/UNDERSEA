package com.type2labs.undersea.visualiser;

public class Visualiser {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Properties file location must be provided");
        } else {
            new VisualiserRunner(args[0]);
        }
    }

}
