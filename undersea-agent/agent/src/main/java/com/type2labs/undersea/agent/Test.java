package com.type2labs.undersea.agent;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.monitor.Visualiser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class Test {

    public static void main(String[] args) {
        new Visualiser();

        AgentFactory agentFactory = new AgentFactory();

        for (int i = 0; i < 5; i++) {
            VisualiserClientImpl client = new VisualiserClientImpl();
            Agent agent = agentFactory.create();
            agent.config().enableVisualiser(true);

            agent.services().registerService(client);
            agent.services().startServices();
        }
    }

}
