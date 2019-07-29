package com.type2labs.undersea.dsl;


import com.type2labs.undersea.agent.model.Sensor;
import com.type2labs.undersea.dsl.uuv.factory.FactoryProvider;
import com.type2labs.undersea.dsl.uuv.factory.SensorFactory;
import com.type2labs.undersea.dsl.uuv.model.AgentProxy;
import com.type2labs.undersea.utilities.Utility;

import java.io.File;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
class MoosConfigurationWriter {

    private static EnvironmentProperties environmentProperties;
    private static final SensorFactory sensorFactory = FactoryProvider.getSensorFactory();
    private static String buildDir;


    public static void init(EnvironmentProperties environmentProperties) {
        MoosConfigurationWriter.environmentProperties = environmentProperties;

        buildDir = Utility.getProperty(environmentProperties.getRunnerProperties(), "config.output");
    }

    private static void generateIvPHelmBlock(AgentProxy agent) {
        StringBuilder ivpBlock = new StringBuilder();
        ivpBlock.append("//-----------------------------\n");
        ivpBlock.append("// Helm IvP configuration block\n");
        ivpBlock.append("//-----------------------------\n");
        ivpBlock.append("ProcessConfig = pHelmIvP\n");
        ivpBlock.append("{\n");
        ivpBlock.append("\tAppTick\t\t\t= 4\n");
        ivpBlock.append("\tCommsTick\t\t= 4\n");
        ivpBlock.append("\tBehaviors\t\t= meta_" + agent.getName() + ".bhv\n");
        ivpBlock.append("\tVerbose\t\t\t= quiet\n");
        ivpBlock.append("\tok_skew\t\t\t= any\n");
        ivpBlock.append("\tactive_start\t= false\n");
        ivpBlock.append("\tDomain\t\t\t= course:0:359:360\n");
//        ivpBlock.append("\tDomain\t\t\t= depth:0:100:101\n");
        ivpBlock.append("\tDomain\t\t\t= speed:" + agent.getSpeedRange().getMin() + ":" + agent.getSpeedRange().getMax() + ":" + agent
                .getSpeedRange().getValue() + "\n");

        ivpBlock.append("}");

        //write
        Utility.exportToFile(MoosConfigurationWriter.buildDir + "/plug_pHelmIvP_" + agent.getName() + ".moos",
                ivpBlock.toString(),
                false);
    }

    private static void generateLaunchAndCleanScripts() {
        StringBuilder cleanScript = new StringBuilder();
        cleanScript.append("#!/bin/bash\n");
        cleanScript.append("\n");
        cleanScript.append("rm -rf\tMOOSLog_*\n");
        cleanScript.append("rm -rf\tLOG_*\n");
        cleanScript.append("rm -f\t*~\n");
        cleanScript.append("rm -f\t*.moos++\n");
        cleanScript.append("rm -f\t.LastOpenedMOOSLogDirectory\n");

        Utility.exportToFileWithPermissions(MoosConfigurationWriter.buildDir + File.separator + "clean.sh",
                cleanScript.toString(),
                false, Collections.singletonList(PosixFilePermission.OWNER_EXECUTE));

        String timeWarp =
                environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW);
        StringBuilder launchScript = new StringBuilder();

        launchScript.append("#---------------------\n");
        launchScript.append("# Launch the processes\n");
        launchScript.append("#---------------------\n");

        AgentProxy shoreside = environmentProperties.getShoreside();

        launchScript.append("printf \"Launching " + shoreside.getName() + " MOOS Community\"\n");
        launchScript.append("pAntler " + shoreside.getMetaFileName() + " >& /dev/null &\n\n");

        for (Map.Entry<String, AgentProxy> e : environmentProperties.getAgents().entrySet()) {
            AgentProxy agent = e.getValue();
            launchScript.append("printf \"Launching " + agent.getName() + " MOOS Community\"\n");
            launchScript.append("pAntler " + agent.getMetaFileName() + " >& /dev/null &\n\n");
        }

        launchScript.append("#--------------------------------------------------\n");
        launchScript.append("# Launch uMAC and kill everything upon exiting uMAC\n");
        launchScript.append("#--------------------------------------------------\n");

        launchScript.append("uMAC " + environmentProperties.getShoreside().getMetaFileName() + "\n");
        launchScript.append("printf \"Killing all processes...\\n\"\n");

        launchScript.append("kill");

        for (int i = 0; i < environmentProperties.getTotalNumberOfAgents(); i++) {
            launchScript.append(" %").append(i + 1);
        }

        launchScript.append("\nprintf \"Done killing processes...\\n\"\n");

        Utility.exportToFileWithPermissions(MoosConfigurationWriter.buildDir + File.separator + "launch.sh",
                launchScript.toString(),
                false, Collections.singletonList(PosixFilePermission.OWNER_EXECUTE));
    }

    private static void generateSensors() {
        for (Map.Entry<String, Sensor> entry : sensorFactory.getSensors().entrySet()) {
            Sensor sensor = entry.getValue();
            String filename = MoosConfigurationWriter.buildDir + "/plug_" + sensor.getName() + ".moos";
            Utility.exportToFile(filename, sensor.toString(), false);
        }
    }

    private static void generateShoreside() {
        StringBuilder shoreside = new StringBuilder();
        shoreside.append("//----------------------\n");
        shoreside.append("// Shoreside config file\n");
        shoreside.append("//----------------------\n");
        shoreside.append("#include plug_origin_info.moos\n");
        shoreside.append("\n");
        shoreside.append("//---------------------------\n");
        shoreside.append("// Antler configuration block\n");
        shoreside.append("//---------------------------\n");
        shoreside.append("ProcessConfig = ANTLER\n");
        shoreside.append("{\n");
        shoreside.append("  MSBetweenLaunches = 200\n");
        shoreside.append("\n");
        shoreside.append("\tRun = MOOSDB\t\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = pMarineViewer\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = pLogger\t\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = pShare\t\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = pHostInfo\t\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = uFldShoreBroker\t@ NewConsole = false\n");
        shoreside.append("}\n");

        shoreside.append("#include plug_pShare.moos\n");
        shoreside.append("#include plug_pHostInfo.moos\n");
        shoreside.append("#include plug_pLogger.moos\n");

        shoreside.append("//------------------------------------\n");
        shoreside.append("// uFldShoreBroker Configuration Block\n");
        shoreside.append("//------------------------------------\n");
        shoreside.append("ProcessConfig = uFldShoreBroker\n");
        shoreside.append("{\n");
        shoreside.append("\tAppTick       = 1\n");
        shoreside.append("\tCommsTick     = 1\n");
        shoreside.append("\n");
        shoreside.append("\tQBRIDGE  = DEPLOY, NODE_REPORT\n");
        shoreside.append("\tQBRIDGE  = STATION_KEEP, APPCAST_REQ\n");
        shoreside.append("\tQBRIDGE  = MOOS_MANUAL_OVERRIDE\n");
        shoreside.append("\n");
        shoreside.append("\tBRIDGE   = src=RETURN_ALL, alias=RETURN\n");
        shoreside.append("\tBRIDGE   = src=RETURN_$V,  alias=RETURN\n");
        shoreside.append("}\n");
        shoreside.append("\n");

        shoreside.append("//----------------------------------\n");
        shoreside.append("// pMarineViewer Configuration Block\n");
        shoreside.append("//----------------------------------\n");
        shoreside.append("ProcessConfig = pMarineViewer\n");
        shoreside.append("{\n");
        shoreside.append("\tAppTick    = 4\n");
        shoreside.append("\tCommsTick  = 4\n");
        shoreside.append("\n");
        shoreside.append("\tTIFF_FILE            = forrest19.tif\n");
        shoreside.append("\tset_pan_x            = -90\n");
        shoreside.append("\tset_pan_y            = -280\n");
        shoreside.append("\tzoom                 = 0.65\n");
        shoreside.append("\tvehicles_shape_scale = 1.5\n");
        shoreside.append("\tvehicles_name_mode   = names+shortmode\n");
        shoreside.append("\n");
        shoreside.append("\tpoint_viewable_labels   = false\n");
        shoreside.append("\tpolygon_viewable_labels = false\n");
        shoreside.append("\tappcast_viewable     = true\n");
        shoreside.append("\tappcast_color_scheme = indigo\n");
        shoreside.append("\thash_delta           = 50\n");
        shoreside.append("\thash_viewable        = true\n");
        shoreside.append("\thash_shade           = 0.35\n");
        shoreside.append("\tSCOPE = PHI_HOST_INFO\n\n");
        shoreside.append("\tBUTTON_ONE   = DEPLOY  # DEPLOY_ALL=true \n");
        shoreside.append("\tBUTTON_ONE   = MOOS_MANUAL_OVERRIDE_ALL=false \n");
        shoreside.append("\tBUTTON_ONE   = RETURN_ALL=false # STATION_KEEP_ALL=false\n");
        shoreside.append("\tBUTTON_TWO   = RETURN  # RETURN_ALL=true\n");
        shoreside.append("\tBUTTON_TWO   = STATION_KEEP_ALL=false\n");
        shoreside.append("\tBUTTON_THREE = STATION  # STATION_KEEP_ALL=true\n");
        shoreside.append("}\n");

        String fileName = "meta_shoreside.moos";

        AgentProxy agent = new AgentProxy();
        agent.setName("shoreside");
        agent.setMetaFileName(fileName);

        environmentProperties.addAgent(agent);

        Utility.exportToFile(MoosConfigurationWriter.buildDir + "/" + fileName,
                shoreside.toString(),
                false);
    }

    private static void generateTargetVehicleBlock(AgentProxy agent) {
        StringBuilder vehicleBlock = new StringBuilder();
        vehicleBlock.append("//-------------------------\n");
        vehicleBlock.append("// Meta vehicle config file\n");
        vehicleBlock.append("//-------------------------\n");
        vehicleBlock.append("#include plug_origin_info.moos\n");
        vehicleBlock.append("//---------------------------\n");
        vehicleBlock.append("// Antler configuration block\n");
        vehicleBlock.append("//---------------------------\n");
        vehicleBlock.append("ProcessConfig = ANTLER\n");
        vehicleBlock.append("{\n");
        vehicleBlock.append("\tMSBetweenLaunches = 200\n");
        vehicleBlock.append("\tRun = MOOSDB         @ NewConsole = false\n");
        vehicleBlock.append("\tRun = uSimMarine     @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pLogger        @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pNodeReporter  @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pMarinePID     @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pHelmIvP       @ NewConsole = false\n");
        vehicleBlock.append("\tRun = uProcessWatch  @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pShare         @ NewConsole = false\n");
        vehicleBlock.append("\tRun = uTimerScript   @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pHostInfo\t\t @ NewConsole = false\n");
        vehicleBlock.append("\tRun = sUUV           @ NewConsole = false\n");
        vehicleBlock.append("\tRun = uFldNodeBroker @ NewConsole = false\n");

        for (Sensor sensor : agent.getSensors()) {
            vehicleBlock.append("\tRun = sSensor\t\t @ NewConsole = false ~" + sensor.getName() + "\n");
        }

        vehicleBlock.append("}\n\n");

        vehicleBlock.append("#include plug_uSimMarine.moos\n");
        vehicleBlock.append("#include plug_pLogger.moos\n");
        vehicleBlock.append("#include plug_pNodeReporter.moos\n");
        vehicleBlock.append("#include plug_pMarinePID.moos\n");
        vehicleBlock.append("#include plug_pHelmIvP_" + agent.getName() + ".moos\n");
        vehicleBlock.append("#include plug_uProcessWatch.moos\n");
        vehicleBlock.append("#include plug_pShare.moos\n");
        vehicleBlock.append("#include plug_pHostInfo.moos\n");
        vehicleBlock.append("#include plug_uFldNodeBroker.moos\n");
        vehicleBlock.append("#include plug_uTimerScript.moos\n");
        vehicleBlock.append("#include plug_agent_" + agent.getName() + ".moos\n");

        for (Sensor sensor : agent.getSensors()) {
            vehicleBlock.append("#include plug_" + sensor.getName() + ".moos\n");
        }

        String fileName = "meta_vehicle_" + agent.getName() + ".moos";
        agent.setMetaFileName(fileName);

        Utility.exportToFile(MoosConfigurationWriter.buildDir + File.separator + fileName,
                vehicleBlock.toString(),
                false);
    }

    static void run() {
        generateShoreside();
        generateSensors();

        Map<String, AgentProxy> agents = environmentProperties.getAgents();

        for (Map.Entry<String, AgentProxy> entry : agents.entrySet()) {
            AgentProxy agent = entry.getValue();

            //generate agent moos block
            Utility.exportToFile(MoosConfigurationWriter.buildDir + File.separator + "plug_agent_" + agent.getName() +
                            ".moos",
                    agent.toString(), false);

            //generate target vehicle block
            generateTargetVehicleBlock(agent);

            //generate IvPHelm vehicle block
            generateIvPHelmBlock(agent);
        }

        generateLaunchAndCleanScripts();
    }

    private static void writeHostInfo(StringBuilder vehicleBlock) {
        vehicleBlock.append("//------------------------------\n");
        vehicleBlock.append("// pHostInfo configuration block\n");
        vehicleBlock.append("//------------------------------\n");
        vehicleBlock.append("ProcessConfig = pHostInfo\n");
        vehicleBlock.append("{\n");
        vehicleBlock.append("\tAppTick\t\t= 1\n");
        vehicleBlock.append("\tCommsTick\t= 1\n");
        vehicleBlock.append("\n");
        vehicleBlock.append("\tDEFAULT_HOSTIP_FORCE = localhost\n");
        vehicleBlock.append("}\n");
    }

}