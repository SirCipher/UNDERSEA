package com.type2labs.undersea.prospect.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RaftPeerId {

    private static final Map<String, RaftPeerId> peerIds = new ConcurrentHashMap<>();

    private final String id;

    private RaftPeerId(String id) {
        this.id = id;
    }

    public static RaftPeerId valueOf(String id) {
        return peerIds.computeIfAbsent(id, RaftPeerId::new);
    }

    public static RaftPeerId newId() {
        return valueOf(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return id;
    }
}
