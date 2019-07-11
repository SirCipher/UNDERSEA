package controllerCT.SimCA;

import java.util.HashMap;

import org.apache.commons.math3.optimization.GoalType;

public class OptimGoal {

	HashMap<Integer, Double> sensorOptimProperties = new HashMap<>();
	GoalType goalType;
	//goal name

	public OptimGoal(GoalType goalType)
	{
		this.goalType = goalType;
		this.sensorOptimProperties = new HashMap<>();
	}
}