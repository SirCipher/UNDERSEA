package com.type2labs.undersea.common.logger;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.logger.model.RingBuffer;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionData;
import com.type2labs.undersea.utilities.concurrent.SimpleFutureCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogServiceImpl implements LogService {

    private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);
    private final RingBuffer<LogEntry> ringBuffer = new RingBuffer<>();

    private Map<Client, Integer> clientIndexMap = new ConcurrentHashMap<>();
    private Agent agent;
    private ServiceManager serviceManager;

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
        this.serviceManager = agent.services();
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

    private int getEndIndex(int startIndex) {
        int batchSize = agent.config().getLogBatchSize();

        if (startIndex + batchSize > size()) {
            return size() - 1;
        } else {
            return startIndex + batchSize;
        }
    }

    @Override
    public synchronized List<LogEntry> readNextForClient(Client client) {
        int startIndex = clientIndexMap.computeIfAbsent(client, e -> 0);

        // If no new data to send
        if (startIndex == size()) {
            return new ArrayList<>();
        }

        int endIndex = getEndIndex(startIndex);
        List<LogEntry> entries = ringBuffer.readBetween(startIndex, endIndex);
        clientIndexMap.put(client, endIndex);

        return entries;
    }

    private Transaction transactionFromEntry(LogEntry logEntry) {
        return new Transaction.Builder(agent)
                .invokedBy(this)
                .forService(logEntry.getAgentService().getClass())
                .withStatus(LifecycleEvent.APPEND_REQUEST)
                .withPrimaryData(TransactionData.from(logEntry.getData()))
                .withSecondaryData(TransactionData.from(logEntry.getValue()))
                .build();
    }

    @Override
    public void appendEntries(List<LogEntry> logEntries) {
        logEntries.forEach(e -> {
            ringBuffer.put(e);

            Transaction transaction = transactionFromEntry(e);

            for (ListenableFuture<?> future : serviceManager.commitTransaction(transaction)) {
                Futures.addCallback(future, new SimpleFutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nullable Object result) {
                        logger.info(agent.name() + ": appending log entry: " + e.toString(), agent);
                    }

                }, e.getAgentService().transactionExecutor());
            }
        });
    }

    @Override
    public synchronized int size() {
        return ringBuffer.capacity() - ringBuffer.remainingCapacity();
    }

}
