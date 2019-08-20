package com.type2labs.undersea.prospect.networking;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RaftClientImpl implements RaftClient {

    private static final Logger logger = LogManager.getLogger(RaftClientImpl.class);

    private final InetSocketAddress socketAddress;
    private final ManagedChannel channel;
    private final RaftProtocolServiceGrpc.RaftProtocolServiceFutureStub futureStub;
    private final RaftProtocolServiceGrpc.RaftProtocolServiceBlockingStub blockingStub;
    private final ExecutorService clientExecutor;
    private final PeerId clientId;
    private boolean isSelf = false;

    public RaftClientImpl(RaftNode parent, InetSocketAddress socketAddress, PeerId peerId) {
        this.clientId = peerId;
        this.socketAddress = socketAddress;
        this.channel =
                ManagedChannelBuilder.forAddress(socketAddress.getHostString(), socketAddress.getPort()).usePlaintext().build();
        this.futureStub = RaftProtocolServiceGrpc.newFutureStub(channel);
        this.blockingStub = RaftProtocolServiceGrpc.newBlockingStub(channel);

        int noThreads = parent.config().executorThreads();
        this.clientExecutor = Executors.newFixedThreadPool(noThreads,
                new ThreadFactoryBuilder().setNameFormat(parent.name() + "-rpc-client-%d").build());
    }

    public static Client ofSelf(RaftNode raftNode) {
        RaftClientImpl self = new RaftClientImpl(raftNode, new InetSocketAddress(0), raftNode.peerId());
        self.isSelf = true;
        return self;
    }

    @Override
    public PeerId peerId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RaftClientImpl client = (RaftClientImpl) o;

        return clientId.equals(client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }

    @Override
    public String toString() {
        return "ClientImpl{" +
                "clientId=" + clientId +
                '}';
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
