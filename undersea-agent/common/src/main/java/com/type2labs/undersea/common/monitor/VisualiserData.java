package com.type2labs.undersea.common.monitor;

import java.io.Serializable;

public class VisualiserData implements Serializable {

    private static final long serialVersionUID = 1980452255341789631L;
    private String name;
    private String raftPeerId;
    private String multiRoleStatus = "N/A";
    private String serviceManagerStatus = "N/A";
    private String raftRole = "N/A";
    private String leaderPeerId;
    private int noAssignedTasks;
    private int completedTasks;
    private int port;
    private int noPeers;

    public VisualiserData(String raftPeerId, String name) {
        this.raftPeerId = raftPeerId;
        this.name = name;
    }

    public VisualiserData(String name, String raftPeerId, String multiRoleStatus, String serviceManagerStatus,
                          String raftRole, String leaderPeerId, int noAssignedTasks, int completedTasks, int port,
                          int noPeers) {
        this.name = name;
        this.raftPeerId = raftPeerId;
        this.multiRoleStatus = multiRoleStatus;
        this.serviceManagerStatus = serviceManagerStatus;
        this.raftRole = raftRole;
        this.leaderPeerId = leaderPeerId;
        this.noAssignedTasks = noAssignedTasks;
        this.completedTasks = completedTasks;
        this.port = port;
        this.noPeers = noPeers;
    }

    public int getPort() {
        return port;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public String getMultiRoleStatus() {
        return multiRoleStatus;
    }

    @Override
    public String toString() {
        return "VisualiserData{" +
                "name='" + name + '\'' +
                ", raftPeerId='" + raftPeerId + '\'' +
                ", multiRoleStatus='" + multiRoleStatus + '\'' +
                ", serviceManagerStatus='" + serviceManagerStatus + '\'' +
                ", raftRole='" + raftRole + '\'' +
                ", leaderPeerId='" + leaderPeerId + '\'' +
                ", noTasks=" + noAssignedTasks +
                ", completedTasks=" + completedTasks +
                ", port=" + port +
                ", noPeers=" + noPeers +
                '}';
    }

    public String getServiceManagerStatus() {
        return serviceManagerStatus;
    }

    public void setServiceManagerStatus(String serviceManagerStatus) {
        this.serviceManagerStatus = serviceManagerStatus;
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

    public void setRaftRole(String raftRole) {
        this.raftRole = raftRole;
    }

    public int getNoAssignedTasks() {
        return noAssignedTasks;
    }

    public String getLeaderPeerId() {
        return leaderPeerId;
    }

    public void setLeaderPeerId(String leaderPeerId) {
        this.leaderPeerId = leaderPeerId;
    }

    public int getNoPeers() {
        return noPeers;
    }
}
