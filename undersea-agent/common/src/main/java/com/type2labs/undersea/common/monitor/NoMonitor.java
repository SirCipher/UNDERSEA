package com.type2labs.undersea.common.monitor;

public class NoMonitor implements Monitor {

    @Override
    public void update() {

    }

    @Override
    public VisualiserClient visualiser() {
        return new NoVisualiser();
    }

    @Override
    public void setVisualiser(VisualiserClient visualiser) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void run() {

    }
}
