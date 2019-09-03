package com.type2labs.undersea.common.logger;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogServiceImpl implements LogService {

    private Agent agent;
    private final RingBuffer<LogEntry> ringBuffer = new RingBuffer<>();
    private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);

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
    public void registerCallback(ServiceCallback serviceCallback) {

    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

    @Override
    public void add(LogEntry logEntry) {
        ringBuffer.put(logEntry);
        logger.info(agent.name() + ": adding log message: " + logEntry, agent);
    }

    @Override
    public LogEntry read(int index) {
        return ringBuffer.read(index);
    }

    @Override
    public int size() {
        return ringBuffer.capacity() - ringBuffer.remainingCapacity();
    }

}
