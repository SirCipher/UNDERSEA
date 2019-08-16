package com.type2labs.undersea.common.networking;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class EndpointImpl implements Endpoint {

    private static final Logger logger = LogManager.getLogger(EndpointImpl.class);

    private final InetSocketAddress socketAddress;
    private final ManagedChannel channel;
    private final ServerSocket serverSocket;
    private final String name;


    public EndpointImpl(String name, InetSocketAddress socketAddress) {
        try {
            this.serverSocket = new ServerSocket(socketAddress.getPort());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to bind server socket", e);
        }

        this.name = name;
        this.channel = ManagedChannelBuilder.forAddress(socketAddress.getHostName(), serverSocket.getLocalPort())
                .usePlaintext()
                .build();
        this.socketAddress = socketAddress;

        logger.info(name + ": created endpoint at: " + socketAddress.getHostString() + ":" + serverSocket.getLocalPort());
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
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServerSocket serverSocket() {
        return serverSocket;
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
