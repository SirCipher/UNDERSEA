package com.type2labs.undersea.utilities.executor;

import com.type2labs.undersea.common.agent.Agent;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.*;

public class ThrowableCachedThreadPool extends ThreadPoolExecutor {

    private final Thread.UncaughtExceptionHandler ueh;
    private final Logger logger;
    private final Agent agent;


    public ThrowableCachedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, Logger logger,
                                     Agent agent) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);

        this.agent = agent;
        this.logger = Objects.requireNonNull(logger);
        this.ueh = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                UnderseaExceptionHandler.handle(e, agent);
            }
        };
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        if (t != null) {
            logger.error(t);

            UnderseaExceptionHandler.handle(t, agent);

            throw new RuntimeException(t);
        }
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task));
    }


    @Override
    public void execute(Runnable command) {
        if (isShutdown() || isTerminating() || isTerminating()) {
            return;
        }

        try {
            super.execute(wrap(command));
        } catch (RejectedExecutionException ignored) {

        }
    }

    private <T> Callable<T> wrap(final Callable<T> callable) {
        return () -> {
            try {
                return callable.call();
            } catch (Throwable t) {
                ueh.uncaughtException(Thread.currentThread(), t);
                throw t;
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
