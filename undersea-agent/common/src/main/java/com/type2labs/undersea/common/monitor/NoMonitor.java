package com.type2labs.undersea.common.monitor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.Transaction;

import java.util.concurrent.ScheduledFuture;

public class NoMonitor implements Monitor {

    @Override
    public void update() {

    }

    @Override
    public VisualiserClient visualiser() {
        return new NoVisualiser();
    }

    @Override
    public void setVisualiser(VisualiserClient visualiser) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public ScheduledFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public void run() {

    }
}
