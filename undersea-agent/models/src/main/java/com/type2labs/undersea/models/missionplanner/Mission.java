package com.type2labs.undersea.models.missionplanner;

import java.util.List;

public interface Mission {

    List<Task> tasks();

    double progress();

}
