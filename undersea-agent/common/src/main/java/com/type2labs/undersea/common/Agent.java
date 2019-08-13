package com.type2labs.undersea.common;

import com.type2labs.undersea.common.visualiser.VisualiserClient;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface Agent extends AgentService {

    ServiceManager services();

    AgentService getService();

    List<Pair<String, String>> status();

    VisualiserClient visualiser();

    Endpoint endpoint();

}
