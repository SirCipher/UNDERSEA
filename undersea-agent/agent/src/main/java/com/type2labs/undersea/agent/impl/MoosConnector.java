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

package com.type2labs.undersea.agent.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Connector for the c++ server/client
 */
@NotThreadSafe
public class MoosConnector implements NetworkInterface {

    private static final Logger logger = LogManager.getLogger(MoosConnector.class);
    private final ThrowableExecutor clientProcessingPool = ThrowableExecutor.newSingleThreadExecutor(parent(), logger);
    private Agent agent;
    private boolean aquiredConnection = false;
    private boolean shutdown;

    private Socket connectToServer() {
        int retries = 20;
        int port = (int) agent.metadata().getProperty(AgentMetaData.PropertyKey.HARDWARE_PORT);
        Exception exception = null;

        for (int i = 0; i < retries; i++) {
            try {
                Socket socket = new Socket("localhost", port);
                aquiredConnection = true;

                return socket;
            } catch (IOException e) {
                exception = e;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        throw new UnderseaException("Failed to connect to server", exception);
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    public void listenOnInbound() {
        Thread serverThread = new Thread(() -> {
            try {
                int port = (int) agent.metadata().getProperty(AgentMetaData.PropertyKey.INBOUND_HARDWARE_PORT);
                ServerSocket serverSocket = new ServerSocket(port);
                logger.info(agent.name() + ": initialised MOOS connector inbound server", agent);
                MissionManager missionPlanner = agent.serviceManager().getService(MissionManager.class);

                while (!shutdown) {
                    Socket clientSocket = serverSocket.accept();

                    // Shutdown may have already occurred but we haven't caught it as we've been blocked
                    if (shutdown) {
                        clientSocket.close();
                        return;
                    }

                    clientProcessingPool.execute(() -> {
                        try {
                            InputStream is = clientSocket.getInputStream();
                            BufferedReader in = new BufferedReader(new InputStreamReader(is));
                            String rcv = in.readLine();

                            logger.trace(agent.name() + ": received {" + rcv + "} on inbound", agent);

                            missionPlanner.notify(rcv);
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                logger.info(agent.name() + ": shutdown inbound server", agent);
            } catch (IOException e) {
                logger.error("Unable to process client request", e);
                e.printStackTrace();
            }
        });

        serverThread.start();
    }

    /**
     * Run during initialisation of this class and attempts to open a connection with the sUUV module. If a
     * connection is established, then an {@code ACQ} message is sent to the module and {@code ACQ} is expected to be
     * returned. If this is not the case, then this service is terminated.
     * <p>
     * During initialisation of the {@link com.type2labs.undersea.common.service.ServiceManager} this class is given
     * {@link MoosConnector#transitionTimeout()} to start and if this service fails, then it is terminated and so the
     * socket blocking does not become an issue.
     */
    @Override
    public void run() {
        Socket socket = connectToServer();
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
            out.print("ACQ");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String res = in.readLine();

            if (!"ACQ".equals(res)) {
                throw new RuntimeException("Failed to receive server acknowledgement");
            }

            logger.info(agent.name() + ": received server acknowledgement", agent);

            in.close();
            out.close();
            socket.close();

            listenOnInbound();
        } catch (IOException e) {
            throw new RuntimeException(agent.name() + ": failed to close resources during initialisation", e);
        }
    }

    @Override
    public void shutdown() {
        write("SHUTDOWN");

        shutdown = true;
        clientProcessingPool.shutdownNow();
    }

    @Override
    public boolean started() {
        return aquiredConnection;
    }

    @Override
    public long transitionTimeout() {
        return 30000;
    }

    @Override
    public synchronized String write(String message) {
        logger.info(agent.name() + ": sending: " + message);
        Socket socket = connectToServer();
        PrintWriter out;
        BufferedReader in;

        try {
            out = new PrintWriter(socket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        out.print(message);
        out.flush();

        String response;

        try {
            response = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UnderseaLogger.info(logger, agent, "received: " + response);

        return response;
    }
}
