/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.prospect.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentState;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithmRole;
import com.type2labs.undersea.common.consensus.MultiRoleState;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
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
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.MultiRoleNotification;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.impl.MultiRoleLeaderClientImpl;
import com.type2labs.undersea.prospect.networking.impl.RaftClientImpl;
import com.type2labs.undersea.prospect.networking.model.RaftClient;
import com.type2labs.undersea.prospect.task.AcquireStatusTask;
import com.type2labs.undersea.prospect.task.RequireRoleTask;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import com.type2labs.undersea.utilities.lang.ReschedulableTask;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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
    private final RaftClusterConfig raftClusterConfig;
    private final InetSocketAddress address;
    private GrpcServer server;
    private RaftState raftState;
    private List<ServiceCallback> serviceCallbacks = new ArrayList<>();
    private Agent agent;
    private ConsensusAlgorithmRole role = ConsensusAlgorithmRole.CANDIDATE;
    private MultiRoleStateImpl multiRoleState;
    private RaftClientImpl selfRaftClientImpl;
    private boolean started = false;
    private long lastHeartbeatTime;
    private long lastAppendRequestTime;


    public RaftNodeImpl(RaftClusterConfig raftClusterConfig) {
        this(raftClusterConfig, new InetSocketAddress(0));
    }


    public RaftNodeImpl(RaftClusterConfig raftClusterConfig,
                        InetSocketAddress address) {
        this.raftClusterConfig = raftClusterConfig;
        this.address = address;

        if (address.getPort() == 0 && !raftClusterConfig.autoPortDiscoveryEnabled()) {
            throw new IllegalStateException("Auto port discovery is not enabled");
        }

        this.singleThreadScheduledExecutor = ThrowableExecutor.newSingleThreadExecutor(parent(), logger);
        this.listeningExecutorService =
                MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(parent(), logger));
        this.multiRoleState = new MultiRoleStateImpl(this);
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
    public Object executeTransaction(Transaction transaction) {
        if (transaction.getStatusCode() == LifecycleEvent.FAILING) {
            notifyMultiRoleLeaderOfFailure();
        }

        throw new NotSupportedException(transaction.getStatusCode().toString(), this.getClass());
    }

    private void notifyMultiRoleLeaderOfFailure() {
        // Only the leader can do this
        if (!multiRoleState.isApplied()) {
            return;
        }

        MultiRoleLeaderClientImpl leaderClient = (MultiRoleLeaderClientImpl) multiRoleState.getLeader();
        RaftProtos.NotificationRequest request = RaftProtos.NotificationRequest.newBuilder()
                .setClient(GrpcUtil.toProtoClient(this))
                .setStatusCode(MultiRoleNotification.FAILING.toString())
                .build();

        leaderClient.notify(request, new FutureCallback<RaftProtos.Empty>() {
            @Override
            public void onSuccess(RaftProtos.@Nullable Empty result) {
                UnderseaLogger.info(logger, agent, "Notified leader {" + leaderClient.name() + "} that I am failing");
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
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
    public ConsensusAlgorithmRole raftRole() {
        return role;
    }

    @Override
    public MultiRoleState multiRoleState() {
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

    public RaftState state() {
        return raftState;
    }

    @Override
    public synchronized void toLeader(int term) {
        // Prevent reassigning the role
        if (role == ConsensusAlgorithmRole.LEADER) {
            return;
        }

        role = ConsensusAlgorithmRole.LEADER;

        multiRoleState.updateStatus();

        logger.info(parent().name() + " is now the leader", agent);
        agent.log(new LogEntry(leaderPeerId(), new Object(), new Object(), state().getCurrentTerm(), this, true));

        fireCallback(LifecycleEvent.ELECTED_LEADER);
        state().toLeader(selfRaftClientImpl);
        scheduleHeartbeat();
    }

    @Override
    public void fireCallback(LifecycleEvent statusCode) {
        if (agent.state().getState() != AgentState.State.ACTIVE) {
            return;
        }

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
        multiRoleState.updateStatus();

        role = ConsensusAlgorithmRole.FOLLOWER;
        raftState.clearCandidate();
        raftState.setCurrentTerm(term);
        logger.info(parent().name() + " is now a follower", agent);

        getMonitor().update();
    }

    @Override
    public void toCandidate() {
        multiRoleState.updateStatus();

        role = ConsensusAlgorithmRole.CANDIDATE;
        raftState.initCandidate();
        logger.info(parent().name() + " is now a candidate", agent);

        getMonitor().update();
    }

    @Override
    public GrpcServer server() {
        return server;
    }

    @Override
    public RaftClusterConfig config() {
        return raftClusterConfig;
    }

    private void alertMultiRoleLeaderOfMission(GeneratedMission generatedMission) {
        MultiRoleLeaderClientImpl leaderClient = (MultiRoleLeaderClientImpl) multiRoleState.getLeader();
        if (leaderClient == null) {
            return;
        }

        RaftProtos.NotificationRequest request = RaftProtos.NotificationRequest.newBuilder()
                .setClient(GrpcUtil.toProtoClient(this))
                .setStatusCode(MultiRoleNotification.GENERATED_MISSION.toString())
                .setNotification(Arrays.deepToString(generatedMission.polygon()))
                .build();

        leaderClient.notify(request, new FutureCallback<RaftProtos.Empty>() {
            @Override
            public void onSuccess(RaftProtos.@Nullable Empty result) {
                logger.info(parent().name() + " alerted multi-role leader of new mission", agent);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                logger.error(parent().name() + " failed to alert multi-role leader of new mission", agent);
            }
        });
    }

    public void distributeMission(GeneratedMission generatedMission) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonMission;

        try {
            jsonMission = mapper.writeValueAsString(generatedMission);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to process mission: " + generatedMission, e);
        }

        alertMultiRoleLeaderOfMission(generatedMission);

        // Iterate over all the submissions in the mission and send them the parent mission.
        for (AgentMission agentMission : generatedMission.subMissions()) {
            RaftClient raftClient = (RaftClient) agentMission.getAssignee();

            if (raftClient.isSelf()) {
                MissionManager manager = agent.serviceManager().getService(MissionManager.class);
                manager.assignMission(generatedMission);

                continue;
            }

            RaftProtos.DistributeMissionRequest request = RaftProtos.DistributeMissionRequest.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(this))
                    .setMission(jsonMission)
                    .build();

            raftClient.distributeMission(request, new FutureCallback<RaftProtos.DisributeMissionResponse>() {
                @Override
                public void onSuccess(RaftProtos.@Nullable DisributeMissionResponse result) {
                    if (result != null) {
                        logger.info(parent().name() + " distributed mission to " + result.getClient(), agent);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }
            });
        }
    }

    private SubsystemMonitor getMonitor() {
        return agent.serviceManager().getService(SubsystemMonitor.class);
    }

    @Override
    public void run() {
        scheduleVerifyLeaderTask();
    }

    @Override
    public void schedule(Runnable task, long delayInMillis) {
        try {
            scheduledExecutor.schedule(task, delayInMillis, MILLISECONDS);
        } catch (RejectedExecutionException e) {
            if (task instanceof ReschedulableTask) {
                ReschedulableTask reschedulableTask = (ReschedulableTask) task;

                if (reschedulableTask.getRunCount() > ReschedulableTask.maxRunCount) {
                    throw new UnderseaException("Attempted to run task " + reschedulableTask.getRunCount() + " times " +
                            "but failed");
                } else {
                    logger.error("Failed to run task, scheduling", e);

                    reschedulableTask.incrementRunCount();
                    schedule(task, reschedulableTask.getRerunTimeWindow());
                }
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
        LogService logService = parent().serviceManager().getService(LogService.class);
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

        follower.appendEntry(request, new FutureCallback<RaftProtos.AppendEntryResponse>() {
            @Override
            public void onSuccess(RaftProtos.@Nullable AppendEntryResponse result) {
//                logger.info(agent.name() + ": successfully sent request. Response: {" + result + "}", agent);
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                    if (statusRuntimeException.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                        Client client = parent().clusterClients().remove(follower.peerId());
                        client.shutdown();

                        broadcastClusterMembers();

                        UnderseaLogger.info(logger, parent(), "Failed to communicate with: " + follower.peerId());

                        schedule(new AcquireStatusTask(RaftNodeImpl.this), 0);
                    }
                } else {
                    t.printStackTrace();
                }
            }
        });

    }

    private void broadcastClusterMembers() {
        RaftProtos.ClusterMembersRequest.Builder builder = RaftProtos.ClusterMembersRequest
                .newBuilder();

        for (Client member : state().localNodes().values()) {
            builder.addMembers(GrpcUtil.toProtoClient(member));
        }

        builder.addMembers(GrpcUtil.toProtoClient(this));

        RaftProtos.ClusterMembersRequest request = builder.setClient(GrpcUtil.toProtoClient(this)).build();

        for (Client follower : state().localNodes().values()) {
            RaftClient raftClient = (RaftClient) follower;
            raftClient.broadcastMembershipChanges(request, new FutureCallback<RaftProtos.Empty>() {
                @Override
                public void onSuccess(RaftProtos.@Nullable Empty result) {

                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void shutdown() {
        scheduledExecutor.shutdownNow();
        singleThreadScheduledExecutor.shutdown();

        if (started) {
            server.close();
        }
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.server = new GrpcServer(this, address);
        this.raftState = new RaftState(this);
        this.selfRaftClientImpl = new RaftClientImpl(agent.serviceManager().getService(RaftNode.class),
                new InetSocketAddress(0), agent.peerId(), true);

        server.start();

        registerCallback(new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> getMonitor().update()));

        UnderseaLogger.info(logger, agent, "Started node. Peer ID: " + agent.peerId());

        this.started = true;
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
                if (!multiRoleState().isLeader() && state().getCandidate() == null) {
                    execute(new AcquireStatusTask(RaftNodeImpl.this));
                }
            }
            // If we haven't heard from the leader then assume they have gone offline
            else if (lastAppendRequestTime + raftClusterConfig.heartbeatTimeout() < System.currentTimeMillis()) {
                UnderseaLogger.info(logger, agent, "leader heartbeat timeout exceeded");
                state().leaderFailed();
                execute(new AcquireStatusTask(RaftNodeImpl.this));
            }

            scheduleVerifyLeaderTask();
        }
    }

    private class HeartbeatTask extends RequireRoleTask {

        HeartbeatTask() {
            super(RaftNodeImpl.this, ConsensusAlgorithmRole.LEADER);
        }

        @Override
        protected void innerRun() {
            logger.info(agent.name() + ": running heartbeat task");

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
