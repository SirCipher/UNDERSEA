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

package com.type2labs.undersea.agent;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.monitor.Visualiser;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class Test {

    public static void main(String[] args) {
        new Visualiser();

        AgentFactory agentFactory = new AgentFactory();

        for (int i = 0; i < 5; i++) {
            VisualiserClientImpl client = new VisualiserClientImpl();
            Agent agent = agentFactory.create();
            agent.config().enableVisualiser(true);

            agent.serviceManager().registerService(client);
            agent.serviceManager().startServices();
        }
    }

}
