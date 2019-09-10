package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SubsystemMonitorImpl implements SubsystemMonitor {

    private static final Logger logger = LogManager.getLogger(SubsystemMonitorImpl.class);

    private Map<String, SubsystemWrapper> subsystems = new HashMap<>();
    private VisualiserClient visualiserClient = new NoVisualiser();
    private Agent agent;
    private Range speedRange;

    @Override
    public void shutdown() {
        visualiserClient.shutdown();
    }

    @Override
    public void monitorSubsystem(Subsystem subsystem) {
        monitorSubsystem(new SubsystemWrapper(subsystem));
    }

    void monitorSubsystem(SubsystemWrapper subsystemWrapper) {
        subsystems.put(subsystemWrapper.subsystem.name(), subsystemWrapper);
    }

    @Override
    public double readSubsystemStatus(Subsystem subsystem) {
        return subsystems.get(subsystem.name()).getStatus();
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
    public double getCurrentCost() {
        CostConfiguration costConfiguration = agent.config().getCostConfiguration();
        double speedWeighting = (double) costConfiguration.getBias("SPEED");

        double result = speedRange.getMax() * speedWeighting;

        for (SubsystemWrapper subsystem : subsystems.values()) {
            result += subsystem.getStatus();
        }

        return result;
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

    class SubsystemWrapper {
        Subsystem subsystem;

        SubsystemWrapper(Subsystem subsystem) {
            this.subsystem = subsystem;
        }

        public double getStatus() {
            CostConfiguration costConfiguration = agent.config().getCostConfiguration();
            double accuracyWeighting = (double) costConfiguration.getBias("ACCURACY");

            return (subsystem.rate() + subsystem.reliability()) / (accuracyWeighting * subsystem.accuracy());
        }
    }

}
