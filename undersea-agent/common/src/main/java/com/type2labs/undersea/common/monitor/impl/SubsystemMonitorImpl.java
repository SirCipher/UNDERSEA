package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubsystemMonitorImpl implements SubsystemMonitor {

    private static final Logger logger = LogManager.getLogger(SubsystemMonitorImpl.class);

    private Map<String, SubsystemWrapper> subsystems = new HashMap<>();
    private VisualiserClient visualiserClient = new NoVisualiser();
    private Agent agent;
    private Range speedRange;

    static class SubsystemWrapper {
        double status;
        Subsystem subsystem;

        SubsystemWrapper(Subsystem subsystem) {
            this.subsystem = subsystem;
        }

        private static SubsystemWrapper wrap(Subsystem subsystem) {
            return new SubsystemWrapper(subsystem);
        }

        public double getStatus() {
            return status;
        }
    }

    @Override
    public void monitorSubsystem(Subsystem subsystem) {
        subsystems.put(subsystem.name(), SubsystemWrapper.wrap(subsystem));
        UnderseaLogger.info(logger, agent, "Monitoring subsystem: " + subsystem);
    }

    void monitorSubsystem(SubsystemWrapper subsystemWrapper){
        subsystems.put(subsystemWrapper.subsystem.name(), subsystemWrapper);
        UnderseaLogger.info(logger, agent, "Monitoring subsystem: " + subsystemWrapper.subsystem);
    }

    @Override
    public double readSubsystemStatus(Subsystem subsystem) {
        return subsystems.get(subsystem.name()).status;
    }

    @Override
    public void update() {
        visualiserClient.update();
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
    public void registerSpeedRange(Range speedRange) {
        this.speedRange = speedRange;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        visualiserClient.initialise(agent);
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

}
