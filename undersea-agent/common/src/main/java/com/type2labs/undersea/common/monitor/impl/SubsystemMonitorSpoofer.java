package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;

import java.util.concurrent.ThreadLocalRandom;

public class SubsystemMonitorSpoofer extends SubsystemMonitorImpl {

    private static int initCount;

    static {
        initCount++;
    }

    private long initTime = System.currentTimeMillis();

    public SubsystemMonitorSpoofer() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        super.registerSpeedRange(new Range(0, random.nextDouble(100), random.nextDouble(100)));
    }

    private double getElapsedTimeInMinutes() {
        return ((System.currentTimeMillis() - initTime) / 1000F) % 3600 / 60;
    }

    @Override
    public void monitorSubsystem(Subsystem subsystem) {
        super.monitorSubsystem(new DecayableSubsystemWrapper(subsystem));
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

}
