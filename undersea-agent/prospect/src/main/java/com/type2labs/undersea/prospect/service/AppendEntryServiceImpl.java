package com.type2labs.undersea.prospect.service;

import com.type2labs.undersea.prospect.AppendEntryServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppendEntryServiceImpl extends AppendEntryServiceGrpc.AppendEntryServiceImplBase {

    private static final Logger logger = LogManager.getLogger(AppendEntryServiceImpl.class);
    private final RaftNode raftNode;

    public AppendEntryServiceImpl(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request,
                            StreamObserver<RaftProtos.AppendEntryResponse> responseObserver) {
        logger.info("Received request: " + request.toString());

        RaftProtos.AppendEntryResponse response = RaftProtos.AppendEntryResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}
