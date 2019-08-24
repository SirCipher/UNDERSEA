package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class MoosConnector implements NetworkInterface {

    private Agent agent;

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public String read(String request) {
        return "";
    }

    @Override
    public void write(String message) {

    }

    @Override
    public void run() {

    }

    @Override
    public void shutdown() {
        //close socket
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }
}
