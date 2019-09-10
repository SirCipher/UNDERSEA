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
