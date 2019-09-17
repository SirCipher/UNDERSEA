package com.type2labs.undersea.tutorials.tutoriala;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.GeneratedMissionImpl;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskType;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MissionManagerSample implements MissionManager {

    private Agent agent;

    @Override
    public void addTasks(List<Task> tasks) {

    }

    @Override
    public void assignMission(GeneratedMission mission) {

    }

    @Override
    public List<Task> getAssignedTasks() {
        return null;
    }

    @Override
    public boolean missionHasBeenAssigned() {
        return false;
    }

    @Override
    public MissionPlanner missionPlanner() {
        return null;
    }

    @Override
    public void notify(String message) {

    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public Object executeTransaction(Transaction transaction) {
        LifecycleEvent statusCode = (LifecycleEvent) transaction.getStatusCode();

        if (statusCode == LifecycleEvent.ELECTED_LEADER) {
            Collection<Client> clients = agent.clusterClients().values();
            GeneratedMission generatedMission = new GeneratedMissionImpl();

            for (Client client : clients) {
                List<Task> tasks = new ArrayList<>();
                Task task = new TaskImpl(new double[]{0, 0}, TaskType.SURVEY);
                tasks.add(task);

                generatedMission.addAgentMission(new AgentMissionImpl(client, tasks));
            }

            return generatedMission;
        }

        throw new NotSupportedException();
    }

    @Override
    public void run() {

    }

}
