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
import com.type2labs.undersea.common.consensus.ConsensusClusterConfig;
import com.type2labs.undersea.prospect.ConsensusProtos;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.networking.model.ConsensusAlgorithmClient;
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
    private final ConsensusNode consensusNode;
    private int noResponses = 0;
    private int clusterSize;

    public AcquireStatusTask(ConsensusNode consensusNode) {
        this.consensusNode = consensusNode;
        this.clusterSize = consensusNode.parent().clusterClients().size();
    }

    @Override
    public void run() {
        if (consensusNode.state().isPreVoteState()) {
            return;
        }

        Collection<Client> localNodes = consensusNode.state().localNodes().values();

        consensusNode.state().initPreVoteClusterState();

        if (localNodes.size() == 0) {
            consensusNode.execute(new VoteTask(consensusNode));
            return;
        }

        for (Client localNode : localNodes) {
            ConsensusAlgorithmClient consensusAlgorithmClient = (ConsensusAlgorithmClient) localNode;

            ConsensusProtos.AcquireStatusRequest request = ConsensusProtos.AcquireStatusRequest
                    .newBuilder()
                    .setClient(GrpcUtil.toProtoClient(consensusNode))
                    .build();

            ConsensusProtos.AcquireStatusResponse response;
            ClusterState preVoteClusterState = consensusNode.state().getPreVoteClusterState();

            try {
                ConsensusClusterConfig config = (ConsensusClusterConfig) consensusNode.config();

                response = consensusAlgorithmClient.getStatus(request, Deadline.after(config.getStatusDeadline(), TimeUnit.SECONDS));

                ClusterState.ClientState agentInfo = new ClusterState.ClientState(localNode, response.getCost());
                preVoteClusterState.setAgentInformation(localNode, agentInfo);

                incrementAndVote();
            } catch (StatusRuntimeException e) {
                Status.Code code = e.getStatus().getCode();

                if (code.equals(Status.Code.DEADLINE_EXCEEDED)) {
                    logger.info(consensusNode.parent().name() + ": deadline exceeded while contacting client: " + consensusAlgorithmClient.name()
                            , consensusNode.parent());
                    incrementAndVote();
                }
            }
        }
    }

    private void incrementAndVote() {
        noResponses++;

        if (noResponses == clusterSize) {
            consensusNode.execute(new VoteTask(consensusNode));
        }
    }
}
