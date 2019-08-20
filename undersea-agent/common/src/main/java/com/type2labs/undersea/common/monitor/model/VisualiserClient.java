package com.type2labs.undersea.common.monitor.model;

import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.service.AgentService;

import java.io.IOException;

public interface VisualiserClient extends AgentService {

    void write(String data) throws IOException;

    void write(LogMessage data) throws IOException;

    void closeConnection() throws IOException;

    void update();
}
