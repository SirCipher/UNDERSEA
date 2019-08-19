package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.model.RaftIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RaftIntegrationImpl implements RaftIntegration {

    private static final Logger logger = LogManager.getLogger(RaftIntegrationImpl.class);
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private final String parentName;

    public RaftIntegrationImpl(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void schedule(Runnable task, long delay, TimeUnit timeUnit) {
        try {
            scheduledExecutor.schedule(task, delay, timeUnit);
        } catch (RejectedExecutionException e) {
            logger.error(e);
        }
    }

}
