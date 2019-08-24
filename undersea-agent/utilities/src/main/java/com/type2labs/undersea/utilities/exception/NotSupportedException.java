package com.type2labs.undersea.utilities.exception;

public class NotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 6980719636480571134L;

    public NotSupportedException(){

    }

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
