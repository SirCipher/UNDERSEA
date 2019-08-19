package com.type2labs.undersea.utilities;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    /**
     * Creates a new {@link ExecutorService} that reuses a fixed number of threads. Initialised with a
     * {@link java.util.concurrent.ThreadFactory} using the provided name format
     * <p>
     * If using for an agent then the name format should be prepended with the agent's name
     *
     * @param noThreads  in the pool
     * @param nameFormat the name format for the {@link ThreadFactoryBuilder}
     * @return a newly created thread pool
     * @throws IllegalArgumentException is the number of threads if <=0
     */
    public static ExecutorService newExecutor(int noThreads, String nameFormat) {
        return Executors.newFixedThreadPool(noThreads, new ThreadFactoryBuilder().setNameFormat(nameFormat).build());

    }

}
