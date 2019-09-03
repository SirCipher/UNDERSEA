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

    private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);
    private final RingBuffer<LogEntry> ringBuffer = new RingBuffer<>();

    private Map<Client, Integer> clientIndexMap = new ConcurrentHashMap<>();
    private Agent agent;

    @Override
    public void shutdown() {

    }

    @Override
    public boolean started() {
        return true;
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
    public List<LogEntry> read(int index) {
        return ringBuffer.readBetween(index, getEndIndex(index));
    }

    private int getEndIndex(int index) {
        int batchSize = agent.config().getLogBatchSize();

        if (index + batchSize > size()) {
            return size() - 1;
        } else {
            return index;
        }
    }

    @Override
    public synchronized List<LogEntry> readNextForClient(Client client) {
        int index = clientIndexMap.computeIfAbsent(client, e -> 0);

        // If no new data to send
        if (index == size()) {
            return new ArrayList<>();
        }

        int endIndex = getEndIndex(index);
        List<LogEntry> entries = ringBuffer.readBetween(index, endIndex);
        clientIndexMap.put(client, endIndex);

        return entries;
    }

    @Override
    public void appendEntries(List<LogEntry> logEntries) {

    }

    @Override
    public synchronized int size() {
        return ringBuffer.capacity() - ringBuffer.remainingCapacity();
    }

}
