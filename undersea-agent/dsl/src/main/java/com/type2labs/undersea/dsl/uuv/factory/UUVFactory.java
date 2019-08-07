package com.type2labs.undersea.dsl.uuv.factory;

import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import org.antlr.v4.runtime.ParserRuleContext;

public class UUVFactory implements AbstractFactory<DslAgentProxy> {

    @Override
    public void create(ParserRuleContext context) {
    }

    @Override
    public DslAgentProxy get(String name) {
        return null;
    }

}
