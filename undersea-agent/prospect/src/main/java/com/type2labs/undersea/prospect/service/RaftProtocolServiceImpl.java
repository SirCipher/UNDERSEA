package com.type2labs.undersea.prospect.service;

import com.google.protobuf.AbstractMessage;
import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.ClusterState;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        logger.info(raftNode.name() + " processing get status request: " + request, raftNode.agent());

        sendAbstractAsyncMessage(responseObserver, () -> {
            RaftProtos.AcquireStatusResponse.Builder builder = RaftProtos.AcquireStatusResponse.newBuilder();
            List<Pair<String, String>> status = raftNode.agent().status();

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

            int requestPreviousLogIndex = request.getPrevLogIndex();
//            Client client = GrpcUtil.toProtoClient(request.getClient());

            NodeLog.LogEntry logEntry = NodeLog.LogEntry.valueOf(request.getLogEntry());
            return RaftProtos.AppendEntryResponse.newBuilder().build();
        });
    }

    private <U extends AbstractMessage> void sendAbstractAsyncMessage(StreamObserver<U> responseObserver,
                                                                      Supplier<U> supplier) {
        final CompletableFuture<U> future = CompletableFuture.supplyAsync(supplier, executor);

        future.whenComplete((result, e) -> {
            if (e == null) {
                responseObserver.onNext(result);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(e);
            }
        });
    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request, StreamObserver<RaftProtos.VoteResponse> responseObserver) {
        logger.info(raftNode.name() + " processing vote request: " + request, raftNode.agent());

        sendAbstractAsyncMessage(responseObserver, () -> {
            Pair<Client, ClusterState.ClientState> nominee = raftNode.state().clusterState().getNominee();

            RaftProtos.VoteResponse response = RaftProtos.VoteResponse.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .setNominee(GrpcUtil.toProtoClient(nominee.getKey()))
                    .build();

            logger.info(raftNode.name() + ": voting for: " + nominee.getKey(), raftNode.agent());
            return response;
        });
    }


}
