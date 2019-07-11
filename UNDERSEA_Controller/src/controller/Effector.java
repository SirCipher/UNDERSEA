package controller;

import java.io.IOException;

import controller.comms.Client;

public class Effector {

	/** Communication handles*/
    private Client client;			

    /** Command to send to managed system*/
    private String command;

    /** Reply from managed system*/
    private String reply;

    
    
    /**
     * Constructor: create a new sensor
     */
	public Effector(Client client) {
		this.client = client;
	}

	
	protected void run (){
		try {
			reply = client.write(command); 
		} 
		catch (IOException e) {
			e.printStackTrace();
			reply = "ERROR";
		}
	}
	
	
	public void setCommand(String command){
		this.command = command;
	}
	
	
	public String getReply(){
		return this.reply;
	}
}
