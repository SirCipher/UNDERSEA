package com.type2labs.undersea.models;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

// TODO: Agent really needs to be a superclass that modules attack submodules to for parsing. Otherwise,
// implementations are going to be far too cluttered
public interface Agent {

    List<Pair<String, String>> status();

}
