package com.type2labs.undersea.missionplanner.planner.vrp;

import com.google.ortools.constraintsolver.*;
import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missionplanner.*;
import com.type2labs.undersea.common.service.Transaction;
import com.type2labs.undersea.missionplanner.decomposer.delaunay.DelaunayDecomposer;
import com.type2labs.undersea.missionplanner.model.MissionImpl;
import com.type2labs.undersea.missionplanner.model.MissionParametersImpl;
import com.type2labs.undersea.missionplanner.model.PlanDataModel;
import com.type2labs.undersea.missionplanner.utils.MatlabUtils;
import com.type2labs.undersea.utilities.PlannerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class VehicleRoutingOptimiser implements MissionPlanner {

    private static final Logger logger = LogManager.getLogger(VehicleRoutingOptimiser.class);
    private static final int SPEED_SCALAR = 100;
    private static DelaunayDecomposer decomposer;

    static {
        try {
            System.loadLibrary("jniortools");
        } catch (Error e) {
            logger.error("Failed to load native library jniortools. Expected to be on environment variable " +
                    "LD_LIBRARY_PATH or DYLD_LIBRARY_PATH");
            e.printStackTrace();
        }
    }

    private MissionImpl generatedMission;

    private double[][] decompose(double[] x, double[] y, double sensorRange) throws PlannerException {
        MWNumericArray xArray = new MWNumericArray(x, MWClassID.DOUBLE);
        MWNumericArray yArray = new MWNumericArray(y, MWClassID.DOUBLE);
        double[][] results;

        try {
            if (decomposer == null) {
                MatlabUtils.initialise();
                logger.info("Initialising delaunay decomposer");
                decomposer = new DelaunayDecomposer();
                logger.info("Initialised delaunay decomposer");
            }

            MWNumericArray numericArray = (MWNumericArray) decomposer.decompose(1, xArray, yArray, sensorRange)[0];
            results = (double[][]) numericArray.toDoubleArray();

            decomposer.dispose();
            MWApplication.terminate();

            MatlabUtils.dispose(xArray, yArray, numericArray);
        } catch (MWException e) {
            throw new PlannerException(e);
        }

        return results;
    }

    @Override
    public MissionImpl generate(MissionParameters missionParameters) throws PlannerException {
        MissionParametersImpl missionParametersImpl = (MissionParametersImpl) missionParameters;

        double[][] polygon = missionParametersImpl.getPolygon();
        double[] x = new double[polygon.length];
        double[] y = new double[polygon.length];

        for (int i = 0; i < polygon.length; i++) {
            x[i] = polygon[i][0];
            y[i] = polygon[i][1];
        }

        double[][] centroids = decompose(x, y, missionParametersImpl.getMinimumSensorRange());
        missionParametersImpl.setCentroids(centroids);

        double[][] distanceMatrix = PlannerUtils.computeEuclideanDistanceMatrix(centroids);

        PlanDataModel model = new PlanDataModel(missionParametersImpl, distanceMatrix);

        return solve(model, missionParametersImpl);
    }

    @Override
    public void print(Mission mission) {
        MissionImpl missionImpl = (MissionImpl) mission;

        Assignment assignment = missionImpl.getAssignment();
        RoutingModel routing = missionImpl.getRoutingModel();
        RoutingIndexManager manager = missionImpl.getRoutingIndexManager();

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
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i) / VehicleRoutingOptimiser.SPEED_SCALAR;
            }

            logger.info(route + manager.indexToNode(index));
            logger.info("Distance of the route: " + routeDistance + "m");
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
        logger.info("Maximum of the route distances: " + maxRouteDistance + "m");

    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>();
    }

    private MissionImpl solve(PlanDataModel model, MissionParametersImpl missionParametersImpl) {
        logger.info("Generating solution");
        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(model.getDistanceMatrix().length, missionParametersImpl.getAgentCount(),
                        missionParametersImpl.getStartingNode());

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        List<Integer> callbacks = new ArrayList<>();

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return (long) model.getDistanceMatrix()[fromNode][toNode];
                });

        // Define cost of each arc.
        final int bigNumber = 100000;
        routing.addDimension(transitCallbackIndex, bigNumber, bigNumber, false, "Time");

        callbacks.add(transitCallbackIndex);

//        final Random randomGenerator = new Random(0xBEEF);
//        final int costCoefficientMax = 3;

//        final int[] speeds = {1, 2, 3, 4, 5, 500};

        for (int vehicle = 0; vehicle < manager.getNumberOfVehicles(); ++vehicle) {
            final Agent dslAgent = missionParametersImpl.getAgents().get(vehicle);

            final int callback = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                // Convert from routing variable Index to user NodeIndex.
                int fromNode = manager.indexToNode(fromIndex);
                int toNode = manager.indexToNode(toIndex);

                return (long) (model.getDistanceMatrix()[fromNode][toNode]);// / dslAgent.getSpeedRange().getMax()) *
                // VehicleRoutingOptimiser.SPEED_SCALAR;
            });

            routing.setArcCostEvaluatorOfVehicle(callback, vehicle);
            callbacks.add(callback);
        }

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
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.AUTOMATIC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        generatedMission = new MissionImpl(model, solution, routing, manager, new ArrayList<>(), missionParametersImpl);

        return generatedMission;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public ScheduledFuture<?> executeTransaction(Transaction transaction) {
        logger.info("Received transaction: " + transaction);
        Agent agent = transaction.getAgent();
        Transaction.StatusCode statusCode = transaction.getStatusCode();

        if(statusCode== Transaction.StatusCode.ELECTED_LEADER){
            // TODO: 20/08/2019 Need to pull clients from agent. Move from RaftImpl
        }

        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }


    @Override
    public void run() {

    }
}