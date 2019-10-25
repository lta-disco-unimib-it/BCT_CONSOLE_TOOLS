/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: CallsiteProbeMetaData.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;

public class CallsiteProbeMetaData extends AbstractProbeMetaData {
	private static final FragmentType[] _availableFragmentTypes;
	
	static {
		_availableFragmentTypes = 
			new FragmentType[]{
				FragmentType.BEFORE_CALL_LITERAL, 
				FragmentType.AFTER_CALL_LITERAL
			};
	}
				
	public CallsiteProbeMetaData(IProbeMetaData probe) {
		super(probe);
	}
	
	public CallsiteProbeMetaData() {
		super();
	}
	
	public final int getProbeType() {
		return IProbeMetaData.CALLSITE_PROBE;
	}
	
	public FragmentType[] getAvailableFragmentTypes() {
		return _availableFragmentTypes;
	}
	
	protected FragmentType getDefaultFragmentType() {
		return FragmentType.BEFORE_CALL_LITERAL;
	}
	
	public String getProbeTypeExplanation() {
		return ProbekitMessages._62;
	}
}
