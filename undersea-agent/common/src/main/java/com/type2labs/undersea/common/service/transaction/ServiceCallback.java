package com.type2labs.undersea.common.service.transaction;

import java.util.Objects;

public class ServiceCallback {

    private final LifecycleEvent statusCode;
    private final Callback callback;

    public interface Callback {
        void call();
    }

    public ServiceCallback(LifecycleEvent statusCode, Callback callback) {
        this.statusCode = Objects.requireNonNull(statusCode);
        this.callback = Objects.requireNonNull(callback);
    }

    public LifecycleEvent getStatusCode() {
        return statusCode;
    }

    public void call() {
        callback.call();
    }
}
