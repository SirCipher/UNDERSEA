package com.type2labs.undersea.utilities.process;

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
