package com.type2labs.undersea.common.agent;

import java.io.File;
import java.util.Properties;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class AgentMetaData {

    private boolean isMaster = false;

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    private String metadataFileName;
    private Properties runnerProperties;

    public Properties getRunnerProperties() {
        return runnerProperties;
    }

    public void setRunnerProperties(Properties runnerProperties) {
        this.runnerProperties = runnerProperties;
    }

    private File missionDirectory;
    private String missionName;

    public File getMissionDirectory() {
        return missionDirectory;
    }

    public void setMissionDirectory(File missionDirectory) {
        this.missionDirectory = missionDirectory;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    private int hardwarePort;

    public String getMetadataFileName() {
        return metadataFileName;
    }

    public void setMetadataFileName(String metadataFileName) {
        this.metadataFileName = metadataFileName;
    }

    public int getHardwarePort() {
        return hardwarePort;
    }

    public void setHardwarePort(int hardwarePort) {
        this.hardwarePort = hardwarePort;
    }
}
