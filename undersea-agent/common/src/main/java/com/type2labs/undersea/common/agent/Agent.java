package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.List;

public interface Agent extends Serializable {

    ServiceManager services();

    List<Pair<String, String>> status();

    String name();

    void schedule(Runnable task);

    UnderseaRuntimeConfig config();

    void shutdown();

}
