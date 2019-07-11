package controller.uuv;

import java.util.ArrayList;
import java.util.List;

public class UUV{
	/** name*/
	private String name;
	
	/** speed*/
	private double speed;
	
	/** speed history as decided by the controller*/
	protected List<Double> speedList;


	public  UUV(String name){
		this.name			= name;
		this.speedList		= new ArrayList<Double>();
	}
	
	public  String getName(){
		return this.name;
	}

	public void setSpeed(double s){
		this.speed = s;
		this.speedList.add(s);
	}

	public double getSpeed(){
		return this.speed;
	}

	public List<Double> getSpeedList(){
		return this.speedList;
	}

	public boolean speedSame(){
		int size = speedList.size();
		if (size >1)
			return ( (speedList.get(size-1)) == (speedList.get(size-2)));
		return false;
	}

}