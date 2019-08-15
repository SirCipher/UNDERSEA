package com.type2labs.undersea.monitor;


import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VisualiserConnectionTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(VisualiserConnectionTask.class);
    private final Agent parent;

    public VisualiserConnectionTask(Agent parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        VisualiserClientImpl visualiserClient = new VisualiserClientImpl(parent);
        ServiceManager serviceManager = parent.services();
        Monitor monitor = (Monitor) serviceManager.getService(Monitor.class);

        if (monitor != null) {
            monitor.setVisualiser(visualiserClient);
        }
    }


}
