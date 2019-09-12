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

package com.type2labs.undersea.utilities.testing;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;

/**
 * Ensures that unit tests annotated with {@link IgnoredOnCi} are ignored on a build server when the gradle flag
 * {@code buildserver} is supplied.
 * <p>
 * At the moment, if this results in a class having no unit tests once the filtering has taken place then JUnit will
 * throw an exception. So an empty test needs to exist if this is a posibility
 * <p>
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
