package controllerPMC;

import controller.Knowledge;
import controller.Monitor;

public class MonitorPMC extends Monitor {

	public MonitorPMC() {
	}

	@Override
	public void run() {
		boolean analysisRequired = Knowledge.getInstance().systemStateChanged();
		Knowledge.getInstance().analysisRequired  = analysisRequired;
	}	

}
