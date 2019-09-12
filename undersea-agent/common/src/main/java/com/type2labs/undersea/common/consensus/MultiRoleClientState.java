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

package com.type2labs.undersea.common.consensus;

import com.type2labs.undersea.common.cluster.Client;

/**
 * A {@link MultiRoleState} client state. Currently, this stores what mission a client is performing. However, future
 * revisions will include the agent's status and will allow for task progression state storage
 */
public class MultiRoleClientState {

    /**
     * The associated client
     */
    private final Client client;

    /**
     * A multidimensional double array containing the coordinates of the mission that this client is executing
     */
    private String jsonMissionCoordinates;

    public MultiRoleClientState(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public String getJsonMissionCoordinates() {
        return jsonMissionCoordinates;
    }

    public void setJsonMissionCoordinates(String jsonMissionCoordinates) {
        this.jsonMissionCoordinates = jsonMissionCoordinates;
    }
}
