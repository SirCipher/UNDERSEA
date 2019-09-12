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


import com.type2labs.undersea.common.agent.AbstractAgent;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class UnderseaAgent extends AbstractAgent {

    private double speed;
    private double remainingBattery;
    private double range;
    private double accuracy;
    private String name;

    public UnderseaAgent(RuntimeConfig config, String name, ServiceManager serviceManager           , PeerId peerId) {
        super(config, name, serviceManager, peerId);

        ThreadLocalRandom random = ThreadLocalRandom.current();

        speed = random.nextDouble(100);
        remainingBattery = random.nextDouble(100);
        range = random.nextDouble(100);
        accuracy = random.nextDouble(100);
    }

    public static UnderseaAgent DEFAULT() {
        return new UnderseaAgent(
                new RuntimeConfig(),
                "DEFAULT",
                new ServiceManager(),
                PeerId.newId()
        );
    }

    public void start() {
        serviceManager().startServices();
    }

}
