package com.type2labs.undersea.common.monitor;

public class MonitorImpl implements Monitor {

    private VisualiserClient visualiserClient;

    @Override
    public void update() {

    }

    @Override
    public VisualiserClient visualiser() {
        return visualiserClient;
    }

    @Override
    public void setVisualiser(VisualiserClient visualiserClient) {
        this.visualiserClient = visualiserClient;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void shutdown() {

    }
}
