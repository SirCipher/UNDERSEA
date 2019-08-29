package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.impl.MoosConnector;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class MoosConnectorTest {

    @Test
    @IgnoredOnCi
    public void doTest() {
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.setAgent(new Agent() {
            private static final long serialVersionUID = -5163456321116979949L;

            @Override
            public AgentMetaData metadata() {
                return null;
            }

            @Override
            public void setMetadata(AgentMetaData metaData) {

            }

            @Override
            public ServiceManager services() {
                return null;
            }

            @Override
            public List<Pair<String, String>> status() {
                return null;
            }

            @Override
            public String name() {
                return "test";
            }

            @Override
            public void schedule(Runnable task) {

            }

            @Override
            public UnderseaRuntimeConfig config() {
                return null;
            }

            @Override
            public void shutdown() {

            }

            @Override
            public ConcurrentMap<PeerId, Client> clusterClients() {
                return null;
            }

            @Override
            public PeerId peerId() {
                return null;
            }
        });

        serviceManager.registerService(new MoosConnector());
        serviceManager.startServices();

        while (true) {
        }
    }

}
