package com.type2labs.undersea.utilities.executor;

import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.*;

public class ThrowableExecutor extends ThreadPoolExecutor {

    private final Thread.UncaughtExceptionHandler ueh;
    private final Logger logger;

    public ThrowableExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                             BlockingQueue<Runnable> workQueue, Logger logger) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        this.logger = Objects.requireNonNull(logger);
        this.ueh = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                e.printStackTrace();
            }
        };
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

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task));
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
                ueh.uncaughtException(Thread.currentThread(), t);
                throw t;
            }
        };
    }

}
