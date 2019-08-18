package com.type2labs.undersea.prospect.service;

import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.stub.StreamObserver;

public class RaftProtocolServiceImpl extends RaftProtocolServiceGrpc.RaftProtocolServiceImplBase {

    private final RaftNode raftNode;

    public RaftProtocolServiceImpl(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void getStatus(RaftProtos.AcquireStatusRequest request, StreamObserver<RaftProtos.AcquireStatusResponse> responseObserver) {
        super.getStatus(request, responseObserver);
    }

    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request,
                            StreamObserver<RaftProtos.AppendEntryResponse> responseObserver) {
        super.appendEntry(request, responseObserver);
    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request, StreamObserver<RaftProtos.VoteResponse> responseObserver) {
        super.requestVote(request, responseObserver);
    }

}
