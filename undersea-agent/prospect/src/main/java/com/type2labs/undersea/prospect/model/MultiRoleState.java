package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.cluster.Client;

/**
 * Contains an agent's multi-role state. A node that is in a {@link MultiRoleStatus#LEADER_FOLLOWER} is leading one
 * cluster and a follower in another.
 */
public class MultiRoleState {

    /**
     * The leader of the parent cluster
     */
    private Client leader;
    private MultiRoleStatus multiRoleStatus = MultiRoleStatus.NOT_APPLIED;

    public Client getLeader() {
        return leader;
    }

    public void setLeader(Client leader) {
        this.leader = leader;
    }

    public MultiRoleStatus getMultiRoleStatus() {
        return multiRoleStatus;
    }

    public enum MultiRoleStatus {
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
