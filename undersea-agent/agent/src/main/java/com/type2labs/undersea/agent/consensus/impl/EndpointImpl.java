package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.agent.consensus.model.Endpoint;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.net.InetSocketAddress;

public class EndpointImpl implements Endpoint {

    private final InetSocketAddress socketAddress;
    private final ManagedChannel channel;
    private final String name;

    public EndpointImpl(String name, InetSocketAddress socketAddress) {
        this.name = name;

        this.channel = ManagedChannelBuilder.forAddress(socketAddress.getHostName(), socketAddress.getPort())
                .usePlaintext()
                .build();
        this.socketAddress = socketAddress;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public InetSocketAddress socketAddress() {
        return socketAddress;
    }

    @Override
    public ManagedChannel channel() {
        return channel;
    }

    @Override
    public void shutdown() {
        channel.shutdownNow();
    }

    @Override
    public String toString() {
        return "EndpointImpl{" +
                "socketAddress=" + socketAddress.getHostName() + ":" + socketAddress.getPort() +
                ", channel=" + channel +
                ", name='" + name + '\'' +
                '}';
    }
}
