package com.type2labs.undersea.common.service;

import com.type2labs.undersea.common.agent.Agent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * A transportable object which
 */
public class Transaction {

    private static final Object NO_DATA = new Object();

    private final Agent agent;
    private final Collection<Class<? extends AgentService>> destinationServices;
    private final StatusCode statusCode;
    private Object data;
    private CompletableFuture<?> future;

    private Transaction(Agent agent, Collection<Class<? extends AgentService>> destinationServices,
                        CompletableFuture<?> task, StatusCode code, Object data) {
        this.agent = agent;
        this.future = task;
        this.destinationServices = destinationServices;
        this.statusCode = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "agent=" + agent +
                ", destinationServices=" + destinationServices +
                ", statusCode=" + statusCode +
                ", data=" + data +
                ", future=" + future +
                '}';
    }

    public boolean hasData() {
        return data == Transaction.NO_DATA;
    }

    public Agent getAgent() {
        return agent;
    }

    public CompletableFuture<?> getFuture() {
        return future;
    }

    public Collection<Class<? extends AgentService>> getDestinationServices() {
        return destinationServices;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public enum StatusCode {
        ELECTED_LEADER,
        NOT_LEADER,
        FAILING
    }

    public static class Builder {

        private Agent agent;
        private CompletableFuture<?> task;
        private Collection<Class<? extends AgentService>> services = new HashSet<>();
        private StatusCode statusCode;
        private Object data = NO_DATA;

        public Builder(Agent agent) {
            this.agent = agent;
        }

        public Builder withTask(CompletableFuture<?> task) {
            this.task = task;
            return this;
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

        public Builder withStatus(StatusCode status) {
            this.statusCode = status;
            return this;
        }

        public Builder withData(Object data) {
            this.data = data;
            return this;
        }

        public Transaction build() {
            validate();
            return new Transaction(agent, services, task, statusCode, data);
        }

        private void validate() {
            Objects.requireNonNull(agent, "Transaction sender cannot be null");
            Objects.requireNonNull(services, "Transaction destination services cannot be null");
            Objects.requireNonNull(statusCode, "Transaction status code cannot be null");
        }

    }

}
