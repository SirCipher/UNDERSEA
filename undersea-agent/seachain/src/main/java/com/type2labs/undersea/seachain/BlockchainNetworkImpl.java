package com.type2labs.undersea.seachain;

import com.type2labs.undersea.common.blockchain.BlockchainNetwork;
import com.type2labs.undersea.common.service.Transaction;

import java.util.concurrent.ScheduledFuture;

public class BlockchainNetworkImpl implements BlockchainNetwork {

    @Override
    public void shutdown() {

    }

    @Override
    public ScheduledFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }


    @Override
    public void run() {
    }

}
