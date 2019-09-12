/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.agent;


import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple implementation of agent used for testing.
 *
 * This needs to be removed.
 */
public class AgentImpl implements Agent {

    private static final Logger logger = LogManager.getLogger(AgentImpl.class);

    private String name;

    private String rate;
    private int serverPort;
    private Range speedRange;
    private List<Sensor> sensors = new ArrayList<>();
    private String host = "localhost";
    private String groupName = "test";
    private double batteryRange = ThreadLocalRandom.current().nextDouble(100);
    private AgentMetaData agentMetaData = new AgentMetaData();
    private AgentState agentState = new AgentState();

    public AgentImpl(String name) {
        this.name = name;
    }

    @Override
    public AgentMetaData metadata() {
        return agentMetaData;
    }

    @Override
    public void setMetadata(AgentMetaData metaData) {

    }

    @Override
    public ServiceManager serviceManager() {
        return null;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public RuntimeConfig config() {
        return null;
    }

    public double getBatteryRange() {
        return batteryRange;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = Integer.parseInt(serverPort);
    }

    public void setServerPort(int port) {
        this.serverPort = port;
    }

    public Range getSpeedRange() {
        return speedRange;
    }

    public void setSpeedRange(Range speedRange) {
        this.speedRange = speedRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentImpl dslAgent = (AgentImpl) o;

        return getName().equals(dslAgent.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getServerPort());
    }


    @Override
    public void shutdown() {

    }

    @Override
    public ConcurrentMap<PeerId, Client> clusterClients() {
        return new ConcurrentHashMap<>();
    }

    @Override
    public PeerId peerId() {
        return null;
    }

    @Override
    public void log(LogEntry logEntry) {

    }

    @Override
    public AgentState state() {
        return agentState;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}


