/**********************************************************************
 * Copyright (c) 2005, 2010 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: Compiler.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit.internal;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.models.internal.probekit.DocumentRoot;
import org.eclipse.hyades.models.internal.probekit.Fragment;
import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.models.internal.probekit.Import;
import org.eclipse.hyades.models.internal.probekit.Label;
import org.eclipse.hyades.models.internal.probekit.Name;
import org.eclipse.hyades.models.internal.probekit.Probe;
import org.eclipse.hyades.models.internal.probekit.Probekit;
import org.eclipse.hyades.models.internal.probekit.StaticField;
import org.eclipse.hyades.models.internal.probekit.Target;
import org.eclipse.hyades.probekit.IProbeCompiler;
import org.eclipse.hyades.probekit.LocationAdapter;
import org.eclipse.hyades.probekit.ProbekitCompileProblemException;
import org.eclipse.hyades.probekit.ProbekitException;
import org.eclipse.hyades.probekit.ProbekitPlugin;

/**
 * This is the default implementation of IProbeCompiler.
 * CompilerFactory will return an instance of this class if nobody
 * uses the extension point to tell it to return a different one.
 */

public class Compiler implements IProbeCompiler {
	//------------------------------- constructors ------------------------------
	/**
	 * The default constructor. The factory uses this.
	 */
	public Compiler() {
		// State initializes to "dirty"
	}

	/**
	 * Constructor which takes a Probekit object.
	 * 
	 * @deprecated
	 * @param probekit
	 */
	public Compiler(Probekit probekit) {
		probekits.add(new ProbekitPair(probekit, ProbekitPlugin.getWorkspace().getRoot()));
		// State initializes to "dirty"
	}

	/**
	 * Constructor which takes a file name, opens it as a resource,
	 * and adds probes found inside to the compiler.
	 * Since no IResource is available to report errors against,
	 * errors are reported using marker objects at the top level of the workspace.
	 * @deprecated
	 * @param file the name of the file to load.
	 * @throws ProbekitException for serious errors (not just syntax errors in the probe source)
	 */
	public Compiler(String file) throws ProbekitException {
		addFile(file);
		// State initializes to "dirty"
	}

	//------------------------------- resource adders ------------------------------
	/**
	 * Add a probekit object to the list of probekits this compiler will compile.
	 * Since no IResource is available to report errors against,
	 * errors are reported using marker objects at the top level of the workspace.
	 * @deprecated
	 * @param probekit the Probekit to add
	 * @throws ProbekitException for serious errors (not just syntax errors in the probe source)
	 */
	public void addProbekit(Probekit probekit) throws ProbekitException {
		probekits.add(new ProbekitPair(probekit, ProbekitPlugin.getWorkspace().getRoot()));
	}

	/**
	 * Load the named file as a resource and add probekit objects found
	 * at the top level to this Compiler.
	 * Since no IResource is available to report errors against,
	 * errors are reported using marker objects at the top level of the workspace.
	 * 
	 * @deprecated
	 * @param file the name of the file to load.
	 * @throws ProbekitException for serious errors (not just syntax errors in the probe source)
	 */
	public void addFile(String file) throws ProbekitException {
		URI fileURI = URI.createFileURI(file);
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource res = resourceSet.getResource(fileURI, true);
		if (res == null) {
			throw new ProbekitException(ProbekitPlugin.getString("Compiler.CantReadSourceFile") + file); //$NON-NLS-1$
		}
		addResource(res);
	}
	
	/**
	 * Add probekits that appear at the top level of an EMF resource 
	 * to the probekits this compiler will compile.
	 * <P>
	 * Since no IResource is available to report errors against,
	 * errors are reported using marker objects at the top level of the workspace.
	 * <P>
	 * See {@link addResource(Resource,IResource)} regarding LocationAdapters
	 * being put on the EObjects of the Resource.
	 * @deprecated Use {@link addResource(Resource,IResource)} instead,
	 * because it can report errors against the proper Workspace resource.
	 * @param res the Resource to scan for probekits.
	 * @throws ProbekitException for serious errors
	 */
	public void addResource(Resource res) throws ProbekitException {
		Iterator iter = res.getContents().iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (!(obj instanceof DocumentRoot)) {
				// This Should Not Happen.
				// If the resource's iterator didn't cough up a DocumentRoot, then what is it?
				throw new ProbekitException("Probe compiler internal error: resource doesn't contain a DocumentRoot"); //$NON-NLS-1$
			}
			else {
				DocumentRoot droot = (DocumentRoot)obj;
				Probekit pk = droot.getProbekit();
				if (pk == null) {
					// the resource doesn't contain a probekit ... the add "succeeds" without
					// an error because you asked to add all probekits in the resource, and there
					// aren't any, so we declare victory but do nothing.
				}
				else {
					probekits.add(new ProbekitPair(pk, ProbekitPlugin.getWorkspace().getRoot()));
				}
			}
		}		
		setDirtyState();
	}
	
	/**
	 * A class to hold a pair: a probekit and its resource, for error reporting.
	 */
	static class ProbekitPair {
		public Probekit probekit;
		public IResource iresource;
		ProbekitPair(Probekit pk, IResource ires) {
			probekit = pk;
			iresource = ires;
		}
	}

	/**
	 * Add probekits that appear at the top level of an EMF resource 
	 * to the probekits this compiler will compile.
	 * This method takes an IResource argument for error reporting:
	 * errors are reported using Markers on that resource. (The IResource
	 * isn't used for anything else.)
	 * <P>
	 * The Resource you pass in will have {@link LocationAdapter} instances
	 * attached to its EObjects using the EMF eAdapters system. The LocationAdapter
	 * tells which lines in the generated Java source came from each EObject
	 * in the Resource.
	 * @param res the EMF Resource to scan for probekits.
	 * @param ires the Eclipse IResource to use for reporting errors
	 * @return the "id" of the probe in the file
	 * @throws ProbekitException for serious errors (not just syntax errors in the probe source)
	 */
	public String addResource(Resource res, IResource ires) throws ProbekitException {
		// Remove Probekit markers on this IResource: they're obsolete since
		// you're about to try to compile it again.
		this.removeProbekitMarkers(ires); 
		Iterator iter = res.getContents().iterator();
		String idString = null;
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (!(obj instanceof DocumentRoot)) {
				// This Should Not Happen.
				// If the resource's iterator didn't cough up a DocumentRoot, then what is it?
				throw new ProbekitException(ProbekitPlugin.getString("Compiler.NoProbekitResource")); //$NON-NLS-1$
			}
			else {
				DocumentRoot droot = (DocumentRoot)obj;
				Probekit pk = droot.getProbekit();
				if (pk == null) {
					// No probekit resources in this document
					reportError(ProbekitPlugin.getString("Compiler.NoProbekitSource"), ires); //$NON-NLS-1$
				}
				else {
					probekits.add(new ProbekitPair(pk, ires));
					idString = pk.getId();
				}
			}
		}
		setDirtyState();
		return idString;
	}

	public String addIFile(IFile file) throws ProbekitException {
		// Remove any past Probekit problem markers that are on this file.
		this.removeProbekitMarkers(file);

		// If the file is empty or something, say so with a clear error message

		Resource res;
		try {
			// If the is out of synch, missing, or something, say so.
			if (!file.exists()) {
				reportError(ProbekitPlugin.getString("Compiler.MissingFile"), file); //$NON-NLS-1$
				throw new ProbekitCompileProblemException(EMPTY_STRING);
			}
			IPath p = file.getLocation();
			if (p == null) { 
				reportError(ProbekitPlugin.getString("Compiler.NoProbekitResource"), file); //$NON-NLS-1$
				throw new ProbekitCompileProblemException(EMPTY_STRING);
			}
			File f = p.toFile();
			if (f.length() == 0) {
				reportError(ProbekitPlugin.getString("Compiler.EmptyFile"), file); //$NON-NLS-1$
				throw new ProbekitCompileProblemException(EMPTY_STRING);
			}

			URI fileURI = URI.createFileURI(p.toOSString());
			ResourceSet resourceSet = new ResourceSetImpl();
			res = resourceSet.getResource(fileURI, true);
			if (res == null) {
				reportError(ProbekitPlugin.getString("Compiler.NoProbekitResource"), file); //$NON-NLS-1$
				throw new ProbekitCompileProblemException(EMPTY_STRING);
			}
		}
		catch (ProbekitCompileProblemException ex) {
			// Pass simple exceptions out: they've already been reported
			throw ex;
		}
		catch (Exception e) {
			// Report any exception as an error in this resource.
			// Fix bugzilla 70386: don't append e.getMessage to the message,
			// because it might not be translated. Put the exception in the error log instead.
			IStatus status = new Status(IStatus.ERROR, 
					"org.eclipse.hyades.probekit", 0,  //$NON-NLS-1$
					ProbekitPlugin.getString("Compiler.NoProbekitObject"), //$NON-NLS-1$
					e);
			ProbekitPlugin.getDefault().getLog().log(status);
			
			reportError(
					ProbekitPlugin.getString("Compiler.NoProbekitObjectWithErrorLog") + e.getMessage(),  //$NON-NLS-1$
					file);

			// Now that we've reported the problem, throw the "little exception"
			// that tells our caller that the resource didn't compile (but no further reporting is needed)
			throw new ProbekitCompileProblemException(EMPTY_STRING);
		}
		return addResource(res, file);
	}

	//------------------------------- parameter setters ------------------------------
	/**
	 * Sets the class prefix to use for generated probe classes. They will
	 * be in a package determined by the probe compiler, but here the caller
	 * can set the class name prefix. You'll get classes called classPrefix_probe.
	 * @throws ProbekitException if you pass a class prefix that's not valid Java syntax
	 */
	public void setClassPrefix(String prefix) throws ProbekitException {
		if (!isValidJavaIdentifier(prefix, false)) {
			// API abuse: bad parameter passed from our caller.
			throw new ProbekitException(ProbekitPlugin.getString("Compiler.BadSetPrefixArg") + prefix); //$NON-NLS-1$
		}
		this.classPrefix = prefix;
		setDirtyState();
	}

	/**
	 * Sets the package name to use. If this is never called, 
	 * generated classes will appear in the default package.
	 * @param pkg
	 * @throws ProbekitException
	 */
	public void setPackageName(String pkg) throws ProbekitException {
		if (!isValidJavaIdentifier(pkg, true)) {
			// API abuse: bad parameter passed from our caller.
			// Non-translated string because this is only intended for developers to interpret.
			throw new ProbekitException("Probekit API error: bad argument to setPackageName: " + pkg); //$NON-NLS-1$
		}
		this.packageName = pkg;
	}

	// Utility functions
	
	/**
	 * API function to return the suffix string that is added to
	 * the string from setClassPrefix() to form the class name.
	 * Callers have to know this so they can form the Java source file name.
	 * <P>
	 * Don't change the value of this string ("_probe") because it's something
	 * users of the static instrumentation system are likely to write into their
	 * automation scripts. 
	 * @return the class name suffix
	 */
	public String getClassSuffix() {
		return "_probe"; //$NON-NLS-1$
	}

	/**
	 * See the interface definition for an explanation.
	 * Utility function for callers to convert file name parts
	 * into class prefixes consistently.
	 */
	public String makeValidJavaIdentifier(String arg) {
		StringBuffer out = new StringBuffer();
		int len = arg.length();
		if(len > 0) {
			char c;
			c = arg.charAt(0);
			if (!Character.isJavaIdentifierStart(c)) {
				out.append('_');
				out.append(Integer.toString((int)c, 16).toUpperCase());
			}
			else out.append(c);
			for (int i = 1; i < len; i++) {
				c = arg.charAt(i);
				if (!Character.isJavaIdentifierPart(c)) {
					out.append('_');
					out.append(Integer.toString((int)c, 16).toUpperCase());
				}
				else out.append(c);
			}
		}
		return out.toString();
	}

	//------------------ Private implementation ---------------------
	/*
	 * These strings are used instead of literals because externalizing strings
	 * leaves behind ugly comments.
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String SPACE_STRING = " "; //$NON-NLS-1$

	//------------------------------- problem marker handling ------------------------------

	/**
	 * Remove all markers of the Probekit problem type from the indicated resource.
	 * This is called when something is added to the compiler, on the theory that
	 * we're about to find out what its (possibly new) problems are.
	 * <P>
	 * If the thing being compiled did not come from a resource, then ires
	 * will be the workbench root, and in that case this will remove all probekit 
	 * markers related to all probekits that didn't come from resources.
	 * That's probably not what you wanted, but probekits that don't come from
	 * resources are all deprecated anyway.
	 * @param ires the resource for which to remove all Probekit markers.
	 */
	private void removeProbekitMarkers(IResource ires) {
		try {
			ires.deleteMarkers(PROBEKIT_PROBLEM_MARKER, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			// Well, we tried...
		}
	}

	/**
	 * Create a probekit problem marker on the resource 
	 * using the message. Also remember that this Compiler
	 * object has errors, so we don't continue trying to compile it.
	 * @param msg the message
	 * @param ires the IResource to put the marker on
	 */
	private void reportError(String msg, IResource ires) {
		compilerState = STATE_ERROR;
		try {
			IMarker m = ires.createMarker(PROBEKIT_PROBLEM_MARKER);
			m.setAttribute(IMarker.MESSAGE, msg);
			m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		} catch (CoreException e) {
			// Report this failure to the error log
			IStatus status = new Status(IStatus.ERROR, 
					"org.eclipse.hyades.probekit", 0,  //$NON-NLS-1$
					ProbekitPlugin.getString("Compiler.MarkerCreateError"), //$NON-NLS-1$
					null);
			ProbekitPlugin.getDefault().getLog().log(status);
		}
	}

	//------------------------------- verify ------------------------------
	
	/**
	 * Tests a probe set for correctness and consistency. Throws an exception if the probe set fails
	 * any tests. The tests tend to be for things that aren't already forbidden by the modeling.
	 * This verification step permits the core implementation to have less internal
	 * consistency checking logic. 
	 * <P>
	 * Reports errors as Problem markers on the original resource.
	 * 
	 * @throws ProbekitException for serious errors (not just syntax errors)
	 * @throws ProbekitCompileProblemException for problems that have been reported as markers.
	 */
	public void verify() throws ProbekitException {
		// verify each probe's structure

		if (probekits.size() == 0) {
			throw new ProbekitException(ProbekitPlugin.getString("Compiler.VerifyBeforeAdd")); //$NON-NLS-1$
		}
		for (Iterator probekitIter = probekits.iterator(); probekitIter.hasNext(); ) {
			ProbekitPair pair = (ProbekitPair)probekitIter.next();
			Probekit pk = pair.probekit;
			IResource ires = pair.iresource;

			// Do not verify id - the compiler shouldn't fail probes with no id,
			// leave that to the "export" operation.

			// No verification for the version string - you can use anything you want.

			// Verify labels
			// We do not verify that there are no duplicate language strings.
			// If you make that mistake you're on your own.

			if (pk.getLabel() != null) {
				boolean seenDefaultLangYet = false;
				int localizedLabelCount = 0;
				Iterator labelIter = pk.getLabel().iterator();
				while (labelIter.hasNext()) {
					Object o = labelIter.next();
					if (!(o instanceof Label)) {
						reportError("Probekit internal error: a member of the Labels list is not a label", ires); //$NON-NLS-1$
					}
					else {
						Label label = (Label)o;
						String lang = label.getLang();
						String name = label.getName();
						String desc = label.getDescription();
						if (lang == null || lang.equals(EMPTY_STRING)) {
							if (seenDefaultLangYet) {
								reportError(ProbekitPlugin.getString("Compiler.MultipleDefaultLabels"), ires); //$NON-NLS-1$
							}
							seenDefaultLangYet = true;
						}
						else {
							// Can't verify lang against locale code letters - any string is technically legal.
							localizedLabelCount++;
						}
						
						if (name == null || name.equals(EMPTY_STRING)) {
							reportError(ProbekitPlugin.getString("Compiler.EmptyNameInLabel"), ires); //$NON-NLS-1$
						}
						// OK for description to be empty.
					}
				}
				// Error out if there is more than one lang-specific label and no defaults
				if (seenDefaultLangYet == false && localizedLabelCount > 1) {
					reportError(ProbekitPlugin.getString("Compiler.MultipleLabelsNoDefault"), ires); //$NON-NLS-1$
				}
			}

			// Nothing to check for FragmentAtSharedScope, because it can be
			// present or absent, and if present it can be the empty string -
			// this happens if you type something in the editor, then delete it all.
			
			if (pk.getProbe() == null) {
				reportError("Probekit has a null probe list (internal error)", ires); //$NON-NLS-1$
			}
			else {
				if (pk.getProbe().size() == 0)
					reportError(ProbekitPlugin.getString("Compiler.NoProbesInProbekit"), ires); //$NON-NLS-1$

				for (Iterator iter = pk.getProbe().iterator(); iter.hasNext(); ) {
					Object o = iter.next();
					if (!(o instanceof Probe)) {
						reportError(ProbekitPlugin.getString("Compiler.NonProbeObject"), ires); //$NON-NLS-1$
					}
					else {
						Probe probe = (Probe)o;
						verifyOneProbe(probe, ires);
					}
				}
			}
		}
		if (compilerState == STATE_ERROR) {
			throw new ProbekitCompileProblemException(EMPTY_STRING);
		}
	}

	/**
	 * List of Java primitive type names. None of these is allowed to be the type
	 * of a staticField in a probe.
	 */
	static final String[] primitiveTypes = { "int", "long", "char", "short",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
											"byte", "boolean", "float", "double" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	/**
	 * Verifies a single probe. Writes markers for most types of errors.
	 * @param probe 
	 * @throws ProbekitException if there is an internal error
	 */
	private void verifyOneProbe(Probe probe, IResource ires) {
		// These verification tests are implemented:
		// has a non-null fragment list (but the list may be empty)
		// no duplicate fragment types
		// no unrecognized fragment type names
		// no invalid combination of fragments (callsite vs. method)
		// no duplicate data items in a fragment
		// no unrecognized data items in a fragment
		// data items in all fragments are appropriate for that fragment
		// data item names are valid Java identifiers
		// all fragments should have some "code" (even an empty string - not null)
		// If there is a FragmentAtClassScope, its code should not be null
		// well formed imports (if they exist, string is not null)
		// well formed fragment at class scope (if it exists, string is not null)
		// well formed targets - no nulls, and only valid rule strings (include and exclude)
		// No controlKey - it's not implemented yet.
		// No invocationObject - it's mostly implemented but not tested.
		// At most one of each fragment type is allowed: no duplicates.
		// Callsite and non-callsite fragments can't mix
		// StaticField (if present) specifies a valid non-primitive Java type string
		// No fragment uses staticField data item unless staticField is specified
		// Targets aren't all of type "include" ... this is always user error
		// 

		// TODO: These verification tests are not implemented:
		// report on targets/filters that provably exclude everything
		// (if there's a controlKey, it should be a valid identifier)
		// (only one controlKey)
		// (invocation object name (if present) is a valid Java identifier)
		// no two data items have the same name
		// (no data item has the name of the invocationObject)

		// Note about missing strings: a "missing" string might be null,
		// or it might be a string with no characters in it.
		// In the current (December 2003) editor, if you never fill anything 
		// in you get null, but if you fill in something and then delete it all 
		// you get an empty string. All verification of strings must check for both.

		// verify imports
		if (probe.getImport() != null) {
			for (Iterator importIter = probe.getImport().iterator() ;
				 importIter.hasNext() ; ) 
			{
			 	Import imp = (Import)importIter.next();
			 	String impText = imp.getText();
			 	if (impText == null || impText.equals(EMPTY_STRING)) 
			 		reportError(ProbekitPlugin.getString("Compiler.EmptyPackageName"), ires); //$NON-NLS-1$
			}
		}

		// Nothing to verify for fragment at class scope:
		// An empty body is allowed, because the probe editor leaves an
		// empty body when you enter something, then erase what you entered.

		// verify staticField, if any
		// Type string is not optional, and should be a valid Java identifier plus dots
		StaticField staticField = probe.getStaticField(); 
		if (staticField != null) {
			String staticFieldType = staticField.getType();
			if (staticFieldType == null || staticFieldType.equals(EMPTY_STRING)) {
				reportError(ProbekitPlugin.getString("Compiler.EmptyStaticFieldType"), ires); //$NON-NLS-1$
			}
			else {
				if (!isValidJavaIdentifier(staticFieldType, true)) {
					reportError(ProbekitPlugin.getString("Compiler.BadStaticFieldType") + staticFieldType, ires); //$NON-NLS-1$
				}
				for (int i = 0; i < primitiveTypes.length; i++) {
					if (staticFieldType.equals(primitiveTypes[i])) {
						reportError(ProbekitPlugin.getString("Compiler.StaticFieldTypeIsPrimitive") + staticFieldType, ires); //$NON-NLS-1$
					}
				}
			}
		}

		// Verify all fragments
		if (probe.getFragment().size() == 0 && staticField == null) {
			// A probe with no fragments and no static field actually does nothing.
			reportError(ProbekitPlugin.getString("Compiler.NoFragments"), ires); //$NON-NLS-1$
		}
		
		boolean fragmentsInThisProbe[] = new boolean[fragmentDescriptors.length];	// initialized to false

		for (Iterator iter = probe.getFragment().iterator(); iter.hasNext(); ) {
			Fragment f = (Fragment)iter.next();
			FragmentType fenum = f.getType();
			if (fenum == null) {
				// internal error - does not happen
				reportError("Probekit internal error: a fragment type is null", ires); //$NON-NLS-1$
				continue;
			}
			String ftype = fenum.getName();
			if (ftype == null || ftype.equals(EMPTY_STRING)) {
				reportError("Probekit internal error: a fragment type string is null or empty", ires); //$NON-NLS-1$
				continue;
			}

			int fragTypeNum;
			try {
				fragTypeNum = FragTypeNameToNumber(ftype);
				if (fragmentsInThisProbe[fragTypeNum]) {
					reportError(ProbekitPlugin.getString("Compiler.MultipleFragmentsSameType") + ftype, ires); //$NON-NLS-1$
				}
				fragmentsInThisProbe[fragTypeNum] = true;
			}
			catch (ProbekitException e) {
				// Never happens: EMF would have thrown an error for an invalid string long ago.
				reportError(ProbekitPlugin.getString("Compiler.InvalidFragmentType") + ftype, ires); //$NON-NLS-1$
				continue;
			}

			// Verify data item types for this fragment
			// dataItemDescs[].typeName holds the name of each type.
			// At most one of each type is allowed.
			// Some data types are not allowed in some fragment types.
			// There are no invalid combinations of data types with other data types.
			boolean hasType[] = new boolean[dataItemDescs.length];	// initially all false

			if (f.getData() != null) {
				for (Iterator dataIter = f.getData().iterator(); dataIter.hasNext(); ) {
					DataItem data = (DataItem)dataIter.next();
					DataType denum = data.getType();
					if (denum == null) {
						reportError("Probekit internal error: a data item type enum is null", ires); //$NON-NLS-1$
					}
					else {
						String dtype = denum.getName();
						if (dtype == null) {
							reportError("Probekit internal error: a data item has null type string", ires); //$NON-NLS-1$
						}
						else {
							boolean found_dtype_match = false;
							for (int i = 0; i < dataItemDescs.length; i++) {
								if (dtype.equals(dataItemDescs[i].typeName)) {
									if (hasType[i]) {
										reportError(ProbekitPlugin.getString("Compiler.DuplicateDataItem1") + ftype + ProbekitPlugin.getString("Compiler.DuplicateDataItem2") + dtype, ires); //$NON-NLS-1$ //$NON-NLS-2$
									}
									hasType[i] = true;
									
									// Report an error if this fragment type doesn't allow this data type
									if (!fragmentDescriptors[fragTypeNum].allowableDataItems[i]) {
										reportError(ProbekitPlugin.getString("Compiler.DataItemNotAllowed1") +  //$NON-NLS-1$  
												" \"" +  //$NON-NLS-1$
												dtype + "\" " +  //$NON-NLS-1$
												ProbekitPlugin.getString("Compiler.DataItemNotAllowed2") + ftype,  //$NON-NLS-1$ 
												ires);
									}
		
									// Report an error if this is "staticField" and there isn't a
									// staticField declared in the probe
									if (dtype.equals("staticField")) { //$NON-NLS-1$
										if (staticField == null) {
											reportError(ProbekitPlugin.getString("Compiler.NoStaticFieldAvailable") + ftype, ires); //$NON-NLS-1$
										}
									}
									found_dtype_match = true;
									break;
								}
							}
							if (found_dtype_match == false) {
								// Fell out of the loop without finding a match
								reportError(ProbekitPlugin.getString("Compiler.BadDataItemType") + dtype, ires); //$NON-NLS-1$
							}
							if (data.getName() == null || data.getName().equals(EMPTY_STRING)) {
								reportError(ProbekitPlugin.getString("Compiler.MissingDataItemName"), ires); //$NON-NLS-1$
							} 
							else {
								if (!isValidJavaIdentifier(data.getName(), false)) {
									reportError(ProbekitPlugin.getString("Compiler.BadDataItemName") + data.getName(), ires); //$NON-NLS-1$
								}
							}
						}
					}
				}
			}

			if (f.getCode() == null || f.getCode().equals(EMPTY_STRING)) {
				reportError(ProbekitPlugin.getString("Compiler.FragmentIsMissingCode"), ires); //$NON-NLS-1$
			}
		}

		// Deny illegal combinations of non-callsite and callsite fragments.
		// Please forgive the use of constant array indexes here. 
		// Refer to fragmentDescriptors[] for each element's meaning.
		boolean is_callsite_probe = (fragmentsInThisProbe[3] | fragmentsInThisProbe[4]);
		boolean is_method_probe = (fragmentsInThisProbe[0] | fragmentsInThisProbe[1] | fragmentsInThisProbe[2] | 
									fragmentsInThisProbe[5] | fragmentsInThisProbe[6]);
		if (is_callsite_probe && is_method_probe) {
			reportError(ProbekitPlugin.getString("Compiler.BadFragmentTypeCombo"), ires); //$NON-NLS-1$
		}

		// Deny features that shouldn't be in callsite probes
		if (is_callsite_probe && staticField != null) {
			reportError(ProbekitPlugin.getString("Compiler.CallsiteCantUseStaticField"), ires); //$NON-NLS-1$
		}

		// Verify filters/targets
		boolean someExcludeTargets = false;
		boolean someBadTargets = false;
		int sawAllStars = 0;	// 0=no, 1=yes, 2=already reported
		boolean lastTargetIsInclude = false;
		for (Iterator targetIter = probe.getTarget().iterator(); targetIter.hasNext() ; ) {
			if (sawAllStars == 1) {
				reportError(ProbekitPlugin.getString("Compiler.TargetsAfterStars"), ires); // $NON-NLS-1$
				sawAllStars = 2;
			}
			Target targ = (Target)targetIter.next();
			String targType = targ.getType();
			if (targType == null || targType.equals(EMPTY_STRING)) {
				reportError(ProbekitPlugin.getString("Compiler.MissingTargetType"), ires); //$NON-NLS-1$
			}
			else {
				if (targType.equals("exclude")) { //$NON-NLS-1$
					someExcludeTargets = true;
					lastTargetIsInclude = false;
				}
				else if (targType.equals("include")) { //$NON-NLS-1$
					lastTargetIsInclude = true;
				}
				else {
					someBadTargets = true;
					reportError(ProbekitPlugin.getString("Compiler.BadTargetType") + targ.getType(), ires); //$NON-NLS-1$
				}
				// Note: null or empty wildcard pattern strings are allowed
				if (hasWhitespace(targ.getClassName()) ||
					hasWhitespace(targ.getMethod()) ||
					hasWhitespace(targ.getPackage()) ||
					hasWhitespace(targ.getSignature()))
				{
						reportError(ProbekitPlugin.getString("Compiler.BadTargetPattern"), ires); //$NON-NLS-1$
				}
				// If the pattern is nothing but wildcards, remember it; subsequent patterns generate a warning
				if (emptyNullOrStar(targ.getClassName()) &&
					emptyNullOrStar(targ.getMethod()) &&
					emptyNullOrStar(targ.getPackage()) &&
					emptyNullOrStar(targ.getSignature())) 
				{
					sawAllStars = 1;
				}
			}
		}
		if (probe.getTarget().size() > 0) {
			if (!someBadTargets) {
				// Only report these if there are no bad targets.
				// Too confusing otherwise, if a bad target is "EXCLUDE" for example.
				if (!someExcludeTargets) {
					reportError(ProbekitPlugin.getString("Compiler.BadTargetAllInclude"), ires); //$NON-NLS-1$
				}
				else if (lastTargetIsInclude) {
					reportError(ProbekitPlugin.getString("Compiler.LastTargetIsInclude"), ires); //$NON-NLS-1$
				}
			}
		}

		// TODO: remove this when controlKey is implemented and tested
		if (probe.getControlKey() != null) {
			reportError(ProbekitPlugin.getString("Compiler.ControlKeyNotImpl"), ires); //$NON-NLS-1$
		}

		// TODO: remove this when invocationObject is fully implemented and tested
		if (probe.getInvocationObject() != null) {
			reportError(ProbekitPlugin.getString("Compiler.InvocationObjectNotImpl"), ires); //$NON-NLS-1$
		}
	}

	/**
	 * Checks to see if a given string is a valid Java identifier.
	 * @param id the string to test
	 * @return true if it is valid, false if not
	 */
	boolean isValidJavaIdentifier(String id, boolean dotsAllowed) {
		if (id.length() == 0) return false;
		if (!Character.isJavaIdentifierStart(id.charAt(0))) return false;
		for (int i = 1; i < id.length(); i++) {
			char c = id.charAt(i);
			if (!((dotsAllowed && c == '.') || Character.isJavaIdentifierPart(c))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks to see if the string has any whitespace characters in it.
	 * Returns true if so. Uses java.lang.Character.isWhitespace().
	 * Accepts null as a string to test, and returns false.
	 * @param s the string to test
	 * @return true if there are any whitespace characters in the string
	 */
	boolean hasWhitespace(String s) {
		if (s == null) return false;
		for (int i = 0; i < s.length(); i++) {
			if (java.lang.Character.isWhitespace(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return a copy of the input string, with slashes in place of dots.
	 * @param s the input string.
	 * @return a copy of the string, with dots replaced with slashes.
	 */
	static String dotsToSlashes(String s) {
		StringBuffer new_s = new StringBuffer(s);
		for (int i = 0; i < s.length(); i++) {
			if (new_s.charAt(i) == '.') 
				new_s.setCharAt(i, '/');
		}
		return new_s.toString();
	}

	/**
	 * Return true if the input string reference is null, 
	 * or an empty string, or a string consisting of nothing but 
	 * an asterisk (star) character. In a target element,
	 * all of these mean "wildcard pattern that matches anything."
	 * @param s the string to test
	 * @return true iff the string is empty, null, or star.
	 */
	static boolean emptyNullOrStar(String s) {
		return (s == null || s.length() == 0 || s.equals("*"));
	}

	//------------------------------- main functionality ------------------------------

	private void compile() throws ProbekitException {
		if (compilerState == STATE_DIRTY) {
			// Dirty compiler: remove error markers and verify.
			//
			// If all probes came in through addIFile or addResource, then
			// all pair.iresource values have already had all old markers 
			// removed from them by addResource().
			//
			// But if any came in through the (deprecated) functions that don't
			// take an iresource argument, then there might be problem markers
			// on the Workbench Root resource. Here is where we remove them, not
			// when the resource are added. (Probably doesn't matter, really.)

			for (Iterator probekitIter = probekits.iterator(); probekitIter.hasNext(); ) {
				ProbekitPair pair = (ProbekitPair)probekitIter.next();
				IResource ires = pair.iresource;
				if (ires == ires.getWorkspace().getRoot()) {
					removeProbekitMarkers(ires);
					break;
				}
			}

			verify();
		}
		if (compilerState == STATE_ERROR) {
			// All errors are already reported as resource markers
			throw new ProbekitCompileProblemException(EMPTY_STRING);
		}
		generateSource();
		generateEngineScript();
	}

	/**
	 * Get the generated source. If the source hasn't been generated yet, it will be.
	 * Internally calls verify() and can throw ProbekitException if there is a problem.
	 * @return the generated source as a string.
	 */
	public String getGeneratedSource() throws ProbekitException {
		compile();
		// compile throws an error if it fails, so it must have succeeded.
		return generatedSource.toString();	
	}

	/**
	 * Get the script that describes this probe set to the BCI engine.
	 * 
	 * @return the string form of the description data
	 * @throws ProbekitException
	 */
	public String getEngineScript() throws ProbekitException {
		compile();
		// compile throws an error if it fails, so it must have succeeded.
		return engScript.toString();
	}
	
	void generateEngineScript() throws ProbekitException {
		engScript = new StringBuffer();
		for (Iterator iter = probeDetailsMap.entrySet().iterator(); iter.hasNext() ; ) {
			Map.Entry entry = (Map.Entry)iter.next();
			Probe probe = (Probe)entry.getKey();
			ProbeDetails details = (ProbeDetails)entry.getValue();
			
			engScript.append("REM " + details.commentName + lineSeparator); //$NON-NLS-1$
			engScript.append("PROBE" + lineSeparator); //$NON-NLS-1$
			
			for (Iterator targetIter = probe.getTarget().iterator(); targetIter.hasNext(); ) {
				// Note: a "missing" wildcard might be null or it might be the empty string.
				// See verify() for an explanation.
				Target t = (Target)targetIter.next();
				String pkg = t.getPackage();
				if (emptyNullOrStar(pkg)) pkg = "*"; //$NON-NLS-1$
				// Don't dots-to-slashes the package name if it's just "." (meaning the global, unnamed package)
				if (!pkg.equals(".")) pkg = dotsToSlashes(pkg);						// 58277
				String cls = t.getClassName();
				if (emptyNullOrStar(cls)) cls = "*"; //$NON-NLS-1$
				String mth = t.getMethod();
				if (emptyNullOrStar(mth)) mth = "*"; //$NON-NLS-1$
				String sig = t.getSignature();
				if (emptyNullOrStar(sig)) sig = "*"; //$NON-NLS-1$
				engScript.append("RULE " +  //$NON-NLS-1$
								 pkg + SPACE_STRING + 
								 cls + SPACE_STRING +
								 mth + SPACE_STRING +
								 sig + SPACE_STRING +
								 t.getType() + lineSeparator);
			}
			
			for (Iterator fragIter = probe.getFragment().iterator(); fragIter.hasNext(); ) {
				Fragment f = (Fragment)fragIter.next();
				// emit the engine script line for this fragment:
				// the "REF" opcode
				// the fragment type name
				String fragType = f.getType().getName();
				int fragTypeNumber = FragTypeNameToNumber(fragType);

				engScript.append("REF "); //$NON-NLS-1$
				engScript.append(fragmentDescriptors[fragTypeNumber].engineScriptOpcode);
				engScript.append(SPACE_STRING);

				// Emit the internal-form class name for this fragment				
				// It's an inner class of getOuterClassName
				engScript.append(getOuterClassName() + "$" + details.uniqueClassName + SPACE_STRING); //$NON-NLS-1$

				// Note: it's a bad internal error if any of these fragmentDetails arrays 
				// isn't appropriately populated (i.e. causes NullPointerException)

				// Emit the method name for this fragment
				engScript.append(details.fragmentDetails[fragTypeNumber].functionName + SPACE_STRING);

				// Emit the signature for this fragment
				engScript.append(details.fragmentDetails[fragTypeNumber].signature + SPACE_STRING);
				
				// Emit the list of arguments for this fragment
				engScript.append(details.fragmentDetails[fragTypeNumber].argumentList + lineSeparator);
			}
			
			// Emit the staticField element from this probe, if any
			if (details.staticFieldType != null) {
				engScript.append("STATICFIELD " + details.staticFieldTypeInternal + lineSeparator); //$NON-NLS-1$
			}
		}
	}

	/**
	 * The main source code (and metadata) generation method of the Compiler. 
	 * The generated source is a compilable Java source file
	 * containing one class, whose name is PROBE_PACKAGE_NAME.classPrefix_probe.
	 * This function discards all previously-generated state of the Compiler, if any,
	 * and generates new metadata ("probe details") in parallel with the new source code.
	 * 
	 * The "probe details" object generated here includes the metadata that describes the
	 * probe set to the agent.
	 */
		
	private void generateSource() throws ProbekitException {
		// Reset all accumulators and generated stuff,
		// and set the state to "dirty" now. This way
		// if we don't exit this function normally
		// it won't falsely be seen as "clean."
		// controlKeysSet = new HashSet(); not implemented yet
		generatedSource = new StringBuffer();
		engScript = new StringBuffer();
		probeDetailsMap = new HashMap();	// See TODO at declaration re IdentityHashMap
		nextSerialNumber = 0;
		lineNumber = 0; 
		compilerState = STATE_DIRTY;

		emitln("// generated source from Probekit compiler"); //$NON-NLS-1$

		// Loop through all probes and initialize the details map
		for (Iterator probekitIter = probekits.iterator(); probekitIter.hasNext(); ) {
			ProbekitPair pair = (ProbekitPair)probekitIter.next();
			emitProbekitInfo(pair);
			Probekit pk = pair.probekit;
			for (Iterator iter = pk.getProbe().iterator(); iter.hasNext(); ) {
				Probe probe = (Probe)iter.next();
				ProbeDetails details = new ProbeDetails(probe);
				probeDetailsMap.put(probe, details);
			}
		}

		// Emit the package statement if the caller ever called setPackageName()
		if (packageName != null) {
			emitln("package " + packageName + ";");
		}

		// Emit imports
		emitln("// \"imports\" specifications for probes (if any):"); //$NON-NLS-1$
		emitImports();

		// Emit the outer class declaration
		emitln("class " + getOuterClassName() + " {"); //$NON-NLS-1$ //$NON-NLS-2$
		
		// Emit any field members of the outer class.
		// TODO: emit a global enable flag here.
		
		// Now, the per-probe control keys. TODO: implement control keys
		// emitln("  // Probe control keys appear below (if any):");
		// emitControlKeyDeclarations();

		// Generate one inner class per probe in the list of probekits.
		for (Iterator probekitIter = probekits.iterator(); probekitIter.hasNext(); ) {
			ProbekitPair pair = (ProbekitPair)probekitIter.next();
			Probekit pk = pair.probekit;
			
			// Emit the fragment at shared scope, if any
			String frag = pk.getFragmentAtSharedScope();
			if (frag != null && !frag.equals(EMPTY_STRING)) {
				// The LocationAdapter on the Probekit itself tells the
				// starting line number for the FragmentAtSharedScope.
				LocationAdapter sharedFragmentAdapter = getLocationAdapter(pk);
				emitln("  // FragmentAtSharedScope"); //$NON-NLS-1$
				int sharedFragmentStartingLine = emitln(frag);
				sharedFragmentAdapter.setStartLineNumber(sharedFragmentStartingLine);
				sharedFragmentAdapter.setEndLineNumber(lineNumber);
			}

			for (Iterator iter = pk.getProbe().iterator(); iter.hasNext(); ) {
				Probe probe = (Probe)iter.next();
				emitOneProbeInnerClass(probe);
			}
		}
		emitln("}");	// closing brace for the outer class //$NON-NLS-1$
		
		// Mark the state as "clean," ready to return results
		compilerState = STATE_CLEAN;
	}

	//------------------------------- support and helper functions -------------------

	/**
	 * The ProbekitPairs that have been added to this Compiler
	 */
	private List probekits = new LinkedList();

	/**
	 * The state of the compiler object. It's "dirty" until you
	 * generate code from a set of probekits, and then it's "clean"
	 * until you add more probekits.
	 */
	private int compilerState = STATE_DIRTY;
	private static final int STATE_DIRTY = 0;
	private static final int STATE_CLEAN = 1;
	private static final int STATE_ERROR = 2;
	
	/**
	 * The generated source, produced by the generateSource method.
	 */

	private StringBuffer generatedSource;
	
	/**
	 * While generating the _probe.java file into the buffer, this integer
	 * tracks which line number the Compiler just generated. The line number
	 * is used to initialize instances of the LocationAdapter.
	 */
	private int lineNumber = 0;

	/**
	 * The generated engine script, built by generateEngineScript, called by compile()
	 */
	private StringBuffer engScript;
	

	/**
	 * Set the state to "dirty" and release any 
	 * previously generated source string.
	 */
	private void setDirtyState() {
		generatedSource = null;
		engScript = null;
		compilerState = STATE_DIRTY;
	}

	/**
	 * A private shortcut that emits its argument into the
	 * generatedSource buffer.
	 */
	private void emit(String s) {
		generatedSource.append(s);
	}
	
	/**
	 * A private shortcut that emits its argument and a newline into
	 * the generatedSource buffer. Uses the stored lineSeparator
	 * string rather than constantly calling System.getProperty.
	 * 
	 * This method returns the line number that was just appended.
	 */
	private int emitln(String s) {
		generatedSource.append(s);
		generatedSource.append(lineSeparator);
		int index = s.indexOf(lineSeparator);
		while(index >= 0) {
			lineNumber++;
			index = s.indexOf(lineSeparator, index+1);
		}

		// Add one for the appended line separator 
		// (generatedSource.append(lineSeparator))
		lineNumber++; 
		return lineNumber;
	}
	
	/**
	 * Prints probekit id and version, then
	 * goes through each label in the probekit and emits its text. 
	 */
	private void emitProbekitInfo(ProbekitPair pair)
	{
		Probekit probekit = pair.probekit;
		if(probekit == null) {
			return;
		}

		StringBuffer buff = new StringBuffer();

		// Add probekit source file path
		IResource ires = pair.iresource;
		buff.append(
				"probekit " +
				(ires == null ? "" : ires.getFullPath().toOSString()) +
				lineSeparator
				);
		
		// Add probekit id
		String id = probekit.getId();
		if(id != null && id.length() > 0) {
			buff.append("ID: " + id + lineSeparator);
		}

		// Add probekit version
		String version = probekit.getVersion();
		if(version != null && version.length() > 0) {
			buff.append("Version: " + version + lineSeparator);
		}

		// Add probekit labels
		EList labels = probekit.getLabel();
		if(labels != null) {
			for (Iterator labelIter = labels.iterator(); 
				labelIter.hasNext(); ) {
				Label label = (Label)labelIter.next();
				if(label != null) {					
					String name = label.getName();
					if(name != null && name.length() > 0) {
						buff.append(name + lineSeparator);
					}
				}
			} // for
		}

		String out = buff.toString();
		out = out.replaceAll("/\\*", "/ \\*"); // replace /* with / *
		out = out.replaceAll("\\*\\/", "\\* \\/"); // replace */ with * /

		emitln("/* " + out + "*/");
	}

	/**
	 * A convenience variable so we don't have to call
	 * System.getProperty all the time.
	 */
	static private final String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * The prefix to use on generated class names.
	 * If the caller doesn't set one with setClassPrefix, it's empty.
	 */
	private String classPrefix = EMPTY_STRING;
	
	/**
	 * The package name to use. If it's null (i.e. our caller
	 * never called setPackageName(), then
	 * generated classes go in the default (unnamed) package.
	 */
	private String packageName = null;

	/**
	 * Returns the outer class name that encloses all fragment classes
	 * @return the outer class name
	 */
	private String getOuterClassName() { 
		return classPrefix + getClassSuffix();
	}

	// ------------------------------------------------------------------------
	// FragmentDescriptor system, describing fragments
	// Includes the fragment type name string and a bit array of
	// the allowable data item types for this fragment type.
	// The order of the bits in the array is (naturally) the canonical order
	// defined in the dataItemDescs array.
	//
	// notice: fragment type numbers in this probe compiler are independent of the
	// enum values in the model. We look them up by name.
	// Rationale: I don't like coupling the model to the Java source code that tightly,
	// such that reordering the names in the enum can break the Java source code.
	//
	// To add a fragment type, add another "new" statement to the initializer below.
	// Specify the fragment type string, the engine script opcode, and a 14-element
	// boolean sequence for what data types are allowed.
	//
	
	static private class FragmentDescriptor {
		public FragmentDescriptor(String tn, String eso, boolean[] adi) {
			typeName = tn; engineScriptOpcode = eso; allowableDataItems = adi;
		}
		public String typeName;
		public String engineScriptOpcode;
		public boolean allowableDataItems[];
	};

	static private FragmentDescriptor[] fragmentDescriptors = new FragmentDescriptor[] {
			 // ret   ex     cn     mn     sig   this   args   isfin  stfld  source  mns   ltbl   mnum   eunum
		new FragmentDescriptor("entry", "ONENTRY", new boolean[]  //$NON-NLS-1$ //$NON-NLS-2$
			{ false, false, true,  true,  true,  true,  true,  false, true,  true,  true,  true,  true,  false, false } ),
		new FragmentDescriptor("exit", "ONEXIT", new boolean[] //$NON-NLS-1$ //$NON-NLS-2$
			{ true,  true,  true,  true,  true,  true,  true, false,  true,  true,  true,  true,  true,  false, false } ),
		new FragmentDescriptor("catch", "ONCATCH", new boolean[]  //$NON-NLS-1$ //$NON-NLS-2$
			{ false, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true, false } ),
		new FragmentDescriptor("beforeCall", "BEFORECALL", new boolean[] //$NON-NLS-1$ //$NON-NLS-2$
			{ false, false, true,  true,  true,  true,  true,  false, false, false, false, false, false, false, false } ),
		new FragmentDescriptor("afterCall", "AFTERCALL", new boolean[] //$NON-NLS-1$ //$NON-NLS-2$
			{ true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false, false } ),
		new FragmentDescriptor("staticInitializer", "STATICINITIALIZER", new boolean[] //$NON-NLS-1$ //$NON-NLS-2$
			{ false, false, true,  true,  true,  false, false, false, true,  true,  true,  true,  true,  false, false } ),
		new FragmentDescriptor("executableUnit", "EXECUTABLEUNIT", new boolean[] //$NON-NLS-1$ //$NON-NLS-2$
			{ false, false, true,  true,  true,  true,  true,  false, true,  true,  true,  true,  true,  true, true } ) };

	// ------------------------------------------------------------------------
	// DataItemDesc system, describing data items.

	/**
	 * Holds a single data item description. This structure maps
	 * data item type strings that appear in the probekit file (like
	 * "className" and "isFinally") to the Java source code data
	 * types of the corresponding parameters and variables (String
	 * and boolean), and also the internal signature strings
	 * (Ljava/lang/String and Z).
	 */
	static private class DataItemDesc {
		public DataItemDesc(String n, String t, String s) {
			typeName = n;
			typeType = t;
			typeSignature = s;
		}
		public String typeName;
		public String typeType;
		public String typeSignature;
	};

	/**
	 * A static array of descriptors for each type of data item, in
	 * the canonical order. 
	 *
	 * @see DataItemDesc
	 * @see "the Compiler class static constructor"
	 * @see "the BCI engine's canonical order, which this must match"
	 */	
	static private DataItemDesc[] dataItemDescs = new DataItemDesc[] {

		// Implementation details of the BCI engine require that returnedObject and
		// exceptionObject be the first and second arguments, respectively.
		// See comments in the BCI engine at CProbeRef::PushArguments about this,
		// and the ordering of the enum bitmap in class CProbeRef

		new DataItemDesc("returnedObject", "Object", "Ljava/lang/Object;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("exceptionObject", "Throwable", "Ljava/lang/Throwable;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("className", "String", "Ljava/lang/String;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("methodName", "String", "Ljava/lang/String;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("methodSig", "String", "Ljava/lang/String;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("thisObject", "Object", "Ljava/lang/Object;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("args", "Object[]", "[Ljava/lang/Object;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("isFinally", "boolean", "Z"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("staticField", EMPTY_STRING, EMPTY_STRING),	// type is defined elsewhere in the probe //$NON-NLS-1$
		new DataItemDesc("classSourceFile", "String", "Ljava/lang/String;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("methodNames", "String", "Ljava/lang/String;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("methodLineTables", "String", "Ljava/lang/String;"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("methodNumber", "int", "I"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("executableUnitNumber", "int", "I"),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DataItemDesc("numPreviousUnits", "int", "I") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	};

	//------------------- Constant strings for names of things -----------------
	/**
	 * The prefix for the names of generated probe inner classes. The actual classes will
	 * have a trailing number to make their names unique. 
	 */
	static final String PROBE_INNER_CLASS_NAME_PREFIX = "Probe_"; //$NON-NLS-1$

	/**
	 * Type signature string for the parameter type and return type of the invocationObject.
	 * TODO: the invocationObject system is implmented but not tested.
	 */
	static final String INVOCATION_OBJECT_TYPE_SIGNATURE = "Ljava/lang/Object;"; //$NON-NLS-1$

	/**
	 * Holds the computed details about a probe, like its unique name,
	 * the signatures of the fragment functions, and convenience flags and attributes.
	 * The information about a probe is developed over time - it's not complete
	 * until generateSource finishes.
	 * 
	 * The details class contains an array to hold details about each fragment.
	 */
	public static class ProbeDetails {
		public ProbeDetails(Probe probe) {
			// Initialize the "comment name" of the probe.
			// This is the first name string regardless of language.
			// It will appear as the probe's name in comments in generated code,
			// and in exception error messages.
			if (probe.getName() == null || probe.getName().size() == 0) {
				commentName = "unnamed_probe"; //$NON-NLS-1$
			}
			else {
				commentName = ((Name)(probe.getName().get(0))).getText();
			}
			
			// Set staticFieldType nonnull if this probe has a staticField specification
			StaticField sf = probe.getStaticField();
			if (sf != null && !sf.equals(EMPTY_STRING)) {
				staticFieldType = sf.getType();
				staticFieldTypeInternal = dotsToSlashes(sf.getType());
			}
		}
		
		public String commentName = EMPTY_STRING;
		public String uniqueClassName = EMPTY_STRING;
//		public boolean hasInvocationObject = false;	// TODO: use this when invocationObject is implemented
		public String staticFieldType;	// null or empty string means "none"
		public String staticFieldTypeInternal;

		static class FragmentDetails {
			public String functionName;
			public String signature;
			public String argumentList;
		}
		public FragmentDetails[] fragmentDetails = new FragmentDetails[fragmentDescriptors.length];
	}
	
	/**
	 * Convert a fragment type name to a number. This number is used as
	 * the index into the fragmentDetails array in the ProbeDetails object.
	 * It's internal only - there isn't any restriction on the ordering.
	 * I am not using DataEnum.getValue() because I don't want to couple those
	 * enum values to this code in any way.
	 */
	private static int FragTypeNameToNumber(String name) throws ProbekitException {
		for (int i = 0; i < fragmentDescriptors.length; i++) {
			if (name.equals(fragmentDescriptors[i].typeName)) return i;
		}
		throw new ProbekitException("IWAT5008 Internal Probekit error, bad fragment type name: " + name); //$NON-NLS-1$
	}

	/**
	 * This map collects the ProbeDetails of each generated probe.
	 * The key is the Probe, the value is the ProbeDetails.
	 *
	 * TODO: make this IdentityHashMap when I can use 1.4-only features.
	 */
	private Map probeDetailsMap;
	
	/**
	 * The set of ControlKey names used by the probes in this probe set.
	 * This set is part of the output of the compiler, since the GUI
	 * and the BCI engine will want to know all their names. 
	 */
	// private Set controlKeysSet; not implemented yet 

	//----------------------- source generator helper methods ------------------------
	
	/**
	 * Goes through each probe in the probekit and emits its import statement. 
	 */
	private void emitImports() throws ProbekitException {
		for (Iterator probekitIter = probekits.iterator(); probekitIter.hasNext(); ) {
			ProbekitPair pair = (ProbekitPair)probekitIter.next();
			Probekit probekit = pair.probekit;
			Set importStrings = new HashSet();
			for (Iterator probeIter = probekit.getProbe().iterator() ; probeIter.hasNext() ; ) {
				Probe probe = (Probe)probeIter.next();
				if (probe.getImport() != null) {
					Object temp_o = probeDetailsMap.get(probe);
					if (temp_o == null) {
						throw new ProbekitException("IWAT5009E Probekit internal error: failed to find details for probe"); //$NON-NLS-1$
					}
					ProbeDetails details = (ProbeDetails)temp_o;
					for (Iterator importIter = probe.getImport().iterator(); importIter.hasNext() ; ) {
						Import imp = (Import)importIter.next();
						String thisImportString = imp.getText();
						// Only emit if this exact string hasn't already been emitted.
						// This is an imperfect way to prevent warnings for unused imports.
						// (You can still get them if you import both java.util.* and java.util.Map for example.)
						if (thisImportString != null && !importStrings.contains(thisImportString)) {
							importStrings.add(thisImportString);
							int importLineNumber = emitln("import " + thisImportString + "; // from " + details.commentName); //$NON-NLS-1$ //$NON-NLS-2$
							LocationAdapter adapter = getLocationAdapter(imp);
							adapter.setStartLineNumber(importLineNumber);
							adapter.setEndLineNumber(importLineNumber);
						}
					}
				}
			}
		}
	}

	// Either return the existing or create a new adapter for the object.
	private LocationAdapter getLocationAdapter(EObject object) {
		LocationAdapter adapter = null;
	
		Collection c = object.eAdapters();
		if (c != null) {
			Iterator iterator = c.iterator();
			while (iterator.hasNext()) {
				Adapter a = (Adapter)iterator.next();
				if ((a != null) && (a instanceof LocationAdapter)) {
					adapter = (LocationAdapter)a;
					break;
				}
			}
		}
		
		if(adapter == null) {
			adapter = new LocationAdapter();
			adapter.setTarget(object);
			object.eAdapters().add(adapter);
		}
	
		return adapter;
	}
	
//	/**
//	 * A descriptor for a controlKey, the enabling flag for a probe.
//	 * Holds the key name and its initial value (true or false).
//	 * Overrides "equals" to compare just the key name.
//	 * Not implemented yet.
//	 */
//	class ControlKeyDescriptor {
//		String key;
//		String initialValue;
//		ControlKeyDescriptor(String k, String iv) {
//			key = k;
//			initialValue = iv;
//		}
//		
//		/**
//		 * Override for Object.equals: compare just the key name.
//		 * The descriptor goes into a map that should have just one entry per key.
//		 */
//		public boolean equals(Object other) {
//			if (other instanceof ControlKeyDescriptor) {
//				return key.equals(((ControlKeyDescriptor)other).key);
//			}
//			else return false; 
//		}
//
//	}
//	
//	/**
//	 * Goes through each probe in the probekit and collects its control key.
//	 * Emits static variable declarations for the unique keys.
//	 * Emits "true" as the default value for each key.
//	 * Adds each key to the controlKeys collection, so it can be
//	 * passed back out as part of the metadata for this probe set.
//	 * TODO: emit false or a default taken from the probe resource.
//	 */
//	private void emitControlKeyDeclarations() throws ProbekitException {		
//		for (Iterator probekitIter = probekits.iterator(); probekitIter.hasNext(); ) {
//			ProbekitPair pair = (ProbekitPair)probekitIter.next();
//			Probekit probekit = pair.probekit;
//			for (Iterator probeIter = probekit.getProbe().iterator() ; probeIter.hasNext() ; ) {
//				Probe probe = (Probe)probeIter.next();
//				Object temp_o = probeDetailsMap.get(probe);
//				if (temp_o == null) {
//					throw new ProbekitException("Internal error: failed to find details for probe");
//				}
//				ProbeDetails details = (ProbeDetails)temp_o;
//				if (probe.getControlKey() != null) {
//					String key = probe.getControlKey().getName();
//					controlKeysSet.add(key);
//					emitln("  // key " + key + " is used by " + details.commentName);
//				}
//			}
//		}
//
//		// TODO: control keys default to TRUE now, should be FALSE for real
//		for (Iterator i = controlKeysSet.iterator(); i.hasNext() ; ) {
//			String key = (String)i.next();			
//			emitln("  public static boolean " + key + " = true;");
//		}
//	}

	/**
	 * A counter used by getNextProbeClassName to keep the names unique 
	 */
	private int nextSerialNumber;

	/**
	 * Using nextSerialNumber, generate the next unique class name for a probe.
	 * Called from emitOneProbeInnerClass.
	 * @return the class name, unique within this probe set
	 */
	private String getNextProbeClassName() {
		String s = PROBE_INNER_CLASS_NAME_PREFIX + nextSerialNumber;
		nextSerialNumber++;
		return s;
	}

	/**
	 * Emit the probe inner class for one probe. This inner class has member functions
	 * for each fragment the probe defines. A unique name is generated for the
	 * inner class, and this name, the fragment method signatures, and other
	 * metadata are saved as part of the "details" for this probe.
	 * <P>
	 * Throughout this method, LocationAdapters are created and the line numbers
	 * returned from emitln() are used to map the EObjects making up the probe
	 * to the Java source lines generated from them. The compiler's caller will
	 * be able to retrieve these using EMF's eAdapter system.
	 * 
	 * @param probe the probe to generate the inner class for.
	 * @throws ProbekitException
	 */
	private void emitOneProbeInnerClass(Probe probe) throws ProbekitException {
		// Get the details for this probe
		Object temp_o = probeDetailsMap.get(probe);
		if (temp_o == null) {
			throw new ProbekitException("IWAT5010E Internal error: failed to find details for probe"); //$NON-NLS-1$
		}
		ProbeDetails details = (ProbeDetails)temp_o;
		LocationAdapter probeLocationAdapter = getLocationAdapter(probe);
		
		int probeStartLine = emitln("  // Class for probe " + details.commentName); //$NON-NLS-1$
		probeLocationAdapter.setStartLineNumber(probeStartLine);

		// Compute the inner class name for this probe and remember it in details		
		details.uniqueClassName = getNextProbeClassName();
		
		// Emit the inner class declaration for this probe's class
		emitln("  public static class " + 	details.uniqueClassName + " {"); //$NON-NLS-1$ //$NON-NLS-2$

		// Emit the "fragment at class scope"
		// Handle a present-but-empty fragmentAtClassScope as if it were missing.
		String facs = probe.getFragmentAtClassScope(); 
		if (facs != null && !facs.equals(EMPTY_STRING)) {
			emitln("    // Fragment at class scope"); //$NON-NLS-1$
			emitln(facs);
		}

		// Emit the fragments. Each fragment is a function with its own name (entry, catch, exit).
		// No error checking is done here for malformed probes, like two "entry" fragments,
		// or an entry fragment with "isFinally" as one of the arguments.

		for (Iterator fragIter = probe.getFragment().iterator(); fragIter.hasNext(); ) {
			Fragment frag = (Fragment)fragIter.next();
			String fragType = frag.getType().getName();
			int fragTypeNumber = FragTypeNameToNumber(fragType);
			LocationAdapter fragmentAdapter = getLocationAdapter(frag);
			
			String funcName = "_" + fragType;	// prepend underscore so "catch" isn't a keyword //$NON-NLS-1$
			details.fragmentDetails[fragTypeNumber] = new ProbeDetails.FragmentDetails();
			details.fragmentDetails[fragTypeNumber].functionName = funcName;

			// Compute the return type of the probe: void for most, but Object
			// for entry probes that ask to have an invocationObject.
			String returnType = "void"; //$NON-NLS-1$
			String returnSignature = "V"; //$NON-NLS-1$

//			// TODO: use this when invocationObject is implemented
//			if (details.hasInvocationObject && fragType.equals("entry")) {
//				returnType = "Object";
//				returnSignature = INVOCATION_OBJECT_TYPE_SIGNATURE;
//			}
//			else {	
//				returnType = "void";
//				returnSignature = "V";
//			}

			// Emit the function declaration
			int fragmentStartLine = emitln("    public static " + returnType + SPACE_STRING + funcName + " ("); //$NON-NLS-1$ //$NON-NLS-2$
			fragmentAdapter.setStartLineNumber(fragmentStartLine);

			// Crank through the desired data items and emit them as parameter declarations.
			// Emit them in the canonical order. This means going through
			// the dataItemDescs array in its order, and looking for
			// a data item with each corresponding name in the fragment.
			// This way the user can specify them in any order.
			// See comments near "class DataItemDesc" and the static
			// initializer that populates it for important details of ordering. 
			//
			// In parallel, build the internal form of the fragment function signature.
		
			boolean first = true;
			details.fragmentDetails[fragTypeNumber].signature = "("; //$NON-NLS-1$
			details.fragmentDetails[fragTypeNumber].argumentList = new String();
			for (int j = 0; j < dataItemDescs.length; j++) {
				// Look through the fragment's data items for one with a matching string
				for (Iterator dataIter = frag.getData().iterator(); dataIter.hasNext(); ) {
					DataItem data = (DataItem)dataIter.next();
					if (data.getType().getName().equals(dataItemDescs[j].typeName)) {
						LocationAdapter dataAdapter = getLocationAdapter(data);
						// If typeType is the empty string, this is a flag saying the type
						// comes from elsewhere in the probe. Same for typeSignature
						String typeTypeToUse;
						String typeSignatureToUse;
						if (dataItemDescs[j].typeType.equals(EMPTY_STRING)) {
							typeTypeToUse = details.staticFieldType;
							typeSignatureToUse = "L" + details.staticFieldTypeInternal + ";"; //$NON-NLS-1$ //$NON-NLS-2$
						}
						else {
							typeTypeToUse = dataItemDescs[j].typeType;
							typeSignatureToUse =  dataItemDescs[j].typeSignature;
						}

						String paramName = data.getName();
						if (!first) {
							emitln(",");  //$NON-NLS-1$
						}
						emit("      " + typeTypeToUse + " /*" + dataItemDescs[j].typeName + "*/ " + paramName); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						details.fragmentDetails[fragTypeNumber].signature += typeSignatureToUse;
						if (!first) {
							details.fragmentDetails[fragTypeNumber].argumentList += ","; //$NON-NLS-1$
						}
						details.fragmentDetails[fragTypeNumber].argumentList += dataItemDescs[j].typeName;
						dataAdapter.setStartLineNumber(lineNumber+1); // Add one because "emit" was used instead of "emitln".
						dataAdapter.setEndLineNumber(lineNumber+1);
						first = false;
					}
				}
			}

			// TODO: Emit the invocation object parameter if any

			// Close paren for the signature, open brace for the body
			emitln("      ) {"); //$NON-NLS-1$
			
			// Append the return type to the internal signature
			details.fragmentDetails[fragTypeNumber].signature += ")" + returnSignature; //$NON-NLS-1$
			emitln("      // Internal signature for this method: " + details.fragmentDetails[fragTypeNumber].signature); //$NON-NLS-1$

			// TODO: Emit global and per-probe control flag test logic

//			// TODO: emit the local variable declaration for the invocation object (ENTRY fragments only)
//			if (details.hasInvocationObject && fragType.equals("entry")) {
//				emitln("      Object " + frag.getInvocationObject().getName());
//			}
			
			// Emit the user-written source code for this fragment.
			emitln("//------------------ begin user-written fragment code ----------------"); //$NON-NLS-1$
			emitln(frag.getCode());
			emitln("//------------------- end user-written fragment code -----------------"); //$NON-NLS-1$

//			// TODO: use this when invocationObject is implemented
//			if (details.hasInvocationObject && fragType.equals("entry")) {
//				emitln("      return " + frag.getInvocationObject().getName());
//			}
					
			// Close brace for this function
			int fragmentEndLine = emitln("    }"); //$NON-NLS-1$
			fragmentAdapter.setEndLineNumber(fragmentEndLine);
		}
		// Close brace for this class
		int lastLine = emitln("  }"); //$NON-NLS-1$
		probeLocationAdapter.setEndLineNumber(lastLine);
	}
}
