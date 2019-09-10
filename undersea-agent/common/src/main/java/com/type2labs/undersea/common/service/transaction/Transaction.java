package com.type2labs.undersea.common.service.transaction;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * A transportable object for performing operations between services.
 * <p>
 * An example transaction is the consensus service achieving becoming the leader and requesting
 * {@link LifecycleEvent#ELECTED_LEADER} the mission planner perform mission decomposition.
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
                ", agent=" + agent +
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
            this.services = agent.services().getServiceClasses();
            return this;
        }

        public Builder forAllRunningServices() {
            this.services = agent.services().getRunningServiceClasses();
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
