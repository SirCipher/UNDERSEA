package uuv.parser;

import auxiliary.Utility;
import uuv.dsl.model.Sensor;
import uuv.properties.SimulationProperties;

@SuppressWarnings("Duplicates")
public class MoosConfigurationWriter {

    private SimulationProperties simulationProperties = SimulationProperties.getInstance();

    public void write(){
        //generate UUV moos block
        Utility.exportToFile(ParserEngine.missionDir + "/plug_UUV.moos", uuv.toString(), false);

        //generate sensors moos files
        for (Sensor sensor : sensorsMap.values()) {
            String filename = ParserEngine.missionDir + "/plug_" + sensor.getName() + ".moos";
            Utility.exportToFile(filename, sensor.toString(), false);
        }

        //generate target vehicle block
        generateTargetVehicleBlock();

        //generate IvPHelm vehicle block
        generateIvPHelmBlock();

        //generate controller properties
        generateControllerProperties();
    }


}
