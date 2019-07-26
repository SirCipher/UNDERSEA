package com.type2labs.undersea.dsl;

import com.type2labs.undersea.utilities.Utility;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * Created by Thomas Klapwijk on 2019-07-11.
 */
class ParserEngineTest {

    @Test
    void testParser() {
        try {
            Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
            ParserEngine parserEngine = new ParserEngine(properties);
            parserEngine.parseMission();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}