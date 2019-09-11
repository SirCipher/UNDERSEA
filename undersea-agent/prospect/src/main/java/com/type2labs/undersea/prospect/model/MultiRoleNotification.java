package com.type2labs.undersea.prospect.model;

public enum MultiRoleNotification {

    /**
     * Tells the MRS leader what the follower's current mission is
     */
    GENERATED_MISSION,

    /**
     * Tells the MRS leader that a follower is failing
     */
    FAILING,

    /**
     * Tells the MRS leader that a remote peer requires backup
     */
    REQUIRE_BACKUP

}
