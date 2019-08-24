package com.type2labs.undersea.utilities.process;

import java.io.File;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class ProcessBuilderUtil {

    public static ProcessBuilder getSanitisedBuilder(){
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
