package com.type2labs.undersea.common.monitor.model;

import com.type2labs.undersea.common.service.AgentService;

public interface Monitor extends AgentService {

    void update();

    VisualiserClient visualiser();

    void setVisualiser(VisualiserClient visualiserClient);

}
