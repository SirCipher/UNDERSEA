package com.type2labs.undersea.prospect;

import com.google.common.util.concurrent.*;
import com.type2labs.undersea.utilities.ExecutorUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class FutureTest {

    @Test
    public void test() {
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "test";
        }, ExecutorUtils.newExecutor(1, "t %d"));

        future.whenComplete((result, e) -> {
            System.out.println("lollz: " + result);
        });
    }

    @Test
    public void test2() throws InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> listenableFuture = service.submit(() -> {
            return "Hello";
        });

        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, ExecutorUtils.newExecutor(1, "t %d"));

        Thread.sleep(5000);

    }

}
