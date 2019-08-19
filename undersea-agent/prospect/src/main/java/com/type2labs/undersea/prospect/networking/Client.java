package com.type2labs.undersea.prospect.networking;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftPeerId;

import java.net.InetSocketAddress;

public interface Client {

    RaftPeerId peerId();

    InetSocketAddress socketAddress();

    void shutdown();

    void getStatus(RaftProtos.AcquireStatusRequest request, FutureCallback<RaftProtos.AcquireStatusResponse> callback);

    void appendEntry(RaftProtos.AppendEntryRequest request, FutureCallback<RaftProtos.AppendEntryResponse> callback);

    void requestVote(RaftProtos.VoteRequest request, FutureCallback<RaftProtos.VoteResponse> callback);

    void sayHello(RaftProtos.HelloRequest request, FutureCallback<RaftProtos.HelloResponse> callback);

}
