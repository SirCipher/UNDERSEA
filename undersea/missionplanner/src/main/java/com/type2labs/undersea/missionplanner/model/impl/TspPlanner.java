package com.type2labs.undersea.missionplanner.model.impl;

import com.google.ortools.constraintsolver.*;
import com.type2labs.undersea.missionplanner.model.PlanDataModel;
import com.type2labs.undersea.missionplanner.model.Planner;

import java.util.logging.Logger;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class TspPlanner implements Planner {

    private static final Logger logger = Logger.getLogger(TspPlanner.class.getName());

    static {
        System.loadLibrary("jniortools");
    }

    @Override
    public void generate(PlanDataModel model) {
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

        // Print solution on console.
        printPlan(routing, manager, solution);
    }

    @Override
    public void printPlan(RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        // Solution cost.
        logger.info("Objective: " + solution.objectiveValue() + "miles");
        // Inspect solution.
        logger.info("Route:");
        long routeDistance = 0;
        String route = "";
        long index = routing.start(0);
        while (!routing.isEnd(index)) {
            route += manager.indexToNode(index) + " -> ";
            long previousIndex = index;
            index = solution.value(routing.nextVar(index));
            routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += manager.indexToNode(routing.end(0));
        logger.info(route);
        logger.info("Route distance: " + routeDistance + "miles");

    }


}
