/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: MethodProbeMetaData.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;


public class MethodProbeMetaData extends AbstractProbeMetaData {
	private static final FragmentType[] _availableFragmentTypes;
	
	static {
		// Because this MethodProbe is for the creation of a single fragmentType
		// on a probe, even though the "staticField" data type is valid for many
		// of these Fragment types, since the ability to create a "staticField"
		// on the probe does not exist, the "staticField" data type will not be
		// presented in the UI.
		_availableFragmentTypes = 
			new FragmentType[] {
				FragmentType.CATCH_LITERAL, 
				FragmentType.ENTRY_LITERAL, 
				FragmentType.EXECUTABLE_UNIT_LITERAL, 
				FragmentType.EXIT_LITERAL, 
				FragmentType.STATIC_INITIALIZER_LITERAL
			};
	}
	
	public MethodProbeMetaData(IProbeMetaData probe) {
		super(probe);
	}
	
	public MethodProbeMetaData() {
		super();
	}
	
	public FragmentType[] getAvailableFragmentTypes() {
		return _availableFragmentTypes;
	}
		
	public final int getProbeType() {
		return IProbeMetaData.METHOD_PROBE;
	}
	
	protected FragmentType getDefaultFragmentType() {
		return FragmentType.ENTRY_LITERAL;
	}
	
	public String getProbeTypeExplanation() {
		return ProbekitMessages._63;
	}
}
