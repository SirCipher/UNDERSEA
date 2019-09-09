package com.type2labs.undersea.common.monitor.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;

public class NoSubsystemMonitor implements SubsystemMonitor {

    @Override
    public void monitorSubsystem(Subsystem subsystem) {

    }

    @Override
    public double readSubsystemStatus(Subsystem subsystem) {
        return 0;
    }

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
    public void registerSpeedRange(Range speedRange) {

    }

    @Override
    public double getCurrentCost() {
        return 0;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean started() {
        return true;
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public long transitionTimeout() {
        return 0;
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {

    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }

    @Override
    public void run() {

    }
}
