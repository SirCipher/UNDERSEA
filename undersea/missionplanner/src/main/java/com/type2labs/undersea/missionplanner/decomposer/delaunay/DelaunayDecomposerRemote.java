/*
 * MATLAB Compiler: 7.0 (R2018b)
 * Date: Sun Jul 21 15:15:42 2019
 * Arguments:
 * "-B""macro_default""-W""java:DelaunayDecomposer,DelaunayDecomposer""-T""link:lib""-d""C:\\Users\\tklap\\Desktop\\PACS\\coverage
 * -planning\\DelaunayDecomposer\\decompose\\for_testing""class{DelaunayDecomposer:C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA
 * \\coverageplanner\\matlab\\decompose.m}""-a""C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA\\coverageplanner\\matlab
 * \\decompose.m"
 */

package com.type2labs.undersea.missionplanner.decomposer.delaunay;

import com.mathworks.toolbox.javabuilder.pooling.Poolable;

import java.rmi.RemoteException;

/**
 * The <code>DelaunayDecomposerRemote</code> class provides a Java RMI-compliant interface to
 * MATLAB functions. The interface is compiled from the following files:
 * <pre>
 *  C:\\Users\\tklap\\Desktop\\PACS\\UNDERSEA\\coverageplanner\\matlab\\decompose.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>DelaunayDecomposerRemote</code>
 * instance when it is no longer needed to ensure that native resources allocated by this
 * class are properly freed, and the server-side proxy is unexported.  (Failure to call
 * dispose may result in server-side threads not being properly shut down, which often
 * appears as a hang.)
 * <p>
 * This interface is designed to be used together with
 * <code>com.mathworks.toolbox.javabuilder.remoting.RemoteProxy</code> to automatically
 * generate RMI server proxy objects for instances of DelaunayDecomposer.DelaunayDecomposer.
 */
public interface DelaunayDecomposerRemote extends Poolable {

    /**
     * Provides the standard interface for calling the <code>decompose</code> MATLAB
     * function with 1 input argument.
     * <p>
     * Input arguments to standard interface methods may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of any
     * supported Java type (i.e. scalars and multidimensional arrays of any numeric,
     * boolean, or character type, or String). Arguments passed as Java types are
     * converted to MATLAB arrays according to default conversion rules.
     * <p>
     * All inputs to this method must implement either Serializable (pass-by-value) or
     * Remote (pass-by-reference) as per the RMI specification.
     * <p>
     * Documentation as provided by the author of the MATLAB function:
     * <pre>
     * % X = [0 0; 0 20; 10 50; 20 20; 20 0];
     * </pre>
     *
     * @param nargout Number of outputs to return.
     * @param rhs     The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs are
     * returned as sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code> method.
     * @throws RemoteException An error has occurred during the function call or
     *                         in communication with the server.
     */
    Object[] decompose(int nargout, Object... rhs) throws RemoteException;

    /**
     * Frees native resources associated with the remote server object
     *
     * @throws RemoteException An error has occurred during the function call or in communication with the server.
     */
    void dispose() throws RemoteException;
}
