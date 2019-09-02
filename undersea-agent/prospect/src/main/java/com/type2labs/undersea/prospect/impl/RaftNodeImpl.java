package com.type2labs.undersea.prospect.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.MultiRoleState;
import com.type2labs.undersea.prospect.model.RaftIntegration;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClient;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import com.type2labs.undersea.utilities.concurrent.SimpleFutureCallback;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RaftNodeImpl implements RaftNode {

    private static final Logger logger = LogManager.getLogger(RaftNodeImpl.class);

    // TODO: 20/08/2019 Migrate to 4 threads
    private final ThrowableExecutor singleThreadScheduledExecutor;
    private final ListeningExecutorService listeningExecutorService;

    private final String name;
    private final GrpcServer server;
    private final RaftIntegration integration;
    private final RaftClusterConfig raftClusterConfig;
    private RaftState raftState;
    // This cannot be final as both an Agent and this class require it
    private Agent agent;
    private RaftRole role = RaftRole.CANDIDATE;
    private MultiRoleState multiRoleState;
    private boolean started = false;

    private long lastHeartbeatTime;
    private PeerId peerId;

    public ThrowableExecutor getSingleThreadScheduledExecutor() {
        return singleThreadScheduledExecutor;
    }

    public ListeningExecutorService getListeningExecutorService() {
        return listeningExecutorService;
    }

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig,
                        String name,
                        RaftIntegration integration) {
        this(raftClusterConfig, name, integration, new InetSocketAddress(0), PeerId.newId());
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

        if (address.getPort() == 0 && !raftClusterConfig.autoPortDiscoveryEnabled()) {
            throw new IllegalArgumentException("Auto port discovery is not enabled");
        }

        this.server = new GrpcServer(this, address);

        this.singleThreadScheduledExecutor = ThrowableExecutor.newSingleThreadExecutor(logger);
        this.listeningExecutorService =
                MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(logger));
        this.multiRoleState = new MultiRoleState();
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
    public RaftRole getRaftRole() {
        return role;
    }

    @Override
    public MultiRoleState multiRole() {
        return multiRoleState;
    }

    @Override
    public RaftIntegration integration() {
        return integration;
    }

    @Override
    public String name() {
        return name;
    }

    public RaftState state() {
        return raftState;
    }

    @Override
    public synchronized void toLeader(int term) {
        // Prevent reassigning the role
        if (role == RaftRole.LEADER) {
            return;
        }

        role = RaftRole.LEADER;
        logger.info(name + " is now the leader", agent);

        handleLifecycleCallback(LifecycleEvent.ELECTED_LEADER);

        getMonitor().update();
        scheduleHeartbeat();
    }

    private void handleLifecycleCallback(LifecycleEvent statusCode) {
        Collection<ServiceCallback> transactions =
                serviceCallbacks.stream().filter(c -> c.getStatusCode() == statusCode).collect(Collectors.toList());

        for (ServiceCallback t : transactions) {
            t.getCallback().get();
        }
    }

    private List<ServiceCallback> serviceCallbacks = new ArrayList<>();

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {
        this.serviceCallbacks.add(serviceCallback);
    }

    @Override
    public void toFollower(int term) {
        role = RaftRole.FOLLOWER;
        raftState.setTerm(term);
        logger.info(name + " is now a follower", agent);

        getMonitor().update();
    }

    public void toCandidate() {
        role = RaftRole.CANDIDATE;
        state().initCandidate(0);
        logger.info(name + " is now a candidate", agent);

        getMonitor().update();
    }

    @Override
    public GrpcServer server() {
        return server;
    }

    private void broadcastMissionProgress() {
        for (Client follower : state().localNodes().values()) {
            sendMissionUpdateRequest(follower);
        }
    }

    public RaftClusterConfig config() {
        return raftClusterConfig;
    }

    void distributeMission(GeneratedMission result) {
        agent.clusterClients();

        ObjectMapper mapper = new ObjectMapper();

        for (AgentMission agentMission : result.subMissions()) {
            String mission;

            try {
                mission = mapper.writeValueAsString(agentMission);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to process mission: " + agentMission, e);
            }

            RaftClient raftClient = (RaftClient) agentMission.getAssignee();

            if (raftClient.isSelf()) {
                MissionManager manager = agent.services().getService(MissionManager.class);
                manager.assignMission(agentMission);

                continue;
            }

            RaftProtos.DistributeMissionRequest request = RaftProtos.DistributeMissionRequest.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(this))
                    .setMission(mission)
                    .build();
            raftClient.distributeMission(request, new SimpleFutureCallback<RaftProtos.DisributeMissionResponse>() {
                @Override
                public void onSuccess(RaftProtos.@Nullable DisributeMissionResponse result) {
                    logger.info(name + " distributed mission to " + result, agent);
                }
            });
        }
    }

    private Monitor getMonitor() {
        return agent.services().getService(Monitor.class);
    }

    public GrpcServer getServer() {
        return server;
    }

    @Override
    public void run() {
        if (agent == null) {
            logger.error("Agent not set for: " + name);
            throw new RuntimeException("Agent not set for: " + name);
        }

        server.start();
//        startVotingRound();
        logger.trace("Started node: " + name, agent);
        started = true;
    }

    @Override
    public void schedule(Runnable task, long delayInMillis) {
        integration.schedule(task, delayInMillis, MILLISECONDS);
    }

    private void scheduleHeartbeat() {
        broadcastMissionProgress();
        schedule(new HeartbeatTask(), 500);
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

    public void shutdown() {
        singleThreadScheduledExecutor.shutdown();
        server.close();
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.raftState = new RaftState(this);
    }

    @Override
    public Agent parent() {
        return agent;
    }

    public void startVotingRound() {
        if (!multiRole().isLeader()) {
            logger.info(agent.name() + " starting voting round", agent);
            execute(new AcquireStatusTask(RaftNodeImpl.this));
        }
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
