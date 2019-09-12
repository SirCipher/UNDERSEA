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

package com.type2labs.undersea.utilities.processbuilder;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class ProcessBuilderUtil {

    /**
     * Constructs a {@link ProcessBuilder} that does not have a {@code DYLD_LIBRARY_PATH} or {@code LD_LIBRARY_PATH}
     * set and the error stream redirected to the standard output. This is useful when the library paths have
     * conflicting paths. Such as for MOOS
     *
     * @return a sanitized {@link ProcessBuilder}
     */
    public static ProcessBuilder getSanitisedBuilder() {
        ProcessBuilder pb = new ProcessBuilder();

        pb.redirectErrorStream(true);

        /*
         Process builder copies the current environment variables across to the new process, however, the MOOS
         libraries require a different version of libtiff that MATLAB uses and it causes the display to fail
         to load
         */
        pb.environment().remove("LD_LIBRARY_PATH");
        pb.environment().remove("DYLD_LIBRARY_PATH");

        return pb;
    }

}
