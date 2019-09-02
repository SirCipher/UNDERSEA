package com.type2labs.undersea.agent.impl;

import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import org.junit.Test;

public class MoosConnectorTest {

    @Test
    @IgnoredOnCi
    public void doTest() throws InterruptedException {
        MoosConnector moosConnector = new MoosConnector();
        moosConnector.initialise(new AgentFactory().create());
        moosConnector.listenOnInbound();

        Thread.sleep(10000);

    }

}