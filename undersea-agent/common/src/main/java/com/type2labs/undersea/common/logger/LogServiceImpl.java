package com.type2labs.undersea.common.logger;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.logger.model.RingBuffer;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogServiceImpl implements LogService {

    private Agent agent;
    private final RingBuffer<LogEntry> ringBuffer = new RingBuffer<>();
    private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);
    private Map<Client, Integer> clientIndexMap = new ConcurrentHashMap<>();

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
    public synchronized void add(LogEntry logEntry) {
        ringBuffer.put(logEntry);
        logger.info(agent.name() + ": adding log message: " + logEntry, agent);
    }

    @Override
    public List<LogEntry> read(int index) {
        return ringBuffer.readBetween(index);
    }

    @Override
    public synchronized List<LogEntry> readNextForClient(Client client) {
        int index = clientIndexMap.computeIfAbsent(client, e -> 0);

        // If no new data to send
        if (index == size()) {
            return new ArrayList<>();
        }

        List<LogEntry> entries = ringBuffer.readBetween(index);
        clientIndexMap.put(client, size());

        return entries;
    }

    @Override
    public synchronized int size() {
        return ringBuffer.capacity() - ringBuffer.remainingCapacity();
    }

}
