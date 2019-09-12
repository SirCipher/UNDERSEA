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

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.MWComponentInstance;
import com.mathworks.toolbox.javabuilder.internal.MWFunctionSignature;
import com.mathworks.toolbox.javabuilder.internal.MWMCR;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The <code>DelaunayDecomposer</code> class provides a Java interface to MATLAB functions.
 * The interface is compiled from the following files:
 * <pre>
 *  C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA\\coverageplanner\\matlab\\decompose.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>DelaunayDecomposer</code>
 * instance when it is no longer needed to ensure that native resources allocated by this
 * class are properly freed.
 *
 * @version 0.0
 */
public class DelaunayDecomposer extends MWComponentInstance<DelaunayDecomposer> {

    private static final Logger logger = LogManager.getLogger(DelaunayDecomposer.class);

    /**
     * Tracks all instances of this class to ensure their dispose method is
     * called on shutdown.
     */
    private static final Set<Disposable> sInstances = new HashSet<Disposable>();
    /**
     * Maintains information used in calling the <code>decompose</code> MATLAB function.
     */
    private static final MWFunctionSignature sDecomposeSignature =
            new MWFunctionSignature(/* max outputs = */ 1,
                    /* has varargout = */ false,
                    /* function name = */ "decompose",
                    /* max inputs = */ 1,
                    /* has varargin = */ false);
    private static DelaunayDecomposer instance;

    /**
     * Shared initialization implementation - private
     */
    private DelaunayDecomposer(final MWMCR mcr) {
        super(mcr);

        // add this to sInstances
        synchronized (DelaunayDecomposer.class) {
            sInstances.add(this);
        }
    }

    /**
     * Constructs a new instance of the <code>DelaunayDecomposer</code> class.
     *
     * @throws MWException An error has occurred during the function call.
     */
    public DelaunayDecomposer() throws MWException {

        this(DelaunayDecomposerMCRFactory.newInstance());
    }

    /**
     * @param pathToComponent Path to component directory.
     * @throws MWException An error has occurred during the function call.
     * @deprecated Please use the constructor {@link #DelaunayDecomposer(MWComponentOptions componentOptions)}.
     * The <code>com.mathworks.toolbox.javabuilder.MWComponentOptions</code> class provides an API to set the
     * path to the component.
     */
    @Deprecated
    public DelaunayDecomposer(String pathToComponent) throws MWException {
        this(DelaunayDecomposerMCRFactory.newInstance(getPathToComponentOptions(pathToComponent)));
    }

    /**
     * Constructs a new instance of the <code>DelaunayDecomposer</code> class. Use this
     * constructor to specify the options required to instantiate this component.  The
     * options will be specific to the instance of this component being created.
     *
     * @param componentOptions Options specific to the component.
     * @throws MWException An error has occurred during the function call.
     */
    public DelaunayDecomposer(MWComponentOptions componentOptions) throws MWException {
        this(DelaunayDecomposerMCRFactory.newInstance(componentOptions));
    }

    /**
     * Calls dispose method for each outstanding instance of this class.
     */
    public static void disposeAllInstances() {
        synchronized (DelaunayDecomposer.class) {
            for (Disposable i : sInstances) i.dispose();
            sInstances.clear();
        }
    }

    private static MWComponentOptions getPathToComponentOptions(String path) {
        MWComponentOptions options = new MWComponentOptions(new MWCtfExtractLocation(path),
                new MWCtfDirectorySource(path));
        return options;
    }

    /**
     * Invokes the first MATLAB function specified to MCC, with any arguments given on
     * the command line, and prints the result.
     *
     * @param args arguments to the function
     */
    public static void main(String[] args) {
        try {
            MWMCR mcr = DelaunayDecomposerMCRFactory.newInstance();
            mcr.runMain(sDecomposeSignature, args);
            mcr.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Provides the interface for calling the <code>decompose</code> MATLAB function
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * % X = [0 0; 0 20; 10 50; 20 20; 20 0];
     * </pre>
     *
     * @param lhs List in which to return outputs. Number of outputs (nargout) is
     *            determined by allocated size of this List. Outputs are returned as
     *            sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     *            Each output array should be freed by calling its <code>dispose()</code>
     *            method.
     * @param rhs List containing inputs. Number of inputs (nargin) is determined
     *            by the allocated size of this List. Input arguments may be passed as
     *            sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or
     *            as arrays of any supported Java type. Arguments passed as Java types are
     *            converted to MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void decompose(List lhs, List rhs) throws MWException {
        fMCR.invoke(lhs, rhs, sDecomposeSignature);
    }

    /**
     * Provides the interface for calling the <code>decompose</code> MATLAB function
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * % X = [0 0; 0 20; 10 50; 20 20; 20 0];
     * </pre>
     *
     * @param lhs array in which to return outputs. Number of outputs (nargout)
     *            is determined by allocated size of this array. Outputs are returned as
     *            sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     *            Each output array should be freed by calling its <code>dispose()</code>
     *            method.
     * @param rhs array containing inputs. Number of inputs (nargin) is
     *            determined by the allocated size of this array. Input arguments may be
     *            passed as sub-classes of
     *            <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     *            any supported Java type. Arguments passed as Java types are converted to
     *            MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void decompose(Object[] lhs, Object[] rhs) throws MWException {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sDecomposeSignature);
    }

    /**
     * Provides the standard interface for calling the <code>decompose</code> MATLAB function with
     * 1 input argument.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * % X = [0 0; 0 20; 10 50; 20 20; 20 0];
     * </pre>
     *
     * @param nargout Number of outputs to return.
     * @param rhs     The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] decompose(int nargout, Object... rhs) throws MWException {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs),
                MWMCR.getRhsCompat(rhs, sDecomposeSignature),
                sDecomposeSignature);
        return lhs;
    }

    /**
     * Frees native resources associated with this object
     */
    public void dispose() {
        try {
            super.dispose();
        } finally {
            synchronized (DelaunayDecomposer.class) {
                sInstances.remove(this);
            }
        }
    }
}
