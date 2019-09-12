/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
