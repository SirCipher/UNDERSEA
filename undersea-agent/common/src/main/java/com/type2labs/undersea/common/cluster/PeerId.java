package com.type2labs.undersea.common.cluster;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PeerId {

    private static final Map<String, PeerId> peerIds = new ConcurrentHashMap<>();

    private final String id;

    private PeerId(String id) {
        this.id = id;
    }

    public static PeerId valueOf(String id) {
        return peerIds.computeIfAbsent(id, PeerId::new);
    }

    public static PeerId newId() {
        return valueOf(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return id;
    }
}
