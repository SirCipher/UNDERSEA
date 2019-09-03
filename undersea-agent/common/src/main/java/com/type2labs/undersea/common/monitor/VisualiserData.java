package com.type2labs.undersea.common.monitor;

import java.io.Serializable;

public class VisualiserData implements Serializable {

    private static final long serialVersionUID = 1980452255341789631L;
    private final String name;
    private final String raftPeerId;
    private final String multiRoleStatus;
    private final String serviceManagerStatus;
    private final String raftRole;
    private final int noTasks;
    private final int completedTasks;

    public VisualiserData(String name, String raftPeerId, String multiRoleStatus, String serviceManagerStatus,
                          String raftRole, int noTasks,
                          int completedTasks) {
        this.name = name;
        this.raftPeerId = raftPeerId;
        this.multiRoleStatus = multiRoleStatus;
        this.serviceManagerStatus = serviceManagerStatus;
        this.raftRole = raftRole;
        this.noTasks = noTasks;
        this.completedTasks = completedTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public String getMultiRoleStatus() {
        return multiRoleStatus;
    }

    public String getServiceManagerStatus() {
        return serviceManagerStatus;
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

}
