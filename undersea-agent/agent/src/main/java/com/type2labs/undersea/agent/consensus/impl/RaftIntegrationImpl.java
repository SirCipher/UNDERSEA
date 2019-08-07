package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.agent.consensus.model.Endpoint;
import com.type2labs.undersea.agent.consensus.model.RaftIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class RaftIntegrationImpl implements RaftIntegration {

    private static final Logger logger = LogManager.getLogger(RaftIntegrationImpl.class);
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private final ConcurrentMap<Endpoint, RaftNodeImpl> nodes = new ConcurrentHashMap<>();
    private final Endpoint endpoint;
    private final String parentName;

    public RaftIntegrationImpl(String parentName, Endpoint endpoint) {
        this.parentName = parentName;
        this.endpoint = endpoint;
    }

    public void discoverNode(RaftNodeImpl node) {
        nodes.putIfAbsent(node.getLocalEndpoint(), node);
    }

    @Override
    public ConcurrentMap<Endpoint, RaftNodeImpl> localNodes() {
        return nodes;
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