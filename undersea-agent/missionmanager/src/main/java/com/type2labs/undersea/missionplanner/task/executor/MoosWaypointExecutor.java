package com.type2labs.undersea.missionplanner.task.executor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.utilities.exception.ServiceNotRegisteredException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        this.networkInterface = agent.services().getService(NetworkInterface.class);
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
