package com.type2labs.undersea.agent.consensus.task;

import com.type2labs.undersea.agent.consensus.impl.RaftNodeImpl;
import com.type2labs.undersea.agent.consensus.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RequireRoleTask implements Runnable {

    private final Logger logger = LogManager.getLogger(RequireRoleTask.class);
    private final RaftNode raftNode;
    private final RaftNodeImpl.RaftRole requestedRole;

    public RequireRoleTask(RaftNode raftNode, RaftNodeImpl.RaftRole requestedRole) {
        this.raftNode = raftNode;
        this.requestedRole = requestedRole;
    }

    @Override
    public void run() {
        if (!raftNode.isAvailable()) {
            logger.error("Unable to run task as agent is unavailable");
            return;
        }

        if (raftNode.getRaftRole() != requestedRole) {
            logger.error("Unable to run task as agent does not meet requested role");
            return;
        }

        try {
            innerRun();
        } catch (Throwable e) {
            logger.error(e);
        }
    }

    protected abstract void innerRun();

}
