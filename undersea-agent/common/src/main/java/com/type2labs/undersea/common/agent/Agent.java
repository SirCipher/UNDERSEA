package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.networking.Endpoint;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.List;

public interface Agent extends AgentService, Serializable {

    ServiceManager services();

    AgentService getService();

    List<Pair<String, String>> status();

    Endpoint endpoint();

    String name();

    void schedule(Runnable task);

    Monitor getMonitor();

    UnderseaRuntimeConfig config();

}
