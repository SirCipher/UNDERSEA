package com.type2labs.undersea.controller.controller.comms;


import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressWarnings("PlaceholderCountMatchesArgumentCount")
public class Client {

    private static final Logger logger = LogManager.getLogger(Client.class);
    private PrintWriter out;
    private BufferedReader in;
    private Agent agent;

    public Client(Agent agent) {
        this.agent = agent;

        connectToServer();
    }

    public static void main(String[] args) {
        new Client(null);
    }

    private void connectToServer() {
        int retries = 10;
        int port = (int) agent.metadata().getProperty(AgentMetaData.PropertyKey.HARDWARE_PORT);
        Exception exception = null;

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

    public boolean shutDown() throws IOException {
        String SHUT_DOWN_STR = "###";
        String inputStr = write(SHUT_DOWN_STR);

        logger.info(agent.name() + ": writing: " + SHUT_DOWN_STR + ". received back: " + inputStr, agent);

        return true;//inputStr.equals(SHUT_DOWN_STR);
    }

    public String write(String outputStr) throws IOException {
        out.println(outputStr);
        out.flush();
        return in.readLine();
    }

}
