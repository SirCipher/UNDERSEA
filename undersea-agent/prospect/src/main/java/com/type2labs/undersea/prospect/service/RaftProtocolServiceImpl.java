package com.type2labs.undersea.prospect.service;

import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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

    @Override
    public void sayHello(RaftProtos.HelloRequest request, StreamObserver<RaftProtos.HelloResponse> observer) {
        logger.info(raftNode.name() + " processing request: " + request);

        final CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    logger.info("Computing result");
                    return "received: " + request.getName();
                },
                executor);

        future.whenComplete((result, e) -> {
            if (e == null) {
                observer.onNext(response(result));
                observer.onCompleted();
            } else {
                observer.onError(e);
            }
        });
    }

    private static RaftProtos.HelloResponse response(String result) {
        return RaftProtos.HelloResponse.newBuilder().
                setName(result).
                build();
    }

}
