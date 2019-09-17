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

/**
 * Models a subsystem that is present on an {@link Agent}. A subsystem has an associated cost that denotes the
 * overall quality of the system. Such costs are used when the
 * {@link com.type2labs.undersea.common.consensus.ConsensusAlgorithm} is calculating the overall cost for a leader
 */
public interface Subsystem {

    /**
     * A human-readable name for this subsystem
     *
     * @return the name
     */
    String name();

    /**
     * The rate this subsystem operates at
     *
     * @return the rate
     */
    double rate();

    /**
     * The reliability of this subsystem
     *
     * @return the reliability
     */
    double reliability();

    /**
     * The accuracy of this subsystem
     *
     * @return the accuracy
     */
    double accuracy();

    double health();

}
