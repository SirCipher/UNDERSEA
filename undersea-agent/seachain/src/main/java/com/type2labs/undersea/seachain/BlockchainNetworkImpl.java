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

package com.type2labs.undersea.seachain;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.blockchain.BlockchainNetwork;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BlockchainNetworkImpl implements BlockchainNetwork {

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }


    @Override
    public void run() {
    }

    @Override
    public void add(LogEntry logEntry) {

    }

    @Override
    public List<LogEntry> read(int startIndex) {
        return new ArrayList<>();
    }

    @Override
    public List<LogEntry> readNextForClient(Client client) {
        return new ArrayList<>();
    }

    @Override
    public void appendEntries(List<LogEntry> logEntries) {

    }

    @Override
    public int size() {
        return 0;
    }

}
