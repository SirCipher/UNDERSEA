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

package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithmRole;
import com.type2labs.undersea.prospect.impl.GrpcServer;
import com.type2labs.undersea.prospect.impl.RaftState;
import com.type2labs.undersea.prospect.networking.model.RaftClient;

public interface RaftNode extends ConsensusAlgorithm {

    void execute(Runnable task);

    ConsensusAlgorithmRole raftRole();

    RaftState state();

    void toLeader(int term);

    void toFollower(int term);

    void toCandidate();

    GrpcServer server();

    void schedule(Runnable task, long delayInMillis);

    RaftClient self();

}
