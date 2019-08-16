package com.type2labs.undersea.prospect.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.networking.Endpoint;
import com.type2labs.undersea.prospect.*;
import com.type2labs.undersea.prospect.model.RaftIntegration;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import com.type2labs.undersea.prospect.task.VoteTask;
import io.grpc.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

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
    private final RaftState raftState;
    private final Server server;
    private final RaftIntegration integration;
    private final RaftClusterConfig raftClusterConfig;
    // This cannot be final as both an Agent and this class require it
    private Agent agent;
    private RaftRole role = RaftRole.CANDIDATE;
    private PoolInfo poolInfo;
    private boolean started = false;

    private long lastHeartbeatTime;

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig, String name, Endpoint endpoint,
                        RaftIntegration integration) {
        this.raftClusterConfig = raftClusterConfig;
        this.name = name;
        this.endpoint = endpoint;
        this.integration = integration;
        this.server = ServerBuilder.build(endpoint.socketAddress(), this);
        this.raftState = new RaftState();
        this.poolInfo = new PoolInfo(this);
    }

    private void broadcastMissionProgress() {
        for (Endpoint follower : state().localNodes().keySet()) {
            sendMissionUpdateRequest(follower);
        }
    }

    /**
     * Used as heartbeat also
     *
     * @param follower
     */
    private void sendMissionUpdateRequest(Endpoint follower) {
        RaftProtos.AppendEntryRequest.Builder builder = RaftProtos.AppendEntryRequest.newBuilder();
        builder.setLogEntry(new NodeLog.LogEntry().toLogEntryProto());

        RaftProtos.AppendEntryRequest request = builder.build();

        AppendEntryServiceGrpc.AppendEntryServiceFutureStub futureStub = AppendEntryServiceGrpc.newFutureStub(follower.channel());
        ListenableFuture<RaftProtos.AppendEntryResponse> response = futureStub.appendEntry(request);

        Futures.addCallback(response, new FutureCallback<RaftProtos.AppendEntryResponse>() {
            @Override
            public void onSuccess(RaftProtos.@Nullable AppendEntryResponse result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, MoreExecutors.directExecutor());


        logger.info("Sending heartbeat to: " + follower.name(), agent);
    }

    public RaftState state() {
        return raftState;
    }

    public RaftRole getRole() {
        return role;
    }

    public boolean isAvailable() {
        return !(server.isShutdown() || server.isTerminated()) && started;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Endpoint getLocalEndpoint() {
        return endpoint;
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
        return raftClusterConfig;
    }

    public void schedule(Runnable task, long delayInMillis) {
        if (!isAvailable()) {
            return;
        }

        integration.schedule(task, delayInMillis, MILLISECONDS);
    }

    private void scheduleHeartbeat() {
        broadcastMissionProgress();
        schedule(new HeartbeatTask(), 500);
    }

    public void setAgent(Agent agent) {
        if (this.agent != null) {
            throw new RuntimeException("Cannot set agent again: " + name);
        }

        this.agent = agent;
    }

    public void shutdown() {
        scheduledExecutor.shutdown();
        endpoint.shutdown();
        server.shutdownNow();
    }

    public void toCandidate() {
        role = RaftRole.CANDIDATE;
        state().setVote(null);
        logger.info(name + " is now a candidate", agent);

        getMonitor().update();
    }

    @Override
    public void toFollower(int term) {
        role = RaftRole.FOLLOWER;
        raftState.setTerm(term);
        logger.info(name + " is now a follower", agent);

        getMonitor().update();
    }

    private Monitor getMonitor() {
        return (Monitor) agent.services().getService(Monitor.class);
    }

    @Override
    public void toLeader() {
        role = RaftRole.LEADER;
        logger.info(name + " is now the leader", agent);

        getMonitor().update();
        scheduleHeartbeat();
    }

    @Override
    public void run() {
        if (agent == null) {
            logger.error("Agent not set for: " + name, agent);
            throw new RuntimeException("Agent not set for: " + name);
        }

        try {
            server.start();
            execute(new AcquireStatusTask(RaftNodeImpl.this));
            execute(new VoteTask(RaftNodeImpl.this, 0));

            logger.trace("Started node: " + name, agent);
            started = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server: " + name, e);
        }
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
            if (lastHeartbeatTime < System.currentTimeMillis() - RaftClusterConfig.HEARTBEAT_PERIOD) {
                broadcastMissionProgress();
            } else {
                broadcastMissionProgress();
                scheduleHeartbeat();
            }
        }
    }
}
