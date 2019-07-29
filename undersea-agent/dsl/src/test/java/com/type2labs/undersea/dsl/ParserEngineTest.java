package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.model.AgentProxy;
import com.type2labs.undersea.missionplanner.model.node.Node;
import com.type2labs.undersea.utilities.Utility;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Thomas Klapwijk on 2019-07-11.
 */
public class ParserEngineTest {

    @Test
    public void testParser() {
        try {
            Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
            ParserEngine parserEngine = new ParserEngine(properties);
            parserEngine.parseMission();

            EnvironmentProperties environmentProperties = parserEngine.getEnvironmentProperties();

            for(Map.Entry<String, AgentProxy> entry: environmentProperties.getAllAgents().entrySet()){
                AgentProxy agentProxy = entry.getValue();

                agentProxy.assignNode(Node.randomLocalNode(0.0,150.0, -140.0,0.0));
                agentProxy.assignNode(Node.randomLocalNode(0.0,150.0, -140.0,0.0));
                agentProxy.assignNode(Node.randomLocalNode(0.0,150.0, -140.0,0.0));
                agentProxy.assignNode(Node.randomLocalNode(0.0,150.0, -140.0,0.0));
                agentProxy.assignNode(Node.randomLocalNode(0.0,150.0, -140.0,0.0));
            }

            parserEngine.generateFiles();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}