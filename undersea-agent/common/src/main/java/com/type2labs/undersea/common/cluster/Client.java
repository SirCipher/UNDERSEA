package com.type2labs.undersea.common.cluster;

import com.type2labs.undersea.utilities.exception.NotSupportedException;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface Client {

    default ClusterState.ClientState state() {
        throw new NotSupportedException("state not implemented");
    }

    PeerId peerId();

    InetSocketAddress socketAddress();

    void shutdown();

    default ConcurrentMap<PeerId, Client> localNodes() {
        return new ConcurrentHashMap<>();
    }

}
