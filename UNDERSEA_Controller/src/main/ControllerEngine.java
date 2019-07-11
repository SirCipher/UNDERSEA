package main;

import java.util.Date;
import java.util.Timer;

import auxiliary.Utility;
import controller.Controller;
import controller.Knowledge;

public class ControllerEngine {

	private ControllerEngine() {}
	
    /** Time window for invoking the uuv*/
    private static long TIME_WINDOW = Math.round(Double.parseDouble(Utility.getProperty("TIME_WINDOW")) * 1000);
    
    /** Simulation time*/
    private static long SIMULATION_TIME = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_TIME")) * 1000);

    /** Simulation speed*/
    private static long SIMULATION_SPEED = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_SPEED")));

	
	public static void start(Controller controller) {
		try {
			System.out.println("Controller begins! :" + new Date());
						
			//schedule
			Timer timer = new Timer();
			timer.schedule(controller, 0, TIME_WINDOW/SIMULATION_SPEED);

			// wait until the simulation time is passed
			try {
				Thread.sleep(SIMULATION_TIME/SIMULATION_SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}

			System.out.println("Controller shutting down! :" + new Date());
			timer.cancel();
			controller.shutDown();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
			Knowledge.getInstance().logData();
			System.exit(1);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
