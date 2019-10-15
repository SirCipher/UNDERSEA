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

package com.type2labs.undersea.missionplanner.task.executor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;

public class MoosWaypointExecutor implements TaskExecutor {

    private static final Logger logger = LogManager.getLogger(WaypointExecutor.class);
    private final AgentMissionImpl agentMission;
    private Agent agent;
    private NetworkInterface networkInterface;

    public MoosWaypointExecutor(AgentMissionImpl agentMission) {
        this.agentMission = agentMission;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.networkInterface = agent.serviceManager().getService(NetworkInterface.class);
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {
        networkInterface.write("FWD:WAYPOINT_" + agent.name() + "_UPDATES=points=" + agentMission.getPoints().replace("[", "").replace("]", "") + "");
    }
}
