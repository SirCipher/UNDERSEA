package uuv.parser;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Created by Thomas Klapwijk on 2019-07-11.
 */
class ParserEngineTest {

    @Test
    void testParser() throws IOException {
        String[] args = {"resources/mission.config", "resources/sensors.config", "../UNDERSEA_Controller",
                "../moos-ivp-UNDERSEA/missions"};
        
        try {
            ParserEngine.main(args);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}