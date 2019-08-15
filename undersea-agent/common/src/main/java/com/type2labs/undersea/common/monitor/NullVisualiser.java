package com.type2labs.undersea.common.monitor;

import com.type2labs.undersea.common.logger.LogMessage;

import java.io.IOException;

public class NullVisualiser implements VisualiserClient {

    @Override
    public void write(String data) throws IOException {

    }

    @Override
    public void write(LogMessage data) throws IOException {

    }

    @Override
    public void closeConnection() throws IOException {

    }

    @Override
    public void update() {

    }

}
