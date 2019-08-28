package com.type2labs.undersea.controller.controller;

import com.type2labs.undersea.common.service.hardware.NetworkInterface;

public class Effector {

    /**
     * Communication handles
     */
    private NetworkInterface networkInterface;

    /**
     * Command to send to managed system
     */
    private String command;

    /**
     * Reply from managed system
     */
    private String reply;


    /**
     * Constructor: create a new sensor
     */
    public Effector(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getReply() {
        return this.reply;
    }

    public void run() {
        networkInterface.write(command);
        reply = networkInterface.read();
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
