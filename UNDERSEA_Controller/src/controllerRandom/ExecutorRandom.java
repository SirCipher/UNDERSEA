package controllerRandom;

import java.util.Iterator;

import controller.Executor;
import controller.Knowledge;

public class ExecutorRandom extends Executor {

	public ExecutorRandom() {
	}

	
	@Override
	public void run () {
		command = "SPEED="   + (Knowledge.getInstance().getUUVspeed()) +",";
		
		Iterator<String> it = Knowledge.getInstance().sensorsMap.keySet().iterator();
		while (it.hasNext()){
			String sensorName = it.next();
			command += sensorName +"="+ (Knowledge.getInstance().getSensorState(sensorName));
			
			if (it.hasNext())
				command += ",";
		}
	}		
}
