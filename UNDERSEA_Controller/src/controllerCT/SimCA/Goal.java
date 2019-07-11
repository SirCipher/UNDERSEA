package controllerCT.SimCA;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.common.collect.Maps;

import controllerCT.simplex.implementation.Relationship;

public class Goal {

	static final double DEFAULT_POLE = 0.9;
	static final double DEFAULT_KALMAN_PROCESS_NOISE = 0; // Assuming the actual slope is almost constant
	static final double DEFAULT_KALMAN_MEASUREMENT_NOISE = Math.pow(10, -6);

	final double kalman_measuremnt_noise_variance = DEFAULT_KALMAN_MEASUREMENT_NOISE;
	final double kalman_process_noise_variace = DEFAULT_KALMAN_PROCESS_NOISE;
	double kalman_noise_covariance;

	double error;
	double goal=0;
	Relationship goalType;
	double updatedGoal=0;
	double originalGoal=0;
	Double pole = DEFAULT_POLE;

	Double controlDecision;
	Double oldControlDecision;

	Map<Double, Double> dataForModelIdentification;
	LinkedList<Double> lastControls;
	LinkedList<Double> lastObservedValues;
	LinkedList<Double> changePointErrorsSequence;
    Double nextIdentitificationControlDecision;

	HashMap<Integer, Double> sensorProperties;
	double maxValue, minValue;
	Double slope;

	//goal name

	public Goal()
    {
  	  this.dataForModelIdentification = Maps.newHashMap();
      this.lastControls = new LinkedList<Double>();
      this.lastObservedValues = new LinkedList<Double>();
      this.changePointErrorsSequence = new LinkedList<Double>();
      this.nextIdentitificationControlDecision = 0.0;
      sensorProperties = new HashMap<>();
      this.error = 0;
      this.goal = 0;
      this.goalType = Relationship.EQ;
      this.updatedGoal = 0;
      this.originalGoal = 0;
      this.pole = DEFAULT_POLE;
      this.controlDecision = 0.0;//0.5
      this.oldControlDecision = .5d;
      this.maxValue=0; this.minValue=0;
      this.kalman_noise_covariance = kalman_process_noise_variace + kalman_measuremnt_noise_variance;
      this.slope = 1d;
    }

	public void setGoalType(String type)
	{
		if (type.equals("Setpoint"))
			this.goalType = Relationship.EQ;
		else if(type.equals("Higher than"))
			this.goalType = Relationship.GEQ;
		else if(type.equals("Lower than"))
			this.goalType = Relationship.LEQ;

	}

	public Relationship getReverseGoalType()
	{
		if (this.goalType == Relationship.GEQ)
				return Relationship.LEQ;
		else if (this.goalType == Relationship.LEQ)
			return Relationship.GEQ;
		else
			return Relationship.EQ;

	}



}

