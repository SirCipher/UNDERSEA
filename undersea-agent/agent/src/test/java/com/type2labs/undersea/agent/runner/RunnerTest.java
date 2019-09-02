package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.monitor.Visualiser;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(UnderseaRunner.class)
public class RunnerTest {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

    @Test
    public void ignored() {

    }

    @After
    public void killMoos() {
        Utility.killMoos();
    }

    @Test
    @IgnoredOnCi
    public void testRunner() throws InterruptedException {
        String testProperties = "src/test/resources/test.properties";
        Visualiser visualiser = new Visualiser();
        Runner runner = new Runner(testProperties);

        runner.setup();
        runner.start();

        runner.onParsed(testProperties);

        // TODO: 02/09/2019
//        while (!runner.missionComplete()){
//            Thread.sleep(500);
//        }
        
        
        Thread.sleep(30000);

        System.out.println("RunnerTest: Shutting down");

        runner.shutdown();
        visualiser.shutdown();
    }

}
