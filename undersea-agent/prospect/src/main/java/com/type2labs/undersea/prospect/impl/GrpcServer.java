/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.service.MultiRaftProtocolService;
import com.type2labs.undersea.prospect.service.RaftProtocolService;
import com.type2labs.undersea.utilities.executor.ExecutorUtils;
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
import java.util.concurrent.TimeUnit;

@SuppressWarnings("PlaceholderCountMatchesArgumentCount")
public class GrpcServer implements Closeable {

    private static final Logger logger = LogManager.getLogger(GrpcServer.class);

    private final Server server;
    private final InetSocketAddress socketAddress;
    private final RaftNode parentNode;
    private final ExecutorService handlerExecutor;
    private final ExecutorService serverExecutor;

    /**
     * Creates a new gRPC server for the given {@link RaftNode} and listening on the provided socket address.
     * The server is initialised with a handler executor for services and a server executor for the {@link Server}.
     * Both are initialised using the number of threads defined in the
     * {@link RaftClusterConfig}
     *
     * @param raftNode      that this server belongs to
     * @param socketAddress to bind the server to. If the port is defined to be 0 then an attempt is made to discover
     *                      an available local port on the machine
     */
    public GrpcServer(RaftNode raftNode, InetSocketAddress socketAddress) {
        this.parentNode = raftNode;
        String agentName = raftNode.parent().name();
        ServerSocket serverSocket;
        int port;

        /*
            It may be possible that between getting the port and binding the Netty server that the port becomes
            unavailable. This should be improved in the future
         */
        try {
            serverSocket = new ServerSocket(socketAddress.getPort());
            port = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to bind to socket", e);
        }

        // If the provided port number was zero then the new port will be auto-discovered
        this.socketAddress = new InetSocketAddress(port);
        final ServerBuilder builder = NettyServerBuilder.forPort(port);

        int executorThreads = raftNode.config().executorThreads();

        handlerExecutor = ExecutorUtils.newExecutor(4, agentName + "-grpc-handler-%d");
        builder.addService(new RaftProtocolService(raftNode, handlerExecutor));

        // TODO: This service should be started and shutdown as required instead of always running
        builder.addService(new MultiRaftProtocolService(raftNode, handlerExecutor));

        serverExecutor = ExecutorUtils.newExecutor(4, agentName + "-grpc-server-%d");
        this.server = builder.executor(serverExecutor).build();

        logger.info(agentName + ": gRPC server available at  " + socketAddress.getHostString() + ":" + port,
                raftNode.parent());
    }

    /**
     * Returns the {@link InetSocketAddress} that this server has binded to. This may be different to the initial
     * socket address that was provided originally if the port was 0
     *
     * @return the address of the server
     */
    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void start() {
        try {
            server.start();
        } catch (IOException e) {
            logger.error(parentNode.parent().name() + " failed to start. Attempted to use port: " + socketAddress.getPort(),
                    parentNode.parent());
            e.printStackTrace();
            throw new RuntimeException("Failed to start", e);
        }
    }

    @Override
    public String toString() {
        return "GrpcServer{" +
                "socketAddress=" + socketAddress.getHostName() + ":" + socketAddress.getPort() +
                '}';
    }

    /**
     * Indicates that the server should stop processing any new requests
     */
    @Override
    public void close() {
        handlerExecutor.shutdownNow();
        serverExecutor.shutdownNow();

        String name = parentNode.parent().name();

        logger.info(name + ": shutting down gRPC server", parentNode.parent());

        try {
            server.shutdownNow().awaitTermination(10, TimeUnit.SECONDS);
            logger.info(name + ": shutdown gRPC server", parentNode.parent());
        } catch (InterruptedException e) {
            logger.error(name + ": failed to gracefully shutdown gRPC server", parentNode.parent());
        }
    }

}
