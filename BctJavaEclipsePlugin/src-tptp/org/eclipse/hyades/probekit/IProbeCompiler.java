/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: IProbeCompiler.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.hyades.models.internal.probekit.Probekit;

/**
 * This is the interface that probe compilers must implement.
 * The Probe Compiler Factory will return one of these when asked.
 * The decision of which one it returns depends on who extends the
 * org.eclipse.hyades.probekit.compilerFactory extension point, and how.
 * 
 * HOW TO USE PROBE COMPILERS: You instantiate one from the
 * factory, and you feed it one or more resources of type
 * org.eclipse.hyades.models.probekit.Probekit. Then you extract
 * the artifacts necessary to use the probes: a string representing
 * the Java source for the class containing the probe fragments,
 * and a string representing the engine script that will instrument
 * class files based on those probes.
 *
 * The class ProbekitException is used for all exceptions.
 * It wraps all exceptions that can occur inside the compiler,
 * including internal errors like NullPointerException.
 * The exception text string tells what really happened.
 * 
 * Use the ProbeCompiler class this way:
 *	1. Create an instance
 *		Use default constructor or convenience constructors
 *	2. Populate the instance by calling addIFile or the two-parameter addResource
 *		(all other adders are deprecated: they don't return the id of the added
 *		probe source, and they don't know where to hang markers to report errors) 
 *	3. Use getGeneratedSource() and getEngineScript() (in either order) to
 *	   get the Java source and engine script text, respectively.
 *
 * You need to call setClassPrefix() to set the first part of the
 * generated class name. Also, call setPackageName to put the
 * generated classes in a package - otherwise they'll be in the
 * default package.
 * 
 * The class prefix normally comes from the file name, and the
 * package name might come from anywhere. Of course
 * file names might have characters in them that are not valid
 * Java identifier characters. For consistency among users
 * of Probekit, the utility function makeValidJavaIdentifier 
 * is provided in this.
 */

public interface IProbeCompiler {
	/**
	 * This string is the marker type string for Probekit markers (see IMarker).
	 */
	public static final String PROBEKIT_PROBLEM_MARKER = "org.eclipse.hyades.probekit.probekitProblem"; //$NON-NLS-1$

	//------------------------------- parameter setters ------------------------------
	/**
	 * Sets the class prefix to use for generated probe classes. They will
	 * be in a package determined by the probe compiler, but here the caller
	 * can set the class name prefix. You'll get classes called classPrefix_probe.
	 */
	public abstract void setClassPrefix(String prefix) throws ProbekitException;
	/**
	 * Sets the package name to use for generated probe classes.
	 * Must be a valid Java package name string, with dots as separators,
	 * like "com.sample.probes"
	 * 
	 * One reason to put probes in a package is to put them into a package
	 * which is filtered by default from other kinds of observation.
	 * 
	 * @param pkg the package name to use, in dotted form.
	 * @throws ProbekitException if the pkg argument is not a valid Java package name string.
	 */
	public abstract void setPackageName(String pkg) throws ProbekitException;
	
	/**
	 * Returns the suffix string that is added to
	 * the string from setClassPrefix() to form the class name.
	 * Callers have to know this so they can form the Java source file name.
	 * <P>
	 * Don't change the value of this string ("_probe") because it's something
	 * users of the static instrumentation system are likely to write into their
	 * automation scripts. 
	 * @return the class name suffix
	 */
	public abstract String getClassSuffix();
	
	/**
	 * Method to turn any string into a valid Java class name prefix.
	 * Characters which are inappropriate as Java class name characters
	 * (including leading characters which can't <em>start</em> Java class names)
	 * are replaced with _XXXX (where XXXX is the hex code for the character,
	 * using as many digits as necessary).
	 * <P>
	 * Note that the output, while valid for Java class names, is not 
	 * unique or reversible: The output string "_201" can come from a 
	 * single-letter input string with char value 0x201,
	 * or from a two-letter input string " 1" (space-one).
	 * @param arg the string to convert
	 * @return a converted form of the string which is a valid Java class name prefix
	 */
	public String makeValidJavaIdentifier(String arg);
	
	//------------------------------- resource adders ------------------------------
	/**
	 * Add a probekit object to the list of probekits this compiler will compile.
	 * @deprecated use addIFile
	 * @param probekit the Probekit to add
	 */
	public abstract void addProbekit(Probekit probekit) throws ProbekitException;
	/**
	 * Load the named file as a resource and add probekit objects found
	 * at the top level to this Compiler.
	 * @deprecated use addIFile
	 * @param file the name of the file to load.
	 * @throws ProbekitException
	 */
	public abstract void addFile(String file) throws ProbekitException;
	/**
	 * Add probekits that appear at the top level of a resource 
	 * to the probekits this compiler will compile.
	 * @deprecated use addIFile
	 * @param res the Resource to scan for probekits.
	 */
	public abstract void addResource(Resource res) throws ProbekitException;
	/**
	 * Add probekits that appear in the indicated IFile.
	 * Use this in preference to other adders, so errors will be reported
	 * as markers on the proper resource. Opens the IFile as an EMF Resource
	 * and calls addResrouce(res, f).
	 * @param f the IFile to add to the Compiler.
	 * @return the "id" string for the added probe, or null if it does not specify one
	 * @throws ProbekitException for serious errors; uses markers for probe source errors etc.
	 */
	public abstract String addIFile(IFile f) throws ProbekitException;
	/**
	 * Add probekits that appear in the indicated resource.
	 * The first argument is an EMF resource; the second is 
	 * @param res the EMF Resource containing a probekit
	 * @param ires the Eclipse IResource that this probekit source comes from.
	 * This is the resource that error markers will be attached to.
	 * @return the "id" string for the added probe, or null if it does not specify one
	 * @throws ProbekitException for serious errors; uses markers for probe source errors etc.
	 */
	public abstract String addResource(Resource res, IResource ires) throws ProbekitException;

	//------------------------------- verify ------------------------------
	/**
	 * Tests a probe set for correctness and consistency. Throws an exception if the probe set fails
	 * any tests. The tests tend to be for things that aren't already forbidden by the modeling.
	 * @throws ProbekitException if there is any inconsistency in the set. The exception string
	 * describes one or more problems encountered, separated by the host-specific newline separator.
	 * It's possible that not all problems will be reported - for instance, only one internal
	 * integrity failure for a given probe might get reported, even if it has more than one problem.
	 *
	 * This verification step permits the core implementation to have less internal
	 * consistency checking logic.
	 */
	public abstract void verify() throws ProbekitException;
	//------------------------------- main functionality ------------------------------
	/**
	 * Get the generated source. If the source hasn't been generated yet, it will be.
	 * Internally calls verify() and can throw ProbekitException if there is a problem.
	 * @return the generated source as a string.
	 */
	public abstract String getGeneratedSource() throws ProbekitException;
	/**
	 * Get the script that describes this probe set to the BCI engine.
	 * 
	 * @return the string form of the description data
	 * @throws ProbekitException
	 */
	public abstract String getEngineScript() throws ProbekitException;
}