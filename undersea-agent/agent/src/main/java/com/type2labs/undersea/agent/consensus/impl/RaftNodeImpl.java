package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.agent.consensus.model.Endpoint;
import com.type2labs.undersea.agent.consensus.model.GroupId;
import com.type2labs.undersea.agent.consensus.model.RaftIntegration;
import com.type2labs.undersea.agent.consensus.model.RaftNode;
import com.type2labs.undersea.agent.consensus.service.AcquireStatusImpl;
import com.type2labs.undersea.agent.consensus.task.AcquireStatusTask;
import com.type2labs.undersea.agent.consensus.task.RequireRoleTask;
import com.type2labs.undersea.models.Agent;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
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
    private final Agent agent;
    private GroupId groupId;
    private RaftRole role = RaftRole.CANDIDATE;
    private PoolInfo poolInfo = new PoolInfo();

    public void shutdown() {
        scheduledExecutor.shutdown();
        endpoint.shutdown();
        server.shutdownNow();
    }

    public enum RaftRole {
        LEADER, FOLLOWER, CANDIDATE
    }

    public RaftRole getRole() {
        return role;
    }

    public RaftNodeImpl(Agent agent, String name, Endpoint endpoint, GroupId groupId, RaftIntegration integration) {
        this.agent = agent;
        this.name = name;
        this.endpoint = endpoint;
        this.groupId = groupId;
        this.integration = integration;

        InetSocketAddress address = endpoint.socketAddress();

        server = ServerBuilder.forPort(address.getPort())
                .addService(new AcquireStatusImpl(RaftNodeImpl.this))
                .build();
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

    @Override
    public PoolInfo poolInfo() {
        return poolInfo;
    }

    @Override
    public RaftRole getRaftRole() {
        return role;
    }

    private void scheduleHeartbeat() {
//        broadcastAppendRequest();
        schedule(new HeartbeatTask(), 500);
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


    @Override
    public void start() {
        try {
            server.start();
            logger.trace("Started node: " + name);

            execute(new AcquireStatusTask(RaftNodeImpl.this));
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server: " + name, e);
        }
    }

    @Override
    public boolean isAvailable() {
        return !(server.isShutdown() || server.isTerminated());
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
}
