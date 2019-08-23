package com.type2labs.undersea.common.agentlog;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.transaction.Transaction;

// TODO: 20/08/2019 implement ring buffer
public class AgentLog implements AgentService {


    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }

    @Override
    public void run() {

    }

}
