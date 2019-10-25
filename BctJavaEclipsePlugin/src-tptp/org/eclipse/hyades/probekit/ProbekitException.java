/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbekitException.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit;

/**
 * The exception class for probekit exceptions.
 * All thrown exceptions are of this type, and are created with a string value which
 * describes the problem. 
 */
public class ProbekitException extends Exception {
	/**
	 * This serialVersionUID was generated May 19, 2005.
	 */
	private static final long serialVersionUID = 3257569507542579248L;

	/**
	 * String constructor. Exceptions are always created with a string telling what's wrong.
	 * @param string A message (in English) telling what's wrong.
	 */
	public ProbekitException(String string) {
		super(string);
	}
}

