package com.type2labs.undersea.common.networking;

import io.grpc.ManagedChannel;

import java.net.InetSocketAddress;

public interface Endpoint {

    String name();

    InetSocketAddress socketAddress();

    ManagedChannel channel();

    void shutdown();

}
