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
 * An ID that is to be assigned to a newly formed cluster. This is WIP
 */
public class ClusterId {

    @JsonIgnore
    private static final Map<String, ClusterId> clusterIds = new ConcurrentHashMap<>();
    private final String id;

    private ClusterId(String id) {
        this.id = id;
    }

    public static ClusterId valueOf(String id) {
        return clusterIds.computeIfAbsent(id, ClusterId::new);
    }

    public static ClusterId newId() {
        return valueOf(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return id;
    }

}
