package com.type2labs.undersea.prospect.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.ServerBuilder;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GrpcServer  {

    private static final Logger logger = LogManager.getLogger(GrpcServer.class);
    private final ExecutorService executor;
    private final Server server;
    private final InetSocketAddress socketAddress;
    private final String name;

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public GrpcServer(RaftNode raftNode, InetSocketAddress socketAddress) {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(socketAddress.getPort());
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to bind server socket", e);
        }

        this.name = raftNode.name();
        this.socketAddress = socketAddress;
        this.executor = Executors.newFixedThreadPool(4,
                new ThreadFactoryBuilder().setNameFormat(name + "-rpc-client-%d").build());
        this.server = ServerBuilder.build(socketAddress, raftNode, executor);

        logger.info(name + ": running at " + socketAddress.getHostString() + ":" + serverSocket.getLocalPort());
    }

    public String name() {
        return name;
    }

    public void shutdown() {
        server.shutdownNow();
    }

    public void start() {
        try {
            server.start();
        } catch (IOException e) {
            logger.error(name + " failed to start");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "GrpcServer{" +
                "socketAddress=" + socketAddress.getHostName() + ":" + socketAddress.getPort() +
                ", name='" + name + '\'' +
                '}';
    }

}
