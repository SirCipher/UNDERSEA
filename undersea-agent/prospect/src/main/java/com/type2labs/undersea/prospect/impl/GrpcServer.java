package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.service.RaftProtocolServiceImpl;
import com.type2labs.undersea.utilities.ExecutorUtils;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class GrpcServer implements Closeable {

    private static final Logger logger = LogManager.getLogger(GrpcServer.class);

    private final Server server;
    private final InetSocketAddress socketAddress;
    private final String name;

    public GrpcServer(RaftNode raftNode, InetSocketAddress socketAddress) {
        this.name = raftNode.name();
        ServerSocket serverSocket;
        int port;

        try {
            serverSocket = new ServerSocket(socketAddress.getPort());
            port = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.socketAddress = new InetSocketAddress(port);
        final ServerBuilder builder = NettyServerBuilder.forPort(port);

        int executorThreads = raftNode.config().executorThreads();

        ExecutorService handlerExecutor = ExecutorUtils.newExecutor(executorThreads, name + "-grpc-handler-%d");
        builder.addService(new RaftProtocolServiceImpl(raftNode, handlerExecutor));

        ExecutorService serverExecutor = ExecutorUtils.newExecutor(executorThreads, name + "-grpc-server-%d");
        this.server = builder.executor(serverExecutor).build();

        logger.info(name + ": running at " + socketAddress.getHostString() + ":" + port);
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public String name() {
        return name;
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
//                "socketAddress=" + socketAddress.getHostName() + ":" + socketAddress.getPort() +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void close() {
        logger.info(name + " shutting down gRPC server");
        server.shutdownNow();
        logger.info(name + " shutdown gRPC server");
    }
}
