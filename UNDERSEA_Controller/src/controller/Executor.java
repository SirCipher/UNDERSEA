package controller;

public abstract class Executor {

	protected String command;

	public Executor() {
	}

	/** Construct command:
	 *  it should be in the form: SPEED=xx.xx,SENSOR1=x,SENSOR2=x,....
	 */
	public abstract void run();
	
	
	/** Get the command: 
	 *  it should be in the form: SPEED=xx.xx,SENSOR1=x,SENSOR2=x,....
	 * */
	public String getCommand() {
		return command;
	}	
}
