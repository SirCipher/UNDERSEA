package com.type2labs.undersea.common.monitor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.Transaction;

import java.util.concurrent.ScheduledFuture;

public class MonitorImpl implements Monitor {

    private VisualiserClient visualiserClient = new NoVisualiser();

    @Override
    public void update() {
        visualiserClient.update();
    }

    @Override
    public VisualiserClient visualiser() {
        return visualiserClient;
    }

    @Override
    public void setVisualiser(VisualiserClient visualiserClient) {
        this.visualiserClient = visualiserClient;
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
