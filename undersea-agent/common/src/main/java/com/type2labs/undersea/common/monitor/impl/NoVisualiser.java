package com.type2labs.undersea.common.monitor.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.logger.VisualiserMessage;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class NoVisualiser implements VisualiserClient {

    @Override
    public void write(String data) throws IOException {

    }

    @Override
    public void write(VisualiserMessage data) throws IOException {

    }

    @Override
    public void closeConnection() throws IOException {

    }

    @Override
    public void update() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean started() {
        return true;
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public long transitionTimeout() {
        return 0;
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {

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
