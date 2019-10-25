/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: NoProbeMetaData.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;

public class NoProbeMetaData extends AbstractProbeMetaData {
	public NoProbeMetaData(IProbeMetaData probe) {
		super(probe);
	}
	
	public final int getProbeType() {
		return IProbeMetaData.NO_PROBE;
	}
	
	public String getCode() {
		return ResourceUtil.NO_TEXT;
	}

	public FragmentType[] getAvailableFragmentTypes() {
		return new FragmentType[0];
	}
	
	protected FragmentType getDefaultFragmentType() {
		return null;
	}
	
	public String getProbeTypeExplanation() {
		return ProbekitMessages._64;
	}
}
