package com.type2labs.undersea.common.service;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public interface NetworkInterface extends AgentService {

    /**
     * Writes a request to the physical network that the agent is operating on and returns the response
     *
     * @param request to write
     * @return the response from the network
     */
    String read(String request);

    /**
     * Writes a message to the physical network the agent is operating on. No message is returned after the message
     * is written to the physical network. See {@link NetworkInterface#read(String)} for this functionality
     *
     * @param message to write
     */
    void write(String message);


}
