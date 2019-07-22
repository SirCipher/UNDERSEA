package com.type2labs.undersea.dsl.uuv.factory;

import com.type2labs.undersea.dsl.uuv.model.UUV;
import org.antlr.v4.runtime.ParserRuleContext;

public class UUVFactory implements AbstractFactory<UUV> {

    @Override
    public void create(ParserRuleContext context) {
    }

    @Override
    public UUV get(String name) {
        return null;
    }

}
