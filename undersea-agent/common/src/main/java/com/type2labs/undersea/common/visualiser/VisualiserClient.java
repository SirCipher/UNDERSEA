package com.type2labs.undersea.common.visualiser;

import com.type2labs.undersea.common.logger.LogMessage;

import java.io.IOException;

public interface VisualiserClient {

    void write(String data) throws IOException;

    void write(LogMessage data) throws IOException;

    void closeConnection() throws IOException;

    void update();
}
