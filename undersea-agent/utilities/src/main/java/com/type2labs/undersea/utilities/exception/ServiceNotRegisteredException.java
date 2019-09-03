package com.type2labs.undersea.utilities.exception;

import java.util.Objects;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class ServiceNotRegisteredException extends RuntimeException {

    private static final long serialVersionUID = 2974497035142592479L;

    public ServiceNotRegisteredException(String message) {
        super(message);
    }

    public ServiceNotRegisteredException(Class<?> service, Class<?> caller) {
        super(Objects.requireNonNull(service).getSimpleName()
                + " is not registered. It is required by: "
                + Objects.requireNonNull(caller).getSimpleName());
    }

}
