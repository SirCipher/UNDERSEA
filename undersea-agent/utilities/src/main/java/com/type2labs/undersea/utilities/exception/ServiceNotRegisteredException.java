package com.type2labs.undersea.utilities.exception;

import java.util.Objects;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class ServiceNotRegisteredException extends RuntimeException {

    public ServiceNotRegisteredException(Class<?> service, Class<?> caller) {
        super(Objects.requireNonNull(service).getSimpleName()
                + " is not registered. It is required by: "
                + Objects.requireNonNull(caller).getSimpleName());
    }

}
