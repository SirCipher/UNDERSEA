package com.type2labs.undersea.common.monitor;

import java.io.Serializable;

public class VisualiserData implements Serializable {

    private static final long serialVersionUID = 1980452255341789631L;
    private final String name;
    private final String raftPeerId;
    private final String raftRole;
    private final int noTasks;
    private final double[] pos;


    public VisualiserData(String name, String raftPeerId, String raftRole, int noTasks, double[] pos) {
        this.name = name;
        this.raftPeerId = raftPeerId;
        this.raftRole = raftRole;
        this.noTasks = noTasks;
        this.pos = pos;
    }

    public String getRaftPeerId() {
        return raftPeerId;
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
