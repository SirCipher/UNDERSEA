package com.type2labs.undersea.prospect.model;

import io.grpc.ManagedChannel;

import java.net.InetSocketAddress;

public interface Endpoint {

    String name();

    InetSocketAddress socketAddress();

    ManagedChannel channel();

    void shutdown();

}
