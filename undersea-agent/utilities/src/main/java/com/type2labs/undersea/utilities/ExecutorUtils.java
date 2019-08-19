package com.type2labs.undersea.utilities;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    public static ExecutorService newExecutor(int noThreads, String nameFormat){
        return Executors.newFixedThreadPool(noThreads, new ThreadFactoryBuilder().setNameFormat(nameFormat).build());

    }

}
