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
 * Denotes the current state of an {@link Agent}.
 * <p>
 * See {@link Agent#state()}
 */
public class AgentState {

    private State state = State.OFFLINE;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        /**
         * The {@link Agent} is online and active
         */
        ACTIVE,

        /**
         * The {@link Agent} is online and is not performing any tasks
         */
        INACTIVE,

        /**
         * The {@link Agent} is offline either shutdown gracefully or is yet to start
         */
        OFFLINE,

        /**
         * The {@link Agent} is offline due to a failure. This must align with {@link ServiceManager#isHealthy()} being
         * false
         */
        FAILED,

        /**
         * Denotes that this agent is a reserve
         */
        BACKUP
    }
}
