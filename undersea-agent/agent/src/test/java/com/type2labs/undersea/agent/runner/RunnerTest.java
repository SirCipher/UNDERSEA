package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.lang.ThreadUtils;
import com.type2labs.undersea.utilities.process.ProcessBuilderUtil;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RunWith(UnderseaRunner.class)
public class RunnerTest {

    private static final Logger logger = LogManager.getLogger(RunnerTest.class);

    @Test
    public void ignored() {

    }

    @After
    public void killMoos() {
        Utility.killMoos();
    }


    @Test
    @IgnoredOnCi
    public void testMultiple() throws InterruptedException, IOException {
        launchAgent("./shoreside.sh");
        launchAgent("./alpha.sh");
        launchAgent("./bravo.sh");
        launchAgent("./charlie.sh");


        Thread.sleep(10000);
    }

    private void launchAgent(String script) throws IOException {
        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command(script);
        pb.directory(new File("../missions/test_01/"));

        Process process = pb.start();
        Runtime.getRuntime().addShutdownHook(new Thread(process::destroyForcibly));
    }

    @Test
    @IgnoredOnCi
    public void test() throws IOException, InterruptedException {
        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command("pAntler", "meta_shoreside.moos");
        pb.directory(new File("../missions/test_01/"));

        Process process = pb.start();

        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        Thread.sleep(100);

        process.destroyForcibly();
    }


    @Test
    @IgnoredOnCi
    public void testRunner() {
        String testProperties = "src/test/resources/test.properties";

        Runner runner = new Runner(testProperties);
        runner.run();

//        ThreadUtils.sleep(20000, logger);


        runner.shutdown();
    }

}
