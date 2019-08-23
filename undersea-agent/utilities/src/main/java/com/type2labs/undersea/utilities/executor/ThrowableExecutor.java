package com.type2labs.undersea.utilities.executor;

import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThrowableExecutor extends ThreadPoolExecutor {

    private final Logger logger;

    public ThrowableExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                             BlockingQueue<Runnable> workQueue, Logger logger) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.logger = Objects.requireNonNull(logger);
    }

    public static ThrowableExecutor newExecutor(int threads, Logger logger) {
        return new ThrowableExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), logger);
    }

    public static ThrowableExecutor newSingleThreadExecutor(Logger logger) {
        return newExecutor(1, logger);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        if (t != null) {
            logger.error(t);
            throw new RuntimeException(t);
        }
    }

}
