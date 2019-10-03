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
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftState;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.model.RaftClient;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class VoteTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(VoteTask.class);
    private static final int MAX_RETRIES = 5;

    private final RaftNode raftNode;
    private final Map<Client, Integer> retries = new HashMap<>();
    private RaftState.Candidate candidate;

    public VoteTask(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void run() {
        logger.info(raftNode.parent().name() + " starting voting", raftNode.parent());
        raftNode.toCandidate();
        candidate = raftNode.state().getCandidate();

        ConcurrentMap<PeerId, Client> localNodes = raftNode.parent().clusterClients();

        if (localNodes.size() == 0) {
            logger.warn(raftNode.parent().name() + " has no peers", raftNode.parent());
        }

        final Iterator<Client> iterator = localNodes.values().iterator();

        while (iterator.hasNext()) {
            RaftClient raftClient = (RaftClient) iterator.next();
            retries.put(raftClient, 0);

            int term = raftNode.state().getCurrentTerm();

            sendMessage(term, raftClient);
        }

        // Check if we voted for ourself
        Client self = raftNode.self();
        Pair<Client, ClusterState.ClientState> nominee = raftNode.state().getVotedFor();
        PeerId nomineeId = nominee.getKey().peerId();

        if (nomineeId.equals(raftNode.parent().peerId())) {
            candidate.vote(self);

            if (localNodes.size() == 0) {
                if (candidate.wonRound()) {
                    raftNode.toLeader(raftNode.state().getCurrentTerm());
                }
            }
        }

        raftNode.schedule(new VoteTaskTimeout(raftNode), 10000);
    }

    private void sendMessage(int term, RaftClient raftClient) {
        RaftProtos.VoteRequest request = RaftProtos.VoteRequest.newBuilder()
                .setClient(GrpcUtil.toProtoClient(raftNode))
                .setTerm(term)
                .build();

        raftClient.requestVote(request, new FutureCallback<RaftProtos.VoteResponse>() {
            @Override
            public void onSuccess(RaftProtos.VoteResponse result) {
                PeerId nomineeId = PeerId.valueOf(result.getNominee().getRaftPeerId());
                PeerId responderId = PeerId.valueOf(result.getClient().getRaftPeerId());

                // Grant vote if we were nominated
                if (nomineeId.equals(raftNode.parent().peerId())) {
                    Client responderClient = raftNode.state().getClient(responderId);
                    candidate.vote(responderClient);
                }

                if (candidate.wonRound()) {
                    raftNode.toLeader(raftNode.state().getCurrentTerm());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;

                    if (statusRuntimeException.getStatus() == Status.PERMISSION_DENIED) {
                        int count = retries.get(raftClient);
                        if (count == MAX_RETRIES) {
                            raftNode.state().removeNode(raftClient.peerId());
                            logger.warn(raftNode.parent().name() + ": exceeded maximum retries while contacting client: " + raftClient.peerId(), raftNode.parent());
                        }

                        retries.put(raftClient, count + 1);
                    }
                } else {
                    logger.warn(raftNode.parent().name() + ": exception thrown while contacting client: " + raftClient.peerId(), t);
                    raftNode.state().removeNode(raftClient.peerId());
                }
            }
        });
    }

}
