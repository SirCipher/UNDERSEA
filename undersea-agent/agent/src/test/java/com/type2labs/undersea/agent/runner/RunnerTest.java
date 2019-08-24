package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.utilities.process.ProcessBuilderUtil;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RunWith(UnderseaRunner.class)
public class RunnerTest {

    @Test
    public void ignored() {

    }

    @Test
    @IgnoredOnCi
    public void test() throws IOException, InterruptedException {
        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command("/Volumes/MiniMudkipz/dev/PACS/moos-ivp/bin/pMarineViewer", "meta_shoreside.moos");
        pb.directory(new File("../missions/test_01/"));

        Process process = pb.start();

        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        Thread.sleep(10000);
    }

    @Test
    @IgnoredOnCi
    public void testRunner() throws InterruptedException {
        String testProperties = "src/test/resources/test.properties";

        Runner runner = new Runner(testProperties);
        runner.run();

        Thread.sleep(10000);

        runner.shutdown();
    }

}
