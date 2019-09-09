package com.type2labs.undersea.common.service.transaction;

/**
 * enum for services to extend for their own lifecycle events
 */
public enum LifecycleEvent {

    /**
     * Denotes that the agent has just been elected the leader of a cluster
     */
    ELECTED_LEADER,

    /**
     * Denotes that the transaction should perform mission distribution
     */
    DISTRIBUTE_MISSION,

    /**
     * Denotes that the agent is no longer the leader of a cluster
     */
    NOT_LEADER,

    /**
     * Denotes that the agent is failing
     */
    FAILING,

    /**
     * Denotes that a write request should take place
     */
    WRITE_OBJECT,

    /**
     * Denotes that a read request should take place
     */
    READ_OBJECT,

    /**
     * Denotes that an {@link com.type2labs.undersea.common.service.AgentService} has failed in some capacity
     */
    SERVICE_FAILED,

    /**
     * Denotes that the {@link com.type2labs.undersea.common.logger.model.LogService} has received an entry that
     * matches the target service
     */
    APPEND_REQUEST,

    /**
     * Denotes that the agent should shutdown
     */
    SHUTDOWN
}
