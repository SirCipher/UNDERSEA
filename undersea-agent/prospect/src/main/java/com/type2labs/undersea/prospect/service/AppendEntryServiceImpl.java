package com.type2labs.undersea.prospect.service;

import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppendEntryServiceImpl {

    private static final Logger logger = LogManager.getLogger(AppendEntryServiceImpl.class);
    private final RaftNode raftNode;

    public AppendEntryServiceImpl(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    //    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request,
                            StreamObserver<RaftProtos.AppendEntryResponse> responseObserver) {
        int requestTerm = request.getTerm();

        if (requestTerm > raftNode.state().getTerm()) {
            raftNode.toFollower(requestTerm);
        }

        int requestPreviousLogIndex = request.getPrevLogIndex();
        Client client = GrpcUtil.raftPeerProtoToEndpoint(request.getClient());

        NodeLog.LogEntry logEntry = NodeLog.LogEntry.valueOf(request.getLogEntry());
        RaftProtos.AppendEntryResponse response = RaftProtos.AppendEntryResponse.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        client.shutdown();
    }


}
