package com.type2labs.undersea.missionplanner.planner.mco;

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
import com.type2labs.undersea.utilities.PlannerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MultiConstraintOptimiser implements MissionPlanner {

    private static final Logger logger = LogManager.getLogger(MultiConstraintOptimiser.class);
    private static final DelaunayDecomposer decomposer = DelaunayDecomposer.getInstance();

    static {
        try {
            System.loadLibrary("jniortools");
        } catch (Error e) {
            logger.error("Failed to load native library jniortools. Expected to be on environment variable " +
                    "LD_LIBRARY_PATH or DYLD_LIBRARY_PATH");
            e.printStackTrace();
        }
    }

    private Mission generatedMission;

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

        decomposer.dispose();

        return solve(model, missionParameters);
    }

    private Mission solve(PlanDataModel model, MissionParameters missionParameters) {
        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(model.getDistanceMatrix().length, missionParameters.getAgentCount(),
                        missionParameters.getDepot());

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        for (int i = 0; i < manager.getNumberOfVehicles(); i++) {


//            final int agentTransitCallback =
//                    routing.registerTransitCallback((long fromIndex, long toIndex) -> {
//
//                    });

//            routing.setArcCostEvaluatorOfVehicle(i, agentTransitCallback);
        }

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

        // Add Distance constraint.
        routing.addDimension(transitCallbackIndex, 0, 3000,
                true, // start cumul to zero
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(100);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        generatedMission = new Mission(model, solution, routing, manager, new ArrayList<>(), missionParameters);

        return generatedMission;
    }

    @Override
    public void print(Mission mission) {
        Assignment assignment = mission.getAssignment();
        RoutingModel routing = mission.getRoutingModel();
        RoutingIndexManager manager = mission.getRoutingIndexManager();

        long maxRouteDistance = 0;

        for (int i = 0; i < manager.getNumberOfVehicles(); ++i) {
            long index = routing.start(i);
            logger.info("Route for Vehicle " + (i + 1) + ":");
            long routeDistance = 0;
            String route = "";

            while (!routing.isEnd(index)) {
                route += manager.indexToNode(index) + " -> ";
                long previousIndex = index;
                index = assignment.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }

            logger.info(route + manager.indexToNode(index));
            logger.info("Distance of the route: " + routeDistance + "m");
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
        logger.info("Maximum of the route distances: " + maxRouteDistance + "m");

    }


}
