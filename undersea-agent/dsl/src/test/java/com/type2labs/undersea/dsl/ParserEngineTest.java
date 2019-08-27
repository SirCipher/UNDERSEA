package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
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
            Properties properties = Utility.getPropertiesByName("src/test/resources/test.properties");
            ParserEngine parserEngine = new ParserEngine(properties);
            parserEngine.parseMission();

            EnvironmentProperties environmentProperties = parserEngine.getEnvironmentProperties();

            for (Map.Entry<String, DslAgentProxy> entry : environmentProperties.getAllAgents().entrySet()) {
                DslAgentProxy agentProxy = entry.getValue();
                agentProxy.assignRandomNodes(20, 0.0, 150.0, -140.0, 0.0);
            }

            parserEngine.generateFiles();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}