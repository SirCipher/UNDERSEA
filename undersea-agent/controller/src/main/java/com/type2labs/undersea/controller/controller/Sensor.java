package com.type2labs.undersea.controller.controller;

import com.type2labs.undersea.common.service.hardware.NetworkInterface;


public class Sensor {

    /**
     * Communication handles
     */
    private NetworkInterface networkInterface;

    /**
     * Command to send to managed system
     */
    private String command = "SENSORS";

    /**
     * Reply from managed system
     */
    private String reply;

    private Knowledge knowledge;

    /**
     * Constructor: create a new sensor
     */
    public Sensor(NetworkInterface networkInterface, Knowledge knowledge) {
        //assign client handler
        this.networkInterface = networkInterface;
        this.knowledge = knowledge;
    }

    public String getReply() {
        return this.reply;
    }

    private void parseReply(String reply) {
        //SENSOR1:RATE:#READINGS:#SUCC_READINGS,SENSOR2:RATE:#READINGS:#SUCC_READINGS,...
        String[] sensorsStr = reply.split(",");//multiple SENSOR1=RATE:#READINGS:#SUCC_READINGS

        for (String str : sensorsStr) {
            String[] sensorData = str.split(":");
            //sensorData[0] --> sensor name
            //sensorData[1] --> sensor average reading rate
            //sensorData[2] --> readings since previous invocation
            //sensorData[3] --> accurate readings since previous invocation

            //sensor rate cannot be zero --> use a very small value
            double rate = Double.parseDouble(sensorData[1]);
            if (rate == 0)
                rate = 0.2;

            knowledge.setSensorRate(sensorData[0], rate);
            knowledge.setSensorReadings(sensorData[0], Integer.parseInt(sensorData[2]));
            knowledge.setSensorAccurateReadings(sensorData[0], Integer.parseInt(sensorData[3]));
        }
    }

    public void run() {
        networkInterface.write(command);
        reply = networkInterface.read();

        if (reply != null) {
            parseReply(reply);
        }
    }
}
