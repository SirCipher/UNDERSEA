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
 * The status that is associated with this {@link MultiRoleState}.
 */
public enum MultiRoleStatus {
    /**
     * Denotes that this node is the leader of all groups. It's leader (client) will also be null
     */
    LEADER,

    /**
     * Denotes that this node is a leader of one cluster and a follower in another
     */
    LEADER_FOLLOWER,

    /**
     * Default status.
     * <p>
     * Denotes that this node is not in a leader-follower state. Such that it is a follower in its cluster
     */
    NOT_APPLIED
}
