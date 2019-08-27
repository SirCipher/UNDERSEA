package com.type2labs.undersea.utilities.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that a test should be ignored on a CI server.
 * <p>
 * See {@link UnderseaRunner}
 * <p>
 * Created by Thomas Klapwijk on 2019-08-24.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoredOnCi {
}
