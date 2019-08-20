package com.type2labs.undersea.utilities.executor;

import java.util.concurrent.*;

public class ScheduledThrowableExecutor extends ScheduledThreadPoolExecutor {

    public ScheduledThrowableExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public static ScheduledThrowableExecutor newSingleThreadExecutor() {
        return newExecutor(1);
    }

    public static ScheduledThrowableExecutor newExecutor(int threads) {
        return new ScheduledThrowableExecutor(threads);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        if (t != null) {
            throw new RuntimeException(t);
        }
    }

}
