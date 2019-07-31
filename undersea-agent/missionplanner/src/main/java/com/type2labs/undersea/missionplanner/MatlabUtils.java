package com.type2labs.undersea.missionplanner;

import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWMCROption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatlabUtils {

    private static final Logger logger = LogManager.getLogger(MatlabUtils.class);

    public static void initialise() {
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
