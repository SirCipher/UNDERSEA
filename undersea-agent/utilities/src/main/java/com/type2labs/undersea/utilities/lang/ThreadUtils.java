package com.type2labs.undersea.utilities.lang;

import org.apache.logging.log4j.Logger;

public class ThreadUtils {

    public static void sleep(long ms) {
        sleep(ms, null);
    }

    public static void sleep(long ms, Logger logger) {
        boolean log = logger != null;

        if (log) {
            logger.info("Sleeping");
        }

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            if (log) {
                logger.error("Failed to sleep", e);
            }
            throw new RuntimeException(e);
        }

        if (log) {
            logger.info("Waking up");
        }

    }


}
