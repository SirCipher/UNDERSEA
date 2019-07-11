package controllerCT;

import java.util.HashMap;

public class Sensor {

	HashMap<Integer, Double> properties = new HashMap<>();
	//goal name
	public double energry;
	public double rate;
	public double accuracy;
	public double speed;
	public int ID;

	public Sensor()
	{
		this.properties = new HashMap<>();
	}

	public Sensor(int ID, double p1, double p2,double p3)
	{
		this.properties = new HashMap<>();
		this.ID = ID;
		this.energry = p1;
		this.rate = p2;
		this.accuracy = p3;
	}
}