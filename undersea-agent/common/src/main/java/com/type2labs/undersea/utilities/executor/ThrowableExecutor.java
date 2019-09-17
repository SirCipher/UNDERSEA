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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class ThrowableExecutor extends ThreadPoolExecutor {

    private final Thread.UncaughtExceptionHandler ueh;
    private final Logger logger;
    private final Agent agent;

    public ThrowableExecutor(Agent agent, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                             BlockingQueue<Runnable> workQueue, Logger logger) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        this.agent = agent;

        if (logger == null) {
            this.logger = LogManager.getLogger(ThrowableExecutor.class);
        } else {
            this.logger = logger;
        }

        this.ueh = (t, e) -> {
            UnderseaExceptionHandler.handle(e, agent);
            e.printStackTrace();
        };
    }

    public static ThrowableExecutor newExecutor(Agent agent, int threads, Logger logger) {
        return new ThrowableExecutor(agent, threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), logger);
    }

    public static ThrowableExecutor newSingleThreadExecutor(Agent agent, Logger logger) {
        return newExecutor(agent, 1, logger);
    }

    public static ThrowableExecutor newSingleThreadExecutor(Agent agent) {
        return newExecutor(agent, 1, null);
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
