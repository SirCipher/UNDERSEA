package com.type2labs.undersea.common.monitor;

public class NullMonitor implements Monitor {
    @Override
    public void update() {

    }

    @Override
    public VisualiserClient visualiser() {
        return new NullVisualiser();
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
