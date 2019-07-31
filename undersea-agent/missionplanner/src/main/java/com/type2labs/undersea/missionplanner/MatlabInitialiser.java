package com.type2labs.undersea.missionplanner;

import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWMCROption;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatlabInitialiser {

    private static final Logger logger = LogManager.getLogger(MatlabInitialiser.class);

    public static void initalise(){
        if (!MWApplication.isMCRInitialized()) {
            logger.debug("Initialising MW Application with arguments NODISPLAY and NOJVM");

            MWApplication.initialize(MWMCROption.NODISPLAY, MWMCROption.NOJVM);

            logger.debug("Initialised MW Application");
        }
    }

}
