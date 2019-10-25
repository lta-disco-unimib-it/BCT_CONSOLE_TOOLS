/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: AbstractProbeMetaData.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;



public abstract class AbstractProbeMetaData implements IProbeMetaData {
	private FragmentType _probeFragmentType;
	private List _dataItems;
	protected static final String DEFAULT_CODE = ProbekitMessages._65;
	
	protected AbstractProbeMetaData() {
		setFragmentType(getDefaultFragmentType());
		_dataItems = new ArrayList(ProbekitTypesSingleton.singleton().getDataTypes(getFragmentType()).length);
	}
	
	protected AbstractProbeMetaData(IProbeMetaData probe) {
		this();
		setTypes(probe);
	}
	
	public String getCode() {
		return DEFAULT_CODE;
	}
	
	public final FragmentType getFragmentType() {
		return _probeFragmentType;
	}
	
	public final String getFragmentTypeExplanation() {
		return ProbekitTypesSingleton.singleton().getFragmentTypeExplanation(getFragmentType());
	}
	
	public final void setFragmentType(FragmentType type) {
		if(isValidFragmentType(type)) {
			_probeFragmentType = type;
		}
		else {
			_probeFragmentType = getDefaultFragmentType();
		}
	}
	
	protected abstract FragmentType getDefaultFragmentType();
	
	public void release() {
		_probeFragmentType = null;
		_dataItems.clear();
	}
	
	protected final void setTypes(IProbeMetaData probe) {
		release();
		
		setFragmentType(probe.getFragmentType());

		DataItem[] items = probe.getDataItems();
		for(int i=0; i<items.length; i++) {
			DataItem item = items[i];
			_dataItems.add(item);
		}
	}
	
	public void forceValid() {
		FragmentType type = getFragmentType();
		FragmentType[] validTypes = getAvailableFragmentTypes();
		boolean valid = false;
		for(int i=0; i<validTypes.length; i++) {
			FragmentType validType = validTypes[i];
			if(validType.equals(type)) {
				valid = true;
				break;
			}
		}
		if(!valid) {
			setFragmentType(getDefaultFragmentType());
		}
		
		DataItem[] dataItems = getDataItems();
		DataItem[] temp = new DataItem[dataItems.length];
		int count = 0;
		for(int i=0; i<dataItems.length; i++) {
			DataItem item = dataItems[i];
			if(isValidDataItem(type, item)) {
				temp[count++] = item;
			}
		}
		
		DataItem[] validItems = new DataItem[count];
		System.arraycopy(temp, 0, validItems, 0, count);
		setDataItems(validItems);
	}
	
	public DataType[] getAvailableDataTypes() {
		DataType[] allTypes = ProbekitTypesSingleton.singleton().getDataTypes(getFragmentType());
		DataType[] typesUsedSoFar = getInUseDataTypes();
		
		// typesUsedSoFar.length can be > allTypes.length when this probe is created by
		// copying another probe. The other probe may use a fragment type that accepts more
		// data types than this probe type does.
		DataType[] temp = new DataType[allTypes.length];
		int count = 0;
		for(int i=0; i<allTypes.length; i++) {
			DataType type = allTypes[i];
			boolean inUse = false;
			for(int j=0; j<typesUsedSoFar.length; j++) {
				DataType inUseType = typesUsedSoFar[j];
				if(type.equals(inUseType)) {
					inUse = true;
					break;
				}
			}
			if(!inUse) {
				temp[count++] = type;
			}
		}
		
		DataType[] availableTypes = new DataType[count];
		System.arraycopy(temp, 0, availableTypes, 0, count);
		return availableTypes;
	}
	
	public String[] getDataTypeNames(DataItem selectedItem) {
		DataType[] types = getAvailableDataTypes();
		String[] names = null;
		if(selectedItem == null) {
			names = new String[types.length];
			for(int i=0; i<types.length; i++) {
				names[i] = types[i].getName();
			}
		}
		else {
			DataType type = selectedItem.getType();
			names = new String[types.length + 1];
			names[0] = type.getName();
			for(int i=0; i<types.length; i++) {
				names[i+1] = types[i].getName();
			}
		}
		return names;
	}
	
	public String getDataTypeExplanation(DataItem item) {
		if(item == null) {
			return ResourceUtil.NO_TEXT;
		}
		return ProbekitTypesSingleton.singleton().getDataTypeExplanation(item.getType());
	}
	
	public int countDataItems() {
		return _dataItems.size();
	}
	
	public DataItem[] getDataItems() {
		return (DataItem[])_dataItems.toArray(new DataItem[0]);
	}
	
	public void addDataItem(DataItem item) {
		_dataItems.add(item);
	}

	public void removeDataItem(DataItem item) {
		_dataItems.remove(item);
	}
	
	public void setDataItems(DataItem[] items) {
		_dataItems.clear();
		for(int i=0; i<items.length; i++) {
			_dataItems.add(items[i]);
		}
	}
	
	public void setDataItem(int index, DataItem item) {
		_dataItems.set(index, item);
	}
	
	public DataItem[] getInvalidDataItems(FragmentType type) {
		DataItem[] dataItems = getDataItems();
		DataItem[] temp = new DataItem[dataItems.length];
		int count = 0;
		for(int i=0; i<dataItems.length; i++) {
			DataItem item = dataItems[i];
			if(!isValidDataItem(type, item)) {
				temp[count++] = item;
			}
		}
		
		DataItem[] invalidItems = new DataItem[count];
		System.arraycopy(temp, 0, invalidItems, 0, count);
		return invalidItems;
	}
	
	private boolean isValidFragmentType(FragmentType type) {
		FragmentType[] validTypes = getAvailableFragmentTypes();
		for(int i=0; i<validTypes.length; i++) {
			FragmentType validType = validTypes[i];
			if(validType.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidDataItem(FragmentType type, DataItem item) {
		DataType[] items = ProbekitTypesSingleton.singleton().getDataTypes(type);
		DataType itemType = item.getType();
		for(int i=0; i<items.length; i++) {
			if(itemType.equals(items[i])) {
				return true;
			}
		}
		return false;
	}
	
	private final DataType[] getInUseDataTypes() {
		DataItem[] items = getDataItems();
		DataType[] inUse = new DataType[items.length];
		for(int i=0; i<items.length; i++) {
			DataItem item = items[i];
			inUse[i] = item.getType();
		}
		return inUse;
	}
}
