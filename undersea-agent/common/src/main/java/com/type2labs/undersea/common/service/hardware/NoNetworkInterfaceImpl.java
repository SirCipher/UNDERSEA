package com.type2labs.undersea.common.service.hardware;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;

public class NoNetworkInterfaceImpl implements NetworkInterface {

    private Agent parentAgent;

    @Override
    public String read() {
        return "";
    }

    @Override
    public void write(String message) {

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
        throw new NotSupportedException();
    }

    @Override
    public long transitionTimeout() {
        return 0;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.parentAgent = parentAgent;
    }

    @Override
    public Agent parent() {
        return parentAgent;
    }

    @Override
    public void run() {

    }
}
