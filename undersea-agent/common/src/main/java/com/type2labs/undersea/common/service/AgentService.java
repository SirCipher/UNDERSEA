package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.AgentAware;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
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

}
