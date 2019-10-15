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
import com.type2labs.undersea.prospect.ConsensusProtocolServiceGrpc;
import com.type2labs.undersea.prospect.ConsensusProtos;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.networking.model.ConsensusAlgorithmClient;
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
import java.util.concurrent.TimeUnit;

public class ConsensusAlgorithmClientImpl implements ConsensusAlgorithmClient {

    private static final Logger logger = LogManager.getLogger(ConsensusAlgorithmClientImpl.class);

    private final InetSocketAddress socketAddress;
    private final ManagedChannel channel;
    private final ConsensusProtocolServiceGrpc.ConsensusProtocolServiceFutureStub futureStub;
    private final ConsensusProtocolServiceGrpc.ConsensusProtocolServiceBlockingStub blockingStub;
    private final ExecutorService clientExecutor;
    private final PeerId clientId;
    private ConsensusNode consensusAlgorithm;
    private boolean isSelf = false;

    public ConsensusAlgorithmClientImpl(ConsensusNode consensusAlgorithm, InetSocketAddress socketAddress,
                                        PeerId peerId) {
        this.consensusAlgorithm = consensusAlgorithm;
        this.clientId = peerId;
        this.socketAddress = socketAddress;
        this.channel =
                ManagedChannelBuilder.forAddress(socketAddress.getHostString(), socketAddress.getPort()).usePlaintext().build();
        this.futureStub = ConsensusProtocolServiceGrpc.newFutureStub(channel);
        this.blockingStub = ConsensusProtocolServiceGrpc.newBlockingStub(channel);

        int executorThreads = ((ConsensusClusterConfig) consensusAlgorithm.config()).executorThreads();
        this.clientExecutor = Executors.newFixedThreadPool(executorThreads,
                new ThreadFactoryBuilder().setNameFormat(consensusAlgorithm.parent().name() + "-rpc-client-%d").build());
    }

    public ConsensusAlgorithmClientImpl(ConsensusNode consensusAlgorithm, InetSocketAddress socketAddress,
                                        PeerId peerId, boolean isSelf) {
        this(consensusAlgorithm, socketAddress, peerId);
        this.isSelf = isSelf;
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
        clientExecutor.shutdown();

        try {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Interrupted exception during gRPC channel close", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean isSelf() {
        return isSelf;
    }

    @Override
    public ConsensusProtos.AcquireStatusResponse getStatus(ConsensusProtos.AcquireStatusRequest request,
                                                           Deadline deadline) throws StatusRuntimeException {
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
    public void appendEntry(ConsensusProtos.AppendEntryRequest request,
                            FutureCallback<ConsensusProtos.AppendEntryResponse> callback) {
        ListenableFuture<ConsensusProtos.AppendEntryResponse> response =
                futureStub.withDeadline(Deadline.after(10, TimeUnit.SECONDS)).appendEntry(request);
        Futures.addCallback(response, callback, clientExecutor);
    }

    @Override
    public void requestVote(ConsensusProtos.VoteRequest request,
                            FutureCallback<ConsensusProtos.VoteResponse> callback) {
        ListenableFuture<ConsensusProtos.VoteResponse> response = futureStub.requestVote(request);
        Futures.addCallback(response, callback, clientExecutor);
    }

    @Override
    public void distributeMission(ConsensusProtos.DistributeMissionRequest request,
                                  FutureCallback<ConsensusProtos.DisributeMissionResponse> callback) {
        ListenableFuture<ConsensusProtos.DisributeMissionResponse> response = futureStub.distributeMission(request);
        Futures.addCallback(response, callback, clientExecutor);
    }

    @Override
    public void broadcastMembershipChanges(ConsensusProtos.ClusterMembersRequest request,
                                           FutureCallback<ConsensusProtos.Empty> callback) {
        ListenableFuture<ConsensusProtos.Empty> response = futureStub.broadcastMembershipChanges(request);
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

        ConsensusAlgorithmClientImpl client = (ConsensusAlgorithmClientImpl) o;

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

}
