package com.type2labs.undersea.agent;

import com.type2labs.undersea.agent.impl.MoosConnector;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskType;
import com.type2labs.undersea.missionplanner.task.executor.WaypointExecutor;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import org.junit.Test;

public class WaypointExecutorTest {

    @Test
    @IgnoredOnCi
    public void doTest() throws InterruptedException {
//        Agent agent = (new AgentFactory().createWithName("alpha"));
//        agent.services().registerService(new MoosConnector());
//        agent.services().startServices();
//
//        Task task = new TaskImpl("110.87291551628573, -18.830200577525677", TaskType.WAYPOINT);
//
//        WaypointExecutor waypointExecutor = new WaypointExecutor(task);
//        waypointExecutor.initialise(agent);
//        waypointExecutor.run();
//
//
//        Thread.sleep(1000);
//        System.out.println("");
    }

}