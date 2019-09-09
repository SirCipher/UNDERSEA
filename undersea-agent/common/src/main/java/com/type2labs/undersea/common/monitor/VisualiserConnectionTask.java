package com.type2labs.undersea.common.monitor;


import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
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
        VisualiserClientImpl visualiserClient = new VisualiserClientImpl();
        ServiceManager serviceManager = parent.services();
        SubsystemMonitor subsystemMonitor = (SubsystemMonitor) serviceManager.getService(SubsystemMonitor.class);

        if (subsystemMonitor != null) {
            subsystemMonitor.setVisualiser(visualiserClient);
        }
    }


}
