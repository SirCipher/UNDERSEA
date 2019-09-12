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

package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.AgentAware;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * A top-level structure that all services must implement. Services are registered and managed by the
 * {@link ServiceManager}. The {@link ServiceManager} will initialise, start and waiting for the service to
 * transition to a running state.
 */
public interface AgentService extends Runnable, AgentAware {

    /**
     * Denotes that a service's requirement is critical to the {@link com.type2labs.undersea.common.agent.Agent}'s
     * operation. Without it, the required duties cannot be performed. If a service fails, such as one which is
     * performing a non-mission-critical task, then the {@link ServiceManager} will not start to shutdown the agent.
     *
     * @return whether or not the service is critical
     */
    default boolean isCritical() {
        return true;
    }

    /**
     * Signals that the service should start the shutdown procedure. This must be a non-blocking process
     */
    default void shutdown() {

    }

    /**
     * Whether or not the service has started successfully. This is polled during startup before starting the next
     * service. If the {@link AgentService#transitionTimeout()} has been exceeded before the service has started then
     * it is assumed that an error has occurred.
     *
     * @return whether or not the service has successfully started
     */
    default boolean started() {
        return true;
    }

    /**
     * Executes a transaction on the service and returns the result
     *
     * @param transaction to execute
     * @return scheduled future
     */
    default ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }

    default ExecutorService transactionExecutor() {
        return ThrowableExecutor.newSingleThreadExecutor();
    }

    /**
     * The time (in milliseconds) that the {@link ServiceManager} should wait before assuming that there has been an
     * error during a state transition. See {@link ServiceManager.ServiceState}. Transition events occur when a
     * service is starting and stopping.
     *
     * @return the transition timeout
     */
    default long transitionTimeout() {
        return ServiceManager.DEFAULT_TRANSITION_TIMEOUT;
    }


    /**
     * Register a {@link ServiceCallback} with the {@link AgentService} that will fire when the provided
     * {@link LifecycleEvent} happens
     *
     * @param serviceCallback to register
     */
    default void registerCallback(ServiceCallback serviceCallback) {

    }

    /**
     * Denotes the services that this {@link AgentService} requires in order to run correctly.
     *
     * @return the required services
     */
    default Collection<Class<? extends AgentService>> requiredServices() {
        return new ArrayList<>();
    }

    /**
     * Fire all {@link ServiceCallback}s that have been registered with the service that match the provided
     * {@link LifecycleEvent}
     *
     * @param event to fire
     */
    default void fireCallback(LifecycleEvent event) {

    }
}
