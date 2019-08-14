package com.type2labs.undersea.common.monitor;

import java.io.Serializable;

public class VisualiserData implements Serializable {

    private static final long serialVersionUID = 1980452255341789631L;
    private String name;
    private String raftRole;
    private int noTasks;
    private double[] pos;


    public VisualiserData(String name, String raftRole, int noTasks, double[] pos) {
        this.name = name;
        this.raftRole = raftRole;
        this.noTasks = noTasks;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public String getRaftRole() {
        return raftRole;
    }

    public int getNoTasks() {
        return noTasks;
    }

    public double[] getPos() {
        return pos;
    }
}
