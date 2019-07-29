package com.type2labs.undersea.missionplanner.planner.mco;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.missionplanner.exception.PlannerException;
import com.type2labs.undersea.missionplanner.model.Mission;
import com.type2labs.undersea.missionplanner.model.MissionParameters;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.models.Agent;
import com.type2labs.undersea.models.Node;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MultiConstraintOptimiserTest {

    private static final Logger logger = LogManager.getLogger(MultiConstraintOptimiserTest.class);
    private static List<Agent> agents = new ArrayList<>(5);

    static {
        for (int i = 0; i < 3; i++) {
            agents.add(new Agent(Integer.toString(i)));
        }
    }

    @Test
    public void init() {
        new MultiConstraintOptimiser();
    }

    @Test
    public void test() {
        MissionPlanner missionPlanner = new MultiConstraintOptimiser();
        double[][] area = Utility.stringTo2dDoubleArray("0 0; 150 0; 150 -140; 0 -140;");

        MissionParameters missionParameters = new MissionParameters(agents, 0, area, 30);
        Mission mission;

        try {
            mission = missionPlanner.generate(missionParameters);
            missionPlanner.print(mission);
        } catch (PlannerException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to plan mission", e);
        }

        RoutingModel routingModel = mission.getRoutingModel();
        Assignment assignment = mission.getAssignment();
        RoutingIndexManager manager = mission.getRoutingIndexManager();

        for (int i = 0; i < manager.getNumberOfVehicles(); ++i) {
            long index = routingModel.start(i);
            Agent agent = agents.get(i);

            while (!routingModel.isEnd(index)) {
                int centroidIndex = manager.indexToNode(index);
                index = assignment.value(routingModel.nextVar(index));

                double[] centroid = mission.getMissionParameters().getCentroid(centroidIndex);

                Node node = new Node(centroid[0], centroid[1]);
                agent.assignNode(node);
            }

            if (agent.getAssignedNodes().size() == 1) {
                logger.warn("More agents than required have been assigned to the mission. Removing nodes for agent: " + agent.getName());
                agent.setAssignedNodes(new ArrayList<>());
            }
        }
    }


}