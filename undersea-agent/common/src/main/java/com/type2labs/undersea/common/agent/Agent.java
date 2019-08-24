package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface Agent extends Serializable {

    AgentMetaData metadata();

    void setMetadata(AgentMetaData metaData);

    ServiceManager services();

    List<Pair<String, String>> status();

    String name();

    void schedule(Runnable task);

    UnderseaRuntimeConfig config();

    void shutdown();

    ConcurrentMap<PeerId, Client> clusterClients();

    PeerId peerId();

}
