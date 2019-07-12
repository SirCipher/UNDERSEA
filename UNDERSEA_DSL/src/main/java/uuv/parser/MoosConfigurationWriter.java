package uuv.parser;

import auxiliary.Utility;
import uuv.dsl.factory.FactoryProvider;
import uuv.dsl.factory.SensorFactory;
import uuv.dsl.model.PShareConfig;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;
import uuv.properties.SimulationProperties;

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
            String propertiesDirName = ParserEngine.controllerDir + "/resources/";
            String propertiesFilename = propertiesDirName + "config.properties";
            File propertiesDir = new File(propertiesDirName);
            File propertiesFile = new File(propertiesFilename);

            if (!propertiesDir.exists()) {
                if (propertiesDir.mkdirs()) {
                    throw new IOException("Unable to create properties directory: " + propertiesDir.getAbsolutePath());
                }
            }

            if (!propertiesFile.exists()) {
                if (!propertiesFile.createNewFile()) {
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
            properties.put("PORT", uuv.getServerPort());

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
        StringBuilder str = new StringBuilder();
        str.append("//------------------------------------------\n");
        str.append("// Helm IvP configuration  block\n");
        str.append("//------------------------------------------\n");
        str.append("ProcessConfig = pHelmIvP\n");
        str.append("{\n");
        str.append("\t   AppTick        = 4\n");
        str.append("\t   CommsTick      = 4\n");
        str.append("\t   Behaviors      = " + uuv.getBehaviourFileName() + "\n");
        str.append("\t   Verbose        = quiet\n");
        str.append("\t   ok_skew        = any\n");
        str.append("\t   active_start   = false\n");
        str.append("\t   Domain         = course:0:359:360\n");
        str.append("\t   Domain         = depth:0:100:101\n");
        str.append("\t   Domain         = speed:" + uuv.getSpeedMin() + ":" + uuv.getSpeedMax() + ":" + uuv
                .getSpeedSteps() + "\n");

        str.append("}");

        //write
        Utility.exportToFile(ParserEngine.missionDir + "/plug_pHelmIvP_" + uuv.getName() + ".moos", str.toString(),
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
        shoreside.append("ServerHost   = " + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + "\n");
        shoreside.append("ServerPort   = " + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.PORT_START) + "\n");
        shoreside.append("Community    = shoreside\n");
        shoreside.append("MOOSTimeWarp = 1\n");
        shoreside.append("\n");
        shoreside.append("// Forest Lake\n");
        shoreside.append("LatOrigin  = 43.825300 \n");
        shoreside.append("LongOrigin = -70.330400 \n");
        shoreside.append("\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("// Antler configuration block\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("ProcessConfig = ANTLER\n");
        shoreside.append("{\n");
        shoreside.append("  MSBetweenLaunches = 200\n");
        shoreside.append("\n");
        shoreside.append("  Run = MOOSDB        @ NewConsole = false\n");
        shoreside.append("  Run = pMarineViewer @ NewConsole = false\n");
        shoreside.append("  Run = pShare        @ NewConsole = false\n");
        shoreside.append("}\n");

        shoreside.append("\n//------------------------------------------\n");
        shoreside.append("// pMarineViewer config block\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("ProcessConfig = pMarineViewer\n");
        shoreside.append("{\n");
        shoreside.append("\tAppTick    = 4\n");
        shoreside.append("\tCommsTick  = 4\n");
        shoreside.append("\n");
        shoreside.append("\ttiff_file            = forrest19.tif\n");
        shoreside.append("\tset_pan_x            = -90\n");
        shoreside.append("\tset_pan_y            = -280\n");
        shoreside.append("\tzoom                 = 0.65\n");
        shoreside.append("\tvehicle_shape_scale  = 1.5\n");
        shoreside.append("\thash_delta           = 50\n");
        shoreside.append("\thash_shade           = 0.22\n");
        shoreside.append("\thash_viewable        = true\n");
        shoreside.append("\ttrails_point_size    = 1\n");
        shoreside.append("\n");
        shoreside.append("\t// Appcast configuration\n");
        shoreside.append("\tappcast_height       = 75\n");
        shoreside.append("\tappcast_width        = 30\n");
        shoreside.append("\tappcast_viewable     = true\n");
        shoreside.append("\tappcast_color_scheme = indigo\n");
        shoreside.append("\tnodes_font_size      = medium\n");
        shoreside.append("\tprocs_font_size      = medium\n");
        shoreside.append("\tappcast_font_size    = small\n");
        shoreside.append("\n");
        shoreside.append("\tright_context[return] = DEPLOY=true\n");
        shoreside.append("\tright_context[return] = MOOS_MANUAL_OVERRIDE=false\n");
        shoreside.append("\tright_context[return] = RETURN=false\n");
        shoreside.append("\n");
        shoreside.append("\tscope  = RETURN\n");
        shoreside.append("\tscope  = WPT_STAT\n");
        shoreside.append("\tscope  = VIEW_SEGLIST\n");
        shoreside.append("\tscope  = VIEW_POINT\n");
        shoreside.append("\tscope  = VIEW_POLYGON\n");
        shoreside.append("\tscope  = MVIEWER_LCLICK\n");
        shoreside.append("\tscope  = MVIEWER_RCLICK\n");
        shoreside.append("\n");
        shoreside.append("\tbutton_one = DEPLOY # DEPLOY_ALL=true\n");
        shoreside.append("\tbutton_one = MOOS_MANUAL_OVERRIDE_ALL=false # RETURN_ALL=false\n");
        shoreside.append("\tbutton_two = RETURN # RETURN_ALL=true\n");
        shoreside.append("\n");
        shoreside.append("\taction  = MENU_KEY=deploy # DEPLOY_ALL = true # RETURN_ALL = false\n");
        shoreside.append("\taction+ = MENU_KEY=deploy # MOOS_MANUAL_OVERRIDE_ALL=false\n");
        shoreside.append("\taction  = RETURN_ALL=true\n");
        shoreside.append("\taction  = UPDATES_RETURN_ALL=speed=1.4\n");

        for (Map.Entry<String, UUV> entry : simulationProperties.getAgents().entrySet()) {
            UUV uuv = entry.getValue();
            String lower = uuv.getName().toLowerCase();
            String upper = uuv.getName().toUpperCase();

            shoreside.append("\taction  = MENU_KEY=deploy-" + lower + " # DEPLOY_" + upper + " = true # RETURN_" + upper + " = false\n");
            shoreside.append("\taction+ = MENU_KEY=deploy-" + lower + " # MOOS_MANUAL_OVERRIDE_" + upper + "=false" +
                    "\n");
            shoreside.append("\taction  = MENU_KEY = return-" + lower + " # RETURN_" + upper + "=true\n");
            shoreside.append("\taction+ = UPDATES_RETURN_" + upper + "=speed=1.4\n");
        }

        shoreside.append("}\n");

        shoreside.append("\n//------------------------------------------\n");
        shoreside.append("// pShare configuration block\n");
        shoreside.append("//------------------------------------------\n");
        shoreside.append("ProcessConfig = pShare\n");
        shoreside.append("{\n");
        shoreside.append("\tAppTick    = 4\n");
        shoreside.append("\tCommsTick  = 4\n");
        shoreside.append("\n");
        shoreside.append("\tinput  = route = localhost:9200\n");
        shoreside.append("\n");

        for (Map.Entry<String, UUV> entry : simulationProperties.getAgents().entrySet()) {
            UUV uuv = entry.getValue();
            String upper = uuv.getName().toUpperCase();
            String route =
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + ":" + uuv.getpShareConfig().getpSharePort();

            shoreside.append(new PShareConfig.Output("DEPLOY_ALL", "DEPLOY", route));
            shoreside.append(new PShareConfig.Output("RETURN_ALL", "RETURN", route));
            shoreside.append(new PShareConfig.Output("MOOS_MANUAL_OVERRIDE_ALL", "MOOS_MANUAL_OVERRIDE", route));
            shoreside.append(new PShareConfig.Output("UPDATES_RETURN_ALL", "UPDATES_RETURN", route));
            shoreside.append(new PShareConfig.Output("DEPLOY_" + upper, "DEPLOY", route));
            shoreside.append(new PShareConfig.Output("RETURN_" + upper, "DEPLOY", route));
            shoreside.append(new PShareConfig.Output("MOOS_MANUAL_OVERRIDE_" + upper, "MOOS_MANUAL_OVERRIDE", route));
            shoreside.append(new PShareConfig.Output("UPDATES_RETURN_" + upper, "UPDATES_RETURN", route));

            shoreside.append("\n");
        }

        shoreside.append("}\n");

        Utility.exportToFile(ParserEngine.missionDir + "/meta_shoreside.moos",
                shoreside.toString(),
                false);
    }

    private static void generateTargetVehicleBlock(UUV uuv) {
        StringBuilder vehicleBlock = new StringBuilder();
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("// Meta vehicle config file\n");
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("ServerHost   = " + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + "\n");
        vehicleBlock.append("ServerPort   = " + uuv.getServerPort() + "\n");
        vehicleBlock.append("Simulator    = true\n");
        vehicleBlock.append("Community    = " + uuv.getName() + "\n");
        vehicleBlock.append("LatOrigin    = 43.825300\n");
        vehicleBlock.append("LongOrigin   = -70.330400\n");
        vehicleBlock.append("MOOSTimeWarp = " + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_SPEED) + "\n\n");

        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("// Antler configuration  block\n");
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("ProcessConfig = ANTLER\n");
        vehicleBlock.append("{\n");
        vehicleBlock.append("\t MSBetweenLaunches = 200\n");
        vehicleBlock.append("\t Run = MOOSDB        @ NewConsole = false\n");
        vehicleBlock.append("\t Run = uProcessWatch @ NewConsole = false\n");
        vehicleBlock.append("\t Run = uSimMarine    @ NewConsole = false\n");
        vehicleBlock.append("\t Run = pNodeReporter @ NewConsole = false\n");
        vehicleBlock.append("\t Run = pMarinePID    @ NewConsole = false\n");
        vehicleBlock.append("\t Run = uTimerScript  @ NewConsole = false\n");
        vehicleBlock.append("\t Run = pHelmIvP      @ NewConsole = false\n");
        vehicleBlock.append("\t Run = sUUV          @ NewConsole = false ~" + uuv.getName() + "\n");

        for (Sensor sensor : uuv.getSensors()) {
            vehicleBlock.append("\t Run = sSensor       @ NewConsole = false ~" + sensor.getName() + "\n");
        }

        vehicleBlock.append("}\n\n");

        vehicleBlock.append("#include plug_uProcessWatch.moos\n");
        vehicleBlock.append("#include plug_uSimMarine.moos\n");
        vehicleBlock.append("#include plug_uTimerScript.moos\n");
        vehicleBlock.append("#include plug_pNodeReporter.moos\n");
        vehicleBlock.append("#include plug_pMarinePID.moos\n");
        vehicleBlock.append("#include plug_pHelmIvP_" + uuv.getName() + ".moos\n");
        vehicleBlock.append("#include plug_UUV_" + uuv.getName() + ".moos\n");

        for (Sensor sensor : uuv.getSensors()) {
            vehicleBlock.append("#include plug_" + sensor.getName() + ".moos\n");
        }

        vehicleBlock.append(uuv.getpShareConfig().toString());

        Utility.exportToFile(ParserEngine.missionDir + "/meta_vehicle_" + uuv.getName() + ".moos",
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
            Utility.exportToFile(ParserEngine.missionDir + "/plug_UUV_" + uuv.getName() + ".moos",
                    uuv.toString(), false);

            //generate target vehicle block
            generateTargetVehicleBlock(uuv);

            //generate IvPHelm vehicle block
            generateIvPHelmBlock(uuv);

            //generate controller properties
            generateControllerProperties(uuv);
        }
    }

}
