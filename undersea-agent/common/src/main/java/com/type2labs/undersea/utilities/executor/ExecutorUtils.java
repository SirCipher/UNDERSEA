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

package com.type2labs.undersea.utilities.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    /**
     * Creates a new {@link ExecutorService} that reuses a fixed number of threads. Initialised with a
     * {@link java.util.concurrent.ThreadFactory} using the provided name format
     * <p>
     * If using for an agent then the name format should be prepended with the agent's name
     *
     * @param noThreads  in the pool
     * @param nameFormat the name format for the {@link ThreadFactoryBuilder}
     * @return a newly created thread pool
     * @throws IllegalArgumentException is the number of threads if <=0
     */
    public static ExecutorService newExecutor(int noThreads, String nameFormat) {
        return Executors.newFixedThreadPool(noThreads, new ThreadFactoryBuilder().setNameFormat(nameFormat).build());
    }

}
