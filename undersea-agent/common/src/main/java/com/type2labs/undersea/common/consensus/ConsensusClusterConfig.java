/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.consensus;

import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.config.UnderseaConfig;

/**
 * A configuration object for {@link ConsensusAlgorithm}s
 */
public class ConsensusClusterConfig implements UnderseaConfig {

    public static final long HEARTBEAT_PERIOD = 100;
    private static final long heatbeatTimeout = 3000L;
    private final long appendRequestDeadline = 10;
    private long voteTaskTimeout = 10000;

    /**
     * In seconds
     */
    private final long statusDeadlineLong = 60;
    private RuntimeConfig runtimeConfig;
    private boolean autoPortDiscovery = true;
    private int executorThreads = 4;
    private long getStatusDeadline = 60;

    public ConsensusClusterConfig() {
    }

    public long getVoteTaskTimeout() {
        return voteTaskTimeout;
    }

    public ConsensusClusterConfig(RuntimeConfig runtimeConfig) {
        this.runtimeConfig = runtimeConfig;
    }

    public long getStatusDeadlineLong() {
        return statusDeadlineLong;
    }

    public long getStatusDeadline() {
        return getStatusDeadline;
    }

    public ConsensusClusterConfig setStatusDeadline(long duration) {
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
    public ConsensusClusterConfig autoPortDiscovery(boolean autoPortDiscovery) {
        this.autoPortDiscovery = autoPortDiscovery;
        return this;
    }

    public boolean autoPortDiscoveryEnabled() {
        return autoPortDiscovery;
    }

    public RuntimeConfig getRuntimeConfig() {
        return runtimeConfig;
    }

    /**
     * Signifies the number of threads that the {@link java.util.concurrent.ExecutorService}s should use for the gRPC
     * server handlers, clients, and servers
     *
     * @param executorThreads to use per {@link java.util.concurrent.ExecutorService}
     * @return this
     */
    public ConsensusClusterConfig noExecutorThreads(int executorThreads) {
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
