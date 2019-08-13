package com.type2labs.undersea.common.missionplanner;


import java.util.List;

public interface Mission {

    List<Task> tasks();

    double progress();

}
