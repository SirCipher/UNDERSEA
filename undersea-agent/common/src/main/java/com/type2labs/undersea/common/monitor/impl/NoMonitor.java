package com.type2labs.undersea.common.monitor;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.Transaction;

import java.util.concurrent.Future;

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