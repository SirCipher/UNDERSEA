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

package com.type2labs.undersea.missionplanner.utils;

import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWMCROption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatlabUtils {

    private static final Logger logger = LogManager.getLogger(MatlabUtils.class);

    public static synchronized void initialise() {
        if (!MWApplication.isMCRInitialized()) {
            logger.debug("Initialising MW Application with arguments NODISPLAY and NOJVM");

            MWApplication.initialize(MWMCROption.NODISPLAY, MWMCROption.NOJVM);

            logger.debug("Initialised MW Application");
        }
    }

    public static void dispose(MWArray... arrays) {
        for (MWArray a : arrays) {
            a.dispose();
        }
    }

}
