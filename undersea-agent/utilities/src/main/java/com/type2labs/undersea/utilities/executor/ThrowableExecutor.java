package com.type2labs.undersea.utilities.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThrowableExecutor extends ThreadPoolExecutor {

    public ThrowableExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                             BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static ThrowableExecutor newSingleThreadExecutor() {
        return newExecutor(1);
    }

    public static ThrowableExecutor newExecutor(int threads) {
        return new ThrowableExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        if (t != null) {
            throw new RuntimeException(t);
        }
    }

}
