package com.type2labs.undersea.common.monitor;

import java.io.Serializable;

public class VisualiserData implements Serializable {

    private static final long serialVersionUID = 1980452255341789631L;
    private final String name;
    private final String raftPeerId;
    private final String multiRoleStatus;
    private String serviceManagerStatus;
    private final String raftRole;
    private final String leaderPeerId;

    public void setServiceManagerStatus(String serviceManagerStatus) {
        this.serviceManagerStatus = serviceManagerStatus;
    }

    private final int noTasks;
    private final int completedTasks;
    private final int port;
    private final int noPeers;

    public int getPort() {
        return port;
    }

    public VisualiserData(String name, String raftPeerId, String multiRoleStatus, String serviceManagerStatus,
                          String raftRole, String leaderPeerId, int noTasks, int completedTasks, int port,
                          int noPeers) {
        this.name = name;
        this.raftPeerId = raftPeerId;
        this.multiRoleStatus = multiRoleStatus;
        this.serviceManagerStatus = serviceManagerStatus;
        this.raftRole = raftRole;
        this.leaderPeerId = leaderPeerId;
        this.noTasks = noTasks;
        this.completedTasks = completedTasks;
        this.port = port;
        this.noPeers = noPeers;
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

    public String getLeaderPeerId() {
        return leaderPeerId;
    }

    public int getNoPeers() {
        return noPeers;
    }
}
