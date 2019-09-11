package com.type2labs.undersea.prospect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.AbstractMessage;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.missions.GeneratedMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class RaftProtocolService extends RaftProtocolServiceGrpc.RaftProtocolServiceImplBase {

    private static final Logger logger = LogManager.getLogger(RaftProtocolService.class);
    private final RaftNodeImpl raftNode;
    private final Executor executor;

    public RaftProtocolService(RaftNode raftNode, Executor executor) {
        this.raftNode = (RaftNodeImpl) raftNode;
        this.executor = executor;
    }

    @Override
    public void getStatus(RaftProtos.AcquireStatusRequest request,
                          StreamObserver<RaftProtos.AcquireStatusResponse> responseObserver) {
        sendAbstractAsyncMessage(responseObserver, () -> {
            RaftProtos.AcquireStatusResponse.Builder builder = RaftProtos.AcquireStatusResponse.newBuilder();
            SubsystemMonitor subsystemMonitor = raftNode.parent().services().getService(SubsystemMonitor.class);
            double cost = subsystemMonitor.getCurrentCost();

            builder.setCost(cost);

            return builder.build();
        });
    }

    private void handleLogEntries(RaftProtos.AppendEntryRequest request) {
        List<LogEntry> logEntries = new ArrayList<>(request.getLogEntryCount());

        for (RaftProtos.LogEntryProto proto : request.getLogEntryList()) {
            AgentService agentService =
                    raftNode.parent().services().getService(LogEntry.forName(proto.getAgentService()));

            logEntries.add(new LogEntry(raftNode.leaderPeerId(), proto.getData(), proto.getValue(),
                    proto.getTerm(), agentService));
        }

        LogService logService = raftNode.parent().services().getService(LogService.class);
        logService.appendEntries(logEntries);
    }

    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request,
                            StreamObserver<RaftProtos.AppendEntryResponse> responseObserver) {
        sendAbstractAsyncMessage(responseObserver, () -> {
            int requestTerm = request.getTerm();
            int currentTerm = raftNode.state().getCurrentTerm();

            if (requestTerm < currentTerm) {
                return emptyAppendResponse();
            }

            if (requestTerm > raftNode.state().getCurrentTerm()) {
                raftNode.toFollower(requestTerm);
                UnderseaLogger.info(logger, raftNode.parent(), "Demoting to follower due to receiving a greater term: "
                        + requestTerm);
                return emptyAppendResponse();
            }

            if (raftNode.raftRole() != RaftRole.FOLLOWER) {
                raftNode.toFollower(requestTerm);
                UnderseaLogger.info(logger, raftNode.parent(), "Demoting to follower for term term: " + requestTerm);
            }

            PeerId peerId = PeerId.valueOf(request.getLeader().getRaftPeerId());
            Client leader = raftNode.parent().clusterClients().get(peerId);

            if (!leader.equals(raftNode.state().getLeader())) {
                UnderseaLogger.info(logger, raftNode.parent(), "Setting leader: " + leader.name());
                raftNode.state().toLeader(leader);
            }

            raftNode.updateLastAppendRequestTime();
            handleLogEntries(request);

            return RaftProtos.AppendEntryResponse.newBuilder().setTerm(raftNode.state().getCurrentTerm()).build();
        });
    }

    private RaftProtos.AppendEntryResponse emptyAppendResponse() {
        return RaftProtos.AppendEntryResponse.newBuilder()
                .setClient(GrpcUtil.toProtoClient(raftNode))
                .setTerm(raftNode.term())
                .build();
    }

    @Override
    public void broadcastMembershipChanges(RaftProtos.ClusterMembersRequest request,
                                           StreamObserver<RaftProtos.Empty> responseObserver) {
        UnderseaLogger.info(logger, raftNode.parent(), "Received new members: " + request.getMembersList());

        List<Client> clients = new ArrayList<>(request.getMembersCount());

        for (int i = 0; i < request.getMembersCount(); i++) {
            RaftProtos.RaftPeerProto raftPeerProto = request.getMembersList().get(i);

            PeerId peerId = PeerId.valueOf(raftPeerProto.getRaftPeerId());
            Client member = raftNode.parent().clusterClients().get(peerId);

            if (member == null) {
                member = new RaftClientImpl(raftNode,
                        new InetSocketAddress(raftPeerProto.getHost(), raftPeerProto.getPort()),
                        peerId);
            }

            clients.add(member);
        }

        raftNode.state().updateMembers(clients);
    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request, StreamObserver<RaftProtos.VoteResponse> responseObserver) {
        sendAbstractAsyncMessage(responseObserver, () -> {
            Pair<Client, ClusterState.ClientState> nominee = raftNode.state().getVotedFor();

            UnderseaLogger.info(logger, raftNode.parent(), "Nominating: " + nominee.getKey().peerId() + ". With cost:" +
                    " " + nominee.getValue().getCost());

            return RaftProtos.VoteResponse.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .setNominee(GrpcUtil.toProtoClient(nominee.getKey()))
                    .build();
        });
    }

    @Override
    public void distributeMission(RaftProtos.DistributeMissionRequest request,
                                  StreamObserver<RaftProtos.DisributeMissionResponse> responseObserver) {
        logger.info(raftNode.parent().name() + " processing distribute mission request: " + request, raftNode.parent());

        GeneratedMission generatedMission;

        try {
            ObjectMapper mapper = new ObjectMapper();
            generatedMission = mapper.readValue(request.getMission(), GeneratedMissionImpl.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to process: " + request.toString(), e);
        }

        logger.info(raftNode.parent().name() + ": received mission: " + generatedMission);

        sendAbstractAsyncMessage(responseObserver, () -> RaftProtos.DisributeMissionResponse.newBuilder()
                .setClient(GrpcUtil.toProtoClient(raftNode))
                .setResponse(1)
                .build());

        MissionManager manager = raftNode.parent().services().getService(MissionManager.class);
        manager.assignMission(generatedMission);
    }

    private <M extends AbstractMessage> void sendAbstractAsyncMessage(StreamObserver<M> responseObserver,
                                                                      Supplier<M> supplier) {
        try {
            final CompletableFuture<M> future = CompletableFuture.supplyAsync(supplier, executor);

            future.whenComplete((result, e) -> {
                if (e == null) {
                    responseObserver.onNext(result);
                    responseObserver.onCompleted();
                } else {
                    responseObserver.onError(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
