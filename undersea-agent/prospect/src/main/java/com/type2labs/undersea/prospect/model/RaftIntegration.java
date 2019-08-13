package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.Endpoint;

import java.util.concurrent.TimeUnit;

public interface RaftIntegration {

    Endpoint getLocalEndpoint();

    boolean ready();

    void schedule(Runnable task, long delayInMillis, TimeUnit timeUnit);

}
