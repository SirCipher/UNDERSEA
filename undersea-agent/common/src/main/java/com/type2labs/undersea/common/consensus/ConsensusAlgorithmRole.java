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

/**
 * The role that an {@link com.type2labs.undersea.common.agent.Agent} is in the cluster
 */
public enum ConsensusAlgorithmRole {

    /**
     * This agent is the leader of the cluster
     */
    LEADER,

    /**
     * This agent is a follower in the cluster
     */
    FOLLOWER,

    /**
     * This agent is currently a candidate and is performing a voting round
     */
    CANDIDATE

}