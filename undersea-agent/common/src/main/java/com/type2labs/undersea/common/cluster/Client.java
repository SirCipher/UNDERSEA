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

package com.type2labs.undersea.common.cluster;

import com.type2labs.undersea.utilities.exception.NotSupportedException;

import java.net.InetSocketAddress;

/**
 * A remote client that is available in the {@link com.type2labs.undersea.common.agent.Agent}'s cluster. Where used,
 * implementors will be able to cast this to a known type and communicate with the agent over gRPC calls or similar.
 */
public interface Client {

    /**
     * A human-readable name for the client
     *
     * @return the name
     */
    String name();

    /**
     * The last known state of this client. Used to store the associated cost with the client when performing a
     * pre-vote task
     *
     * @return the state
     */
    default ClusterState.ClientState state() {
        throw new NotSupportedException("state not implemented");
    }

    /**
     * The remote {@link PeerId} of the client
     *
     * @return the peer ID
     */
    PeerId peerId();

    /**
     * Shutdown the networking associated with this client. gRPC servers and similar
     */
    void shutdown();

    /**
     * Whether or not this client is actually this agent. This is to be removed, however, at present it is used to
     * determine
     * if a peer has assigned a task to this agent.
     *
     * @return whether or not this 'client' is actually this agent
     */
    boolean isSelf();

    /**
     * The remote address of this client
     *
     * @return the address
     */
    InetSocketAddress socketAddress();

}
