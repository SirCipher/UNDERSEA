package controller.uuv;

import java.util.ArrayList;
import java.util.List;

public class UUVSensor{
	/** name*/
	private String name;
	
	/** current rate*/
	private double currentRate;

	/** rate history*/
	protected List<Double> ratesList;
	
	/** current state*/
	private int currentState;

	/** states history as determined by the controller*/
	protected List<Integer> statesList;

	/** num of readings every time_window seconds*/
	protected List<Integer> readingsList;
	
	/** num of accurate readings every time_window seconds*/
	protected List<Integer> accurateReadingsList;
	

	
	public UUVSensor(String name, double rate){
		this.name					= name;
		this.currentRate			= rate;
		this.currentState			= -1;
		this.ratesList				= new ArrayList<Double>();
		this.statesList				= new ArrayList<Integer>();
		this.readingsList			= new ArrayList<Integer>();
		this.accurateReadingsList	= new ArrayList<Integer>();
	}
			
	
	public String getName(){
		return this.name;
	}
	
	public double getCurrentRate(){
		return this.currentRate;
	}

	public double getCurrentState(){
		return this.currentState;
	}
	
	public List<Double> getRatesList(){
		return this.ratesList;
	}

	public List<Integer> getStatesList(){
		return this.statesList;
	}

	public List<Integer> getReadingsList(){
		return this.readingsList;
	}

	public List<Integer> getAccurateReadingsList(){
		return this.accurateReadingsList;
	}
	
	public void setRate(double rate){
		this.currentRate = rate;
		this.ratesList.add(rate);
	}

	public void setState(int state){
		this.currentState = state;
		this.statesList.add(state);
	}

	public void setReadings(int readings){
		this.readingsList.add(readings);
	}

	public void setAccurateReadings(int readings){
		this.accurateReadingsList.add(readings);
	}

	
	
	public boolean rateSame(){
		int size = ratesList.size();
		return ( (ratesList.get(size-1)) == (ratesList.get(size-2)));
	}

}