package com.type2labs.undersea.prospect.networking;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftPeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientImpl implements Client {

    private static final Logger logger = LogManager.getLogger(ClientImpl.class);

    private final InetSocketAddress socketAddress;
    private final ManagedChannel channel;
    private final RaftProtocolServiceGrpc.RaftProtocolServiceFutureStub futureStub;
    private final RaftProtocolServiceGrpc.RaftProtocolServiceBlockingStub blockingStub;
    private final ExecutorService clientExecutor;
    private final RaftPeerId clientId;

    public ClientImpl(RaftNode parent, InetSocketAddress socketAddress, RaftPeerId raftPeerId) {
        this.clientId = raftPeerId;
        this.socketAddress = socketAddress;
        this.channel =
                ManagedChannelBuilder.forAddress(socketAddress.getHostString(), socketAddress.getPort()).usePlaintext().build();
        this.futureStub = RaftProtocolServiceGrpc.newFutureStub(channel);
        this.blockingStub = RaftProtocolServiceGrpc.newBlockingStub(channel);

        int noThreads = parent.config().executorThreads();
        this.clientExecutor = Executors.newFixedThreadPool(noThreads,
                new ThreadFactoryBuilder().setNameFormat(parent.name() + "-rpc-client-%d").build());
    }

    @Override
    public RaftPeerId peerId() {
        return clientId;
    }

    @Override
    public InetSocketAddress socketAddress() {
        return socketAddress;
    }

    @Override
    public void shutdown() {
        channel.shutdownNow();
    }

    @Override
    public RaftProtos.AcquireStatusResponse getStatus(RaftProtos.AcquireStatusRequest request, Deadline deadline) throws StatusRuntimeException {
        try {
            return blockingStub.withDeadline(deadline).getStatus(request);
        } catch (Exception e) {
            if (e instanceof StatusRuntimeException) {
                throw e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request,
                            FutureCallback<RaftProtos.AppendEntryResponse> callback) {
        ListenableFuture<RaftProtos.AppendEntryResponse> response = futureStub.appendEntry(request);
        Futures.addCallback(response, callback, clientExecutor);
    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request, FutureCallback<RaftProtos.VoteResponse> callback) {
        ListenableFuture<RaftProtos.VoteResponse> response = futureStub.requestVote(request);
        Futures.addCallback(response, callback, clientExecutor);
    }

    public abstract static class Callback<T> implements FutureCallback<T> {

        @Override
        public void onSuccess(T response) {
            logger.info("Processing response: " + response);
        }

        @Override
        public final void onFailure(Throwable t) {
            throw new RuntimeException(t);
        }
    }

}
