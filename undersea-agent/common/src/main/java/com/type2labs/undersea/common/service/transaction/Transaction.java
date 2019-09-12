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

package com.type2labs.undersea.common.service.transaction;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * A transportable object for performing operations between services. Constructed using {@link Transaction.Builder}
 * and executed by the {@link Agent}'s {@link com.type2labs.undersea.common.service.ServiceManager}
 * <p>
 * An example transaction is the consensus service becoming the leader and firing a {@link Transaction} to the
 * {@link com.type2labs.undersea.common.consensus.ConsensusAlgorithm} service with a
 * {@link LifecycleEvent#ELECTED_LEADER} so the mission planner performs mission decomposition.
 */
public class Transaction {

    /**
     * The executor service that will execute the transaction
     */
    private final ListeningExecutorService executorService;

    /**
     * The agent that requested the transaction. This will nearly always be the same agent as the transaction is
     * executed on
     */
    private final Agent agent;

    /**
     * The service that created the transaction
     */
    private final AgentService caller;

    /**
     * The services that the transaction should be executed on. If the transaction is to be executed on many services
     * then a thread pool executor may be preferred over a a single thread executor
     */
    private final Collection<Class<? extends AgentService>> destinationServices;

    /**
     * The status/event code that has triggered this transaction. This transactionData type generic so
     * implementations may
     * implement their own status codes.
     */
    private final Enum<? extends LifecycleEvent> statusCode;

    /**
     * A corresponding object that may be used for performing the transaction. This may be {@code null} and the
     * {@link LifecycleEvent} may be enough to achieve the desired output.
     */
    private TransactionData primaryTransactionData;

    /**
     * A corresponding object that may be used for performing the transaction. This may be {@code null} and the
     * {@link LifecycleEvent} may be enough to achieve the desired output.
     */
    private TransactionData secondaryTransactionData;

    private Transaction(Agent agent,
                        Collection<Class<? extends AgentService>> destinationServices,
                        Enum<? extends LifecycleEvent> code,
                        TransactionData primaryTransactionData,
                        TransactionData secondaryTransactionData,
                        ListeningExecutorService executorService, AgentService caller) {
        this.agent = agent;
        this.destinationServices = destinationServices;
        this.statusCode = code;
        this.primaryTransactionData = primaryTransactionData;
        this.secondaryTransactionData = secondaryTransactionData;
        this.executorService = executorService;
        this.caller = caller;
    }

    public TransactionData getSecondaryTransactionData() {
        return secondaryTransactionData;
    }

    public Agent getAgent() {
        return agent;
    }

    public Collection<Class<? extends AgentService>> getDestinationServices() {
        return destinationServices;
    }

    public ListeningExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "executorService=" + executorService +
                ", agent=" + agent.name() +
                ", caller=" + caller +
                ", destinationServices=" + destinationServices +
                ", statusCode=" + statusCode +
                ", primaryTransactionData=" + primaryTransactionData +
                ", secondaryTransactionData=" + secondaryTransactionData +
                '}';
    }

    public AgentService getCaller() {
        return caller;
    }

    public Enum<? extends LifecycleEvent> getStatusCode() {
        return statusCode;
    }

    public TransactionData getPrimaryTransactionData() {
        return primaryTransactionData;
    }

    /**
     * Builds a {@link Transaction} given the parameters, validating it to ensure that it has been constructed correctly
     */
    public static class Builder {

        private Agent agent;
        private Collection<Class<? extends AgentService>> services = new HashSet<>();
        private Enum<? extends LifecycleEvent> statusCode;
        private TransactionData primaryTransactionData;
        private TransactionData secondaryTransactionData;
        private ListeningExecutorService executorService;
        private AgentService caller;

        public Builder(Agent agent) {
            this.agent = agent;
        }

        /**
         * Validates and builds the {@link Transaction}
         *
         * @return a constructed {@link Transaction}
         * @throws NullPointerException if any of the required parameters have not been provided
         */
        public Transaction build() {
            validate();
            return new Transaction(agent, services, statusCode, primaryTransactionData, secondaryTransactionData,
                    executorService, caller);
        }

        public Builder invokedBy(AgentService caller) {
            this.caller = caller;
            return this;
        }

        public Builder forAllServices() {
            this.services = agent.serviceManager().getServiceClasses();
            return this;
        }

        public Builder forAllRunningServices() {
            this.services = agent.serviceManager().getRunningServiceClasses();
            return this;
        }

        public Builder usingExecutorService(ListeningExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder forService(Class<? extends AgentService> service) {
            this.services = Collections.singleton(service);
            return this;
        }

        public Builder forServices(Collection<Class<? extends AgentService>> services) {
            this.services = services;
            return this;
        }

        private void validate() {
            Objects.requireNonNull(agent, "Transaction sender cannot be null");
            Objects.requireNonNull(services, "Transaction destination services cannot be null");
            Objects.requireNonNull(statusCode, "Transaction status code cannot be null");
            Objects.requireNonNull(caller, "Invoked by cannot be null");
        }

        public Builder withPrimaryData(TransactionData primaryTransactionData) {
            this.primaryTransactionData = primaryTransactionData;
            return this;
        }

        public Builder withSecondaryData(TransactionData secondaryTransactionData) {
            this.secondaryTransactionData = secondaryTransactionData;
            return this;
        }

        public Builder withStatus(LifecycleEvent status) {
            this.statusCode = status;
            return this;
        }

    }

}
