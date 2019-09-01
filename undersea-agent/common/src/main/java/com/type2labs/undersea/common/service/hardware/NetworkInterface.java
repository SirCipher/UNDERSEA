package com.type2labs.undersea.common.service.hardware;

import com.type2labs.undersea.common.service.AgentService;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public interface NetworkInterface extends AgentService {

    /**
     * Writes a message to the physical network the agent is operating on
     *
     * @param message to write
     * @return
     */
    String write(String message);

}
