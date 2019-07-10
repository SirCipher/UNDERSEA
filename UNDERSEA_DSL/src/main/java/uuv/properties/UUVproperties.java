package uuv.properties;

import auxiliary.DSLException;
import auxiliary.Utility;
import main.ParserEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class UUVproperties {

    /**
     * Simulation time
     */
    private String simulationTime;

    /**
     * Time window
     */
    private String timeWindow;

    /**
     * host
     */
    private String host;

    /**
     * port
     */
    private String port;

    /**
     * port
     */
    private String simulationSpeed;

    /**
     * UUV
     */
    private UUV uuv;

    /**
     * Sensors
     */
    private Map<String, Sensor> sensorsMap;


    public UUVproperties() {
        simulationTime = null;
        timeWindow = null;
        host = null;
        port = null;
        simulationSpeed = null;
        uuv = null;
        sensorsMap = new HashMap<String, Sensor>();
    }


    public void checkProperties() throws DSLException {
        StringBuilder errors = new StringBuilder();
        if (simulationTime == null)
            errors.append("* Undefined simulation time! (e.g., simulation time = 10)\n");
        if (simulationSpeed == null)
            errors.append("* Undefined simulation speed! (e.g., simulation speed = 2)\n");
        if (timeWindow == null)
            errors.append("* Undefined time window! (e.g., time window = 10)\n");
        if (host == null)
            errors.append("* Undefined host! (e.g., host = localhost)\n");
        if (port == null)
            errors.append("* Undefined port! (e.g., port = 12345)\n");
        if (uuv == null)
            errors.append("* Undefined uuv configuration block! (e.g., UUV {...})\n");
        if (sensorsMap.size() == 0)
            errors.append("* No sensors found! (e.g., (e.g., Sensor {...})\n");

        if (errors.length() > 0)
            throw new DSLException("Incorrect configuration file!\n" + errors.toString());

    }


    public void setSimulationTime(String sm) throws DSLException {
        if (simulationTime != null)
            throw new DSLException("Simulation time is already defined.Value Ignored!");
        this.simulationTime = sm;
    }


    public void setTimeWindow(String tw) throws DSLException {
        if (timeWindow != null)
            throw new DSLException("Time Window is already defined. Value Ignored!");
        this.timeWindow = tw;
    }


    public void setHost(String h) throws DSLException {
        if (host != null)
            throw new DSLException("Host is already defined. Value Ignored!");
        this.host = h;
    }


    public void setPort(String p) throws DSLException {
        if (port != null)
            throw new DSLException("Port is already defined. Value Ignored!");
        this.port = p;
    }


    public void setSpeed(String s) throws DSLException {
        if (simulationSpeed != null)
            throw new DSLException("Speed is already defined. Value Ignored!");
        this.simulationSpeed = s;
    }


    public void setUUV(String name, String port, double min, double max, int steps) throws DSLException {
        if (uuv != null)
            throw new DSLException("UUV properties already defined. Block Ignored!");
        uuv = new UUV(name, port, min, max, steps);
    }


    public void setSensor(String name, double rate, double reliability) throws DSLException {
        if (sensorsMap.containsKey(name))
            throw new DSLException("Sensor " + name + " properties already defined. Block Ignored!");
        Sensor newSensor = new Sensor(name, rate, reliability);
        sensorsMap.put(name, newSensor);
    }


    public void setChange(String sensorName, double begin, double end, double percentage) throws DSLException {
//		if (sensorsMap.containsKey(name))
//			throw new DSLException("Sensor "+ name + "properties already defined. Block Ignored!");
        Range newChange = new Range(begin, end, percentage);
        sensorsMap.get(sensorName).addChange(newChange);
    }


    public void generateMoosBlocks() {
        //generate UUV moos block
        Utility.exportToFile(ParserEngine.missionDir + "/plug_UUV.moos", uuv.toString(), false);

        //generate sensors moos files
        for (Sensor sensor : sensorsMap.values()) {
            String filename = ParserEngine.missionDir + "/plug_" + sensor.name + ".moos";
            Utility.exportToFile(filename, sensor.toString(), false);
        }

        //generate target vehicle block
        generateTargetVehicleBlock();

        //generate IvPHelm vehicle block
        generateIvPHelmBlock();

        //generate controller properties
        generateControllerProperties();
    }


    private void generateTargetVehicleBlock() {
        StringBuilder str = new StringBuilder();
        str.append("//------------------------------------------\n");
        str.append("//meta vehicle config file\n");
        str.append("//------------------------------------------\n");
        str.append("ServerHost = " + host + "\n");
        str.append("ServerPort = " + port + "\n");
        str.append("Simulator =  true\n");
        str.append("Community = " + uuv.name + "\n");
        str.append("LatOrigin  = 43.825300\n");
        str.append("LongOrigin = -70.330400\n");
        str.append("MOOSTimeWarp = " + simulationSpeed + "\n\n");

        str.append("//------------------------------------------\n");
        str.append("// Antler configuration  block\n");
        str.append("//------------------------------------------\n");
        str.append("ProcessConfig = ANTLER\n");
        str.append("{\n");
        str.append("\t MSBetweenLaunches = 200\n");
        str.append("\t   Run = MOOSDB			@ NewConsole = false\n");
        str.append("\t   Run = uProcessWatch	@ NewConsole = false\n");
        str.append("\t   Run = uSimMarine		@ NewConsole = false\n");
        str.append("\t   Run = pNodeReporter	@ NewConsole = false\n");
        str.append("\t   Run = pMarinePID		@ NewConsole = false\n");
        str.append("\t   Run = pMarineViewer	@ NewConsole = false\n\n");

        str.append("\t    Run = uTimerScript 	@ NewConsole = false\n\n");

        str.append("\t   Run = pHelmIvP			@ NewConsole = false\n\n");

        str.append("\t   Run = sUUV				@ NewConsole = false ~" + uuv.name + "\n");

        for (String sensorName : sensorsMap.keySet()) {
            str.append("\t   Run = sSensor			@ NewConsole = false ~" + sensorName + "\n");
        }
        str.append("}\n\n");

        str.append("#include plug_uProcessWatch.moos\n");
        str.append("#include plug_uSimMarine.moos\n");
        str.append("#include plug_uTimerScript.moos\n");
        str.append("#include plug_pNodeReporter.moos\n");
        str.append("#include plug_pMarinePID.moos\n");
        str.append("#include plug_pMarineViewer.moos\n");
        str.append("#include plug_pHelmIvP.moos\n");

        str.append("#include plug_UUV.moos\n");

        for (String sensorName : sensorsMap.keySet()) {
            str.append("#include plug_" + sensorName + ".moos\n");
        }


        //write
        Utility.exportToFile(ParserEngine.missionDir + "/meta_vehicle.moos", str.toString(), false);

    }


    private void generateIvPHelmBlock() {
        StringBuilder str = new StringBuilder();
        str.append("//------------------------------------------\n");
        str.append("// Helm IvP configuration  block\n");
        str.append("//------------------------------------------\n");
        str.append("ProcessConfig = pHelmIvP\n");
        str.append("{\n");
        str.append("\t   AppTick    = 4\n");
        str.append("\t   CommsTick  = 4\n");
        str.append("\t   Behaviors  = targ_uuv.bhv\n");
        str.append("\t   Verbose    = quiet\n");
        str.append("\t   ok_skew = any\n");
        str.append("\t   active_start = false\n");

        str.append("\t   Domain     = course:0:359:360\n");
        str.append("\t   Domain     = depth:0:100:101\n");
        str.append("\t   Domain     = speed:" + uuv.getSpeedMin() + ":" + uuv.getSpeedMax() + ":" + uuv.getSpeedSteps() + "\n");

        str.append("}\n\n");


        //write
        Utility.exportToFile(ParserEngine.missionDir + "/plug_pHelmIvP.moos", str.toString(), false);

    }


    private void generateControllerProperties() {
        try {
            String propertiesDirName = ParserEngine.controllerDir + "/resources/";
            String propertiesFilename = propertiesDirName + "config.properties";
            File propertiesDir = new File(propertiesDirName);
            File propertiesFile = new File(propertiesFilename);

            if (!propertiesDir.exists())
                propertiesDir.mkdirs();

            if (!propertiesFile.exists())
                //create new properties file
                propertiesFile.createNewFile();

            Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesFilename));
            properties.put("TIME_WINDOW", timeWindow);
            properties.put("SIMULATION_TIME", simulationTime);
            properties.put("SIMULATION_SPEED", simulationSpeed);
            properties.put("PORT", uuv.port);

            String sensorsNames = "";
            Iterator<String> it = sensorsMap.keySet().iterator();
            while (it.hasNext()) {
                sensorsNames += it.next();
                if (it.hasNext())
                    sensorsNames += ",";
            }
            properties.put("SENSORS", sensorsNames);

            properties.put("SPEED", uuv.getSpeedMin() + "," + uuv.getSpeedMax() + "," + uuv.getSpeedSteps());


            FileOutputStream out = new FileOutputStream(propertiesFile);
            properties.store(out, null);
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class UUV {
        /**
         * name
         */
        private String name;

        /**
         * name
         */
        private String rate;

        /**
         * port
         */
        private String port;

        /**
         * speed range
         */
        private Range speedRange;


        public UUV(String name, String port, double min, double max, int steps) {
            this.name = name;
            this.port = port;
            this.speedRange = new Range(min, max, steps + 1);
            this.rate = "4";
        }


        public String getName() {
            return this.name;
        }


        public String getPort() {
            return this.port;
        }


        public double getSpeedMax() {
            return speedRange.max;
        }


        public double getSpeedMin() {
            return speedRange.min;
        }


        public int getSpeedSteps() {
            return (int) speedRange.value;
        }


        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("//------------------------------------------\n");
            str.append("// sUUV config block\n");
            str.append("//------------------------------------------\n");
            str.append("ProcessConfig = " + name + "\n");
            str.append("{\n");
            str.append("\t AppTick = " + rate + "\n");
            str.append("\t CommsTick = " + rate + "\n");
            str.append("\t MAX_APPCAST_EVENTS = 25 \n");
            str.append("\t NAME = " + name + "\n");
            str.append("\t PORT = " + port + "\n");

            String sensorsStr = "";
            Iterator<String> iterator = sensorsMap.keySet().iterator();
            while (iterator.hasNext()) {
                sensorsStr += iterator.next();
                if (iterator.hasNext())
                    sensorsStr += ",";
            }
            str.append("\t SENSORS = " + sensorsStr + "\n");

            str.append("}\n\n");

            return str.toString();
        }
    }


    class Sensor {
        /**
         * name
         */
        private String name;

        /**
         * rate
         */
        private double rate;

        /**
         * reliability
         */
        private double reliability;

        /**
         * changes list
         */
        private List<Range> changesList;


        public Sensor(String name, double rate, double reliability) {
            this.name = name;
            this.rate = rate;
            this.reliability = reliability;
            this.changesList = new ArrayList<Range>();
        }


        protected void addChange(Range change) {
            changesList.add(change);
        }


        protected String getName() {
            return this.name;
        }


        protected double getRate() {
            return this.rate;
        }


        protected double getReliability() {
            return this.reliability;
        }


        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("//------------------------------------------\n");
            str.append("// sSensor config block\n");
            str.append("//------------------------------------------\n");
            str.append("ProcessConfig = " + name + "\n");
            str.append("{\n");
            str.append("\t AppTick = " + rate + "\n");
            str.append("\t CommsTick = " + rate + "\n");
            str.append("\t MAX_APPCAST_EVENTS = 25 \n");
            str.append("\t NAME = " + name + "\n");
            str.append("\t RELIABILITY = " + reliability + "\n");
            for (Range d : changesList) {
                str.append("\t CHANGE = " + d.toString() + "\n");
            }
            str.append("}\n");

            return str.toString();
        }

    }


    class Range {
        /**
         * begin
         */
        private double min;

        /**
         * end
         */
        private double max;

        /**
         * percentage
         */
        private Number value;

        public Range(double min, double max, Number value) {
            this.min = min;
            this.max = max;
            this.value = value;
        }

        public String toString() {
            return min + ":" + max + ":" + value;
        }
    }
}



