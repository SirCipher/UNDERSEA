package com.type2labs.undersea.dsl.uuv.factory;

import com.type2labs.undersea.agent.AgentProxy;
import org.antlr.v4.runtime.ParserRuleContext;

public class UUVFactory implements AbstractFactory<AgentProxy> {

    @Override
    public void create(ParserRuleContext context) {
    }

    @Override
    public AgentProxy get(String name) {
        return null;
    }

}
