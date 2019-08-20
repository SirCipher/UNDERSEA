package com.type2labs.undersea.common.cluster;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface Client {

    PeerId peerId();

    InetSocketAddress socketAddress();

    void shutdown();

    default ConcurrentMap<PeerId, Client> localNodes() {
        return new ConcurrentHashMap<>();
    }

}
