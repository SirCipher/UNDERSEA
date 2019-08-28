package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.exception.UnderseaException;
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
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    private void connectToServer() {
        int retries = 10;
        int port = (int) agent.metadata().getProperty(AgentMetaData.PropertyKey.HARDWARE_PORT);
        Exception exception = null;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < retries; i++) {
            try {
                Socket socket = new Socket("localhost", port);

                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                logger.info(agent.name() + ": connected to MOOS server", agent);

                return;
            } catch (IOException e) {
                // Excuse the first few attempts as the server may not have started yet
                if (i > 5) {
                    logger.warn(agent.name() + ": failed to connect to server, retrying", e);
                    exception = e;
                }
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
    public String read() {
        try {
            return in.readLine();
        } catch (IOException e) {
            logger.error(agent.name() + ": failed to read message", e);
            return "ERROR";
        }
    }

    @Override
    public void write(String message) {
        out.println(message);
        out.flush();
    }

    @Override
    public void run() {
        connectToServer();
    }

    @Override
    public void shutdown() {
        String SHUT_DOWN_STR = "###";
        String inputStr = read();

        logger.info(agent.name() + ": writing: " + SHUT_DOWN_STR + ". received back: " + inputStr, agent);
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }
}
