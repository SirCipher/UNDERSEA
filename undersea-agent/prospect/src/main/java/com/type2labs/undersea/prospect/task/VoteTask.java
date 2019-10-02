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
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

public class VoteTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(VoteTask.class);

    private final RaftNode raftNode;

    public VoteTask(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void run() {
        raftNode.toCandidate();

        logger.info(raftNode.parent().name() + " starting voting", raftNode.parent());

        ConcurrentMap<PeerId, Client> localNodes = raftNode.parent().clusterClients();
        RaftState.Candidate candidate = raftNode.state().getCandidate();

        if (localNodes.size() == 0) {
            logger.warn(raftNode.parent().name() + " has no peers", raftNode.parent());
        }

        final Iterator<Client> iterator = localNodes.values().iterator();

        while (iterator.hasNext()) {
            RaftClient raftClient = (RaftClient) iterator.next();

            int term = raftNode.state().getCurrentTerm();

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
//                    logger.error(raftNode.parent().name() + ": exception thrown when contacting: " + raftClient.name(), t);
                    raftNode.state().removeNode(raftClient.peerId());
                }
            });

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

}
