package com.type2labs.undersea.agent.impl;

import com.type2labs.undersea.common.agent.AgentFactory;
import org.junit.Test;
import org.junit.platform.commons.support.ModifierSupport;

import static org.junit.Assert.*;

public class MoosConnectorTest {

    @Test
    public void doTest() throws InterruptedException {
        MoosConnector moosConnector = new MoosConnector();
        moosConnector.initialise(new AgentFactory().create());
        moosConnector.run();

        moosConnector.write("Test");

        Thread.sleep(1000);
    }

}