package com.type2labs.undersea.benchmark;

import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.Test;

import java.util.List;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;
import static org.junit.Assert.assertNotNull;

public class Benchmark {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.WARN);
        ctx.updateLoggers();
    }

    private void warmup() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            createAndWait(3);

            System.gc();
        }
    }

    private void createAndWait(int size) {
        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(size)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> assertNotNull(localAgentGroup.getLeaderNode()), 120);
        }
    }

    @Test
    public void test_3() {
        warmup();
    }

}
