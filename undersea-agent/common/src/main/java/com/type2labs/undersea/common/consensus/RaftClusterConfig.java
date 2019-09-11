package com.type2labs.undersea.common.consensus;

import com.type2labs.undersea.common.config.UnderseaConfig;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;


public class RaftClusterConfig implements UnderseaConfig {

    public static final long HEARTBEAT_PERIOD = 100;
    private static long heatbeatTimeout = 1000L;
    private UnderseaRuntimeConfig underseaRuntimeConfig;
    private boolean autoPortDiscovery = true;
    private int executorThreads = 4;
    private long getStatusDeadline = 30;
    private long appendRequestDeadline = 10;

    /**
     * In seconds
     */
    private long statusDeadlineLong = 30;

    public RaftClusterConfig() {
    }

    public RaftClusterConfig(UnderseaRuntimeConfig underseaRuntimeConfig) {
        this.underseaRuntimeConfig = underseaRuntimeConfig;
    }

    public long getStatusDeadlineLong() {
        return statusDeadlineLong;
    }

    public long getStatusDeadline() {
        return getStatusDeadline;
    }

    public RaftClusterConfig setStatusDeadline(long duration) {
        this.getStatusDeadline = duration;
        return this;
    }

    public long getAppendRequestDeadline() {
        return appendRequestDeadline;
    }

    /**
     * Signals that a node should automatically assign its gRPC server port. Default is true
     *
     * @param autoPortDiscovery enabled
     * @return this
     */
    public RaftClusterConfig autoPortDiscovery(boolean autoPortDiscovery) {
        this.autoPortDiscovery = autoPortDiscovery;
        return this;
    }

    public boolean autoPortDiscoveryEnabled() {
        return autoPortDiscovery;
    }

    public UnderseaRuntimeConfig getUnderseaRuntimeConfig() {
        return underseaRuntimeConfig;
    }


    /**
     * Signifies the number of threads that the {@link java.util.concurrent.ExecutorService}s should use for the gRPC
     * server handlers, clients, and servers
     *
     * @param executorThreads to use per {@link java.util.concurrent.ExecutorService}
     * @return this
     */
    public RaftClusterConfig noExecutorThreads(int executorThreads) {
        this.executorThreads = executorThreads;
        return this;
    }


    /**
     * Returns the number of threads that an {@link java.util.concurrent.ExecutorService} should use
     *
     * @return the number of threads
     */
    public int executorThreads() {
        return executorThreads;
    }

    public long heartbeatTimeout() {
        return heatbeatTimeout;
    }
}
