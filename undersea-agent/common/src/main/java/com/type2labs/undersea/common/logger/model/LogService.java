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

package com.type2labs.undersea.common.logger.model;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

/**
 * A logging service that stores {@link LogEntry}s that have been created throughout the lifetime of this agent.
 * Implementations should ensure that read and write markers are stored for clients for when entries are propagated
 * to the cluster.
 */
public interface LogService extends AgentService {

    /**
     * Add an entry to the underlying data storage system
     *
     * @param logEntry to add
     */
    void add(LogEntry logEntry);

    /**
     * Reads n log entries between the start index and the tail
     *
     * @param startIndex to splice from
     * @return the entries
     */
    List<LogEntry> read(int startIndex);

    /**
     * Read the next batch of {@link LogEntry}s for the given client. Incrementing the read counter afterwards to
     * ensure that the next call returns the correct batch. The batch size is as defined in
     * {@link RuntimeConfig#getLogBatchSize()}
     *
     * @param client to read the entries for
     * @return a batch of the entries
     */
    List<LogEntry> readNextForClient(Client client);

    /**
     * Append all the entries to the underlying data storage system
     *
     * @param logEntries to append
     */
    void appendEntries(List<LogEntry> logEntries);

    /**
     * The current number of entries that exist in the service
     *
     * @return the number of entries
     */
    int size();

}
