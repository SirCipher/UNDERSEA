package com.type2labs.undersea.dsl.uuv.factory;

import com.type2labs.undersea.dsl.uuv.model.AgentImplProxy;
import org.antlr.v4.runtime.ParserRuleContext;

public class UUVFactory implements AbstractFactory<AgentImplProxy> {

    @Override
    public void create(ParserRuleContext context) {
    }

    @Override
    public AgentImplProxy get(String name) {
        return null;
    }

}
