package com.type2labs.undersea.models.missionplanner;

import com.type2labs.undersea.models.NodeLog;

import java.util.List;

public interface Mission {

    List<Task> tasks();

    double progress();

    NodeLog log();

}
