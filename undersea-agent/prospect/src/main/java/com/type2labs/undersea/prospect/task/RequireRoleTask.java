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
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RequireRoleTask implements Runnable {

    private final Logger logger = LogManager.getLogger(RequireRoleTask.class);
    private final RaftNode raftNode;
    private final ConsensusAlgorithmRole requestedRole;

    public RequireRoleTask(RaftNode raftNode, ConsensusAlgorithmRole requestedRole) {
        this.raftNode = raftNode;
        this.requestedRole = requestedRole;
    }

    @Override
    public void run() {
        if (raftNode.raftRole() != requestedRole) {
            logger.error(raftNode.parent().name() + ": unable to run task as agent does not meet requested role: " + requestedRole,
                    raftNode.parent());
            return;
        }

        try {
            innerRun();
        } catch (Throwable e) {
            logger.error("Exception caught", raftNode.parent(), e);
        }
    }

    protected abstract void innerRun();

}
