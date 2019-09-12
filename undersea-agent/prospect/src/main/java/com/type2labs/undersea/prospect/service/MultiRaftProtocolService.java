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
import com.type2labs.undersea.prospect.MultiRaftProtocolServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.MultiRoleNotification;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executor;

public class MultiRaftProtocolService extends MultiRaftProtocolServiceGrpc.MultiRaftProtocolServiceImplBase {

    private static final Logger logger = LogManager.getLogger(RaftProtocolService.class);
    private final RaftNodeImpl raftNode;
    private final Executor executor;

    public MultiRaftProtocolService(RaftNode raftNode, Executor executor) {
        this.raftNode = (RaftNodeImpl) raftNode;
        this.executor = executor;
    }

    @Override
    public void notify(RaftProtos.NotificationRequest request, StreamObserver<RaftProtos.Empty> responseObserver) {
        GrpcUtil.sendAbstractAsyncMessage(responseObserver, () -> {
            MultiRoleNotification statusCode = MultiRoleNotification.valueOf(request.getStatusCode());
            UnderseaLogger.info(logger, raftNode.parent(), "Received notification. Status: " + statusCode);
            PeerId peerId = PeerId.valueOf(request.getClient().getRaftPeerId());
            Client client = raftNode.multiRoleState().remotePeers().get(peerId);

            switch (statusCode) {
                case FAILING:
                    raftNode.multiRoleState().handleFailure(client);
                    break;
                case GENERATED_MISSION:
                    raftNode.multiRoleState().setGeneratedMission(client, request.getNotification());
                    break;
                default:
                    throw new StatusRuntimeException(Status.UNIMPLEMENTED);
            }


            return RaftProtos.Empty.newBuilder().build();
        }, executor);
    }


}
