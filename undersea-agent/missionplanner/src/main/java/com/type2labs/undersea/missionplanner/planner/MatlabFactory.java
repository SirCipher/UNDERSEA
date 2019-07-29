package com.type2labs.undersea.missionplanner.planner;

import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWMCROption;
import com.type2labs.undersea.missionplanner.decomposer.delaunay.DelaunayDecomposer;
import com.type2labs.undersea.missionplanner.planner.tsp.TspMissionPlanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatlabFactory {

    private static final Logger logger = LogManager.getLogger(TspMissionPlanner.class);

    private static DelaunayDecomposer delaunayDecomposer;

    static {
        logger.debug("Initising MW Application with arguments NODISPLAY and NOJVM");

        if (!MWApplication.isMCRInitialized()) {
            MWApplication.initialize(MWMCROption.NODISPLAY, MWMCROption.NOJVM);
        }
    }


    public static DelaunayDecomposer getDelaunayDecomposer() {
        if (delaunayDecomposer == null) {
            try {
                logger.info("Initialising delaunay decomposer");
                delaunayDecomposer = new DelaunayDecomposer();
                logger.info("Initialised delaunay decomposer");
            } catch (MWException e) {
                logger.error("Unable to initialise decomposer");
                throw new RuntimeException("Unable to initialise decomposer", e);
            }
        }

        return delaunayDecomposer;
    }

}
