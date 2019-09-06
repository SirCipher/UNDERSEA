package com.type2labs.undersea.prospect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.AbstractMessage;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class RaftProtocolServiceImpl extends RaftProtocolServiceGrpc.RaftProtocolServiceImplBase {

    private static final Logger logger = LogManager.getLogger(RaftProtocolServiceImpl.class);
    private final RaftNode raftNode;
    private final Executor executor;

    public RaftProtocolServiceImpl(RaftNode raftNode, Executor executor) {
        this.raftNode = raftNode;
        this.executor = executor;
    }

    @Override
    public void getStatus(RaftProtos.AcquireStatusRequest request,
                          StreamObserver<RaftProtos.AcquireStatusResponse> responseObserver) {
        logger.info(raftNode.name() + " processing get status request: " + request, raftNode.parent());

        sendAbstractAsyncMessage(responseObserver, () -> {
            RaftProtos.AcquireStatusResponse.Builder builder = RaftProtos.AcquireStatusResponse.newBuilder();
            List<Pair<String, String>> status = raftNode.parent().status();

            for (Pair<String, String> p : status) {
                RaftProtos.Tuple.Builder tupleBuilder = RaftProtos.Tuple.newBuilder();
                tupleBuilder.setFieldType(p.getKey());
                tupleBuilder.setValue(p.getValue());

                builder.addStatus(tupleBuilder.build());
            }

            return builder.build();
        });
    }

    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request,
                            StreamObserver<RaftProtos.AppendEntryResponse> responseObserver) {
        sendAbstractAsyncMessage(responseObserver, () -> {
            int requestTerm = request.getTerm();

            if (requestTerm > raftNode.state().getTerm()) {
                raftNode.toFollower(requestTerm);
            }

            List<LogEntry> logEntries = new ArrayList<>(request.getLogEntryCount());

            for (RaftProtos.LogEntryProto proto : request.getLogEntryList()) {
                AgentService agentService =
                        raftNode.parent().services().getService(LogEntry.forName(proto.getAgentService()));

                logEntries.add(new LogEntry(proto.getData(), proto.getValue(), proto.getTerm(), agentService));
            }

            LogService logService = raftNode.parent().services().getService(LogService.class);
            logService.appendEntries(logEntries);

            return RaftProtos.AppendEntryResponse.newBuilder().setTerm(raftNode.state().getTerm()).build();
        });
    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request, StreamObserver<RaftProtos.VoteResponse> responseObserver) {
        logger.info(raftNode.name() + " processing vote request: " + request, raftNode.parent());

        sendAbstractAsyncMessage(responseObserver, () -> {
            Client self = RaftClientImpl.ofSelf(raftNode);
            Pair<Client, ClusterState.ClientState> nominee = raftNode.state().clusterState().getNominee(self);

            RaftProtos.VoteResponse response = RaftProtos.VoteResponse.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .setNominee(GrpcUtil.toProtoClient(nominee.getKey()))
                    .build();

            logger.info(raftNode.name() + ": voting for: " + nominee.getKey(), raftNode.parent());
            return response;
        });
    }

    @Override
    public void distributeMission(RaftProtos.DistributeMissionRequest request,
                                  StreamObserver<RaftProtos.DisributeMissionResponse> responseObserver) {
        logger.info(raftNode.name() + " processing distribute mission request: " + request, raftNode.parent());

        AgentMission agentMission;

        try {
            ObjectMapper mapper = new ObjectMapper();
            agentMission = mapper.readValue(request.getMission(), AgentMissionImpl.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to process AgentMission: " + request.toString(), e);
        }

        logger.info(raftNode.name() + ": received mission: " + agentMission);

        sendAbstractAsyncMessage(responseObserver, () -> RaftProtos.DisributeMissionResponse.newBuilder()
                .setClient(GrpcUtil.toProtoClient(raftNode))
                .setResponse(1)
                .build());

        MissionManager manager = raftNode.parent().services().getService(MissionManager.class);
        manager.assignMission(agentMission);
    }

    private <M extends AbstractMessage> void sendAbstractAsyncMessage(StreamObserver<M> responseObserver,
                                                                      Supplier<M> supplier) {
        final CompletableFuture<M> future = CompletableFuture.supplyAsync(supplier, executor);

        future.whenComplete((result, e) -> {
            if (e == null) {
                responseObserver.onNext(result);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(e);
            }
        });
    }


}
