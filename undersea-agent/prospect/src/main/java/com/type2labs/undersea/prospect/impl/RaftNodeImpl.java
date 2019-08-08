package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.models.Agent;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.ServerBuilder;
import com.type2labs.undersea.prospect.model.Endpoint;
import com.type2labs.undersea.prospect.model.GroupId;
import com.type2labs.undersea.prospect.model.RaftIntegration;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import io.grpc.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RaftNodeImpl implements RaftNode {

    private static final Logger logger = LogManager.getLogger(RaftNodeImpl.class);

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private final String name;
    private final Endpoint endpoint;
    private final Server server;
    private final RaftIntegration integration;
    // This cannot be final as both an Agent and this class require it
    private Agent agent;
    private final RaftClusterConfig config;
    private GroupId groupId;
    private RaftRole role = RaftRole.CANDIDATE;
    private PoolInfo poolInfo = new PoolInfo();

    private boolean started = false;

    public RaftNodeImpl(RaftClusterConfig config, String name, Endpoint endpoint, GroupId groupId,
                        RaftIntegration integration) {
        this.config = config;
        this.name = name;
        this.endpoint = endpoint;
        this.groupId = groupId;
        this.integration = integration;
        this.server = ServerBuilder.build(endpoint.socketAddress(), this);
    }

    public void shutdown() {
        scheduledExecutor.shutdown();
        endpoint.shutdown();
        server.shutdownNow();
    }

    public RaftRole getRole() {
        return role;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Endpoint getLocalEndpoint() {
        return endpoint;
    }

    public void toLeader() {
        role = RaftRole.LEADER;
        logger.info(name + " is now the leader");
        scheduleHeartbeat();
    }

    public void toCandidate() {
        role = RaftRole.CANDIDATE;
        logger.info(name + " is now a candidate");
    }

    public void toFollower() {
        role = RaftRole.FOLLOWER;
        logger.info(name + " is now a follower");
    }

    @Override
    public PoolInfo poolInfo() {
        return poolInfo;
    }

    @Override
    public RaftRole getRaftRole() {
        return role;
    }

    @Override
    public RaftClusterConfig config() {
        return config;
    }

    private void scheduleHeartbeat() {
//        broadcastAppendRequest();
        schedule(new HeartbeatTask(), 500);
    }

    private void broadcastAppendRequest() {
        for (Endpoint follower : integration.localNodes().keySet()) {
//            sendAppendRequest(follower);
        }
    }

    public void schedule(Runnable task, long delayInMillis) {
        if (!isAvailable()) {
            return;
        }

        integration.schedule(task, delayInMillis, MILLISECONDS);
    }

    public void setAgent(Agent agent) {
        if (this.agent != null) {
            throw new RuntimeException("Cannot set agent again: " + name);
        }

        this.agent = agent;
    }

    @Override
    public void start() {
        if (agent == null) {
            throw new RuntimeException("Agent not set for: " + name);
        }

        try {
            server.start();
            execute(new AcquireStatusTask(RaftNodeImpl.this));
            logger.trace("Started node: " + name);
            started = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server: " + name, e);
        }
    }

    @Override
    public boolean isAvailable() {
        return !(server.isShutdown() || server.isTerminated()) && started;
    }

    @Override
    public Agent agent() {
        return agent;
    }

    @Override
    public void execute(Runnable task) {
        try {
            scheduledExecutor.execute(task);
        } catch (RejectedExecutionException e) {
            logger.error(e);
        }
    }

    @Override
    public RaftIntegration integration() {
        return integration;
    }

    public enum RaftRole {
        LEADER, FOLLOWER, CANDIDATE
    }

    private class HeartbeatTask extends RequireRoleTask {
        HeartbeatTask() {
            super(RaftNodeImpl.this, RaftRole.LEADER);
        }

        @Override
        protected void innerRun() {
            if (role == RaftRole.LEADER) {
//                if (lastAppendEntriesTimestamp < Clock.currentTimeMillis() - heartbeatPeriodInMillis) {
//                    broadcastAppendRequest();
//                }
                broadcastAppendRequest();
                scheduleHeartbeat();
            }
        }
    }
}
