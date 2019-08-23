package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionData;

import java.util.Objects;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class MoosConnector implements AgentService {

    @Override
    public void run() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        transaction = Objects.requireNonNull(transaction);

        TransactionData transactionData;

        if(transaction.hasTransactionData()){
            transactionData = transaction.getTransactionData();
        }

        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }

}
