package com.type2labs.undersea.utilities.testing;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class UnderseaRunner extends BlockJUnit4ClassRunner {

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public UnderseaRunner(Class<?> klass) throws InitializationError, NoTestsRemainException {
        super(klass);

        if ("true".equals(System.getProperty("buildserver"))) {
            filter(new Filter() {
                @Override
                public boolean shouldRun(Description description) {
                    for (Annotation a : description.getAnnotations()) {
                        if (a instanceof IgnoredOnCi) {
                            return false;
                        }
                    }

                    return true;
                }

                @Override
                public String describe() {
                    return null;
                }
            });

        }
    }

}
