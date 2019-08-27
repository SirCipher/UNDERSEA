package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
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

    /**
     * The Java process system does not kill child processes that have been launched and even though the
     * pMarineViewer is still open, the {@link Process#isAlive()} method returns false after it has been closed.
     * Unfortunately, this results in a number of child processes remaining afterwards. So the only alternative (at
     * present) is to kill all processes matching {@code '.moos'}. This will forcibly kill all processes matching
     * the pattern. Depending on the {@code net.ipv4.tcp_fin_timeout} set, the port may linger after the process has
     * been killed before it can be reused again. Using {@link java.net.ServerSocket#setReuseAddress(boolean)}
     * (SO_REUSEADDR) will reduce this time on sockets.
     * <p>
     * See <a href="https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4770092">Java bug database</a>
     * <p>
     * See
     * <a href="https://unix.stackexchange.com/questions/294616/unbind-port-of-crashed-program">For port release on Linux</a>"
     */
    @After
    public void killMoos() {
        try {
            logger.info("Terminating processes matching .moos");
            Runtime.getRuntime().exec("pkill -f .moos -9");
            logger.info("Terminated processes matching .moos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void runShoreside() throws IOException {
        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command("pAntler", "meta_shoreside.moos");
        pb.directory(new File("../missions/test_01/"));

        Process process = pb.start();
        Runtime.getRuntime().addShutdownHook(new Thread(process::destroyForcibly));
    }

    private void runAlpha() throws IOException {
        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command("pAntler", "meta_vehicle_alpha.moos");
        pb.directory(new File("../missions/test_01/"));

        Process process = pb.start();
        Runtime.getRuntime().addShutdownHook(new Thread(process::destroyForcibly));
    }

    @Test
    @IgnoredOnCi
    public void testMultiple() {
        try {
            runShoreside();
            ThreadUtils.sleep(1000);
            runAlpha();
            ThreadUtils.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void testRunner() throws InterruptedException {
        String testProperties = "src/test/resources/test.properties";

        Runner runner = new Runner(testProperties);
        runner.run();

        ThreadUtils.sleep(10000, logger);

        runner.shutdown();
    }

}
