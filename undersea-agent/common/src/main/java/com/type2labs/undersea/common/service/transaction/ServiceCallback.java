package com.type2labs.undersea.common.service.transaction;

import java.util.function.Supplier;

public class ServiceCallback {

    private final LifecycleEvent statusCode;
    private final Supplier<?> callback;

    public ServiceCallback(LifecycleEvent statusCode, Supplier<?> callback) {
        this.statusCode = statusCode;
        this.callback = callback;
    }

    public LifecycleEvent getStatusCode() {
        return statusCode;
    }

    public Supplier<?> getCallback() {
        return callback;
    }
}
