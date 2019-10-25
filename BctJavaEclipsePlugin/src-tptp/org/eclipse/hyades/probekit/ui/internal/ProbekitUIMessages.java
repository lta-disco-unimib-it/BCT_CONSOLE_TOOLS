/**********************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbekitUIMessages.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.hyades.probekit.ui.internal;
import org.eclipse.osgi.util.NLS;

public class ProbekitUIMessages {
	private static final String BUNDLE_NAME = "org.eclipse.hyades.probekit.ui.internal.messages"; //$NON-NLS-1$
	
	private ProbekitUIMessages(){		
	}
	public static String _1;
	public static String _2;
	public static String _3;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, ProbekitUIMessages.class);
	}
}
