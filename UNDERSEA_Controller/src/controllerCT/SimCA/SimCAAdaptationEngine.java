/**
 *
 */

package controllerCT.SimCA;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.stat.regression.SimpleRegression; //common math 3.3.2 lib

import com.google.common.base.Preconditions; //guava 13 lib
import com.google.common.collect.Maps; //guava 13 lib

import controllerCT.simplex.implementation.LinearEquation;
import controllerCT.simplex.implementation.LinearModel;
import controllerCT.simplex.implementation.LinearObjectiveFunction;
import controllerCT.simplex.implementation.NoFeasibleSolutionException;
import controllerCT.simplex.implementation.Relationship;
import controllerCT.simplex.implementation.SimplexSolver;
import controllerCT.simplex.implementation.UnboundedSolutionException;

/*
 * @author Stepan
 */
public class SimCAAdaptationEngine{
    private static final double CONTROL_DECISION_UPPER_BOUND = 0.99;
    private static final int SLOPE_MEASUREMENTS_DISTANCE = 10;
    private static final Double IDENTIFICATION_PROCEDURE_STEP = 0.1;
    private static final int DEFAULT_SENSOR_NUM = 5;//15

    private static int N_services;
    static int N_concerns;
    private static int N_optim_concerns;
    private int identifiedConcern=0;
    private int adaptationPeriod = 100;//also known as controlPeriod

    private double UUVspeed = 0.5;

    private static final int OPTIM_MODE = 2;

    private enum OperatingPhase
        {
		PREIDENTIF("Preidentif"), IDENTIFICATION("Identification"), CONTROL("Control");
		private final String label;
		private OperatingPhase(String label) {this.label = label;}
		@Override
		public String toString() {
			return label;
		}
	};
	private OperatingPhase currentOperatingMode = OperatingPhase.PREIDENTIF;

    int simplexError=0;
    double pickValue=0;
    private double lowPassFilter = .95;

    ArrayList<Goal> goals;
    ArrayList<OptimGoal> optimGoals;
    private HashMap<Integer, Double> selectionProbabilities = new HashMap<>();

    public SimCAAdaptationEngine()
    {
        resetValues();

    }
    
    public double getUUVspeed()
    {
    	return UUVspeed;
    }
    
	private double getCurGoal(int N)
    { 	return goals.get(N).goal;   }

	public void setGoal(int N, double val)
    { 	goals.get(N).goal = val;   }

	public void setPole(int N, double val)
	{  	goals.get(N).pole = val;   }

	public void setNumberOfGoals(int N)
	{ N_concerns = N;}

	public void setNumberOfOptimGoals(int N)
	{N_optim_concerns = N;}

	public void setSensorNum(int N)
	//must return the number of UUV sensors
	{ N_services = N;}

	public void setSensorProperty(int goalN, int sensorID, double val)
	//must return a property corresponding to goal N of sensor with ID = sensorID
	//e.g., goal 1 = speed, goal 2 = energy consumption, then getSensorProperty(1,3) returns speed of UUV with sensor 3 turned on
	{ goals.get(goalN).sensorProperties.put(sensorID, val);}

	public void setSensorOptimProperty(int goalN, int sensorID, double val)
	//must return a property corresponding to an optimization goal N of sensor with ID = sensorID
	//i.e., returns sensor accuracy
	{ optimGoals.get(goalN).sensorOptimProperties.put(sensorID, val);}

	//public void chooseSensor(int sensorID)
	//must choose a sensor with ID = sensorID to be invoked next
	//most likely should interact with effector
	///{ }

    public int chooseSensors()
    //this method should be invoked invoked after each sensor invocation
    //it selects a sensor to be picked next
    {
    	int chosenSensor = 0;
        double probab= 0.0;
        if (pickValue>=adaptationPeriod)
            pickValue=0;
        for (int i=0;i<N_services;i++)
        {
        	//uncomment if required
        	//unchooseSensor(i);
            probab += Double.parseDouble(selectionProbabilities.get(i).toString());
            if (pickValue/adaptationPeriod<probab)
            {
            	//chooseSensor(i);
            	chosenSensor = i;
                probab =-1.0;
            }

            if ((i+1)%N_services==0) probab = 0.0;
        }
        pickValue++;
        return chosenSensor;
    }

    //-----------------------------------------------------
    //finally, pay attention to the method adapt() and its requirements (scroll down)

    public double getGoal(int l)
    {   return goals.get(l).goal;    }

    public void resetValues()
    {
    	N_services=DEFAULT_SENSOR_NUM;
        N_concerns = 0;
        N_optim_concerns = 0;
        currentOperatingMode = OperatingPhase.PREIDENTIF;

        selectionProbabilities = new HashMap<>();
        simplexError=0; pickValue=0;
        this.goals = new ArrayList<Goal>(N_concerns);
        this.optimGoals =  new ArrayList<OptimGoal>(N_optim_concerns);
    	identifiedConcern = 0;
    	
    	UUVspeed = 0.5;
    }

    public int getServicesN()
    { return N_services;}

    public void initGoals()
    {

            this.goals = new ArrayList<Goal>(N_concerns);
            this.optimGoals =  new ArrayList<OptimGoal>(N_optim_concerns);
        	for (int l=0; l<N_concerns;l++)
        		goals.add(new Goal());
        	for (int o=0; o<N_optim_concerns;o++)
        		optimGoals.add(new OptimGoal(GoalType.MINIMIZE)); //change if need to maximize

            //read requirements
        	//for (int l=0; l<N_concerns;l++)
        	//	goals.get(l).pole = getPole(l);

            for (int i=0;i<N_services;i++)
                selectionProbabilities.put(i,0.0);
           selectionProbabilities.put(0,1.0);
      }
    public void init()
    {
        if (currentOperatingMode == OperatingPhase.PREIDENTIF)
        {

        	for (int l=0; l<N_concerns;l++)
        	//reading sensor properties (speed, energy cons)
        	{
        		HashMap<Integer, Double> tmp = new HashMap<>();
        		for (int i=0;i<N_services;i++)
        		{
                        double prop = goals.get(l).sensorProperties.get(i+1);
                        System.out.println("myProp" + i + ": " + prop);
                      //  goals.get(l).sensorProperties.put(i, prop);
                        tmp.put(i, prop);
        		}
        		goals.get(l).maxValue+=Collections.max(tmp.values(),null)/0.1; //speed 0 to 5
            	goals.get(l).minValue+=Collections.min(tmp.values(),null)/5; //speed 0 to 5
            	tmp.clear();
            }

            for (int o=0; o<N_optim_concerns;o++)
          	{
            //reading sensor optim properties (accuracy)
        		for (int i=0;i<N_services;i++)
        		{
        			double optimProp = optimGoals.get(o).sensorOptimProperties.get(i+1);
                    System.out.println("myOptimProp" + o + ": " + optimProp);
                    	//optimGoals.get(o).sensorOptimProperties.put(o,optimProp);
                }
          	}

            System.out.println("\n\n\n\n\n\n\n ___________IDENTIFICATION__________ \n\n\n\n\n\n\n");
            currentOperatingMode = OperatingPhase.IDENTIFICATION;
        }

    }

    public void adapt(double measured[])
    //this method should be called (most likely by a probe) every X invocations of the UUV, where X = adaptationPeriod
    //array measured[] should contain the measured values of goals (speed, energy consumption) on average during previous X invocations
    {
    	//adaptation logic
    	for (int l=0; l<N_concerns;l++)
    	{
    		goals.get(l).goal = getCurGoal(l);
    		goals.get(l).error = goals.get(l).goal - measured[l];
    		System.out.println("Error " + l + ": " + goals.get(l).error);
		}

        // Identification phase
        if (currentOperatingMode == OperatingPhase.IDENTIFICATION)
        {
        	if (goals.get(identifiedConcern).controlDecision==0)
        		measured[identifiedConcern]=0;

            goals.get(identifiedConcern).dataForModelIdentification.put(goals.get(identifiedConcern).controlDecision, measured[identifiedConcern]);
            if (goals.get(identifiedConcern).controlDecision == (goals.get(identifiedConcern).maxValue-goals.get(identifiedConcern).minValue)*CONTROL_DECISION_UPPER_BOUND+goals.get(identifiedConcern).minValue )
            //goal identified
            {
            	computeNewModel(identifiedConcern);
    			goals.get(identifiedConcern).dataForModelIdentification = Maps.newHashMap();
    	        goals.get(identifiedConcern).kalman_noise_covariance = goals.get(identifiedConcern).kalman_process_noise_variace + goals.get(identifiedConcern).kalman_measuremnt_noise_variance;
            	goals.get(identifiedConcern).goal = getCurGoal(identifiedConcern);
            	goals.get(identifiedConcern).controlDecision = goals.get(identifiedConcern).goal;
            	goals.get(identifiedConcern).originalGoal = goals.get(identifiedConcern).goal;

            	if (identifiedConcern<N_concerns-1)
            		identifiedConcern++;
            	else
	            {
					System.out.println("\n\n\n\n\n\n\n ____________________CONTROL____________________ \n\n\n\n\n\n\n");
					currentOperatingMode = OperatingPhase.CONTROL;
	            }
            }
            else
            	goals.get(identifiedConcern).controlDecision = nextIdentificationControlDecision(identifiedConcern);

        }
        else  // Control phase
        {
	      for (int l=0; l<N_concerns;l++)
	      {
	    	  //controller update
             if (simplexError==0)//only if simplex produced a solution
             {
		         goals.get(l).lastControls.add(goals.get(l).controlDecision);
		         while (goals.get(l).lastControls.size() > SLOPE_MEASUREMENTS_DISTANCE)
		          	goals.get(l).lastControls.removeFirst();

		         goals.get(l).lastObservedValues.add(measured[l]);
		         while (goals.get(l).lastObservedValues.size() > SLOPE_MEASUREMENTS_DISTANCE)
		           	goals.get(l).lastObservedValues.removeFirst();

		        //using Kalman filter
                Double measured_slope = goals.get(l).slope;
                if (goals.get(l).lastControls.size() >= SLOPE_MEASUREMENTS_DISTANCE)
                {
                   measured_slope = (goals.get(l).lastObservedValues.getLast() - goals.get(l).lastObservedValues.getFirst())
                                        / (goals.get(l).lastControls.getLast() - goals.get(l).lastControls.getFirst());
                }
                measured_slope = lowPassFilter * goals.get(l).slope + (1 - lowPassFilter) * measured_slope;

                if (measured_slope.isInfinite() || measured_slope.isNaN())
                {
                    measured_slope = goals.get(l).slope;
                    goals.get(l).kalman_noise_covariance = goals.get(l).kalman_process_noise_variace + goals.get(l).kalman_measuremnt_noise_variance;
                }
                if (measured_slope>goals.get(l).slope*2)
                    measured_slope = goals.get(l).slope*2;
                else if (measured_slope<-goals.get(l).slope*2)
                    measured_slope = -goals.get(l).slope*2;

                goals.get(l).kalman_noise_covariance = goals.get(l).kalman_noise_covariance + goals.get(l).kalman_process_noise_variace;
                Double kalmanK = goals.get(l).kalman_noise_covariance / (goals.get(l).kalman_noise_covariance + goals.get(l).kalman_measuremnt_noise_variance);
                if (!kalmanK.isInfinite() && !kalmanK.isNaN()) {
                    goals.get(l).slope = goals.get(l).slope + kalmanK * (measured_slope - goals.get(l).slope);
                    goals.get(l).kalman_noise_covariance = (1 - kalmanK) * goals.get(l).kalman_noise_covariance;
                }
                if (goals.get(l).slope.isInfinite() || goals.get(l).slope.isNaN()) {
                 	goals.get(l).slope = 1d;
                 }
            }

            System.out.println("slope:"+goals.get(l).slope);

            //controller
            goals.get(l).controlDecision = calculateControlSignal(goals.get(l).oldControlDecision, goals.get(l).error, goals.get(l).pole, l);

          }

        }//end control mode if

        //CT + simplex
        double[] newControl = new double[N_concerns];
        for (int l=0; l<N_concerns;l++)
        {
	        newControl[l] = goals.get(l).controlDecision;
	        //System.out.println("oldcD"+ l + ": " + goals.get(l).oldControlDecision + "; error: "+ goals.get(l).error + " pole:" + goals.get(l).pole);

	        newControl[l]  = Math.max(newControl[l], goals.get(l).minValue);
	        newControl[l]  = Math.min(newControl[l], goals.get(l).maxValue);
	        System.out.println("----------------------------------");
	        System.out.println("controlDecision:"+newControl[l]);
	        System.out.println("measured "+ l + ": " + measured[l] + "; ");
        }

        simplexControl(newControl, OPTIM_MODE);
        //CT + simplex end

        // saving old control Decision
        for (int l=0; l<N_concerns;l++)
	        goals.get(l).oldControlDecision = goals.get(l).controlDecision;
    }

	private Double calculateControlSignal(double oldControlSignal, double error, double pole,int l)
	{
		Preconditions.checkArgument(goals.get(l).slope != 0d);
		double coeff_error = (1 - pole) / goals.get(l).slope;
		double control = oldControlSignal + coeff_error * error;
		return control;
	}

	public void simplexControl(double control[], int mode)
	{
		int concerns;

        if (currentOperatingMode == OperatingPhase.IDENTIFICATION)
        	concerns=1;
        else//CONTROL PHASE
        {
        	concerns = N_concerns;
        	identifiedConcern=0;
        }

        try
        {
        	//filling table: optimization coefficients
        	double []optimCoef = new double[N_services+1];
            for (int i=0;i<N_services;i++)
	            for (int o=0; o<N_optim_concerns;o++)
	                optimCoef[i] = Double.parseDouble(optimGoals.get(o).sensorOptimProperties.get(i+1).toString());
            optimCoef[N_services]=1;//+1*speed
            		
            LinearModel model = new LinearModel(new LinearObjectiveFunction(
            		optimCoef, 0, optimGoals.get(0).goalType));

            //filling table: variable coefficients
        	for (int l=0+identifiedConcern; l<concerns+identifiedConcern;l++)
        	{
        		double []curRow = new double[N_services+1];
                for (int i=0;i<N_services;i++)
               		curRow[i]= Double.parseDouble(goals.get(l).sensorProperties.get(i+1).toString());
                curRow[N_services]=-control[l];//-X*speed                		
                		
              	model.addConstraint(new LinearEquation(curRow, Relationship.EQ, 0));//control[l]

            }
          /* // uncomment to see the equations
            for (int l=0+identifiedConcern; l<concerns+identifiedConcern;l++)
        	{
        		List<LinearEquation> lst = model.getConstraints();
            	System.out.print("eq" + l + ": " + lst.get(l).getCoefficients());
        	}
        	System.out.println();*/
        	
        	//last row
        	double []lastRow = new double[N_services+1];
            for (int i=0;i<N_services;i++)
            {
               	lastRow[i]= 1;
            }
            model.addConstraint(new LinearEquation(lastRow, Relationship.EQ, 1));           


            //solving
        	SimplexSolver solver = new SimplexSolver(model, true);
            LinearEquation solution = solver.solve();

            //filling data and printing solution
            System.out.println("Solution: ");
            for (int i=0;i<N_services;i++)
            {
            	//System.out.println("EC"+ i + ": " + goals.get(0).sensorProperties.get(i+1));

            	selectionProbabilities.put(i, solution.getCoefficients().getEntry(i));            	
                if (solution.getCoefficients().getEntry(i) !=0)
                {
	                System.out.print(solution.getCoefficients().getEntry(i) + "*S" + (i+1));// + " + " + table[3][sol] + "*x" + x4);
	                if (i!=(N_services-1))
	                   System.out.print(" + ");
                }
            }      
            System.out.println(" ");                 
            UUVspeed = solution.getCoefficients().getEntry(N_services);
            System.out.println("UUV speed = " + UUVspeed);
            
            
            simplexError=0;
        }
        catch (UnboundedSolutionException e)
        	{System.err.println("Unbounded Solution"); simplexError=1; }
        catch (NoFeasibleSolutionException e)
        	{System.err.println("No feasible solution");}
	}

	private Double nextIdentificationControlDecision(int l) {
		double controlDec = (goals.get(l).maxValue-goals.get(l).minValue)*goals.get(l).nextIdentitificationControlDecision+goals.get(l).minValue ;
		goals.get(l).nextIdentitificationControlDecision = goals.get(l).nextIdentitificationControlDecision + IDENTIFICATION_PROCEDURE_STEP;
		if (goals.get(l).nextIdentitificationControlDecision > CONTROL_DECISION_UPPER_BOUND)
		{
			goals.get(l).nextIdentitificationControlDecision = CONTROL_DECISION_UPPER_BOUND;
        }
		return controlDec;
	}

	private double computeNewModel(int l)
	{
		SimpleRegression regressionModel = null;
		Preconditions.checkArgument(goals.get(l).dataForModelIdentification.size() >= 2);
		double[][] data = new double[goals.get(l).dataForModelIdentification.size()][2];
		int counter = 0;
		for (Double x : goals.get(l).dataForModelIdentification.keySet()) {
			data[counter][0] = x;
			data[counter][1] = goals.get(l).dataForModelIdentification.get(x);
			counter++;
		}
		regressionModel = new SimpleRegression(true);
		regressionModel.addData(data);
		goals.get(l).slope = regressionModel.getSlope();

		return regressionModel.getRSquare();
	}


}
