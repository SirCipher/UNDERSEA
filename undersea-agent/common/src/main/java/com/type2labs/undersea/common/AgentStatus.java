package com.type2labs.undersea.common;

import com.type2labs.undersea.common.impl.Sensor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class AgentStatus {

    private final String name;
    private final List<Sensor> sensors;

    public AgentStatus(String name, List<Sensor> sensors) {
        this.name = name;
        this.sensors = sensors;
    }

    public String getName() {
        return name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public List<Pair<String, String>> transportableStatus() {
        return new ArrayList<>();
    }

}
