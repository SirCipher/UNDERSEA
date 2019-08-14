package com.type2labs.undersea.common.service;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public interface AgentService {

    void start();

    boolean isAvailable();

    void shutdown();

}
