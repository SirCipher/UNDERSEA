package com.type2labs.undersea.prospect.task;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

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
        if (!raftNode.poolInfo().hasInfo()) {
            logger.info(raftNode.name() + " does not have the pool's info, attempting to acquire");
            raftNode.execute(new AcquireStatusTask(raftNode));
            return;
        }

        if (raftNode.state().getVotedFor() != null) {
            logger.info("Node: " + raftNode.name() + " has already voted during this term. For: " + raftNode.state().getVotedFor() + ". Not voting again");
        }

        raftNode.toCandidate();
        Collection<Client> localNodes = raftNode.state().localNodes().values();

        for (Client client : localNodes) {
            RaftProtos.VoteRequest request = RaftProtos.VoteRequest.newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .setTerm(raftNode.state().getTerm() + 1)
                    .build();

            client.requestVote(request, new FutureCallback<RaftProtos.VoteResponse>() {
                @Override
                public void onSuccess(RaftProtos.VoteResponse result) {

                }

                @Override
                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }
            });
        }

    }

}
