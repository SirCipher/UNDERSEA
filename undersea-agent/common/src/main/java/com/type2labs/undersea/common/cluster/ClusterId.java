package com.type2labs.undersea.common.cluster;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
