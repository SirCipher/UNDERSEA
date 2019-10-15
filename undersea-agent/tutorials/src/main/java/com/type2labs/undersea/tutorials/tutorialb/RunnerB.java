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

package com.type2labs.undersea.tutorials.tutorialb;


import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentState;
import com.type2labs.undersea.common.consensus.ConsensusClusterConfig;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.prospect.impl.ConsensusNodeImpl;
import com.type2labs.undersea.tutorials.tutoriala.MissionManagerSample;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.List;

@SuppressWarnings("Duplicates")
public class RunnerB {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

    public static void main(String[] args) {
        AgentFactory agentFactory = new AgentFactory();
        List<Agent> agents = agentFactory.createN(5);

        for (Agent agent : agents) {
            ServiceManager serviceManager = agent.serviceManager();
            serviceManager.registerService(new ConsensusNodeImpl(new ConsensusClusterConfig()));
            serviceManager.registerService(new SubsystemMonitorSpoofer());
            serviceManager.registerService(new MissionManagerSample());
            serviceManager.registerService(new DiskLogService());

            serviceManager.startServices();

            agent.state().transitionTo(AgentState.State.ACTIVE);
        }

        for (Agent a : agents) {
            for (Agent b : agents) {
                if (a != b) {
                    ConsensusNodeImpl consensusNodeA = a.serviceManager().getService(ConsensusNodeImpl.class);
                    ConsensusNodeImpl consensusNodeB = b.serviceManager().getService(ConsensusNodeImpl.class);

                    consensusNodeA.state().discoverNode(consensusNodeB);
                }
            }
        }

    }

}
