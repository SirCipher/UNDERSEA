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

/*
 * MATLAB Compiler: 7.0 (R2018b)
 * Date: Sun Jul 21 15:15:42 2019
 * Arguments:
 * "-B""macro_default""-W""java:DelaunayDecomposer,
 * DelaunayDecomposer""-T""link:lib""-d""C:\\Users\\tklap\\Desktop\\PACS\\coverage
 * -planning\\DelaunayDecomposer\\decompose\\for_testing""class{DelaunayDecomposer:C:\\Users\\tklap\\Desktop\\PACS
 * \\UNDERSEA
 * \\coverageplanner\\matlab\\decompose.m}""-a""C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA\\coverageplanner\\matlab
 * \\decompose.m"
 */

package com.type2labs.undersea.missionplanner.decomposer.delaunay;

import com.mathworks.toolbox.javabuilder.MWComponentOptions;
import com.mathworks.toolbox.javabuilder.MWCtfClassLoaderSource;
import com.mathworks.toolbox.javabuilder.MWCtfExtractLocation;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.internal.MWMCR;

/**
 * <i>INTERNAL USE ONLY</i>
 */
public class DelaunayDecomposerMCRFactory {


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
                    new MWCtfClassLoaderSource(DelaunayDecomposerMCRFactory.class)
            );


    private DelaunayDecomposerMCRFactory() {
        // Never called.
    }

    public static MWMCR newInstance(MWComponentOptions componentOptions) throws MWException {
        if (null == componentOptions.getCtfSource()) {
            componentOptions = new MWComponentOptions(componentOptions);
            componentOptions.setCtfSource(sDefaultComponentOptions.getCtfSource());
        }

        return MWMCR.newInstance(
                componentOptions,
                DelaunayDecomposerMCRFactory.class,
                sComponentName,
                sComponentId,
                new int[]{9, 5, 0}
        );
    }

    public static MWMCR newInstance() throws MWException {
        return newInstance(sDefaultComponentOptions);
    }
}
