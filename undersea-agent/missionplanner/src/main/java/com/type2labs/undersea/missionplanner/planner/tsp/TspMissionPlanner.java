package com.type2labs.undersea.missionplanner.planner.tsp;

import com.google.ortools.constraintsolver.*;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.type2labs.undersea.missionplanner.decomposer.delaunay.DelaunayDecomposer;
import com.type2labs.undersea.missionplanner.exception.PlannerException;
import com.type2labs.undersea.missionplanner.model.Mission;
import com.type2labs.undersea.missionplanner.model.MissionParameters;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.model.PlanDataModel;
import com.type2labs.undersea.missionplanner.planner.MatlabFactory;
import com.type2labs.undersea.utilities.PlannerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class TspMissionPlanner implements MissionPlanner {

    private static final Logger logger = LogManager.getLogger(TspMissionPlanner.class);
    private static final DelaunayDecomposer decomposer = MatlabFactory.getDelaunayDecomposer();

    static {
        try {
            System.loadLibrary("jniortools");
        } catch (Error e) {
            logger.error("Failed to load native library jniortools. Expected to be on environment variable " +
                    "LD_LIBRARY_PATH or DYLD_LIBRARY_PATH");
            e.printStackTrace();
        }
    }

    private double[][] decompose(double[] x, double[] y, double sensorRange) throws PlannerException {
        MWNumericArray xArray = new MWNumericArray(x, MWClassID.DOUBLE);
        MWNumericArray yArray = new MWNumericArray(y, MWClassID.DOUBLE);
        MWNumericArray results;

        try {
            results = (MWNumericArray) decomposer.decompose(1, xArray, yArray, sensorRange)[0];
        } catch (MWException e) {
            throw new PlannerException(e);
        }

        return (double[][]) results.toDoubleArray();
    }

    @Override
    public Mission generate(MissionParameters missionParameters) throws PlannerException {
        double[][] polygon = missionParameters.getPolygon();
        double[] x = new double[polygon.length];
        double[] y = new double[polygon.length];

        for (int i = 0; i < polygon.length; i++) {
            x[i] = polygon[i][0];
            y[i] = polygon[i][1];
        }

        double[][] centroids = decompose(x, y, missionParameters.getMinimumSensorRange());
        missionParameters.setCentroids(centroids);

        double[][] distanceMatrix = PlannerUtils.computeEuclideanDistanceMatrix(centroids);

        PlanDataModel model = new PlanDataModel(missionParameters, distanceMatrix);

        return solve(model, missionParameters);
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
        long index = routingModel.start(0);

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

    private Mission solve(PlanDataModel model, MissionParameters missionParameters) {
        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(model.getDistanceMatrix().length, model.getAgentCount(), model.getDepot());

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return (long) model.getDistanceMatrix()[fromNode][toNode];
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

        return new Mission(model, solution, routing, manager, new ArrayList<>(), missionParameters);
    }

}