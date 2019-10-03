/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.utilities.executor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.*;

public class ScheduledThrowableExecutor extends ScheduledThreadPoolExecutor {

    private final Thread.UncaughtExceptionHandler ueh;
    private final Logger logger;
    private final Agent agent;

    public ScheduledThrowableExecutor(Agent agent, int corePoolSize, Logger logger) {
        super(corePoolSize);

        this.agent = agent;
        this.logger = Objects.requireNonNull(logger);
        this.ueh = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                UnderseaExceptionHandler.handle(e, agent);
            }
        };

        setRemoveOnCancelPolicy(true);
    }

    public static ScheduledThrowableExecutor newExecutor(Agent agent, int threads, Logger logger) {
        return new ScheduledThrowableExecutor(agent, threads, logger);
    }

    public static ScheduledThrowableExecutor newSingleThreadExecutor(Agent agent, Logger logger) {
        return newExecutor(agent, 1, logger);
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
        return () -> {
            try {
                return callable.call();
            } catch (Throwable t) {
                t.printStackTrace();
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
