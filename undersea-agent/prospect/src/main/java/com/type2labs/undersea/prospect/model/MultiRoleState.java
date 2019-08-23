package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.cluster.Client;

/**
 * Contains an agent's multi-role state. A node that is in a {@link Status#LEADER_FOLLOWER} is leading one
 * cluster and a follower in another.
 */
public class MultiRoleState {

    /**
     * The leader of the parent cluster
     */
    private Client leader;
    private Status status = Status.NOT_APPLIED;
    private Cluster assignedCluster;

    public Cluster getAssignedCluster() {
        return assignedCluster;
    }

    public void setAssignedCluster(Cluster assignedCluster) {
        this.assignedCluster = assignedCluster;
    }

    public Client getLeader() {
        return leader;
    }

    public void setLeader(Client leader) {
        this.leader = leader;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {

        /*
         * Denotes that this node is the leader of all groups. It's leader (client) will also be null
         */
        LEADER,

        /**
         * Denotes that this node is a leader of one cluster and a follower in another
         */
        LEADER_FOLLOWER,

        /**
         * Denotes that this node is not in a leader-follower state. Such that it is a follower in its cluster
         */
        NOT_APPLIED
    }

}
