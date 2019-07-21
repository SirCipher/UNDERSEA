package com.type2labs.undersea.controller.controllerCT.SimCA;

import org.apache.commons.math3.optimization.GoalType;

import java.util.HashMap;

public class OptimGoal {

    HashMap<Integer, Double> sensorOptimProperties = new HashMap<>();
    GoalType goalType;
    //goal name

    public OptimGoal(GoalType goalType) {
        this.goalType = goalType;
        this.sensorOptimProperties = new HashMap<>();
    }
}