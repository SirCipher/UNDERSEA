package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreVoteTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(PreVoteTask.class);

    private final RaftNode raftNode;
    private final int term;

    public PreVoteTask(RaftNode raftNode, int term) {
        this.raftNode = raftNode;
        this.term = term;
    }

    @Override
    public void run() {

    }
}
