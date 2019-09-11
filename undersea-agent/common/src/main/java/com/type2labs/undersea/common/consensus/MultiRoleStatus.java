package com.type2labs.undersea.common.consensus;

public enum MultiRoleStatus {
    /**
     * Denotes that this node is the leader of all groups. It's leader (client) will also be null
     */
    LEADER,

    /**
     * Denotes that this node is a leader of one cluster and a follower in another
     */
    LEADER_FOLLOWER,

    /**
     * Default status.
     * <p>
     * Denotes that this node is not in a leader-follower state. Such that it is a follower in its cluster
     */
    NOT_APPLIED
}
