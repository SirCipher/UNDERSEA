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
public class RaftClusterConfig implements UnderseaConfig {

    public long getHeartbeatPeriod() {
        return heartbeatPeriod;
    }

    private final long heartbeatPeriod = 100;
    private final long heatbeatTimeout = 3000L;
    private long voteTaskTimeout = 10000;

    public long getVoteTaskTimeout() {
        return voteTaskTimeout;
    }

    public void setVoteTaskTimeout(long voteTaskTimeout) {
        this.voteTaskTimeout = voteTaskTimeout;
    }

    private RuntimeConfig runtimeConfig;
    private boolean autoPortDiscovery = true;
    private long requestDeadline = 60;

    public RaftClusterConfig() {
    }

    public RaftClusterConfig(RuntimeConfig runtimeConfig) {
        this.runtimeConfig = runtimeConfig;
    }

    public long requestDeadline() {
        return requestDeadline;
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

    public RuntimeConfig getRuntimeConfig() {
        return runtimeConfig;
    }


    public long heartbeatTimeout() {
        return heatbeatTimeout;
    }
}
