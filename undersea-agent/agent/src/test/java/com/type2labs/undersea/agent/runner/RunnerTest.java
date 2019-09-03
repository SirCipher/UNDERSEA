package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.cost.CostConfigurationImpl;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.monitor.Visualiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
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

import java.util.Map;
import java.util.Properties;

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
        String testProperties = "src/test/resources/case-study-1/runner.properties";
        Runner runner = new Runner(testProperties);
        Visualiser visualiser = new Visualiser();

        runner.setup();
        runner.start();
        runner.onParsed(testProperties);

        while (!runner.missionComplete()) {
            Thread.sleep(500);
        }

        System.out.println("RunnerTest: Shutting down");

        runner.shutdown();
        visualiser.shutdown();
    }

}
