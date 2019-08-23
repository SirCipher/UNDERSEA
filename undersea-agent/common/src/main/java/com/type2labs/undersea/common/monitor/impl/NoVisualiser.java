package com.type2labs.undersea.common.monitor.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.transaction.Transaction;

import java.io.IOException;

public class NoVisualiser implements VisualiserClient {

    @Override
    public void write(String data) throws IOException {

    }

    @Override
    public void write(LogMessage data) throws IOException {

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
