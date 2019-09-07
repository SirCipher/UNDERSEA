package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.service.ServiceManager;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public interface AgentAware {

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
