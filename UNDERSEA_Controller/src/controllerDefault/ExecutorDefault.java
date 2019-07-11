package controllerDefault;

import controller.Executor;

public class ExecutorDefault extends Executor {
	
	public ExecutorDefault() {
	}

	
	@Override
	public void run () {
		command = "Dummy command";
	}
}
