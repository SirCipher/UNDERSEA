package com.type2labs.undersea.common.service;

import com.type2labs.undersea.common.agent.Agent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Transaction {

    public enum StatusCode {
        ELECTED_LEADER,
        NOT_LEADER,
        FAILING
    }

    private final Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public CompletableFuture<?> getFuture() {
        return future;
    }

    public Collection<Class<? extends  AgentService>> getDestinationServices() {
        return destinationServices;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    private CompletableFuture<?> future;
    private final Collection<Class<? extends  AgentService>> destinationServices;
    private final StatusCode statusCode;

    private Transaction(Agent agent, Collection<Class<? extends  AgentService>> destinationServices, CompletableFuture<?> task, StatusCode code) {
        this.agent = agent;
        this.future = task;
        this.destinationServices = destinationServices;
        this.statusCode = code;
    }

    public static class Builder {

        private Agent agent;
        private CompletableFuture<?> task;
        private Collection<Class<? extends  AgentService>> services = new HashSet<>();
        private StatusCode statusCode;

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

        public Builder forService(Class<? extends  AgentService> service) {
            this.services = Collections.singleton(service);
            return this;
        }

        public Builder forServices(Collection<Class<? extends  AgentService>> services) {
            this.services = services;
            return this;
        }

        public Builder withStatus(StatusCode status) {
            this.statusCode = status;
            return this;
        }

        public Transaction build() {
            validate();
            return new Transaction(agent, services, task, statusCode);
        }

        private void validate() {
            Objects.requireNonNull(agent, "Transaction sender cannot be null");
            Objects.requireNonNull(services, "Transaction destination services cannot be null");
            Objects.requireNonNull(statusCode, "Transaction status code cannot be null");
        }

    }

}
