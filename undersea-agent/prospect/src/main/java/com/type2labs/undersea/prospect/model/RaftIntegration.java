package com.type2labs.undersea.prospect.model;


import java.util.concurrent.TimeUnit;

public interface RaftIntegration {

    void schedule(Runnable task, long delayInMillis, TimeUnit timeUnit);

}
