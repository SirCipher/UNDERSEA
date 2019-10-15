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

package com.type2labs.undersea.prospect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithmRole;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.missions.GeneratedMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.prospect.ConsensusProtocolServiceGrpc;
import com.type2labs.undersea.prospect.ConsensusProtos;
import com.type2labs.undersea.prospect.impl.ConsensusNodeImpl;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.networking.impl.ConsensusAlgorithmClientImpl;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ConsensusProtocolService extends ConsensusProtocolServiceGrpc.ConsensusProtocolServiceImplBase {

    private static final Logger logger = LogManager.getLogger(ConsensusProtocolService.class);
    private final ConsensusNodeImpl consensusNode;
    private final Executor executor;

    public ConsensusProtocolService(ConsensusNode consensusNode, Executor executor) {
        this.consensusNode = (ConsensusNodeImpl) consensusNode;
        this.executor = executor;
    }

    @Override
    public void getStatus(ConsensusProtos.AcquireStatusRequest request,
                          StreamObserver<ConsensusProtos.AcquireStatusResponse> responseObserver) {
        GrpcUtil.sendAbstractAsyncMessage(responseObserver, () -> {
            ConsensusProtos.AcquireStatusResponse.Builder builder = ConsensusProtos.AcquireStatusResponse.newBuilder();
            SubsystemMonitor subsystemMonitor =
                    consensusNode.parent().serviceManager().getService(SubsystemMonitor.class);
            double cost = subsystemMonitor.getCurrentCost();

            builder.setCost(cost);

            return builder.build();
        }, executor);
    }

    private void handleLogEntries(ConsensusProtos.AppendEntryRequest request) {
        List<LogEntry> logEntries = new ArrayList<>(request.getLogEntryCount());

        for (ConsensusProtos.LogEntryProto proto : request.getLogEntryList()) {
            AgentService agentService =
                    consensusNode.parent().serviceManager().getService(LogEntry.forName(proto.getAgentService()));

            logEntries.add(new LogEntry(consensusNode.leaderPeerId(), proto.getData(), proto.getValue(),
                    proto.getTerm(), agentService, true));
        }

        LogService logService = consensusNode.parent().serviceManager().getService(LogService.class);
        logService.appendEntries(logEntries);
    }

    @Override
    public void appendEntry(ConsensusProtos.AppendEntryRequest request,
                            StreamObserver<ConsensusProtos.AppendEntryResponse> responseObserver) {
        GrpcUtil.sendAbstractAsyncMessage(responseObserver, () -> {
            int requestTerm = request.getTerm();
            int currentTerm = consensusNode.state().getCurrentTerm();

            if (requestTerm < currentTerm) {
                return emptyAppendResponse();
            }

            if (requestTerm > consensusNode.state().getCurrentTerm()) {
                consensusNode.toFollower(requestTerm);
                UnderseaLogger.info(logger, consensusNode.parent(), "Demoting to follower due to receiving a greater " +
                        "term: "
                        + requestTerm);
                return emptyAppendResponse();
            }

            if (consensusNode.clusterRole() != ConsensusAlgorithmRole.FOLLOWER) {
                consensusNode.toFollower(requestTerm);
                UnderseaLogger.info(logger, consensusNode.parent(),
                        "Demoting to follower for term term: " + requestTerm);
            }

            PeerId peerId = PeerId.valueOf(request.getLeader().getConsensusPeerId());
            Client leader = consensusNode.parent().clusterClients().get(peerId);

            if (!leader.equals(consensusNode.state().getLeader())) {
                UnderseaLogger.info(logger, consensusNode.parent(), "Setting leader: " + leader.peerId());
                consensusNode.state().toLeader(leader);
            }

            consensusNode.updateLastAppendRequestTime();
            handleLogEntries(request);

            return ConsensusProtos.AppendEntryResponse.newBuilder().setTerm(consensusNode.state().getCurrentTerm()).build();
        }, executor);
    }

    private ConsensusProtos.AppendEntryResponse emptyAppendResponse() {
        return ConsensusProtos.AppendEntryResponse.newBuilder()
                .setClient(GrpcUtil.toProtoClient(consensusNode))
                .setTerm(consensusNode.term())
                .build();
    }

    @Override
    public void broadcastMembershipChanges(ConsensusProtos.ClusterMembersRequest request,
                                           StreamObserver<ConsensusProtos.Empty> responseObserver) {
        UnderseaLogger.info(logger, consensusNode.parent(), "Received new members: " + request.getMembersList());

        List<Client> clients = new ArrayList<>(request.getMembersCount());

        for (int i = 0; i < request.getMembersCount(); i++) {
            ConsensusProtos.ConsensusPeerProto peerProto = request.getMembersList().get(i);

            PeerId peerId = PeerId.valueOf(peerProto.getConsensusPeerId());
            Client member = consensusNode.parent().clusterClients().get(peerId);

            if (member == null) {
                member = new ConsensusAlgorithmClientImpl(consensusNode,
                        new InetSocketAddress(
                                peerProto.getHost(),
                                peerProto.getPort()),
                        peerId);
            }

            clients.add(member);
        }

        consensusNode.state().updateMembers(clients);
    }

    @Override
    public void requestVote(ConsensusProtos.VoteRequest request,
                            StreamObserver<ConsensusProtos.VoteResponse> responseObserver) {
        GrpcUtil.sendAbstractAsyncMessage(responseObserver, () -> {
            if (consensusNode.state().isPreVoteState()) {
                Status status = Status.newBuilder()
                        .setCode(Code.PERMISSION_DENIED.getNumber())
                        .setMessage("Not calculated costs")
                        .build();

                throw StatusProto.toStatusRuntimeException(status);
            } else {
                Pair<Client, ClusterState.ClientState> nominee = consensusNode.state().getVotedFor();

                logger.trace(consensusNode.parent() + ": nominating: " + nominee.getKey().peerId() + ". With cost:" + nominee.getValue().getCost(), consensusNode.parent());

                return ConsensusProtos.VoteResponse.newBuilder()
                        .setClient(GrpcUtil.toProtoClient(consensusNode))
                        .setNominee(GrpcUtil.toProtoClient(nominee.getKey()))
                        .build();
            }
        }, executor);
    }

    @Override
    public void distributeMission(ConsensusProtos.DistributeMissionRequest request,
                                  StreamObserver<ConsensusProtos.DisributeMissionResponse> responseObserver) {
        GeneratedMission generatedMission;

        try {
            ObjectMapper mapper = new ObjectMapper();
            generatedMission = mapper.readValue(request.getMission(), GeneratedMissionImpl.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to process: " + request.toString(), e);
        }

        logger.info(consensusNode.parent().name() + ": received mission: " + generatedMission);

        GrpcUtil.sendAbstractAsyncMessage(responseObserver, () -> ConsensusProtos.DisributeMissionResponse.newBuilder()
                .setClient(GrpcUtil.toProtoClient(consensusNode))
                .setResponse(1)
                .build(), executor);

        MissionManager manager = consensusNode.parent().serviceManager().getService(MissionManager.class);
        manager.assignMission(generatedMission);
    }

}
