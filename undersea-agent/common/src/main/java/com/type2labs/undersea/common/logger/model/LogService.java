package com.type2labs.undersea.common.logger.model;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

public interface LogService extends AgentService {

    void add(LogEntry logEntry);

    /**
     * Reads n log entries between the start index and the tail
     *
     * @param startIndex to splice from
     * @return the entries
     */
    List<LogEntry> read(int startIndex);

    List<LogEntry> readNextForClient(Client client);

    int size();

}
