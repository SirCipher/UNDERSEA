package com.type2labs.undersea.prospect.networking;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.prospect.RaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import io.grpc.ManagedChannel;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

public interface Client {

    InetSocketAddress socketAddress();

    void shutdown();



    void getStatus(RaftProtos.AcquireStatusRequest request);

    void appendEntry(RaftProtos.AppendEntryRequest request);

    void requestVote(RaftProtos.VoteRequest request);

    void sayHello(RaftProtos.HelloRequest request, FutureCallback<RaftProtos.HelloResponse> callback);

}
