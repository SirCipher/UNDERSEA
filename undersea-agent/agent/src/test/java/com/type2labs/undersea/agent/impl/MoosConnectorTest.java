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

package com.type2labs.undersea.agent.impl;

import com.type2labs.undersea.common.agent.AgentFactory;

public class MoosConnectorTest {

    //    @Test
//    @IgnoredOnCi
    public void doTest() throws InterruptedException {
        MoosConnector moosConnector = new MoosConnector();
        moosConnector.initialise(new AgentFactory().create());
        moosConnector.run();

        System.out.println();

        Thread.sleep(10000);

    }

}