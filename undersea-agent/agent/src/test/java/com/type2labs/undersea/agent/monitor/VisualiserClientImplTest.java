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

package com.type2labs.undersea.agent.monitor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.monitor.Visualiser;

public class VisualiserClientImplTest {

    //    @Test
//    @IgnoredOnCi
    public void doTest() throws InterruptedException {
        new Visualiser();

        VisualiserClientImpl client = new VisualiserClientImpl();
        Agent agent = new AgentFactory().create();
        agent.config().enableVisualiser(true);

        client.initialise(agent);
        client.run();

        Thread.sleep(10000);
    }

}