package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.type2labs.undersea.common.agent.Agent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * A transportable object which
 */
public class Transaction {

    private static final Object NO_DATA = new Object();

    private final ListeningExecutorService executorService;
    private final Agent agent;
    private final Collection<Class<? extends AgentService>> destinationServices;
    private final Enum<? extends TransactionStatusCode> statusCode;
    private Object data;

    private Transaction(Agent agent,
                        Collection<Class<? extends AgentService>> destinationServices,
                        Enum<? extends TransactionStatusCode> code,
                        Object data,
                        ListeningExecutorService executorService) {
        this.agent = agent;
        this.destinationServices = destinationServices;
        this.statusCode = code;
        this.data = data;
        this.executorService = executorService;
    }

    public ListeningExecutorService getExecutorService() {
        return executorService;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "agent=" + agent +
                ", destinationServices=" + destinationServices +
                ", statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }

    public boolean hasData() {
        return data == Transaction.NO_DATA;
    }

    public Agent getAgent() {
        return agent;
    }


    public Collection<Class<? extends AgentService>> getDestinationServices() {
        return destinationServices;
    }

    public Enum<? extends TransactionStatusCode> getStatusCode() {
        return statusCode;
    }

    public static class Builder {

        private Agent agent;
        private Collection<Class<? extends AgentService>> services = new HashSet<>();
        private Enum<? extends TransactionStatusCode> statusCode;
        private Object data = NO_DATA;
        private ListeningExecutorService executorService;

        public Builder(Agent agent) {
            this.agent = agent;
        }

        public Builder forAllServices() {
            this.services = agent.services().getServiceClasses();
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

        public Builder withStatus(TransactionStatusCode status) {
            this.statusCode = status;
            return this;
        }

        public Builder withData(Object data) {
            this.data = data;
            return this;
        }

        public Builder forExecutorService(ListeningExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Transaction build() {
            validate();
            return new Transaction(agent, services, statusCode, data, executorService);
        }

        private void validate() {
            Objects.requireNonNull(agent, "Transaction sender cannot be null");
            Objects.requireNonNull(services, "Transaction destination services cannot be null");
            Objects.requireNonNull(statusCode, "Transaction status code cannot be null");
            Objects.requireNonNull(executorService, "Executor service cannot be null");
        }

    }

}
