package com.type2labs.undersea.common.logger;

import com.type2labs.undersea.common.service.AgentService;

public interface LogService extends AgentService {

    void add(LogEntry logEntry);

    LogEntry read(int index);

    int size();

}
