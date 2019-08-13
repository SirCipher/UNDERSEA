package com.type2labs.undersea.prospect.service;

import com.type2labs.undersea.common.Endpoint;
import com.type2labs.undersea.prospect.ConsensusProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteRequestServiceServiceImpl extends ConsensusProtocolServiceGrpc.ConsensusProtocolServiceImplBase {

    private static final Logger logger = LogManager.getLogger(VoteRequestServiceServiceImpl.class);
    private final RaftNode raftNode;

    public VoteRequestServiceServiceImpl(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request, StreamObserver<RaftProtos.VoteResponse> responseObserver) {
        Pair<Endpoint, Double> nominee = raftNode.poolInfo().getLowestCost();

        RaftProtos.VoteResponse response = RaftProtos.VoteResponse.newBuilder()
                .setClient(GrpcUtil.toRaftPeer(raftNode))
                .setNominee(GrpcUtil.toRaftPeer(nominee.getKey()))
                .build();

        logger.info(raftNode.name() + ": voting for: " + nominee.getKey());

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        raftNode.state().setVote(nominee.getKey());
    }

}


