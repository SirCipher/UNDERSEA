package uuv.parser;

import auxiliary.Utility;
import uuv.dsl.factory.FactoryProvider;
import uuv.dsl.factory.SensorFactory;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;
import uuv.properties.SimulationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("Duplicates")
public class MoosConfigurationWriter {

    private SimulationProperties simulationProperties = SimulationProperties.getInstance();
    private SensorFactory sensorFactory = FactoryProvider.getSensorFactory();

    private void generateControllerProperties(UUV uuv) {
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
            properties.put("PORT", uuv.getPort());

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

    private void generateIvPHelmBlock(UUV uuv) {
        StringBuilder str = new StringBuilder();
        str.append("//------------------------------------------\n");
        str.append("// Helm IvP configuration  block\n");
        str.append("//------------------------------------------\n");
        str.append("ProcessConfig = pHelmIvP\n");
        str.append("{\n");
        str.append("\t   AppTick    = 4\n");
        str.append("\t   CommsTick  = 4\n");
        str.append("\t   Behaviors  = " + uuv.getBehaviourFileName() + "\n");
        str.append("\t   Verbose    = quiet\n");
        str.append("\t   ok_skew = any\n");
        str.append("\t   active_start = false\n");

        str.append("\t   Domain     = course:0:359:360\n");
        str.append("\t   Domain     = depth:0:100:101\n");
        str.append("\t   Domain     = speed:" + uuv.getSpeedMin() + ":" + uuv.getSpeedMax() + ":" + uuv
                .getSpeedSteps() + "\n");

        str.append("}\n\n");

        //write
        Utility.exportToFile(ParserEngine.missionDir + "/plug_pHelmIvP_" + uuv.getName() + ".moos", str.toString(),
                false);
    }

    private void generateSensors() {
        for (Map.Entry<String, Sensor> entry : sensorFactory.getSensors().entrySet()) {
            Sensor sensor = entry.getValue();
            String filename = ParserEngine.missionDir + "/plug_" + sensor.getName() + ".moos";
            Utility.exportToFile(filename, sensor.toString(), false);
        }
    }

    private void generateTargetVehicleBlock(UUV uuv) {
        StringBuilder vehicleBlock = new StringBuilder();
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("// Meta vehicle config file\n");
        vehicleBlock.append("//------------------------------------------\n");
        vehicleBlock.append("ServerHost   = " + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + "\n");
        vehicleBlock.append("ServerPort   = " + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.PORT) + "\n");
        vehicleBlock.append("Simulator    =  true\n");
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
        vehicleBlock.append("\t   Run = MOOSDB			@ NewConsole = false\n");
        vehicleBlock.append("\t   Run = uProcessWatch	@ NewConsole = false\n");
        vehicleBlock.append("\t   Run = uSimMarine		@ NewConsole = false\n");
        vehicleBlock.append("\t   Run = pNodeReporter	@ NewConsole = false\n");
        vehicleBlock.append("\t   Run = pMarinePID		@ NewConsole = false\n");
        vehicleBlock.append("\t   Run = pMarineViewer	@ NewConsole = false\n\n");
        vehicleBlock.append("\t   Run = uTimerScript 	@ NewConsole = false\n\n");
        vehicleBlock.append("\t   Run = pHelmIvP		@ NewConsole = false\n\n");
        vehicleBlock.append("\t   Run = sUUV			@ NewConsole = false ~" + uuv.getName() + "\n");

        for (Sensor sensor : uuv.getSensors()) {
            vehicleBlock.append("\t   Run = sSensor		@ NewConsole = false ~" + sensor.getName() + "\n");
        }

        vehicleBlock.append("}\n\n");

        vehicleBlock.append("#include plug_uProcessWatch.moos\n");
        vehicleBlock.append("#include plug_uSimMarine.moos\n");
        vehicleBlock.append("#include plug_uTimerScript.moos\n");
        vehicleBlock.append("#include plug_pNodeReporter.moos\n");
        vehicleBlock.append("#include plug_pMarinePID.moos\n");
        vehicleBlock.append("#include plug_pMarineViewer.moos\n");
        vehicleBlock.append("#include plug_pHelmIvP_" + uuv.getName() + ".moos\n");
        vehicleBlock.append("#include plug_UUV_" + uuv.getName() + ".moos\n");

        for (Sensor sensor : uuv.getSensors()) {
            vehicleBlock.append("#include plug_" + sensor.getName() + ".moos\n");
        }

        //write
        Utility.exportToFile(ParserEngine.missionDir + "/meta_vehicle_" + uuv.getName() + ".moos",
                vehicleBlock.toString(),
                false);
    }

    public void run() {
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
