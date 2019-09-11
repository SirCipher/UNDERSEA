package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RequireRoleTask implements Runnable {

    private final Logger logger = LogManager.getLogger(RequireRoleTask.class);
    private final RaftNode raftNode;
    private final RaftRole requestedRole;

    public RequireRoleTask(RaftNode raftNode, RaftRole requestedRole) {
        this.raftNode = raftNode;
        this.requestedRole = requestedRole;
    }

    @Override
    public void run() {
        if (raftNode.getRaftRole() != requestedRole) {
            logger.error(raftNode.parent().name() + ": unable to run task as agent does not meet requested role: " + requestedRole,
                    raftNode.parent());
            return;
        }

        try {
            innerRun();
        } catch (Throwable e) {
            logger.error("Exception caught", raftNode.parent(), e);
        }
    }

    protected abstract void innerRun();

}
