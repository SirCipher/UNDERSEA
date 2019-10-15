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

package com.type2labs.undersea.utilities.networking;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleServer {

    private final Logger logger;
    private final ServerCallback serverCallback;
    private final int port;
    private final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
    private boolean shutdown = false;

    public SimpleServer(int port, ServerCallback serverCallback, Logger logger) {
        this.port = port;
        this.serverCallback = serverCallback;
        this.logger = logger;
    }

    public void shutdown() {
        this.shutdown = true;
        clientProcessingPool.shutdown();
    }

    public void runServer() {
        Thread serverThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while (!shutdown) {
                    Socket clientSocket = serverSocket.accept();
                    clientProcessingPool.execute(() -> serverCallback.onNewConnection(clientSocket));
                }

                logger.info("Shutting down server");
            } catch (IOException e) {
                logger.error("Unable to process client request", e);
                e.printStackTrace();
            }
        });

        serverThread.start();
    }

    public interface ServerCallback {

        void onNewConnection(Socket clientSocket);

    }

}
