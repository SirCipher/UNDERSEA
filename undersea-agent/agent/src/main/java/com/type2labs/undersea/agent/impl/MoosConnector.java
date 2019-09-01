package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
@NotThreadSafe
public class MoosConnector implements NetworkInterface {

    private static final Logger logger = LogManager.getLogger(MoosConnector.class);

    private Agent agent;
    private boolean aquiredConnection = false;
    private ThrowableExecutor executor = ThrowableExecutor.newSingleThreadExecutor(logger);

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    private void listenOnInbound() {
        executor.submit(() -> {
            /*
             * todo: open another port and pass the input to the task executor service to handle
             */
//            int port = (int) agent.metadata().getProperty(AgentMetaData.PropertyKey.INBOUND_HARDWARE_PORT);
            int port = 9080;
            try {
                Socket socket = new Socket("localhost", port);
                System.out.println("Connected to inbound");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void doWithRetries() {

    }

    private Socket connectToServer() {
        int retries = 20;
//        int port = (int) agent.metadata().getProperty(AgentMetaData.PropertyKey.HARDWARE_PORT);
        int port = 9080;
        Exception exception = null;

        logger.info(agent.name() + ": connecting to MOOS server on: " + port, agent);

        for (int i = 0; i < retries; i++) {
            try {
                Socket socket = new Socket("localhost", port);
                aquiredConnection = true;

                logger.info(agent.name() + ": connected to MOOS server", agent);

//                listenOnInbound();

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
    public Agent parent() {
        return agent;
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        out.print(message);
        out.flush();

        String response;
        try {
            response = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info(agent.name() + ": received: " + response);

        return response;
    }

    @Override
    public void run() {
        Socket socket = connectToServer();
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
            out.print("ACQ");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String res = in.readLine();

            if(!"ACQ".equals(res)){
                throw new RuntimeException("Failed to receive server acknowledgement");
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(agent.name() + ": failed to close socket during initialisation", e);
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public long transitionTimeout() {
        return 10000;
    }

    @Override
    public boolean started() {
        return aquiredConnection;
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }
}
