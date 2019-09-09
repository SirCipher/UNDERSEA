package com.type2labs.undersea.agent.monitor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.monitor.Visualiser;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import org.junit.Test;

public class VisualiserClientImplTest {

    @Test
    @IgnoredOnCi
    public void doTest() throws InterruptedException {
        new Visualiser();

        VisualiserClientImpl client = new VisualiserClientImpl();
        Agent agent = new AgentFactory().create();
        agent.config().enableVisualiser(true);

        client.initialise(agent);
        client.run();

        Thread.sleep(10000);
    }

}