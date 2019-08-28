package com.type2labs.undersea.common.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class AgentMetaData {

    private Map<PropertyKey, Object> properties = new HashMap<>();

    public void setProperty(PropertyKey key, Object value) {
        this.properties.put(key, value);
    }

    public Object getProperty(PropertyKey key) {
        return properties.get(key);
    }

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
