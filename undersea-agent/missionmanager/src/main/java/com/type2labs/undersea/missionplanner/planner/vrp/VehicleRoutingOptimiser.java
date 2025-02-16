/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.missionplanner.planner.vrp;

import com.google.ortools.constraintsolver.*;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.GeneratedMissionImpl;
import com.type2labs.undersea.common.missions.PlanDataModel;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskType;
import com.type2labs.undersea.missionplanner.decomposer.delaunay.DelaunayDecomposer;
import com.type2labs.undersea.missionplanner.utils.MatlabUtils;
import com.type2labs.undersea.utilities.PlannerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


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

    private synchronized double[][] decompose(double[] x, double[] y, double sensorRange) throws PlannerException {
        MWNumericArray xArray = new MWNumericArray(x, MWClassID.DOUBLE);
        MWNumericArray yArray = new MWNumericArray(y, MWClassID.DOUBLE);
        double[][] results;

        try {
            MatlabUtils.initialise();
            logger.info(parentAgent.name() + ": initialising delaunay decomposer", parentAgent);
            DelaunayDecomposer decomposer = new DelaunayDecomposer();
            logger.info(parentAgent.name() + ": initialised delaunay decomposer", parentAgent);

            MWNumericArray numericArray = (MWNumericArray) decomposer.decompose(1, xArray, yArray, sensorRange)[0];
            results = (double[][]) numericArray.toDoubleArray();

            decomposer.dispose();

            MatlabUtils.dispose(xArray, yArray, numericArray);
        } catch (MWException e) {
            e.printStackTrace();
            throw new PlannerException(e);
        } finally {
//            MWApplication.terminate();
        }

        logger.info(parentAgent.name() + ": finished decomposing mission area", parentAgent);

        return results;
    }

    private GeneratedMission distributeMission(PlanDataModel model, Assignment assignment, RoutingModel routing,
                                               RoutingIndexManager manager, MissionParameters missionParameters) {
        double[][] centroids = missionParameters.getCentroids();
        GeneratedMission mission = new GeneratedMissionImpl(model, assignment, routing, manager, missionParameters);

        for (int i = 0; i < manager.getNumberOfVehicles(); ++i) {
            long index = routing.start(i);
            Client client = missionParameters.getClients().get(i);
            List<Task> tasks = new ArrayList<>();

            StringBuilder points = new StringBuilder();

            while (!routing.isEnd(index)) {
                index = assignment.value(routing.nextVar(index));
                int ind = manager.indexToNode(index);

                points.append(Arrays.toString(centroids[ind])).append(":");

                TaskImpl task = new TaskImpl(centroids[ind], TaskType.WAYPOINT);
                tasks.add(task);
            }

            if (points.toString().endsWith(":")) {
                points = new StringBuilder(points.substring(0, points.length() - 1));
            }

            AgentMissionImpl agentMission = new AgentMissionImpl(client, tasks);
            agentMission.setPoints(points.toString());

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
            Client client = ((GeneratedMissionImpl) generatedMission).getMissionParameters().getClients().get(i);


            long index = routing.start(i);
            logger.info(parentAgent.name() + ": Route for client: " + client.peerId(), parentAgent);
            long routeDistance = 0;
            String route = "";

            while (!routing.isEnd(index)) {
                route += manager.indexToNode(index) + " -> ";
                long previousIndex = index;
                index = assignment.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i) / VehicleRoutingOptimiser.SPEED_SCALAR;
            }

            logger.info(parentAgent.name() + ": " + route + manager.indexToNode(index), parentAgent);
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.parentAgent = parentAgent;
    }

    @Override
    public Agent parent() {
        return parentAgent;
    }

    private GeneratedMission solve(PlanDataModel model, MissionParameters missionParameters) {
        int clusterSize = parentAgent.clusterClients().size() + 1;

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

        List<Client> clients = new ArrayList<>(parentAgent.clusterClients().values());
        clients.sort(Comparator.comparing(client -> client.peerId().toString()));

        for (int vehicle = 0; vehicle < clients.size(); ++vehicle) {
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

        return distributeMission(model, assignment, routing, manager, missionParameters);
    }
}