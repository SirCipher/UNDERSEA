package com.type2labs.undersea.prospect.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.consensus.MultiRoleState;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClient;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import com.type2labs.undersea.utilities.concurrent.SimpleFutureCallback;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import com.type2labs.undersea.utilities.lang.ReschedulableTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RaftNodeImpl implements RaftNode {

    private static final Logger logger = LogManager.getLogger(RaftNodeImpl.class);

    // TODO: 20/08/2019 Migrate to 4 threads
    private final ThrowableExecutor singleThreadScheduledExecutor;
    private final ListeningExecutorService listeningExecutorService;
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private final String name;
    private final GrpcServer server;
    private final RaftClusterConfig raftClusterConfig;

    private RaftState raftState;
    private List<ServiceCallback> serviceCallbacks = new ArrayList<>();
    private Agent agent;
    private RaftRole role = RaftRole.CANDIDATE;
    private MultiRoleState multiRoleState;
    private boolean started = false;
    private long lastHeartbeatTime;
    private long lastAppendRequestTime;
    private RaftClientImpl selfRaftClientImpl;

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig, String name) {
        this(raftClusterConfig, name, new InetSocketAddress(0));
    }

    public RaftNodeImpl(RaftClusterConfig raftClusterConfig,
                        String name,
                        InetSocketAddress address) {
        this.raftClusterConfig = raftClusterConfig;
        this.name = name;

        if (address.getPort() == 0 && !raftClusterConfig.autoPortDiscoveryEnabled()) {
            throw new IllegalArgumentException("Auto port discovery is not enabled");
        }

        this.server = new GrpcServer(this, address);
        this.singleThreadScheduledExecutor = ThrowableExecutor.newSingleThreadExecutor(logger);
        this.listeningExecutorService =
                MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(logger));
        this.multiRoleState = new MultiRoleState();
    }

    public ThrowableExecutor getSingleThreadScheduledExecutor() {
        return singleThreadScheduledExecutor;
    }

    public ListeningExecutorService getListeningExecutorService() {
        return listeningExecutorService;
    }

    @Override
    public Collection<Class<? extends AgentService>> requiredServices() {
        return Arrays.asList(LogService.class, MissionManager.class, SubsystemMonitor.class);
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
    public int term() {
        return state().getCurrentTerm();
    }

    @Override
    public PeerId leaderPeerId() {
        if (state() != null && state().getLeader() != null) {
            return state().getLeader().peerId();
        } else {
            return null;
        }
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
        state().setLeader(selfRaftClientImpl);
        logger.info(name + " is now the leader", agent);

        fireLifecycleCallbacks(LifecycleEvent.ELECTED_LEADER);
        agent.log(new LogEntry(leaderPeerId(), new Object(), new Object(), state().getCurrentTerm(), this));
        scheduleHeartbeat();
    }

    private void fireLifecycleCallbacks(LifecycleEvent statusCode) {
        Collection<ServiceCallback> callbacks =
                serviceCallbacks.stream().filter(c -> c.getStatusCode() == statusCode).collect(Collectors.toList());

        for (ServiceCallback t : callbacks) {
            t.call();
        }
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {
        this.serviceCallbacks.add(serviceCallback);
    }

    @Override
    public void toFollower(int term) {
        role = RaftRole.FOLLOWER;
        raftState.clearCandidate();
        raftState.setCurrentTerm(term);
        logger.info(name + " is now a follower", agent);

        getMonitor().update();
    }

    @Override
    public void toCandidate() {
        role = RaftRole.CANDIDATE;
        raftState.initCandidate();
        logger.info(name + " is now a candidate", agent);

        getMonitor().update();
    }

    @Override
    public GrpcServer server() {
        return server;
    }

    public RaftClusterConfig config() {
        return raftClusterConfig;
    }

    void distributeMission(GeneratedMission generatedMission) {
        ObjectMapper mapper = new ObjectMapper();

        // Iterate over all the submissions in the mission and send them the parent mission.
        for (AgentMission agentMission : generatedMission.subMissions()) {
            String mission;

            try {
                mission = mapper.writeValueAsString(generatedMission);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to process mission: " + agentMission, e);
            }

            RaftClient raftClient = (RaftClient) agentMission.getAssignee();

            if (raftClient.isSelf()) {
                MissionManager manager = agent.services().getService(MissionManager.class);
                manager.assignMission(generatedMission);

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

    private SubsystemMonitor getMonitor() {
        return agent.services().getService(SubsystemMonitor.class);
    }

    @Override
    public void run() {
        scheduleVerifyLeaderTask();
    }

    @Override
    public void schedule(ReschedulableTask task, long delayInMillis) {
        try {
            scheduledExecutor.schedule(task, delayInMillis, MILLISECONDS);
        } catch (RejectedExecutionException e) {
            if (task.getRunCount() > ReschedulableTask.maxRunCount) {
                throw new UnderseaException("Attempted to run task " + task.getRunCount() + " times but failed");
            } else {
                logger.error("Failed to run task, scheduling", e);

                task.incrementRunCount();
                schedule(task, task.getRerunTimeWindow());
            }
        }
    }

    @Override
    public RaftClient self() {
        return selfRaftClientImpl;
    }

    private void scheduleHeartbeat() {
        schedule(ReschedulableTask.wrap(new HeartbeatTask()), 500);
    }

    private void sendAppendRequest(RaftClient follower) {
        LogService logService = parent().services().getService(LogService.class);
        List<LogEntry> logEntries = logService.readNextForClient(follower);
        List<RaftProtos.LogEntryProto> protoEntries = new ArrayList<>(logEntries.size());

        for (LogEntry e : logEntries) {
            RaftProtos.LogEntryProto.Builder builder = RaftProtos.LogEntryProto.newBuilder();
            Object data = e.getData();

            builder.setData(data == null ? null : data.toString());

            Object value = e.getValue();
            builder.setValue(value == null ? null : value.toString());

            builder.setTerm(e.getTerm());
            builder.setAgentService(MissionManager.class.getSimpleName());

            protoEntries.add(builder.build());
        }

        RaftProtos.AppendEntryRequest request = RaftProtos.AppendEntryRequest.newBuilder()
                .setClient(GrpcUtil.toProtoClient(this))
                .setLeader(GrpcUtil.toProtoClient(this))
                .setTerm(state().getCurrentTerm())
                .addAllLogEntry(protoEntries)
                .build();

        follower.appendEntry(request, new SimpleFutureCallback<RaftProtos.AppendEntryResponse>() {
            @Override
            public void onSuccess(RaftProtos.@Nullable AppendEntryResponse result) {
//                logger.info(agent.name() + ": successfully sent request. Response: {" + result + "}", agent);
            }
        });

    }

    @Override
    public void shutdown() {
        alertLeavingCluster();

        singleThreadScheduledExecutor.shutdown();
        server.close();
    }

    private void alertLeavingCluster() {
        for (Client follower : state().localNodes().values()) {
            RaftClient raftClient = (RaftClient) follower;

            RaftProtos.LeaveClusterRequest request = RaftProtos.LeaveClusterRequest.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(this))
                    .build();

            raftClient.alertLeavingCluster(request, new SimpleFutureCallback<RaftProtos.Empty>() {
                @Override
                public void onSuccess(RaftProtos.@Nullable Empty ignored) {

                }
            });
        }
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.raftState = new RaftState(this);
        this.started = true;
        this.selfRaftClientImpl = new RaftClientImpl(agent.services().getService(RaftNode.class), new InetSocketAddress(0), agent.peerId(), true);

        server.start();

        registerCallback(new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> getMonitor().update()));

        UnderseaLogger.info(logger, agent, "Started node");
    }

    private void scheduleVerifyLeaderTask() {
        schedule(new VerifyLeaderTask(), 5000);
    }

    @Override
    public Agent parent() {
        return agent;
    }

    public void updateLastAppendRequestTime() {
        this.lastAppendRequestTime = System.currentTimeMillis();
    }

    private class VerifyLeaderTask extends ReschedulableTask {
        @Override
        public void innerRun() {
            // If we don't have a leader
            if (state().getLeader() == null) {
                if (!multiRole().isLeader() && state().getCandidate() == null) {
                    UnderseaLogger.info(logger, agent, "Starting voting round");
                    execute(new AcquireStatusTask(RaftNodeImpl.this));
                }
            }
            // If we haven't heard from the leader then assume they have gone offline
            else if (lastAppendRequestTime + raftClusterConfig.heartbeatTimeout() < System.currentTimeMillis()) {
                UnderseaLogger.info(logger, agent, "leader heartbeat timeout exceeded");
                state().setLeader(null);
                execute(new AcquireStatusTask(RaftNodeImpl.this));
            }

            scheduleVerifyLeaderTask();
        }
    }

    private class HeartbeatTask extends RequireRoleTask {

        HeartbeatTask() {
            super(RaftNodeImpl.this, RaftRole.LEADER);
        }

        @Override
        protected void innerRun() {
            if (lastHeartbeatTime < System.currentTimeMillis() - RaftClusterConfig.HEARTBEAT_PERIOD) {
                for (Client follower : state().localNodes().values()) {
                    sendAppendRequest((RaftClient) follower);
                }

                updateLastAppendRequestTime();
                lastHeartbeatTime = System.currentTimeMillis();
            }

            scheduleHeartbeat();
        }
    }
}
