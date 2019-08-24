package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.exception.ServiceNotRegisteredException;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class HardwareInterface implements AgentService {

    private Agent agent;
    private MoosConnector moosConnector;

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.moosConnector = agent.services().getService(MoosConnector.class);

        if (moosConnector == null) {
            throw new ServiceNotRegisteredException(MoosConnector.class, HardwareInterface.class);
        }
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }

}
