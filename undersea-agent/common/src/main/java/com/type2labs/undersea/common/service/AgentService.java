package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public interface AgentService extends Runnable {

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

    /**
     * Sets the parent agent that this service belongs to and invokes initialisation on the service. Some services
     * require an agent to be set before they can be initialised and thus not all initialisation can be done in the
     * constructor.
     * <p>
     * This is invoked by the {@link ServiceManager} before {@link Runnable#run()} to ensure that the service is
     * fully initialised beforehand.
     * <p>
     * Implementors should disallow reinitialisation.
     *
     * @param parentAgent to associate with the service
     */
    void initialise(Agent parentAgent);

    /**
     * The agent that this service is associated with
     *
     * @return the associated agent
     */
    Agent parent();


}
