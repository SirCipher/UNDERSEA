package com.type2labs.undersea.prospect.task;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftState;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClient;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentMap;

public class VoteTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(VoteTask.class);

    private final RaftNode raftNode;
    private final int term;

    public VoteTask(RaftNode raftNode, int term) {
        this.raftNode = raftNode;
        this.term = term;
    }

    /**
     * Allows for an agent to vote more than one time as the client checks to see if it has already received a vote
     * from an endpoint and does not allow for duplicates.
     */
    @Override
    public void run() {
        if (raftNode.state().getVotedFor() != null) {
            logger.info("Node: " + raftNode.name() + " has already voted during this term. For: " + raftNode.state().getVotedFor() + ". Not voting again");
        }

        logger.info(raftNode.name() + " starting voting", raftNode.agent());

        ConcurrentMap<PeerId, Client> localNodes = raftNode.agent().clusterClients();

        if (localNodes.size() == 0) {
            logger.warn(raftNode.name() + " has no peers", raftNode.agent());
        }

        for (Client client : localNodes.values()) {
            RaftClient raftClient = (RaftClient) client;

            int nextTerm = raftNode.state().getTerm() + 1;

            RaftProtos.VoteRequest request = RaftProtos.VoteRequest.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .setTerm(nextTerm)
                    .build();

            raftClient.requestVote(request, new FutureCallback<RaftProtos.VoteResponse>() {
                @Override
                public void onSuccess(RaftProtos.VoteResponse result) {
                    PeerId nomineeId = PeerId.valueOf(result.getNominee().getRaftPeerId());
                    PeerId responderId = PeerId.valueOf(result.getClient().getRaftPeerId());
                    RaftState.Candidate candidate = raftNode.state().getCandidate();

                    // Grant vote if we were nominated
                    if (nomineeId.equals(raftNode.parent().peerId())) {
                        Client responderClient = raftNode.state().getClient(responderId);
                        candidate.vote(responderClient);
                    }

                    if (candidate.wonRound()) {
                        raftNode.toLeader(raftNode.state().getTerm() + 1);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }
            });
        }

    }

}
