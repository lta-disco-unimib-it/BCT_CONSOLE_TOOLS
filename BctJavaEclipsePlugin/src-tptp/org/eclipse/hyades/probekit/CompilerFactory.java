/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: CompilerFactory.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.hyades.probekit.internal.Compiler;

/**
 * The compiler factory returns an instance of IProbeCompiler to its caller.
 * It first queries Eclipse for extenders of a specific extension point
 * (org.eclipse.hyades.probekit.probeCompiler), and if there is one
 * it creates an instance of the indicated class and returns it.
 * If there are no extenders, an instance of the default compiler is returned:
 * org.eclipse.hyades.probekit.Compiler.
 */
public class CompilerFactory {
	
	/**
	 * The extension point name to query.
	 */
	private static final String extensionPointID = "probeCompiler"; //$NON-NLS-1$
	
	/**
	 * Singleton for CompilerFactory.
	 */
	public static CompilerFactory INSTANCE = new CompilerFactory();
	
	/**
	 * This boolean prevents us from reporting bad plug-ins every time somebody
	 * compiles a probe. It goes true after the first time we've looked up
	 * our extenders, so errors only get reported once.
	 */
	private boolean alreadyReportedErrors /* = false */;
	
	/**
	 * Private default constructor, so instances can not be created by users.
	 * This is a Singleton; use CompilerFactory.INSTANCE to get the one and only.
	 *
	 */
	private CompilerFactory() { }
	
	/**
	 * Queries Eclipse for extenders of the extension point and returns
	 * an instance of the class declared by one of them, or returns
	 * an instance of the default compiler if there aren't any extenders.
	 * <P>
	 * Errors (like a malformed plug-in, exporting the wrong type of class)
	 * will throw CoreException so they appear in the Error Log.
	 * @return an IProbeCompiler instance to use.
	 */
	public IProbeCompiler createCompiler() {
		// Check for an extender of our extension point.
		String idString = ProbekitPlugin.getDefault().getBundle().getSymbolicName() + "." + extensionPointID; //$NON-NLS-1$
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(idString);
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IExtension e = extensions[i];
				IConfigurationElement[] configElements = e.getConfigurationElements();
				boolean anyCompilerElements = false;
				for (int j = 0; j < configElements.length; j++) {
					IConfigurationElement ce = configElements[j];
					if (ce.getName().equals("compiler")) { //$NON-NLS-1$
						anyCompilerElements = true;
						Object extensionClass;
						try {
							extensionClass = ce.createExecutableExtension("class"); //$NON-NLS-1$
						}
						catch (CoreException ex) {
							// The workbench automatically records an error in this case
							extensionClass = null;
							// fall through, will report an error in the extender
						}
						if (extensionClass != null) {
							if (extensionClass instanceof IProbeCompiler) {
								// We got one!
								alreadyReportedErrors = true;
								return (IProbeCompiler)extensionClass;
							}
							else {
								// This plug-in didn't follow the rules.
								// Log an error to the ProbekitPlugin error log.
								// But only once per session!
								if (!alreadyReportedErrors) {
									IStatus status = new Status(IStatus.ERROR, 
											"org.eclipse.hyades.probekit", 0,  //$NON-NLS-1$
											ProbekitPlugin.getString("CompilerFactory.ExtenderError") +  //$NON-NLS-1$
											getPrintableExtenderName(e) + 
												ProbekitPlugin.getString("CompilerFactory.DoesNotImplementIProbeCompiler"), //$NON-NLS-1$
											null);
									ProbekitPlugin.getDefault().getLog().log(status);
								}
							}
						}
						// else extensionClass is null, and an error has already been recorded
					}
					// else this configurationElement name isn't "compiler," so ignore it.
				} // end for each configuration element
				if (anyCompilerElements == false && !alreadyReportedErrors) {
					IStatus status = new Status(IStatus.ERROR, 
							"org.eclipse.hyades.probekit", 0,  //$NON-NLS-1$
							ProbekitPlugin.getString("CompilerFactory.ExtenderError") +  //$NON-NLS-1$
								getPrintableExtenderName(e) + 
								ProbekitPlugin.getString("CompilerFactory.NoCompilerElements"), //$NON-NLS-1$
							null);
					ProbekitPlugin.getDefault().getLog().log(status);
				}
			} // end for each extension
			alreadyReportedErrors = true;
		}

		// No extenders were found. Return the default implementation
		return createDefaultCompiler();
	}
	
	/**
	 * Returns a printable name for the plug-in an extension is in:
	 * Its label in quotes, plus its id in parens, like this:
	 * <P>
	 * 		"My probe compiler plug-in" (com.sample.mycompiler)
	 * @param e the extension whose name you want to format
	 * @return a string in the above format that identifies the plug-in
	 */
	private String getPrintableExtenderName(IExtension e) {
		String extenderLabel = e.getLabel();
		String extenderId = e.getUniqueIdentifier();
		String extenderName = "\"" + extenderLabel + "\" (" + extenderId + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return extenderName;
	}

	/**
	 * Extenders can use this to get an instance of the default compiler,
	 * if they desire. (For example, if their extension wraps the default
	 * compiler somehow.)
	 * @return an instance of the default probe compiler as an IProbeCompiler.
	 */
	public IProbeCompiler createDefaultCompiler() {
		return new Compiler();
	}
}
