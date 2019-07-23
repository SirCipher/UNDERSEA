package com.type2labs.undersea.controller.uuv.parser;

import com.type2labs.undersea.dsl.ParserEngine;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Created by Thomas Klapwijk on 2019-07-11.
 */
class ParserEngineTest {

    @Test
    void testParser() {
        String[] args = {"resources/mission.config", "resources/sensors.config", "../UNDERSEA_Controller",
                "missions", "resources/config.properties", "missions", ".." + File.separator + "mission-includes"};
//                "../moos-ivp-UNDERSEA/missions/uuvExemplar"};

        try {
            ParserEngine.main(args);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}