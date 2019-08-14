package com.type2labs.undersea.visualiser;


import com.type2labs.undersea.common.Agent;
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

        parent.setVisualiser(visualiserClient);

    }


}
