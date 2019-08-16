package com.type2labs.undersea.common.controller;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;

public interface Controller extends AgentService {

    Agent agent();

}
