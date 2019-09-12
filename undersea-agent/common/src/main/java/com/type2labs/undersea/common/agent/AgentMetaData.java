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

package com.type2labs.undersea.common.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link Agent}'s associated metadata. Contains a mapping of {@link PropertyKey} to {@link Object} to store
 * required locations of files, network ports and similar. This is largely used during construction and
 * initialisation of agents, however, it can be used to store anything that maps to hardware.
 */
public class AgentMetaData {

    private Map<PropertyKey, Object> properties = new HashMap<>();

    public Map<PropertyKey, Object> getProperties() {
        return properties;
    }

    public void setProperty(PropertyKey key, Object value) {
        this.properties.put(key, value);
    }

    public Object getProperty(PropertyKey key) {
        return properties.get(key);
    }

    /**
     * Keys for the {@link AgentMetaData#properties}. Comments on the values denote the data type
     */
    public enum PropertyKey {
        /**
         * String
         */
        METADATA_FILE_NAME,

        /**
         * String
         */
        LAUNCH_FILE_NAME,

        /**
         * String
         */
        MISSION_NAME,

        /**
         * int
         */
        HARDWARE_PORT,

        /**
         * int
         */
        INBOUND_HARDWARE_PORT,

        /**
         * int
         */
        SERVER_PORT,

        /**
         * boolean
         */
        IS_MASTER_NODE,

        /**
         * double
         */
        SIMULATION_SPEED,

        /**
         * {@link java.io.File}
         */
        MISSION_DIRECTORY
    }

}
