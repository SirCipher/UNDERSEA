package uuv.parser;

import auxiliary.Utility;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;
import uuv.properties.SimulationProperties;

import java.util.Map;

@SuppressWarnings("Duplicates")
public class MoosConfigurationWriter {

    private SimulationProperties simulationProperties = SimulationProperties.getInstance();

    private void iterate() {
        Map<String, UUV> agents = simulationProperties.getAgents();

        for (Map.Entry<String, UUV> entry : agents.entrySet()) {
            UUV uuv = entry.getValue();

        }
    }

    public void write() {
//        //generate UUV moos block
//        Utility.exportToFile(ParserEngine.missionDir + "/plug_UUV.moos", uuv.toString(), false);
//
//        //generate sensors moos files
//        for (Sensor sensor : sensorsMap.values()) {
//            String filename = ParserEngine.missionDir + "/plug_" + sensor.getName() + ".moos";
//            Utility.exportToFile(filename, sensor.toString(), false);
//        }
//
//        //generate target vehicle block
//        generateTargetVehicleBlock();
//
//        //generate IvPHelm vehicle block
//        generateIvPHelmBlock();
//
//        //generate controller properties
//        generateControllerProperties();
    }


}
