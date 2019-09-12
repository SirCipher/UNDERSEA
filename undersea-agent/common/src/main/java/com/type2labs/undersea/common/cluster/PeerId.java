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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A unique identifier for an {@link com.type2labs.undersea.common.agent.Agent}. Values are cached to ensure that the
 * same instance of a {@link PeerId} is returned throughout the lifetime of the
 * {@link com.type2labs.undersea.common.agent.Agent}
 */
public class PeerId {

    @JsonIgnore
    private static final Map<String, PeerId> peerIds = new ConcurrentHashMap<>();
    private final String id;

    private PeerId(String id) {
        this.id = id;
    }

    /**
     * Returns a {@link PeerId} from a {@link String} representation
     *
     * @param id to construct
     * @return the associated {@link PeerId}
     */
    public static PeerId valueOf(String id) {
        return peerIds.computeIfAbsent(id, PeerId::new);
    }

    /**
     * Create a new {@link PeerId}
     *
     * @return the new {@link PeerId}
     */
    public static PeerId newId() {
        return valueOf(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return id;
    }
}
