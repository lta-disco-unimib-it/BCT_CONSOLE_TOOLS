/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbeInstrumenter.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

import org.eclipse.hyades.probekit.ProbeInstrumenterDriver;

/* 
 * Note related to NLS processing: this class runs as a stand-alone
 * Java application, so it can't use the usual resource bundle system.
 * 
 * TODO: improve error reporting, especially when the error occurs
 * deep in a directory hierarchy (which they all do, with Java classes and jars...).
 */ 

/**
 * This class is a command-line executable program (it has a "main")
 * for instrumenting Java class and jar files using probes built by Probekit.
 * <P>
 * This class is in the global (unnamed) package in order to make
 * the command-line interface simpler. It is a thin wrapper for the class
 * org.eclipse.hyades.probekit.ProbeInstrumenterDriver.
 * <P>
 * How to use this as a command-line tool:
 * <P>
 * The first argument must be an engine script file name.
 * Subsequent arguments are class files, jar files, and directories
 * to process.
 * <P>
 * If an argument is a directory name, begin recursive instrumentation
 * of everything in that directory.
 * <P>
 * If an argument is a class file, instrument it.
 * <P>
 * If an argument is a jar (or WAR or EAR) file, instrument its contents by extracting
 * the contents to a temporary location, instrumenting the class files,
 * and rebuilding the jar file. Before the jar file is rebuilt, the
 * MANIFEST.MF file is edited to remove any "Digest" lines, because the
 * hashes will have changed.
 * <P>
 * Classes and jar files are rewritten even if instrumentation resulted
 * in no change whatsoever. 
 */
public class ProbeInstrumenter {
	/**
	 * main: call cmdLineMain in the driver class, exit with the value it returns.
	 * Assure that exceptions are caught and cause non-zero exit status.
	 */
	public static void main(String[] args) {
		int errorStatus = 0;
		try {
			errorStatus = ProbeInstrumenterDriver.cmdLineMain(args);
		}
		catch (Throwable t) {
			// Uncaught exception
			t.printStackTrace();
			errorStatus = 1;
		}
		System.exit(errorStatus);
	}
}