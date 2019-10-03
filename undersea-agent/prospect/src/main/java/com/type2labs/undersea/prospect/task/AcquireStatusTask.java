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

package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.model.RaftClient;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class AcquireStatusTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(AcquireStatusTask.class);
    private final RaftNode raftNode;
    private int noResponses;
    private int clusterSize;

    public AcquireStatusTask(RaftNode raftNode) {
        this.raftNode = raftNode;
        this.clusterSize = raftNode.parent().clusterClients().size();
    }

    @Override
    public void run() {
        if (raftNode.state().isPreVoteState()) {
            return;
        }

        Collection<Client> localNodes = raftNode.state().localNodes().values();

        raftNode.state().initPreVoteClusterState();

        if (localNodes.size() == 0) {
            raftNode.execute(new VoteTask(raftNode));
            return;
        }

        for (Client localNode : localNodes) {
            RaftClient raftClient = (RaftClient) localNode;

            RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest
                    .newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .build();

            RaftProtos.AcquireStatusResponse response;
            ClusterState preVoteClusterState = raftNode.state().getPreVoteClusterState();

            try {
                RaftClusterConfig config = raftNode.config();

                response = raftClient.getStatus(request, Deadline.after(config.getStatusDeadline(), TimeUnit.SECONDS));

                ClusterState.ClientState agentInfo = new ClusterState.ClientState(localNode, response.getCost());
                preVoteClusterState.setAgentInformation(localNode, agentInfo);

                incrementAndVote();
            } catch (StatusRuntimeException e) {
                Status.Code code = e.getStatus().getCode();

                if (code.equals(Status.Code.DEADLINE_EXCEEDED)) {
                    logger.info(raftNode.parent().name() + ": deadline exceeded while contacting client: " + raftClient.name()
                            , raftNode.parent());
                    incrementAndVote();
                } else {
                    logger.error(e);
                }
            }
        }
    }

    private void incrementAndVote() {
        noResponses++;

        if (noResponses == clusterSize) {
            raftNode.execute(new VoteTask(raftNode));
        }
    }
}
