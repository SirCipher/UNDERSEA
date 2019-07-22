package com.type2labs.undersea.missionplanner.planner.tsp;

import com.google.ortools.constraintsolver.*;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.type2labs.undersea.missionplanner.decomposer.Decomposer;
import com.type2labs.undersea.missionplanner.exception.PlannerException;
import com.type2labs.undersea.missionplanner.model.Mission;
import com.type2labs.undersea.missionplanner.model.MissionParameters;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.model.PlanDataModel;
import com.type2labs.undersea.missionplanner.utils.PlannerUtils;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class TspMissionPlanner implements MissionPlanner {

    private static final Logger logger = Logger.getLogger(TspMissionPlanner.class.getName());
    private static Decomposer decomposer = Decomposer.getInstance();

    static {
        System.loadLibrary("jniortools");
    }

    private double[][] decompose(int[][] polygon) throws PlannerException {
        MWNumericArray numericArray = new MWNumericArray(polygon, MWClassID.DOUBLE);

        try {
            numericArray = (MWNumericArray) decomposer.decompose(1, numericArray)[0];
        } catch (MWException e) {
            throw new PlannerException(e);
        }

        return (double[][]) numericArray.toDoubleArray();
    }

    @Override
    public Mission generate(MissionParameters missionParameters, int[][] polygon) throws PlannerException {
        double[][] centroids = decompose(polygon);
        double[][] distanceMatrix = PlannerUtils.computeEuclideanDistanceMatrix(centroids);

        PlanDataModel model = new PlanDataModel(missionParameters, distanceMatrix);

        return solve(model);
    }

    @Override
    public void print(Mission mission) {
        Assignment assignment = mission.getAssignment();
        RoutingModel routingModel = mission.getRoutingModel();
        RoutingIndexManager manager = mission.getRoutingIndexManager();

        // Solution cost.
        logger.info("Objective: " + assignment.objectiveValue() + "miles");
        // Inspect solution.
        logger.info("Route:");

        long routeDistance = 0;
        long index = mission.getRoutingModel().start(0);

        StringBuilder route = new StringBuilder();

        while (!routingModel.isEnd(index)) {
            route.append(manager.indexToNode(index)).append(" -> ");
            long previousIndex = index;

            index = assignment.value(routingModel.nextVar(index));
            routeDistance += routingModel.getArcCostForVehicle(previousIndex, index, 0);
        }

        route.append(manager.indexToNode(routingModel.end(0)));
        logger.info(route.toString());
        logger.info("Route distance: " + routeDistance + "miles");

    }

    private Mission solve(PlanDataModel model) {
        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(model.getData().length, model.getAgentCount(), model.getDepot());

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return (long) model.getData()[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        return new Mission(model, solution, routing, manager, new ArrayList<>());
    }

}
