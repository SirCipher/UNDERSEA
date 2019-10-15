/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.logger;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.logger.model.RingBuffer;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of the {@link LogService} which fires {@link Transaction}s to the
 * {@link LogEntry#getAgentService()} upon appending entries. {@link LogEntry}s are broadcast by the
 * {@link com.type2labs.undersea.common.consensus.ConsensusAlgorithm} leader during heartbeats and as such, follower
 * {@link com.type2labs.undersea.common.service.AgentService} need to keep their states up-to-date. This
 * implementation ensures that services are.
 */
public class LogServiceImpl implements LogService {

    private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);
    private final RingBuffer<LogEntry> ringBuffer = new RingBuffer<>();

    private final Map<Client, Integer> clientIndexMap = new ConcurrentHashMap<>();
    private Agent agent;
    private ServiceManager serviceManager;

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.serviceManager = agent.serviceManager();
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

    /**
     * Returns the end index for a batch. Either the end of the ring buffer or the current index + the batch size
     *
     * @param startIndex last read
     * @return the new index
     */
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

    /**
     * Upon receiving a number of log entries, notify the corresponding
     * {@link com.type2labs.undersea.common.service.AgentService}s that {@link LifecycleEvent#APPEND_REQUEST} were
     * received
     *
     * @param logEntries to append
     */
    @Override
    public void appendEntries(List<LogEntry> logEntries) {
        logEntries.forEach(e -> {
            ringBuffer.put(e);

            Transaction transaction = transactionFromEntry(e);

            for (ListenableFuture<?> future : serviceManager.commitTransaction(transaction)) {
                Futures.addCallback(future, new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nullable Object result) {
                        logger.info(agent.name() + ": appending log entry: " + e.toString(), agent);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        throw new RuntimeException(t);
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
