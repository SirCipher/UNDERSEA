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

package com.type2labs.undersea.common.config;

import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;

import static com.type2labs.undersea.utilities.preconditions.Preconditions.isNotNull;

/**
 * The configuration of an {@link com.type2labs.undersea.common.agent.Agent}
 */
@SuppressWarnings("UnusedReturnValue")
public class RuntimeConfig {

    private boolean visualiserEnabled = true;
    private MissionParameters missionParameters;
    private CostConfiguration costConfiguration;
    private int logBatchSize = 10;
    private double subsystemDecayTime = 0.1;

    /**
     * The batch size that {@link com.type2labs.undersea.common.logger.model.LogEntry}s should be sent in by the
     * {@link com.type2labs.undersea.common.logger.model.LogService}
     *
     * @return the batch size
     */
    public int getLogBatchSize() {
        return logBatchSize;
    }

    public RuntimeConfig withLogBatchSize(int logBatchSize) {
        this.logBatchSize = logBatchSize;
        return this;
    }

    public RuntimeConfig missionParameters(MissionParameters missionParameters) {
        this.missionParameters = missionParameters;
        return this;
    }

    public MissionParameters missionParameters() {
        return missionParameters;
    }

    public boolean isVisualiserEnabled() {
        return visualiserEnabled;
    }

    public RuntimeConfig enableVisualiser(boolean visualiserEnabled) {
        this.visualiserEnabled = visualiserEnabled;

        return this;
    }

    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }

    public RuntimeConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }

    /**
     * While testing, a {@link com.type2labs.undersea.common.agent.Subsystem}'s cost will exponentially decay. This
     * returns the decay factor, lambda
     *
     * @return the decay time
     */
    public double subsystemDecayTime() {
        return subsystemDecayTime;
    }
}
