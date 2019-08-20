package com.type2labs.undersea.utilities.exception;

public class UnderseaException extends RuntimeException {

    private static final long serialVersionUID = 3322424663356101425L;

    public UnderseaException(String message) {
        super(message);
    }

    public UnderseaException(String message, Exception e) {
        super(message, e);
    }

    public String toString() {
        return "Error: \t" + getMessage();
    }
}
