package com.type2labs.undersea.utilities.executor;

import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.*;

public class ScheduledThrowableExecutor extends ScheduledThreadPoolExecutor {

    private final Thread.UncaughtExceptionHandler ueh;
    private final Logger logger;

    public ScheduledThrowableExecutor(int corePoolSize, Logger logger) {
        super(corePoolSize);

        this.logger = Objects.requireNonNull(logger);
        this.ueh = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                e.printStackTrace();
            }
        };

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

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task));
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return super.schedule(wrap(command), delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return super.schedule(wrap(callable), delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return super.scheduleAtFixedRate(wrap(command), initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return super.scheduleWithFixedDelay(wrap(command), initialDelay, delay, unit);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command));
    }

    private <T> Callable<T> wrap(final Callable<T> callable) {
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    return callable.call();
                } catch (Throwable t) {
                    t.printStackTrace();
                    ueh.uncaughtException(Thread.currentThread(), t);
                    throw t;
                }
            }
        };
    }

    private Runnable wrap(final Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (final Throwable t) {
                t.printStackTrace();
                ueh.uncaughtException(Thread.currentThread(), t);
                throw t;
            }
        };
    }

}
