package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteTaskTimeout implements Runnable {

    private static final Logger logger = LogManager.getLogger(VoteTaskTimeout.class);

    private final RaftNode raftNode;

    VoteTaskTimeout(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void run() {
        if (raftNode.getRaftRole() != RaftRole.CANDIDATE) {
            return;
        }

        UnderseaLogger.info(logger, raftNode.parent(), "Voting round timed out, trying again");
        raftNode.execute(new AcquireStatusTask(raftNode));
    }
}
