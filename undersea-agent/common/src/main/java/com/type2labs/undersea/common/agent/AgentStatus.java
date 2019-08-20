package com.type2labs.undersea.common.agent;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class AgentStatus {

    private final String name;
    private final List<Subsystem> subsystems;

    public AgentStatus(String name, List<Subsystem> subsystems) {
        this.name = name;
        this.subsystems = subsystems;
    }

    public String getName() {
        return name;
    }

    public List<Subsystem> getSubsystems() {
        return subsystems;
    }

    public List<Pair<String, String>> transportableStatus() {
        return new ArrayList<>();
    }

}
