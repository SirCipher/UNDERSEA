package com.type2labs.undersea.models;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public interface AgentService {

    void start();

    boolean isAvailable();

    void shutdown();

}
