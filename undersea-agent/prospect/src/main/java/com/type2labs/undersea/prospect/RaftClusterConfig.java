package com.type2labs.undersea.prospect;

import com.type2labs.undersea.common.config.UnderseaConfig;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import io.grpc.Deadline;

import java.util.concurrent.TimeUnit;


public class RaftClusterConfig implements UnderseaConfig {

    public static final long HEARTBEAT_PERIOD = 100;
    private UnderseaRuntimeConfig underseaRuntimeConfig;
    private boolean autoPortDiscovery = true;
    private int executorThreads = 4;
    private Deadline getStatusDeadline = Deadline.after(30, TimeUnit.SECONDS);
    /**
     * In seconds
     */
    private long statusDeadlineLong = 30;
    private long heatbeatTimeout = 2000L;

    public RaftClusterConfig() {
    }

    public RaftClusterConfig(UnderseaRuntimeConfig underseaRuntimeConfig) {
        this.underseaRuntimeConfig = underseaRuntimeConfig;
    }

    public long getStatusDeadlineLong() {
        return statusDeadlineLong;
    }

    public RaftClusterConfig setStatusDeadline(long duration, TimeUnit timeUnit) {
        this.getStatusDeadline = Deadline.after(duration, timeUnit);
        this.statusDeadlineLong = duration;
        return this;
    }

    public Deadline getStatusDeadline() {
        return getStatusDeadline;
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
