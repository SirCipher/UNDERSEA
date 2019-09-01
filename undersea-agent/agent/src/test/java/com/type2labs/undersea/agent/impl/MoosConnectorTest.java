package com.type2labs.undersea.agent.impl;

import com.type2labs.undersea.common.agent.AgentFactory;
import org.junit.Test;

public class MoosConnectorTest {

    @Test
    public void doTest() throws InterruptedException {
        MoosConnector moosConnector = new MoosConnector();
        moosConnector.initialise(new AgentFactory().create());
        moosConnector.run();

        System.out.println(moosConnector.write("SENSORS"));
        System.out.println(moosConnector.write("SENSORS"));
    }

}