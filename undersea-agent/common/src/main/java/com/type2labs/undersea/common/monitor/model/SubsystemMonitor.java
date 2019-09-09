package com.type2labs.undersea.common.monitor.model;

import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;
import com.type2labs.undersea.common.service.AgentService;

public interface SubsystemMonitor extends AgentService {

    void monitorSubsystem(Subsystem subsystem);

    double readSubsystemStatus(Subsystem subsystem);

    void update();

    VisualiserClient visualiser();

    void setVisualiser(VisualiserClient visualiserClient);

    void registerSpeedRange(Range speedRange);

}
