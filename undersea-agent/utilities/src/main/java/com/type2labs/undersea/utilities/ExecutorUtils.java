package com.type2labs.undersea.utilities;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    public static Executor newExecutor(int noThreads, String nameFormat){
        return Executors.newFixedThreadPool(noThreads, new ThreadFactoryBuilder().setNameFormat(nameFormat).build());

    }

}
