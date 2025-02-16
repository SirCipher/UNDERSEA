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

package com.type2labs.undersea.prospect.networking.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusClusterConfig;
import com.type2labs.undersea.prospect.ConsensusProtos;
import com.type2labs.undersea.prospect.MultiConsensusProtocolServiceGrpc;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.networking.model.MultiRoleLeaderClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiRoleLeaderClientImpl implements MultiRoleLeaderClient {

    private static final Logger logger = LogManager.getLogger(ConsensusAlgorithmClientImpl.class);

    private final InetSocketAddress socketAddress;
    private final ManagedChannel channel;
    private final MultiConsensusProtocolServiceGrpc.MultiConsensusProtocolServiceFutureStub futureStub;
    private final MultiConsensusProtocolServiceGrpc.MultiConsensusProtocolServiceBlockingStub blockingStub;
    private final ExecutorService clientExecutor;
    private final PeerId clientId;
    private ConsensusNode consensusAlgorithm;

    public MultiRoleLeaderClientImpl(ConsensusNode consensusAlgorithm, InetSocketAddress socketAddress, PeerId peerId) {
        this.consensusAlgorithm = consensusAlgorithm;
        this.clientId = peerId;
        this.socketAddress = socketAddress;
        this.channel =
                ManagedChannelBuilder.forAddress(socketAddress.getHostString(), socketAddress.getPort()).usePlaintext().build();
        this.futureStub = MultiConsensusProtocolServiceGrpc.newFutureStub(channel);
        this.blockingStub = MultiConsensusProtocolServiceGrpc.newBlockingStub(channel);

        int executorThreads = ((ConsensusClusterConfig) consensusAlgorithm.config()).executorThreads();
        this.clientExecutor = Executors.newFixedThreadPool(executorThreads,
                new ThreadFactoryBuilder().setNameFormat(consensusAlgorithm.parent().name() + "-rpc-client-%d").build());
    }

    @Override
    public String name() {
        return consensusAlgorithm.parent().name();
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
        clientExecutor.shutdown();

        try {
            channel.awaitTermination(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(consensusAlgorithm.parent().name() + ": failed to shutdown channel successfully", e);
        }
    }


    @Override
    public boolean isSelf() {
        return false;
    }

    @Override
    public void notify(ConsensusProtos.NotificationRequest request, FutureCallback<ConsensusProtos.Empty> callback) {
        ListenableFuture<ConsensusProtos.Empty> response = futureStub.notify(request);
        Futures.addCallback(response, callback, clientExecutor);
    }

}
