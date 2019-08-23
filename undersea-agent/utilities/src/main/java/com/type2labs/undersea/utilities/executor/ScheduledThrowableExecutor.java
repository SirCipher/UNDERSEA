package com.type2labs.undersea.utilities.executor;

import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThrowableExecutor extends ScheduledThreadPoolExecutor {

    private final Logger logger;

    public ScheduledThrowableExecutor(int corePoolSize, Logger logger) {
        super(corePoolSize);
        this.logger = Objects.requireNonNull(logger);
        setRemoveOnCancelPolicy(true);
    }

    public static ScheduledThrowableExecutor newExecutor(int threads, Logger logger) {
        return new ScheduledThrowableExecutor(threads, logger);
    }

    public static ScheduledThrowableExecutor newSingleThreadExecutor(Logger logger) {
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
