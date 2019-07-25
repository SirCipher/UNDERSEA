package com.type2labs.undersea.dsl;

public class DSLException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3322424663356101425L;

    public DSLException(String message) {
        super(message);
    }

    public DSLException(String message, Exception e) {
        super(message, e);
    }

    public String toString() {
        return "Error: \t" + getMessage();
    }
}
