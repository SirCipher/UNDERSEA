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

package com.type2labs.undersea.common.monitor.model;

import com.type2labs.undersea.common.logger.VisualiserMessage;
import com.type2labs.undersea.common.service.AgentService;

import java.io.IOException;

/**
 * Maintains communication with the Visualiser if it is running. Updating it with the
 * {@link com.type2labs.undersea.common.agent.Agent}'s current state
 */
public interface VisualiserClient extends AgentService {

    /**
     * Writes a message to the visualiser. Generally, this is used for writing log messages by the
     * {@link com.type2labs.undersea.common.logger.UnderseaLogger}
     *
     * @param data to write
     * @throws IOException if there is an issue communicating with the Visualiser
     */
    void write(String data) throws IOException;

    /**
     * Writes a {@link VisualiserMessage} containing the {@link com.type2labs.undersea.common.agent.Agent}'s current
     * state
     *
     * @param data to write
     * @throws IOException if there is an issue communicating with the Visualiser
     */
    void write(VisualiserMessage data) throws IOException;

    /**
     * Performs a {@link VisualiserClient#write(VisualiserMessage)} operation. Populating the
     * {@link VisualiserMessage} in the process
     */
    void update();

}
