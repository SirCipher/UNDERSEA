package com.type2labs.undersea.agent.consensus.util;

public class TestUtil {



    public static void assertTrueEventually(Runnable task, long timeoutSeconds) {
        AssertionError assertionError = null;

        int sleepMillis = 200;
        long iterations = timeoutSeconds * 5;

        for (int i = 0; i < iterations; i++) {
            try {
                task.run();
                return;
            } catch (AssertionError e) {
                assertionError = e;
            }

            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (assertionError != null) {
            throw assertionError;
        }
    }
}
