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

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.ConsensusProtos;
import com.type2labs.undersea.prospect.impl.ConsensusAlgorithmState;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.networking.model.ConsensusAlgorithmClient;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

public class VoteTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(VoteTask.class);
    private static final int MAX_RETRIES = 5;
    private final ExecutorService executorService;

    private final ConsensusNode consensusNode;
    private final Map<Client, Integer> retries = new ConcurrentHashMap<>();
    private ConsensusAlgorithmState.Candidate candidate;


    public VoteTask(ConsensusNode consensusNode) {
        this.consensusNode = consensusNode;
        this.executorService = ThrowableExecutor.newExecutor(consensusNode.parent(), 1, logger);
    }

    @Override
    public void run() {
        logger.info(consensusNode.parent().name() + " starting voting", consensusNode.parent());
        consensusNode.toCandidate();
        candidate = consensusNode.state().getCandidate();

        ConcurrentMap<PeerId, Client> localNodes = consensusNode.parent().clusterClients();

        if (localNodes.size() == 0) {
            logger.warn(consensusNode.parent().name() + " has no peers", consensusNode.parent());
        }

        for (Client client : localNodes.values()) {
            ConsensusAlgorithmClient consensusClient = (ConsensusAlgorithmClient) client;
            retries.put(consensusClient, 0);

            int term = consensusNode.state().getCurrentTerm();
            executorService.submit(() -> sendVote(term, consensusClient));
        }

        // Check if we voted for ourself
        Client self = consensusNode.self();
        Pair<Client, ClusterState.ClientState> nominee = consensusNode.state().getVotedFor();
        PeerId nomineeId = nominee.getKey().peerId();

        if (nomineeId.equals(consensusNode.parent().peerId())) {
            candidate.vote(self);

            if (localNodes.size() == 0) {
                if (candidate.wonRound()) {
                    consensusNode.toLeader(consensusNode.state().getCurrentTerm());
                }
            }
        }

        consensusNode.schedule(new VoteTaskTimeout(consensusNode), consensusNode.config().getVoteTaskTimeout());
    }

    private void sendVote(int term, ConsensusAlgorithmClient consensusClient) {
        ConsensusProtos.VoteRequest request = ConsensusProtos.VoteRequest.newBuilder()
                .setClient(GrpcUtil.toProtoClient(consensusNode))
                .setTerm(term)
                .build();

        consensusClient.requestVote(request, new FutureCallback<ConsensusProtos.VoteResponse>() {
            @Override
            public void onSuccess(ConsensusProtos.VoteResponse result) {
                PeerId nomineeId = PeerId.valueOf(result.getNominee().getConsensusPeerId());
                PeerId responderId = PeerId.valueOf(result.getClient().getConsensusPeerId());

                // Grant vote if we were nominated
                if (nomineeId.equals(consensusNode.parent().peerId())) {
                    Client responderClient = consensusNode.state().getClient(responderId);
                    candidate.vote(responderClient);
                }

                if (candidate.wonRound()) {
                    consensusNode.toLeader(consensusNode.state().getCurrentTerm());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;

                    if (statusRuntimeException.getStatus().getCode() == Status.Code.PERMISSION_DENIED) {
                        int count = retries.get(consensusClient);
                        if (count == MAX_RETRIES) {
                            consensusNode.state().removeNode(consensusClient.peerId());
                            logger.warn(consensusNode.parent().name() + ": exceeded maximum retries while contacting " +
                                    "client: " + consensusClient.peerId(), consensusNode.parent());
                        } else {
                            retries.put(consensusClient, count + 1);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {
                            }

                            executorService.submit(() -> sendVote(term, consensusClient));

                            logger.warn(consensusNode.parent().name() + ": attempt " + (count + 1) + " to get vote " +
                                            "from: " + consensusClient.peerId() + ". Will try " + MAX_RETRIES + " " +
                                            "times",
                                    consensusNode.parent());
                        }
                    }
                } else {
                    logger.warn(consensusNode.parent().name() + ": exception thrown while contacting client: " + consensusClient.peerId(), t);
                    consensusNode.state().removeNode(consensusClient.peerId());
                }
            }
        });
    }

}
