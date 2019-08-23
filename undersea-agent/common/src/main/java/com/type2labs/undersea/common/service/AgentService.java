package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentAware;
import com.type2labs.undersea.common.service.transaction.Transaction;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public interface AgentService extends Runnable, AgentAware {

    /**
     * Signals that the service should start the shutdown procedure.
     */
    void shutdown();

    /**
     * Executes a transaction on the service and returns the result
     *
     * @param transaction to execute
     * @return scheduled future
     */
    ListenableFuture<?> executeTransaction(Transaction transaction);

}
