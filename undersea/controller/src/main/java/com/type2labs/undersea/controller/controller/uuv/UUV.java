package com.type2labs.undersea.controller.controller.uuv;

import java.util.ArrayList;
import java.util.List;

// TODO: Refactor to agent
public class UUV {
    /**
     * speed history as decided by the controller
     */
    protected List<Double> speedList;
    /**
     * name
     */
    private String name;
    /**
     * speed
     */
    private double speed;


    public UUV(String name) {
        this.name = name;
        this.speedList = new ArrayList<Double>();
    }

    public String getName() {
        return this.name;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double s) {
        this.speed = s;
        this.speedList.add(s);
    }

    public List<Double> getSpeedList() {
        return this.speedList;
    }

    public boolean speedSame() {
        int size = speedList.size();
        if (size > 1)
            return ((speedList.get(size - 1)) == (speedList.get(size - 2)));
        return false;
    }

}