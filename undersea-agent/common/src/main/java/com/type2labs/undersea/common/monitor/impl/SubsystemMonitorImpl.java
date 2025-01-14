/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubsystemMonitorImpl implements SubsystemMonitor {

    private static final Logger logger = LogManager.getLogger(SubsystemMonitorImpl.class);

    private final Map<String, SubsystemWrapper> subsystems = new HashMap<>();
    private VisualiserClient visualiserClient = new NoVisualiser();
    private Agent agent;
    private Range speedRange;
    private MonitorCallback monitorCallback;

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
    public void update() {
        visualiserClient.update();
    }

    @Override
    public VisualiserClient visualiser() {
        return visualiserClient;
    }

    public void setMonitorCallback(MonitorCallback monitorCallback) {
        this.monitorCallback = monitorCallback;
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
        double speedWeighting = 1;

        if (costConfiguration != null) {
            speedWeighting = (double) costConfiguration.getBias("SPEED");
        }

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

        if (monitorCallback != null) {
            parentAgent.serviceManager().startRepeatingTask(() -> {
                for (SubsystemWrapper subsystemWrapper : subsystems.values()) {
                    monitorCallback.onMonitor(subsystemWrapper.subsystem);
                }
            }, 500);
        }
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

    class SubsystemWrapper {
        final Subsystem subsystem;

        SubsystemWrapper(Subsystem subsystem) {
            this.subsystem = subsystem;
        }

        public double getStatus() {
            CostConfiguration costConfiguration = agent.config().getCostConfiguration();
            double accuracyWeighting = 1;
            if (costConfiguration != null) {
                accuracyWeighting = (double) costConfiguration.getBias("ACCURACY");
            }

            return (subsystem.rate() + subsystem.reliability()) / (accuracyWeighting * subsystem.accuracy());
        }
    }

    public List<Subsystem> getSubsystems() {
        List<Subsystem> res = new ArrayList<>();

        for (SubsystemWrapper subsystemWrapper : subsystems.values()) {
            res.add(subsystemWrapper.subsystem);
        }

        return res;
    }

}
