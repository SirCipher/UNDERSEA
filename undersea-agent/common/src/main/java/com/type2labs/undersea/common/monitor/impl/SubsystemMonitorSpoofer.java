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

package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Subsystem;

import java.util.concurrent.ThreadLocalRandom;

public class SubsystemMonitorSpoofer extends SubsystemMonitorImpl {
    
    private static int initCount = 0;

    static {
        initCount++;
    }

    private final long initTime = System.currentTimeMillis();

    public SubsystemMonitorSpoofer() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        super.registerSpeedRange(new Range(0, random.nextDouble(100), random.nextDouble(100)));
    }

    private double getElapsedTimeInMinutes() {
        return ((System.currentTimeMillis() - initTime) / 1000F) % 3600 / 60;
    }

    @Override
    public void monitorSubsystem(Subsystem subsystem) {
        super.monitorSubsystem(new DecayableSubsystemWrapper(subsystem));
    }

    private class DecayableSubsystemWrapper extends SubsystemWrapper {

        DecayableSubsystemWrapper(Subsystem subsystem) {
            super(subsystem);
        }

        @Override
        public double getStatus() {
            double decayFactor = parent().config().subsystemDecayTime();
            double systemTime = getElapsedTimeInMinutes();

            return Math.pow((1 / super.getStatus() * Math.exp(-decayFactor * systemTime * initCount)), -1);
        }
    }

}
