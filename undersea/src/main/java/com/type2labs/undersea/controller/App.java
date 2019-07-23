package com.type2labs.undersea.controller;

import com.type2labs.undersea.dsl.ParserEngine;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        args = new String[]{"resources/mission.config", "resources/sensors.config", "../UNDERSEA_Controller",
                "missions", "resources/config.properties", "missions", "mission-includes"};

        try {
            ParserEngine.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
