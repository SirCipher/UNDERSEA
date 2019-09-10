package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.logger.VisualiserMessage;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;

import java.io.IOException;

public class NoVisualiser implements VisualiserClient {

    @Override
    public void write(String data) throws IOException {

    }

    @Override
    public void write(VisualiserMessage data) throws IOException {

    }

    @Override
    public void update() {

    }


    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }

    @Override
    public void run() {

    }
}
