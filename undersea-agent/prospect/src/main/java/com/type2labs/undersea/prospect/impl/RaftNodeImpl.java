package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftIntegration;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import com.type2labs.undersea.prospect.task.VoteTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RaftNodeImpl implements RaftNode {

    private static final Logger logger = LogManager.getLogger(RaftNodeImpl.class);

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private final String name;
    private final RaftState raftState;
    private final GrpcServer server;
    private final RaftIntegration integration;
    private final RaftClusterConfig raftClusterConfig;
    // This cannot be final as both an Agent and this class require it
    private Agent agent;
    private RaftRole role = RaftRole.CANDIDATE;
    private PoolInfo poolInfo;
    private boolean started = false;

    private long lastHeartbeatTime;
    private RaftPeerId peerId;

    public GrpcServer getServer() {
        return server;
    }

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig,
                        String name,
                        RaftIntegration integration,
                        InetSocketAddress address,
                        RaftPeerId peerId) {
        this.peerId = peerId;
        this.raftClusterConfig = raftClusterConfig;
        this.name = name;
        this.integration = integration;
        this.server = new GrpcServer(this, address);
        this.raftState = new RaftState();
        this.poolInfo = new PoolInfo(this);
    }

    private void broadcastMissionProgress() {
        for (Client follower : state().localNodes().values()) {
            sendMissionUpdateRequest(follower);
        }
    }

    /**
     * Used as heartbeat also
     *
     * @param follower
     */
    private void sendMissionUpdateRequest(Client follower) {
        RaftProtos.AppendEntryRequest.Builder builder = RaftProtos.AppendEntryRequest.newBuilder();
        builder.setLogEntry(new NodeLog.LogEntry().toLogEntryProto());

        RaftProtos.AppendEntryRequest request = builder.build();

//        AppendEntryServiceGrpc.AppendEntryServiceStub futureStub = AppendEntryServiceGrpc.newStub(follower.channel());
//        futureStub.appendEntry(request, new StreamObserver<RaftProtos.AppendEntryResponse>() {
//            @Override
//            public void onNext(RaftProtos.AppendEntryResponse value) {
//                System.out.println(value);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                t.printStackTrace();
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Completed update request");
//            }
//        });


//        logger.info("Sending heartbeat to: " + follower.name(), agent);
    }

    public RaftState state() {
        return raftState;
    }

    public RaftRole getRole() {
        return role;
    }

    @Override
    public String name() {
        return name;
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
        server.shutdown();
    }

    public void toCandidate() {
        role = RaftRole.CANDIDATE;
        state().setVote(null);
        logger.info(name + " is now a candidate", agent);

        getMonitor().update();
    }

    @Override
    public GrpcServer server() {
        return server;
    }

    @Override
    public RaftPeerId peerId() {
        return peerId;
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
//            logger.error("Agent not set for: " + name, agent);
//            throw new RuntimeException("Agent not set for: " + name);
        }

        server.start();
//        execute(new AcquireStatusTask(RaftNodeImpl.this));
//        execute(new VoteTask(RaftNodeImpl.this, 0));
//
//        logger.trace("Started node: " + name, agent);
        started = true;
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
