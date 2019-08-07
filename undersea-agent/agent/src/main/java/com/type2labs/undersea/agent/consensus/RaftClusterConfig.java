package com.type2labs.undersea.agent.consensus;

import static com.type2labs.undersea.utilities.Preconditions.isNotNull;
import static com.type2labs.undersea.utilities.Preconditions.isPositive;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RaftClusterConfig {

    /**
     * Default leader election timeout in millis.
     * See {@link #leaderElectionTimeoutInMillis}.
     */
    public static final long DEFAULT_LEADER_ELECTION_TIMEOUT_IN_MILLIS = 2000;

    /**
     * Default leader heartbeat period in millis.
     * See {@link #leaderHeartbeatPeriodInMillis}.
     */
    public static final long DEFAULT_LEADER_HEARTBEAT_PERIOD_IN_MILLIS = 5000;

    /**
     * Default max append request entry count.
     * See {@link #appendRequestMaxEntryCount}.
     */
    public static final int DEFAULT_APPEND_REQUEST_MAX_ENTRY_COUNT = 100;

    /**
     * Default commit index advance to initiate a snapshot.
     * See {@link #commitIndexAdvanceCountToSnapshot}.
     */
    public static final int DEFAULT_COMMIT_INDEX_ADVANCE_COUNT_TO_SNAPSHOT = 10000;

    /**
     * Default max allowed uncommitted entry count.
     * See {@link #uncommittedEntryCountToRejectNewAppends}.
     */
    public static final int DEFAULT_UNCOMMITTED_ENTRY_COUNT_TO_REJECT_NEW_APPENDS = 100;

    /**
     * Default max number of missed heartbeats to trigger a new leader election.
     */
    public static final int DEFAULT_MAX_MISSED_LEADER_HEARTBEAT_COUNT = 5;

    /**
     * Default append request backoff timeout in millis.
     */
    public static final long DEFAULT_APPEND_REQUEST_BACKOFF_TIMEOUT_IN_MILLIS = 100;

    /**
     * Leader election timeout in milliseconds. If a candidate cannot win
     * majority of the votes in time, a new election round is initiated.
     */
    private long leaderElectionTimeoutInMillis = DEFAULT_LEADER_ELECTION_TIMEOUT_IN_MILLIS;

    /**
     * Period in milliseconds for a leader to send heartbeat messages to
     * its followers
     */
    private long leaderHeartbeatPeriodInMillis = DEFAULT_LEADER_HEARTBEAT_PERIOD_IN_MILLIS;

    /**
     * Maximum number of missed leader heartbeats to trigger
     * a new leader election
     */
    private int maxMissedLeaderHeartbeatCount = DEFAULT_MAX_MISSED_LEADER_HEARTBEAT_COUNT;

    /**
     * Maximum number of entries that can be sent in a single batch of
     * append entries request
     */
    private int appendRequestMaxEntryCount = DEFAULT_APPEND_REQUEST_MAX_ENTRY_COUNT;

    /**
     * Number of new commits to initiate a new snapshot after
     * the last snapshot
     */
    private int commitIndexAdvanceCountToSnapshot = DEFAULT_COMMIT_INDEX_ADVANCE_COUNT_TO_SNAPSHOT;

    /**
     * Maximum number of uncommitted entries in the leader's Raft log before
     * temporarily rejecting new requests of callers.
     */
    private int uncommittedEntryCountToRejectNewAppends = DEFAULT_UNCOMMITTED_ENTRY_COUNT_TO_REJECT_NEW_APPENDS;

    /**
     * Timeout in milliseconds for append request backoff. After the leader
     * sends an append request to a follower, it will not send a subsequent
     * append request until the follower responds to the former request
     * or this timeout occurs.
     */
    private long appendRequestBackoffTimeoutInMillis = DEFAULT_APPEND_REQUEST_BACKOFF_TIMEOUT_IN_MILLIS;
    private CostConfiguration costConfiguration;

    public RaftClusterConfig() {
    }

    public RaftClusterConfig(RaftClusterConfig config) {
        this.leaderElectionTimeoutInMillis = config.leaderElectionTimeoutInMillis;
        this.leaderHeartbeatPeriodInMillis = config.leaderHeartbeatPeriodInMillis;
        this.appendRequestMaxEntryCount = config.appendRequestMaxEntryCount;
        this.commitIndexAdvanceCountToSnapshot = config.commitIndexAdvanceCountToSnapshot;
        this.uncommittedEntryCountToRejectNewAppends = config.uncommittedEntryCountToRejectNewAppends;
        this.maxMissedLeaderHeartbeatCount = config.maxMissedLeaderHeartbeatCount;
        this.appendRequestBackoffTimeoutInMillis = config.appendRequestBackoffTimeoutInMillis;
    }

    public long getLeaderElectionTimeoutInMillis() {
        return leaderElectionTimeoutInMillis;
    }

    public RaftClusterConfig setLeaderElectionTimeoutInMillis(long leaderElectionTimeoutInMillis) {
        isPositive(leaderElectionTimeoutInMillis, "leader election timeout in millis: "
                + leaderElectionTimeoutInMillis + " must be positive!");
        this.leaderElectionTimeoutInMillis = leaderElectionTimeoutInMillis;
        return this;
    }

    public long getLeaderHeartbeatPeriodInMillis() {
        return leaderHeartbeatPeriodInMillis;
    }

    public RaftClusterConfig setLeaderHeartbeatPeriodInMillis(long leaderHeartbeatPeriodInMillis) {
        isPositive(leaderHeartbeatPeriodInMillis, "leader heartbeat period in millis: "
                + leaderHeartbeatPeriodInMillis + " must be positive!");
        this.leaderHeartbeatPeriodInMillis = leaderHeartbeatPeriodInMillis;
        return this;
    }

    public int getAppendRequestMaxEntryCount() {
        return appendRequestMaxEntryCount;
    }

    public RaftClusterConfig setAppendRequestMaxEntryCount(int appendRequestMaxEntryCount) {
        isPositive(appendRequestMaxEntryCount, "append request max entry count: " + appendRequestMaxEntryCount
                + " must be positive!");
        this.appendRequestMaxEntryCount = appendRequestMaxEntryCount;
        return this;
    }

    public int getCommitIndexAdvanceCountToSnapshot() {
        return commitIndexAdvanceCountToSnapshot;
    }

    public RaftClusterConfig setCommitIndexAdvanceCountToSnapshot(int commitIndexAdvanceCountToSnapshot) {
        isPositive(commitIndexAdvanceCountToSnapshot, "commit index advance count to snapshot: "
                + commitIndexAdvanceCountToSnapshot + " must be positive!");
        this.commitIndexAdvanceCountToSnapshot = commitIndexAdvanceCountToSnapshot;
        return this;
    }

    public int getUncommittedEntryCountToRejectNewAppends() {
        return uncommittedEntryCountToRejectNewAppends;
    }

    public RaftClusterConfig setUncommittedEntryCountToRejectNewAppends(int uncommittedEntryCountToRejectNewAppends) {
        isPositive(uncommittedEntryCountToRejectNewAppends, "uncommitted entry count to reject new appends: "
                + uncommittedEntryCountToRejectNewAppends + " must be positive!");
        this.uncommittedEntryCountToRejectNewAppends = uncommittedEntryCountToRejectNewAppends;
        return this;
    }

    public int getMaxMissedLeaderHeartbeatCount() {
        return maxMissedLeaderHeartbeatCount;
    }

    public RaftClusterConfig setMaxMissedLeaderHeartbeatCount(int maxMissedLeaderHeartbeatCount) {
        isPositive(maxMissedLeaderHeartbeatCount, "max missed leader heartbeat count must be positive!");
        this.maxMissedLeaderHeartbeatCount = maxMissedLeaderHeartbeatCount;
        return this;
    }

    public long getAppendRequestBackoffTimeoutInMillis() {
        return appendRequestBackoffTimeoutInMillis;
    }

    public RaftClusterConfig setAppendRequestBackoffTimeoutInMillis(long appendRequestBackoffTimeoutInMillis) {
        isPositive(appendRequestBackoffTimeoutInMillis, "append request backoff timeout must be positive!");
        this.appendRequestBackoffTimeoutInMillis = appendRequestBackoffTimeoutInMillis;
        return this;
    }

    public RaftClusterConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }


    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }
}
