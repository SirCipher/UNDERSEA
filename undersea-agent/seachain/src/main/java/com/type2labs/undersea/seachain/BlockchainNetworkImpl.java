package com.type2labs.undersea.seachain;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.blockchain.BlockchainNetwork;
import com.type2labs.undersea.common.service.transaction.Transaction;

public class BlockchainNetworkImpl implements BlockchainNetwork {

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
