package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.model.Endpoint;
import com.type2labs.undersea.prospect.model.RaftIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class RaftIntegrationImpl implements RaftIntegration {

    private static final Logger logger = LogManager.getLogger(RaftIntegrationImpl.class);
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private final Endpoint endpoint;
    private final String parentName;

    public RaftIntegrationImpl(String parentName, Endpoint endpoint) {
        this.parentName = parentName;
        this.endpoint = endpoint;
    }

    @Override
    public void schedule(Runnable task, long delay, TimeUnit timeUnit) {
        try {
            scheduledExecutor.schedule(task, delay, timeUnit);
        } catch (RejectedExecutionException e) {
            logger.error(e);
        }
    }

    @Override
    public Endpoint getLocalEndpoint() {
        return endpoint;
    }

    @Override
    public boolean ready() {
        return true;
    }
}
