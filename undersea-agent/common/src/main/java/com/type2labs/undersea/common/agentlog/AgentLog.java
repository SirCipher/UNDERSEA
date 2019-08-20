package com.type2labs.undersea.common.agentlog;

import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.Transaction;

import java.util.concurrent.ScheduledFuture;

// TODO: 20/08/2019 implement ring buffer
public class AgentLog implements AgentService {


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
