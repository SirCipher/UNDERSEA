package controller;

import java.io.IOException;
import java.util.TimerTask;

import auxiliary.Utility;
import controller.comms.Client;

public class Controller extends TimerTask{

	/** Sensor handle*/
	private Sensor sensor;
	
	/** Effector handle*/
	private Effector effector;
	
	/** Comms handle*/
	private Client client;

	/** Monitor handle*/
	protected Monitor monitor;

	/** Analyser handle*/
	protected Analyser analyser;

	/** Planner handle*/
	protected Planner planner;

	/** Executor handle*/
	protected Executor executor;
	
	/** Socket details*/
	private String host;//	= "localhost";
	private int port;// 		= 8888;
	
    /** Simulation speed*/
    private static long SIMULATION_SPEED;

    
    private long start = System.currentTimeMillis();		

	
	/**
	 * Controller constructor
	 */
	public Controller(Monitor monitor, Analyser analyser, Planner planner, Executor executor) {		
	    try {
	    	//init comms client
	    	host	= "localhost";
	    	port	= Integer.parseInt(Utility.getProperty("PORT"));
	    	client	= new Client(host, port);
	    	
		    //init MAPE
			sensor 		= new Sensor(client);		    
		    effector 	= new Effector(client);		    
		    
		    this.monitor 	= monitor;
		    this.analyser	= analyser;
		    this.planner	= planner;
		    this.executor	= executor;
		    
			//init simulation speed
			SIMULATION_SPEED = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_SPEED")));
	    }
	    catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * start the controller
	 */
	public void run(){
		double initTime = (System.currentTimeMillis() - start)/1000.0*SIMULATION_SPEED;
		Knowledge.getInstance().addToInitTimeList(initTime);
		System.out.println(initTime +"\tRequested UUV state");
		
		sensor.run();
		double sensorTime = (System.currentTimeMillis() - start)/1000.0*SIMULATION_SPEED ; 
		System.out.println(sensorTime +"\tReceived:\t"+ sensor.getReply());				
		
		monitor.run();
		
		analyser.run();
		
		planner.run();
		
		executor.run();
		
		double controllerTime = (System.currentTimeMillis() - start)/1000.0*SIMULATION_SPEED ; 
		System.out.println(controllerTime +"\tNew config:\t" + executor.getCommand());

		effector.setCommand(executor.getCommand());
		effector.run();
		
		double endTime = (System.currentTimeMillis() - start)/1000.0*SIMULATION_SPEED ;
		System.out.println(endTime +"\tApplied?\t"+ effector.getReply() +"\n");
		Knowledge.getInstance().addToEndTimeList(endTime);
	}
	
	
	public void shutDown(){
		boolean closed = false;
		
		try {
			closed = client.shutDown();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if (closed)
				System.out.println("Comms terminated correctly!");
			else
				System.out.println("Something was wrong with terminating comms!");
		}
	}
}
