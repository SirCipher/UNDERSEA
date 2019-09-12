/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.service.ServiceManager;

/**
 * Denotes that the implementor is aware of its associated {@link Agent}. Mostly used by
 * {@link com.type2labs.undersea.common.service.AgentService}s for their initialisation sequence. Many classes need
 * to know their associated agent during construction, however, the agent itself may not have been constructed yet.
 * In using this, {@link com.type2labs.undersea.common.service.AgentService}s can initialise the implementor afterwards.
 */
public interface AgentAware {

    /**
     * Sets the parent agent that this service belongs to and invokes initialisation on the service. Some services
     * require an agent to be set before they can be initialised and thus not all initialisation can be done in the
     * constructor.
     * <p>
     * This is invoked by the {@link ServiceManager} before {@link Runnable#run()} to ensure that the service is
     * fully initialised beforehand.
     * <p>
     * Implementors should disallow reinitialisation.
     *
     * @param parentAgent to associate with the service
     */
    void initialise(Agent parentAgent);

    /**
     * The agent that this service is associated with
     *
     * @return the associated agent
     */
    Agent parent();

}
