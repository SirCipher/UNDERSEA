package com.type2labs.undersea.agent.consensus.service;


import com.type2labs.undersea.agent.consensus.AcquireStatusServiceGrpc;
import com.type2labs.undersea.agent.consensus.RaftProtos;
import com.type2labs.undersea.agent.consensus.model.RaftNode;
import io.grpc.stub.StreamObserver;

public class AcquireStatusImpl extends AcquireStatusServiceGrpc.AcquireStatusServiceImplBase {

    private final RaftNode raftNode;

    public AcquireStatusImpl(RaftNode raftNode){
        super();
        this.raftNode = raftNode;
    }

    @Override
    public void getStatus(RaftProtos.AcquireStatusRequest request,
                          StreamObserver<RaftProtos.AcquireStatusResponse> responseObserver) {
        RaftProtos.AcquireStatusResponse response = RaftProtos.AcquireStatusResponse
                .newBuilder()
                .setStatus(raftNode.agent().toString())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}