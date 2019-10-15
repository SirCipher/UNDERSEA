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

package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.common.consensus.ConsensusAlgorithmRole;
import com.type2labs.undersea.common.logger.UnderseaLogger;
import com.type2labs.undersea.prospect.model.ConsensusNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteTaskTimeout implements Runnable {

    private static final Logger logger = LogManager.getLogger(VoteTaskTimeout.class);

    private final ConsensusNode consensusNode;

    VoteTaskTimeout(ConsensusNode consensusNode) {
        this.consensusNode = consensusNode;
    }

    @Override
    public void run() {
        if (consensusNode.clusterRole() != ConsensusAlgorithmRole.CANDIDATE) {
            return;
        }

        UnderseaLogger.info(logger, consensusNode.parent(), "Voting round timed out, trying again");
        consensusNode.execute(new AcquireStatusTask(consensusNode));
    }
}
