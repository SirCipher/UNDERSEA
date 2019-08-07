package com.type2labs.undersea.models;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface Agent {

    List<Pair<String, String>> status();

}
