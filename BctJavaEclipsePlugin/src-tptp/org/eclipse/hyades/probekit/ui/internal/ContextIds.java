/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ContextIds.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/
 
package org.eclipse.hyades.probekit.ui.internal;

public interface ContextIds {
	// Constant for the Prokit Editor plug-in id.
	public static final String PLUGIN_ID = ProbekitUIPlugin.getPluginId();

	// Context help ID constants for the editor.
	public static final String PROBEKIT_TREEVIEW = PLUGIN_ID + ".pktv0010"; //$NON-NLS-1$
	public static final String PROBEKIT_DETAILVIEW = PLUGIN_ID + ".pkdv0010"; //$NON-NLS-1$
	
	public static final String PROBEKIT_NEWWIZ_PROBE = PLUGIN_ID + ".pknwp0010"; //$NON-NLS-1$
	public static final String PROBEKIT_NEWWIZ_DATA = PLUGIN_ID + ".pknwd0010"; //$NON-NLS-1$

	public static final String PROBEKIT_INSTRUMENT = PLUGIN_ID + ".pkin0010"; //$NON-NLS-1$
}