package com.type2labs.undersea.common.runner;

import com.type2labs.undersea.common.agent.Agent;

import java.util.List;
import java.util.Map;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public interface AgentInitialiser {

    List<Agent> initialise(Map<String, ? extends Agent> agents);

}
