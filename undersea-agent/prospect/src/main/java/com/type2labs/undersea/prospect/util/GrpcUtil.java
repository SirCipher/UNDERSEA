package com.type2labs.undersea.prospect.util;

import com.google.protobuf.AbstractMessage;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class GrpcUtil {

    public static RaftProtos.RaftPeerProto toProtoClient(Client client) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        builder.setRaftPeerId(client.peerId().toString());

        return builder.build();
    }

    public static RaftProtos.RaftPeerProto toProtoClient(RaftNode raftNode) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        builder.setRaftPeerId(raftNode.parent().peerId().toString());

        return builder.build();
    }

    public static synchronized <M extends AbstractMessage> void sendAbstractAsyncMessage(StreamObserver<M> responseObserver,
                                                                      Supplier<M> supplier, Executor executor) {
        try {
            final CompletableFuture<M> future = CompletableFuture.supplyAsync(supplier, executor);

            future.whenComplete((result, e) -> {
                if (e == null) {
                    responseObserver.onNext(result);
                    responseObserver.onCompleted();
                } else {
                    responseObserver.onError(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
