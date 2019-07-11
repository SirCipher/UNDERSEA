package controllerCT;

import controller.Knowledge;
import controller.Monitor;

public class MonitorCT extends Monitor {

	public MonitorCT() {
	}

	@Override
	public void run() {		
		Knowledge.getInstance().analysisRequired = true; //always analyze
	}	

}
