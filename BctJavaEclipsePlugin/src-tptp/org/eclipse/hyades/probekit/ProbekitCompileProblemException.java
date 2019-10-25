/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbekitCompileProblemException.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.hyades.probekit;

/**
 * Subclass of ProbekitException, thrown when you
 * try to compile a probe set that has errors.
 * When this exception comes out of the Compiler, it means
 * the error has already been reported as a marker on the
 * resource you tried to add or compile, and no further reporting is necessary.
 * 
 * Other Probekit exceptions represent something worse, like internal
 * errors in the Probekit logic itself.
 */
public class ProbekitCompileProblemException extends ProbekitException {
	/**
	 * This serialVersionUID was generated May 19, 2005.
	 */
	private static final long serialVersionUID = 3617571617677784882L;

	/**
	 * @param message
	 */
	public ProbekitCompileProblemException(String message) {
		super(message);
	}
}
