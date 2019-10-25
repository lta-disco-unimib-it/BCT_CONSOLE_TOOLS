/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: IProbeMetaData.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.models.internal.probekit.FragmentType;


public interface IProbeMetaData {
	// One of METHOD_PROBE, CALLSITE_PROBE, or NO_PROBE.
	public int getProbeType();
	
	// Return the paragraph from the online help explaining what this type of probe
	// is used for.
	public String getProbeTypeExplanation();
	
	// Return the list of available fragment types that a probe of this type can use.
	public FragmentType[] getAvailableFragmentTypes();
	
	// Return the paragraph from the online help that explains what this type of fragment 
	// can do.
	public String getFragmentTypeExplanation();
	public FragmentType getFragmentType();
	public void setFragmentType(FragmentType type);
	
	// If this probe type contains a fragment, then return the code that it
	// must be instantiated with.
	public String getCode();
	
	// Return the list of available data types plus the data type currently in use.
	public String[] getDataTypeNames(DataItem item);

	// Return all of the data types that a probe with this fragment type can use.
	public DataType[] getAvailableDataTypes();

	// Return a list of the data items, created when the fragment type was different,
	// that cannot be used by this fragment type.
	public DataItem[] getInvalidDataItems(FragmentType type);

	// Delete the data items returned by getInvalidDataItems, make sure that the
	// fragment type is set to one of the valid fragment types, etc.
	public void forceValid();

	// Return the paragraph from the online help for this data type.
	public String getDataTypeExplanation(DataItem item);

	public int countDataItems();
	public DataItem[] getDataItems();
	public void removeDataItem(DataItem item);
	public void addDataItem(DataItem item);
	public void setDataItems(DataItem[] items);
	public void setDataItem(int index, DataItem item);
	
	// Clear the fields in use by this class.
	public void release();

	public static final int METHOD_PROBE = 0;
	public static final int CALLSITE_PROBE = 1;
	public static final int NO_PROBE = 2;
}
