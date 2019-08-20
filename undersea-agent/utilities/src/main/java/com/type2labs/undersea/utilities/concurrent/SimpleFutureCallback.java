package com.type2labs.undersea.utilities.concurrent;

import com.google.common.util.concurrent.FutureCallback;

/**
 * A simple future callback that rethrows an exception when there is a failure
 */
public abstract class SimpleFutureCallback<T> implements FutureCallback<T> {

    @Override
    public void onFailure(Throwable t) {
        throw new RuntimeException(t);
    }

}
