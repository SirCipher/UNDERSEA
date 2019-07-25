/*
 * MATLAB Compiler: 7.0 (R2018b)
 * Date: Sun Jul 21 15:15:42 2019
 * Arguments:
 * "-B""macro_default""-W""java:Decomposer,Decomposer""-T""link:lib""-d""C:\\Users\\tklap\\Desktop\\PACS\\coverage
 * -planning\\Decomposer\\decompose\\for_testing""class{Decomposer:C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA
 * \\coverageplanner\\matlab\\decompose.m}""-a""C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA\\coverageplanner\\matlab
 * \\decompose.m"
 */

package com.type2labs.undersea.missionplanner.decomposer;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.MWMCR;

/**
 * <i>INTERNAL USE ONLY</i>
 */
public class DecomposerMCRFactory {


    /**
     * Component's uuid
     */
    private static final String sComponentId = "Decomposer_019FA9021181EF23B33BFC4A00EA4048";

    /**
     * Component name
     */
    private static final String sComponentName = "Decomposer";


    /**
     * Pointer to default component options
     */
    private static final MWComponentOptions sDefaultComponentOptions =
            new MWComponentOptions(
                    MWCtfExtractLocation.EXTRACT_TO_CACHE,
                    new MWCtfClassLoaderSource(DecomposerMCRFactory.class)
            );


    private DecomposerMCRFactory() {
        // Never called.
    }

    public static MWMCR newInstance(MWComponentOptions componentOptions) throws MWException {
        if (null == componentOptions.getCtfSource()) {
            componentOptions = new MWComponentOptions(componentOptions);
            componentOptions.setCtfSource(sDefaultComponentOptions.getCtfSource());
        }

        return MWMCR.newInstance(
                componentOptions,
                DecomposerMCRFactory.class,
                sComponentName,
                sComponentId,
                new int[]{9, 5, 0}
        );
    }

    public static MWMCR newInstance() throws MWException {
        return newInstance(sDefaultComponentOptions);
    }
}
