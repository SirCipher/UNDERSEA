package com.type2labs.undersea.missionplanner.task.executor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.missions.task.model.TaskType;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.utilities.exception.ServiceNotRegisteredException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class WaypointExecutor implements TaskExecutor {

    private static final Logger logger = LogManager.getLogger(WaypointExecutor.class);
    private final Task task;
    private Agent agent;
    private NetworkInterface networkInterface;

    public WaypointExecutor(Task task) {
        if (task.getTaskType() != TaskType.WAYPOINT) {
            throw new IllegalArgumentException("Only task type measure is supported");
        }

        this.task = task;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.networkInterface = agent.services().getService(NetworkInterface.class);

        if (networkInterface == null) {
            throw new ServiceNotRegisteredException(NetworkInterface.class, WaypointExecutor.class);
        }
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {
//        logger.info("Running task: " + task);

        networkInterface.write("FWD:WAYPOINT_" + agent.name() + "_UPDATES:points=" + Arrays.toString(task.getCoordinates()).replace("[", "").replace("]", ""));
    }

}
