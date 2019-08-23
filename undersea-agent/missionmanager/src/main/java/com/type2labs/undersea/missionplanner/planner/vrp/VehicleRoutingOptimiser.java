package com.type2labs.undersea.missionplanner.planner.vrp;

import com.google.ortools.constraintsolver.*;
import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;
import com.type2labs.undersea.common.missions.task.model.TaskType;
import com.type2labs.undersea.missionplanner.decomposer.delaunay.DelaunayDecomposer;
import com.type2labs.undersea.missionplanner.model.GeneratedMissionImpl;
import com.type2labs.undersea.missionplanner.model.PlanDataModel;
import com.type2labs.undersea.missionplanner.utils.MatlabUtils;
import com.type2labs.undersea.utilities.PlannerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@NotThreadSafe
public class VehicleRoutingOptimiser implements MissionPlanner {

    private static final Logger logger = LogManager.getLogger(VehicleRoutingOptimiser.class);
    private static final int SPEED_SCALAR = 100;

    static {
        try {
            System.loadLibrary("jniortools");
        } catch (Error e) {
            logger.error("Failed to load native library jniortools. Expected to be on environment variable " +
                    "LD_LIBRARY_PATH or DYLD_LIBRARY_PATH");
            e.printStackTrace();
        }
    }

    private Agent parentAgent;

    private double[][] decompose(double[] x, double[] y, double sensorRange) throws PlannerException {
        MWNumericArray xArray = new MWNumericArray(x, MWClassID.DOUBLE);
        MWNumericArray yArray = new MWNumericArray(y, MWClassID.DOUBLE);
        double[][] results;

        try {
            MatlabUtils.initialise();
            logger.info("Initialising delaunay decomposer");
            DelaunayDecomposer decomposer = new DelaunayDecomposer();
            logger.info("Initialised delaunay decomposer");

            MWNumericArray numericArray = (MWNumericArray) decomposer.decompose(1, xArray, yArray, sensorRange)[0];
            results = (double[][]) numericArray.toDoubleArray();

            decomposer.dispose();

            MatlabUtils.dispose(xArray, yArray, numericArray);
        } catch (MWException e) {
            e.printStackTrace();
            throw new PlannerException(e);
        } finally {
            MWApplication.terminate();
        }

        return results;
    }

    private GeneratedMission distributeMission(PlanDataModel model, Assignment assignment, RoutingModel routing,
                                               RoutingIndexManager manager,
                                               MissionParameters missionParameters) {
        double[][] centroids = missionParameters.getCentroids();
        GeneratedMission mission = new GeneratedMissionImpl(model, assignment, routing, manager, missionParameters);

        for (int i = 0; i < manager.getNumberOfVehicles(); ++i) {
            long index = routing.start(i);
            Client client = missionParameters.getClients().get(i);
            ClusterState.ClientState clientState = client.state();
            List<TaskImpl> tasks = new ArrayList<>();

            while (!routing.isEnd(index)) {
                index = assignment.value(routing.nextVar(index));
                int ind = manager.indexToNode(index);
                TaskImpl task = new TaskImpl(centroids[ind], TaskType.WAYPOINT);

                tasks.add(task);
            }

            AgentMission agentMission = new AgentMissionImpl(clientState.getClient(), tasks);
            mission.addAgentMission(agentMission);
        }

        return mission;
    }


    @Override
    public GeneratedMission generate() throws PlannerException {
        MissionParameters missionParameters = parentAgent.config().missionParameters();

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
    public void print(GeneratedMission generatedMission) {
        GeneratedMissionImpl missionImpl = (GeneratedMissionImpl) generatedMission;

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
    public void initialise(Agent parentAgent) {
        this.parentAgent = parentAgent;
    }

    private GeneratedMission solve(PlanDataModel model, MissionParameters missionParameters) {
        int clusterSize = parentAgent.clusterClients().size();

        logger.info("Generating solution");
        // Create Routing Index Manager
        RoutingIndexManager manager = new RoutingIndexManager(model.getDistanceMatrix().length, clusterSize, 0);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);
        //noinspection MismatchedQueryAndUpdateOfCollection
        List<Integer> callbacks = new ArrayList<>();

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return (long) model.getDistanceMatrix()[fromNode][toNode];
                });

        callbacks.add(transitCallbackIndex);

        // Define cost of each arc.
        routing.addDimension(transitCallbackIndex, 0, 3000, true, "Time");

//        final Random randomGenerator = new Random(0xBEEF);
//        final int costCoefficientMax = 3;

//        final int[] speeds = {1, 2, 3, 4, 5, 500};

        List<Client> clients = new ArrayList<>(parentAgent.clusterClients().values());
        // Sort by raft peer ids
        clients.sort(Comparator.comparing(client -> client.peerId().toString()));

        for (int vehicle = 0; vehicle < clients.size(); ++vehicle) {
            Client client = clients.get(vehicle);
            ClusterState.ClientState clientState = client.state();

            final int callback = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                // Convert from routing variable Index to user NodeIndex.
                int fromNode = manager.indexToNode(fromIndex);
                int toNode = manager.indexToNode(toIndex);

                return (long) (model.getDistanceMatrix()[fromNode][toNode]);// / dslAgent.getSpeedRange().getMax()) *
                // VehicleRoutingOptimiser.SPEED_SCALAR;
            });

            callbacks.add(callback);
            routing.setArcCostEvaluatorOfVehicle(callback, vehicle);
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
        Assignment assignment = routing.solveWithParameters(searchParameters);
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

        return distributeMission(model, assignment, routing, manager, missionParameters);
    }
}