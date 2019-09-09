package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Subsystem;

public class SubsystemMonitorSpoofer extends SubsystemMonitorImpl {

    private long initTime = System.currentTimeMillis();

    private static int initCount;

    static {
        initCount++;
    }

    private class DecayableSubsystemWrapper extends SubsystemWrapper {

        DecayableSubsystemWrapper(Subsystem subsystem) {
            super(subsystem);
        }

        @Override
        public double getStatus() {
            double decayFactor = parent().config().subsystemDecayTime();
            double systemTime = getElapsedTimeInMinutes();

            return Math.pow((1 / super.getStatus() * Math.exp(-decayFactor * systemTime * initCount)), -1);
        }
    }

    private double getElapsedTimeInMinutes() {
        return ((System.currentTimeMillis() - initTime) / 1000F) % 3600 / 60;
    }

    @Override
    public void monitorSubsystem(Subsystem subsystem) {
        super.monitorSubsystem(new DecayableSubsystemWrapper(subsystem));
    }
    
}
