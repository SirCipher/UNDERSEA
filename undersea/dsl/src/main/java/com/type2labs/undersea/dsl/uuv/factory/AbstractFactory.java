package com.type2labs.undersea.dsl.uuv.factory;

import org.antlr.v4.runtime.ParserRuleContext;

public interface AbstractFactory<T> {

    void create(ParserRuleContext context);

    T get(String name);

}
