package undersea;

import undersea.auxiliary.Utility;
import undersea.uuv.dsl.factory.FactoryProvider;
import undersea.uuv.dsl.factory.SensorFactory;
import undersea.uuv.dsl.model.Sensor;
import undersea.uuv.dsl.model.UUV;
import undersea.uuv.properties.SimulationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
class MoosConfigurationWriter {

    private static SimulationProperties simulationProperties = SimulationProperties.getInstance();
    private static SensorFactory sensorFactory = FactoryProvider.getSensorFactory();

    private static void generateControllerProperties(UUV uuv) {
        try {
            String propertiesDirName = ParserEngine.buildDir;
            String propertiesFilename = propertiesDirName + File.separator + "resources" + File.separator + "config" +
                    ".properties";
            File propertiesDir = new File(propertiesDirName);
            File propertiesFile = new File(propertiesFilename);

            if (!propertiesDir.exists()) {
                if (propertiesDir.mkdirs()) {
                    throw new IOException("Unable to create properties directory: " + propertiesDir.getAbsolutePath());
                }
            }

            if (!propertiesFile.exists()) {
                if (!propertiesFile.getParentFile().mkdirs() || !propertiesFile.createNewFile()) {
                    throw new IOException("Unable to create properties file in directory: " + propertiesFile.getAbsolutePath());
                }
            }

            Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesFilename));
            properties.put("TIME_WINDOW",
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.TIME_WINDOW));
            properties.put("SIMULATION_TIME",
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_TIME));
            properties.put("SIMULATION_SPEED",
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_SPEED));
            properties.put("PORT",
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.PORT_START));

            StringBuilder sensorsNames = new StringBuilder();

            for (int i = 0; i < uuv.getSensors().size(); i++) {
                Sensor sensor = uuv.getSensors().get(i);
                sensorsNames.append(sensor.getName());

                if (i + 1 == uuv.getSensors().size()) {
                    sensorsNames.append(",");
                }
            }

            properties.put("SENSORS", sensorsNames.toString());
            properties.put("SPEED", uuv.getSpeedMin() + "," + uuv.getSpeedMax() + "," + uuv.getSpeedSteps());

            FileOutputStream out = new FileOutputStream(propertiesFile);
            properties.store(out, null);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateIvPHelmBlock(UUV uuv) {
        StringBuilder ivpBloxk = new StringBuilder();
        ivpBloxk.append("//------------------------------------------\n");
        ivpBloxk.append("// Helm IvP configuration  block\n");
        ivpBloxk.append("//------------------------------------------\n");
        ivpBloxk.append("ProcessConfig = pHelmIvP\n");
        ivpBloxk.append("{\n");
        ivpBloxk.append("\t   AppTick        = 4\n");
        ivpBloxk.append("\t   CommsTick      = 4\n");
        ivpBloxk.append("\t   Behaviors      = " + uuv.getBehaviourFileName() + "\n");
        ivpBloxk.append("\t   Verbose        = quiet\n");
        ivpBloxk.append("\t   ok_skew        = any\n");
        ivpBloxk.append("\t   active_start   = false\n");
        ivpBloxk.append("\t   Domain         = course:0:359:360\n");
        ivpBloxk.append("\t   Domain         = depth:0:100:101\n");
        ivpBloxk.append("\t   Domain         = speed:" + uuv.getSpeedMin() + ":" + uuv.getSpeedMax() + ":" + uuv
                .getSpeedSteps() + "\n");

        ivpBloxk.append("}");

        File bhvInput = new File(ParserEngine.missionDirectory + File.separator + uuv.getBehaviourFileName());

        File bhvCopied =
                new File(ParserEngine.missionDir + File.separator + uuv.getBehaviourFileName());
        Utility.createFile(bhvCopied);

        Utility.copyFile(bhvInput, bhvCopied);

        //write
        Utility.exportToFile(ParserEngine.missionDir + "/plug_pHelmIvP_" + uuv.getName() + ".moos", ivpBloxk.toString(),
                false);
    }

    private static void generateSensors() {
        for (Map.Entry<String, Sensor> entry : sensorFactory.getSensors().entrySet()) {
            Sensor sensor = entry.getValue();
            String filename = ParserEngine.missionDir + "/plug_" + sensor.getName() + ".moos";
            Utility.exportToFile(filename, sensor.toString(), false);
        }
    }

    private static void generateShoreside() {
        StringBuilder shoreside = new StringBuilder();
        shoreside.append("//------------------------------------------\n");
        shoreside.append("// Shoreside config file\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("#include plug_origin_info.moos\n");
        shoreside.append("\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("// Antler configuration block\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("ProcessConfig = ANTLER\n");
        shoreside.append("{\n");
        shoreside.append("  MSBetweenLaunches = 200\n");
        shoreside.append("\n");
        shoreside.append("\tRun = MOOSDB\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = pMarineViewer\t@ NewConsole = false\n");
        shoreside.append("\tRun = pLogger\t@ NewConsole = false\n");
        shoreside.append("\tRun = pShare\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = pHostInfo\t\t@ NewConsole = false\n");
        shoreside.append("\tRun = uFldShoreBroker\t@ NewConsole = false\n");
        shoreside.append("}\n");

        shoreside.append("#include plug_pShare.moos\n");
        shoreside.append("#include plug_pHostInfo.moos\n");
        shoreside.append("#include plug_pLogger.moos\n");

        shoreside.append("//--------------------------------------------------\n");
        shoreside.append("// uFldShoreBroker Configuration Block\n");
        shoreside.append("//--------------------------------------------------\n");
        shoreside.append("ProcessConfig = uFldShoreBroker\n");
        shoreside.append("{\n");
        shoreside.append("  AppTick       = 1\n");
        shoreside.append("  CommsTick     = 1\n");
        shoreside.append("\n");
        shoreside.append("  QBRIDGE  = DEPLOY, NODE_REPORT\n");
        shoreside.append("  QBRIDGE  = STATION_KEEP, APPCAST_REQ\n");
        shoreside.append("  QBRIDGE  = MOOS_MANUAL_OVERRIDE\n");
        shoreside.append("\n");
        shoreside.append("  BRIDGE   = src=RETURN_ALL, alias=RETURN\n");
        shoreside.append("  BRIDGE   = src=RETURN_$V,  alias=RETURN\n");
        shoreside.append("}\n");
        shoreside.append("\n");

        shoreside.append("//--------------------------------------------------\n");
        shoreside.append("// pMarineViewer Configuration Block\n");
        shoreside.append("//--------------------------------------------------\n");
        shoreside.append("ProcessConfig = pMarineViewer\n");
        shoreside.append("{\n");
        shoreside.append("  AppTick    = 4\n");
        shoreside.append("  CommsTick  = 4\n");
        shoreside.append("\n");
        shoreside.append("  TIFF_FILE            = forrest19.tif\n");
        shoreside.append("  set_pan_x            = -90\n");
        shoreside.append("  set_pan_y            = -280\n");
        shoreside.append("  zoom                 = 0.65\n");
        shoreside.append("  vehicles_shape_scale = 1.5\n");
        shoreside.append("  vehicles_name_mode   = names+shortmode\n");
        shoreside.append("\n");
        shoreside.append("  point_viewable_labels   = false\n");
        shoreside.append("  polygon_viewable_labels = false\n");
        shoreside.append("  appcast_viewable     = true\n");
        shoreside.append("  appcast_color_scheme = indigo\n");
        shoreside.append("  hash_delta           = 50\n");
        shoreside.append("  hash_viewable        = true\n");
        shoreside.append("  hash_shade           = 0.35\n");
        shoreside.append("\n");
        shoreside.append("  SCOPE = PHI_HOST_INFO\n");
        shoreside.append("\n");
        shoreside.append("  BUTTON_ONE   = DEPLOY  # DEPLOY_ALL=true \n");
        shoreside.append("  BUTTON_ONE   = MOOS_MANUAL_OVERRIDE_ALL=false \n");
        shoreside.append("  BUTTON_ONE   = RETURN_ALL=false # STATION_KEEP_ALL=false\n");
        shoreside.append("\n");
        shoreside.append("  BUTTON_TWO   = RETURN  # RETURN_ALL=true\n");
        shoreside.append("  BUTTON_TWO   = STATION_KEEP_ALL=false\n");
        shoreside.append("\n");
        shoreside.append("  BUTTON_THREE = STATION  # STATION_KEEP_ALL=true\n");
        shoreside.append("}\n");

        String fileName = "meta_shoreside.moos";

        UUV uuv = new UUV("shoreside", null, null, 0.0, 0.0, 0);
        uuv.setMetaFileName(fileName);

        simulationProperties.addUUV(uuv);
        generateControllerProperties(uuv);

        Utility.exportToFile(ParserEngine.missionDir + "/" + fileName,
                shoreside.toString(),
                false);
    }

    private static void generateTargetVehicleBlock(UUV uuv) {
        StringBuilder vehicleBlock = new StringBuilder();
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("// Meta vehicle config file\n");
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("#include plug_origin_info.moos\n");

        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("// Antler configuration  block\n");
        vehicleBlock.append("//------------------------------------------\n");
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
        // TODO: Investigate
//        vehicleBlock.append("\tRun = uTimerScript  @ NewConsole = false\n");
        vehicleBlock.append("\tRun = pHostInfo\t\t  @ NewConsole = false\n");
//        vehicleBlock.append("\tRun = sUUV          @ NewConsole = false\n");

        vehicleBlock.append("\tRun = uFldNodeBroker @ NewConsole = false\n");

        for (Sensor sensor : uuv.getSensors()) {
            vehicleBlock.append("\t Run = sSensor   @ NewConsole = false ~" + sensor.getName() + "\n");
        }

        vehicleBlock.append("}\n\n");

        vehicleBlock.append("#include plug_uSimMarine.moos\n");
        vehicleBlock.append("#include plug_pLogger.moos\n");
        vehicleBlock.append("#include plug_pNodeReporter.moos\n");
        vehicleBlock.append("#include plug_pMarinePID.moos\n");
        vehicleBlock.append("#include plug_pHelmIvP_" + uuv.getName() + ".moos\n");
        vehicleBlock.append("#include plug_uProcessWatch.moos\n");
        vehicleBlock.append("#include plug_pShare.moos\n");
        vehicleBlock.append("#include plug_pHostInfo.moos\n");
        vehicleBlock.append("#include plug_uFldNodeBroker.moos\n");
//        vehicleBlock.append("#include plug_uTimerScript.moos\n");
//        vehicleBlock.append("#include plug_UUV_" + uuv.getName() + ".moos\n");

        for (Sensor sensor : uuv.getSensors()) {
            vehicleBlock.append("#include plug_" + sensor.getName() + ".moos\n");
        }

        String fileName = "meta_vehicle_" + uuv.getName() + ".moos";
        uuv.setMetaFileName(fileName);

        Utility.exportToFile(ParserEngine.missionDir + File.separator + fileName,
                vehicleBlock.toString(),
                false);
    }

    static void run() {
        generateShoreside();
        generateSensors();

        Map<String, UUV> agents = simulationProperties.getAgents();

        for (Map.Entry<String, UUV> entry : agents.entrySet()) {
            UUV uuv = entry.getValue();

            //generate UUV moos block
            Utility.exportToFile(ParserEngine.missionDir + File.separator + "plug_UUV_" + uuv.getName() + ".moos",
                    uuv.toString(), false);

            //generate target vehicle block
            generateTargetVehicleBlock(uuv);

            //generate IvPHelm vehicle block
            generateIvPHelmBlock(uuv);

            //generate controller properties
        }
    }

    private static void writeHostInfo(StringBuilder vehicleBlock) {
        vehicleBlock.append("//--------------------------------------------------\n");
        vehicleBlock.append("// pHostInfo configuration block from plugin\n");
        vehicleBlock.append("//--------------------------------------------------\n");
        vehicleBlock.append("ProcessConfig = pHostInfo\n");
        vehicleBlock.append("{\n");
        vehicleBlock.append("\tAppTick\t\t= 1\n");
        vehicleBlock.append("\tCommsTick\t= 1\n");
        vehicleBlock.append("\n");
        vehicleBlock.append("\tDEFAULT_HOSTIP_FORCE = localhost\n");
        vehicleBlock.append("}\n");
    }

}
