package com.type2labs.undersea.prospect.networking;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
    private final ExecutorService clientExecutor = Executors.newFixedThreadPool(4,
            new ThreadFactoryBuilder().setNameFormat("rpc-client-%d").build());

    public ClientImpl(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        this.channel =
                ManagedChannelBuilder.forAddress(socketAddress.getHostString(), socketAddress.getPort()).usePlaintext().build();
        this.futureStub = RaftProtocolServiceGrpc.newFutureStub(channel);
        this.blockingStub = RaftProtocolServiceGrpc.newBlockingStub(channel);
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
    public void getStatus(RaftProtos.AcquireStatusRequest request) {

    }

    @Override
    public void appendEntry(RaftProtos.AppendEntryRequest request) {

    }

    @Override
    public void requestVote(RaftProtos.VoteRequest request) {

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

    @Override
    public void sayHello(RaftProtos.HelloRequest request, FutureCallback<RaftProtos.HelloResponse> callback) {
        ListenableFuture<RaftProtos.HelloResponse> response = futureStub.sayHello(request);
        Futures.addCallback(response, callback, clientExecutor);
    }
}
