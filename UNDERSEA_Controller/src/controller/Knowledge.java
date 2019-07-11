package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.Utility;
import controller.uuv.UUV;
import controller.uuv.UUVSensor;
import controllerPMC.prism.PMCResult;

public class Knowledge {
	private static Knowledge knowledge = null;	
	
	private Knowledge() {
	}

	/** UUV*/
	public UUV uuv = new UUV("name");

	/** Sensors*/
	public Map<String, UUVSensor> sensorsMap = new HashMap<String, UUVSensor>();
	
    /** Structure that keeps the result after PMC */
    public Map<Integer,PMCResult> PMCResultsMap = new HashMap<Integer, PMCResult>();    

    /** Keeps the times when the controller started executing the MAPE loop (i.e, after every "time_window" seconds)*/
    public List<Double> initTimeList = new ArrayList<Double>();    

    /** Keeps the times when the controller completed the execution of its MAPE loop*/
    public List<Double> endTimeList = new ArrayList<Double>();    

	/** flag showning whether analysis is required*/
	public boolean analysisRequired = false;

	
	public static Knowledge getInstance(){
		if (knowledge == null)
			knowledge = new Knowledge();
		return knowledge;
	}
	
	
	public void setUUVspeed (double s){
		uuv.setSpeed(s);
	}	
	
	public double getUUVspeed (){
		return uuv.getSpeed();
	}
	
	
	public void setSensorRate (String sensorName, double rate){
		if (!sensorsMap.containsKey(sensorName))
			sensorsMap.put(sensorName, new UUVSensor(sensorName, rate));
		sensorsMap.get(sensorName).setRate(rate);
	}
	
	
	public double getSensorRate (String sensorName){
		try{
			return sensorsMap.get(sensorName).getCurrentRate();
		} catch (NullPointerException e){
			return 0;
		}
	}
	
	
	public void setSensorState (String sensorName, int state){
		sensorsMap.get(sensorName).setState(state);
	}

	
	public double getSensorState (String sensorName){
		try{
			return sensorsMap.get(sensorName).getCurrentState();
		} catch (NullPointerException e){
			return 0;
		}
	}
	
	
	public void setSensorReadings (String sensorName, int readings){
		sensorsMap.get(sensorName).setReadings(readings);
	}

	
	public void setSensorAccurateReadings (String sensorName, int accurateReadings){
		sensorsMap.get(sensorName).setAccurateReadings(accurateReadings); 
	}
	
	
	public void addToInitTimeList (double time){
		initTimeList.add(time);
	}

	public void addToEndTimeList (double time){
		endTimeList.add(time);
	}

	
	public boolean systemStateChanged(){
		if (!uuv.speedSame())
			return true;
		for (UUVSensor uuvSensor : sensorsMap.values()){
			if (!uuvSensor.rateSame())
				return true;
		}
		return false;
	}
	
	
	public void logData(){
		StringBuilder outputStr = new StringBuilder();
		
		//make header
		String header = "Start Time, End Time,";
		for (String sensorName : sensorsMap.keySet()){
			header += sensorName +"_Rate,"; 
			header += sensorName +"_Readings,";
			header += sensorName +"_AccReadings,";
			header += sensorName +"_State,";
		}
		header += "UUVspeed\n";
		outputStr.append(header);
		
		for (int i=0; i<uuv.getSpeedList().size(); i++){
			//append times
			outputStr.append(initTimeList.get(i) + ",");
			outputStr.append(endTimeList.get(i) + ",");
			
			//append sensor rates first
			for (UUVSensor sensor : sensorsMap.values()){
				outputStr.append(sensor.getRatesList().get(i) + ","); 
				outputStr.append(sensor.getReadingsList().get(i) + ","); 
				outputStr.append(sensor.getAccurateReadingsList().get(i) + ","); 
				outputStr.append(sensor.getStatesList().get(i) + ","); 
			}
			//append current UUV speed
			outputStr.append(uuv.getSpeedList().get(i) + "\n");
		}
		

		try {
			Utility.fileCreate("log", true);
			String filename = "log" + File.separator + "log_" + Calendar.getInstance().getTime() +".csv";
			Utility.exportToFile(filename, outputStr.toString(), false);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	public void addResult (int index, PMCResult result){
		PMCResultsMap.put(index, result);
	}

}


