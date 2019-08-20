package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.missionplanner.MissionPlanner;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.service.Transaction;
import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftIntegration;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import com.type2labs.undersea.prospect.task.VoteTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RaftNodeImpl implements RaftNode {

    private static final Logger logger = LogManager.getLogger(RaftNodeImpl.class);

    private final ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);

    private final String name;
    private RaftState raftState;
    private final GrpcServer server;
    private final RaftIntegration integration;
    private final RaftClusterConfig raftClusterConfig;
    // This cannot be final as both an Agent and this class require it
    private Agent agent;
    private RaftRole role = RaftRole.CANDIDATE;
    private boolean started = false;

    private long lastHeartbeatTime;
    private PeerId peerId;

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig,
                        String name,
                        RaftIntegration integration) {
        this.peerId = PeerId.newId();
        this.raftClusterConfig = raftClusterConfig;
        this.name = name;
        this.integration = integration;

        if (!raftClusterConfig.autoPortDiscoveryEnabled()) {
            throw new IllegalArgumentException("Auto port discovery is not enabled");
        }

        this.server = new GrpcServer(this, new InetSocketAddress(0));
    }

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig,
                        String name,
                        RaftIntegration integration,
                        InetSocketAddress address,
                        PeerId peerId) {
        this.peerId = peerId;
        this.raftClusterConfig = raftClusterConfig;
        this.name = name;
        this.integration = integration;
        this.server = new GrpcServer(this, address);
    }

    public GrpcServer getServer() {
        return server;
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
            singleThreadScheduledExecutor.execute(task);
        } catch (RejectedExecutionException e) {
            logger.error(e);
        }
    }

    @Override
    public RaftIntegration integration() {
        return integration;
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

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.raftState = new RaftState(this);
    }

    public void shutdown() {
        singleThreadScheduledExecutor.shutdown();
        server.close();
    }

    @Override
    public ScheduledFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    public void toCandidate() {
        role = RaftRole.CANDIDATE;
        state().initCandidate();
        logger.info(name + " is now a candidate", agent);

        getMonitor().update();
    }

    @Override
    public GrpcServer server() {
        return server;
    }

    @Override
    public PeerId peerId() {
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
        // Prevent reassigning the role
        if (role == RaftRole.LEADER) {
            return;
        }

        role = RaftRole.LEADER;
        logger.info(name + " is now the leader", agent);

        Transaction transaction = new Transaction.Builder(agent)
                .forService(MissionPlanner.class)
                .withStatus(Transaction.StatusCode.ELECTED_LEADER)
                .build();

        agent.services().commitTransaction(transaction);

        getMonitor().update();
        scheduleHeartbeat();
    }

    private void startVotingRound() {
        logger.info(agent.name() + " starting voting round", agent);
        execute(new AcquireStatusTask(RaftNodeImpl.this));
        execute(new VoteTask(RaftNodeImpl.this, 0));
    }

    @Override
    public void run() {
        if (agent == null) {
            logger.error("Agent not set for: " + name);
            throw new RuntimeException("Agent not set for: " + name);
        }

        server.start();
        startVotingRound();
        logger.trace("Started node: " + name, agent);
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
