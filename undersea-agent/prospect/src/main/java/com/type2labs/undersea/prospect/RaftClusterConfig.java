package com.type2labs.undersea.prospect;

import com.type2labs.undersea.common.config.UnderseaConfig;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.prospect.model.CostCalculator;
import io.grpc.Deadline;

import java.util.concurrent.TimeUnit;

import static com.type2labs.undersea.utilities.Preconditions.isNotNull;

public class RaftClusterConfig implements UnderseaConfig {

    public static final long HEARTBEAT_PERIOD = 100;
    private UnderseaRuntimeConfig underseaRuntimeConfig;
    private CostConfiguration costConfiguration;
    private boolean autoPortDiscovery = true;
    private int executorThreads = 4;
    private Deadline getStatusDeadline = Deadline.after(30, TimeUnit.SECONDS);

    public RaftClusterConfig() {
    }

    public RaftClusterConfig getStatusDeadline(long duration, TimeUnit timeUnit){
        this.getStatusDeadline = Deadline.after(duration, timeUnit);
        return this;
    }

    public Deadline getGetStatusDeadline() {
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

    public RaftClusterConfig(UnderseaRuntimeConfig underseaRuntimeConfig) {
        this.underseaRuntimeConfig = underseaRuntimeConfig;
    }

    public UnderseaRuntimeConfig getUnderseaRuntimeConfig() {
        return underseaRuntimeConfig;
    }

    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }

    public RaftClusterConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
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

    public CostCalculator getCostCalculator() {
        return costConfiguration.getCostCalculator();
    }

    /**
     * Returns the number of threads that an {@link java.util.concurrent.ExecutorService} should use
     * @return the number of threads
     */
    public int executorThreads() {
        return executorThreads;
    }
}
