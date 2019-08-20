package com.type2labs.undersea.common.service;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public interface AgentService extends Runnable {

    void shutdown();

    /**
     * Executes a transaction on the service and returns the result
     *
     * @param transaction to execute
     * @return scheduled future
     */
    ScheduledFuture<?> executeTransaction(Transaction transaction);

}
