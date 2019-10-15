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

package com.type2labs.undersea.prospect.service;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.prospect.ConsensusProtos;
import com.type2labs.undersea.prospect.MultiConsensusProtocolServiceGrpc;
import com.type2labs.undersea.prospect.impl.ConsensusNodeImpl;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.model.MultiRoleNotification;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executor;

public class MultiConsensusProtocolService extends MultiConsensusProtocolServiceGrpc.MultiConsensusProtocolServiceImplBase {

    private static final Logger logger = LogManager.getLogger(ConsensusProtocolService.class);
    private final ConsensusNodeImpl consensusNode;
    private final Executor executor;

    public MultiConsensusProtocolService(ConsensusNode consensusNode, Executor executor) {
        this.consensusNode = (ConsensusNodeImpl) consensusNode;
        this.executor = executor;
    }

    @Override
    public void notify(ConsensusProtos.NotificationRequest request,
                       StreamObserver<ConsensusProtos.Empty> responseObserver) {
        GrpcUtil.sendAbstractAsyncMessage(responseObserver, () -> {
            MultiRoleNotification statusCode = MultiRoleNotification.valueOf(request.getStatusCode());
            UnderseaLogger.info(logger, consensusNode.parent(), "Received notification. Status: " + statusCode);
            PeerId peerId = PeerId.valueOf(request.getClient().getConsensusPeerId());
            Client client = consensusNode.multiRoleState().remotePeers().get(peerId);

            switch (statusCode) {
                case FAILING:
                    consensusNode.multiRoleState().handleFailure(client);
                    break;
                case GENERATED_MISSION:
                    consensusNode.multiRoleState().setGeneratedMission(client, request.getNotification());
                    break;
                default:
                    throw new StatusRuntimeException(Status.UNIMPLEMENTED);
            }


            return ConsensusProtos.Empty.newBuilder().build();
        }, executor);
    }


}
