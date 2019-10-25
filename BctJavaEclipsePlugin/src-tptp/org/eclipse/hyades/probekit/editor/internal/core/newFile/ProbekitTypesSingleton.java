/**********************************************************************
 * Copyright (c) 2005, 2010 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbekitTypesSingleton.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.models.internal.probekit.ProbekitPackage;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;


/**
 * This type tracks which data types are valid for which fragment types for
 * the purpose of the "New Probe File" wizard. Not all data is represented here;
 * for example, because a DataType of staticField is valid only if the probe
 * file has a static field, and the wizard does not enable the user to create
 * a static field, the field was omitted from this singleton.
 */
public final class ProbekitTypesSingleton {
	private static final String DATA_ITEM_DEFAULT_NAME_PREFIX = "a"; //$NON-NLS-1$
	private static ProbekitTypesSingleton _singleton = null;
	private Map _availableFragmentTypes;
	private Map _availableDataTypes;
	
	
	// The data is stored in a structure like this:
	// Map, key is FragmentType int id,
	//      value is instanceof FragmentTypeMetaData, which contains the
	//      String explanation of that fragment type and an array of 
	//      DataType int id valid for that FragmentType.
	// Map, key is DataType int id,
	//      value is a String explanation of that data type.
	// All explanations were copied from the online help.
	public static ProbekitTypesSingleton singleton() {
		if(_singleton == null) {
			_singleton = new ProbekitTypesSingleton();
		}
		return _singleton;
	}
	
	public String getFragmentTypeExplanation(FragmentType type) {
		FragmentTypeMetaData typeMd = (FragmentTypeMetaData)_availableFragmentTypes.get(type);
		if(typeMd == null) {
			return ResourceUtil.NO_TEXT;
		}
		
		return typeMd.getFragmentTypeExplanation();
	}
	
	public DataType[] getDataTypes(FragmentType type) {
		FragmentTypeMetaData typeMd = (FragmentTypeMetaData)_availableFragmentTypes.get(type);
		if(typeMd == null) {
			return new DataType[0];
		}
		
		return typeMd.getValidDataTypes();
	}
	
	public String getDataTypeExplanation(DataType type) {
		String explanation = (String)_availableDataTypes.get(type);
		return ResourceUtil.getString(explanation);
	}
	
	public String[] getDataTypeNames(FragmentType type) {
		DataType[] dataTypes = getDataTypes(type);
		String[] dataTypeNames = new String[dataTypes.length];
		for(int i=0; i<dataTypes.length; i++) {
			DataType dtype = dataTypes[i];
			dataTypeNames[i] = dtype.getName();
		}
		return dataTypeNames;
	}
	
	public boolean isValidDataItemNames(DataItem[] items) {
		Set namesSoFar = new HashSet(items.length);
		for(int i=0; i<items.length; i++) {
			DataItem item = items[i];
			String name = item.getName();
			if(name != null) {
				namesSoFar.add(name);
			}
		}
		
		if(namesSoFar.size() < items.length) {
			// Duplicate name
			return false;
		}
		
		return true;
	}
	
	public boolean isValidDataItemTypes(DataItem[] items) {
		Set typesSoFar = new HashSet(items.length);
		for(int i=0; i<items.length; i++) {
			DataItem item = items[i];
			DataType dataType = item.getType();
			if(dataType != null) {
				typesSoFar.add(dataType);
			}
		}
		
		if(typesSoFar.size() < items.length) {
			// Duplicate type
			return false;
		}
		
		return true;
	}

	// For performance reasons, don't iterate over the list of used names
	// to guarantee that the name is unique. 
	public String getInitialDataItemName(DataType arbitraryType, int count) {
		StringBuffer buffer = new StringBuffer(DATA_ITEM_DEFAULT_NAME_PREFIX);
		buffer.append(arbitraryType.getName());
		buffer.append(count);
		return buffer.toString();
	}
	
	public DataItem createDataItem(DataType[] availableTypes, int count) {
		DataType arbitraryType = availableTypes[0];
		String initialName = ProbekitTypesSingleton.singleton().getInitialDataItemName(arbitraryType, count);

		DataItem item = ProbekitPackage.eINSTANCE.getProbekitFactory().createDataItem();
		item.setType(arbitraryType);
		item.setName(initialName);
		
		return item;
	}
	
	private ProbekitTypesSingleton() {
		_availableFragmentTypes = new TreeMap(new Sorter());
		_availableDataTypes = new HashMap();
		initialize();
	}
	
	private void initialize() {
		initializeFragmentTypes();
		initializeDataTypes();
	}
	
	private void initializeFragmentTypes() {
		_availableFragmentTypes.put(
			FragmentType.BEFORE_CALL_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._67, 
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.METHOD_NAME_LITERAL,
					DataType.METHOD_SIG_LITERAL,
					DataType.THIS_OBJECT_LITERAL,
					DataType.ARGS_LITERAL
				}
			)
		);
		_availableFragmentTypes.put(
			FragmentType.AFTER_CALL_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._68,
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.METHOD_NAME_LITERAL,
					DataType.METHOD_SIG_LITERAL,
					DataType.THIS_OBJECT_LITERAL,
					DataType.EXCEPTION_OBJECT_LITERAL,
					DataType.ARGS_LITERAL,
					DataType.RETURNED_OBJECT_LITERAL
				}
			)
		);
		_availableFragmentTypes.put(
			FragmentType.CATCH_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._69,
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.METHOD_NAME_LITERAL,
					DataType.METHOD_SIG_LITERAL,
					DataType.THIS_OBJECT_LITERAL,
					DataType.ARGS_LITERAL,
					DataType.EXCEPTION_OBJECT_LITERAL,
					DataType.IS_FINALLY_LITERAL,
					DataType.CLASS_SOURCE_FILE_LITERAL,
					DataType.METHOD_NAMES_LITERAL,
					DataType.METHOD_LINE_TABLES_LITERAL,
					DataType.METHOD_NUMBER_LITERAL,
					DataType.EXECUTABLE_UNIT_NUMBER_LITERAL,
					DataType.STATIC_FIELD_LITERAL
				}
			)
		);
		_availableFragmentTypes.put(
			FragmentType.ENTRY_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._70,
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.METHOD_NAME_LITERAL,
					DataType.METHOD_SIG_LITERAL,
					DataType.THIS_OBJECT_LITERAL,
					DataType.ARGS_LITERAL,
					DataType.CLASS_SOURCE_FILE_LITERAL,
					DataType.METHOD_NAMES_LITERAL,
					DataType.METHOD_LINE_TABLES_LITERAL,
					DataType.METHOD_NUMBER_LITERAL,
					DataType.STATIC_FIELD_LITERAL
				}
			)
		);
		_availableFragmentTypes.put(
			FragmentType.EXECUTABLE_UNIT_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._71,
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.METHOD_NAME_LITERAL,
					DataType.METHOD_SIG_LITERAL,
					DataType.ARGS_LITERAL,
					DataType.CLASS_SOURCE_FILE_LITERAL,
					DataType.METHOD_NAMES_LITERAL,
					DataType.METHOD_LINE_TABLES_LITERAL,
					DataType.METHOD_NUMBER_LITERAL,
					DataType.EXECUTABLE_UNIT_NUMBER_LITERAL,
					DataType.STATIC_FIELD_LITERAL, 
					DataType.PREVIOUS_UNIT_NUMBER_LITERAL
				}
			)
		);
		_availableFragmentTypes.put(
			FragmentType.EXIT_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._72,
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.METHOD_NAME_LITERAL,
					DataType.METHOD_SIG_LITERAL,
					DataType.THIS_OBJECT_LITERAL,
					DataType.ARGS_LITERAL,
					DataType.EXCEPTION_OBJECT_LITERAL,
					DataType.RETURNED_OBJECT_LITERAL,
					DataType.CLASS_SOURCE_FILE_LITERAL,
					DataType.METHOD_NAMES_LITERAL,
					DataType.METHOD_LINE_TABLES_LITERAL,
					DataType.METHOD_NUMBER_LITERAL,
					DataType.STATIC_FIELD_LITERAL
				}
			)
		);
		_availableFragmentTypes.put(
			FragmentType.STATIC_INITIALIZER_LITERAL, 
			new FragmentTypeMetaData(
				ProbekitMessages._73,
				new DataType[]{
					DataType.CLASS_NAME_LITERAL,
					DataType.CLASS_SOURCE_FILE_LITERAL,
					DataType.METHOD_NAMES_LITERAL,
					DataType.METHOD_LINE_TABLES_LITERAL,
					DataType.METHOD_NUMBER_LITERAL,
					DataType.STATIC_FIELD_LITERAL
				}
			)
		);
	}

	private void initializeDataTypes() {
		_availableDataTypes.put(DataType.CLASS_NAME_LITERAL,
				ProbekitMessages._74);
		_availableDataTypes.put(DataType.METHOD_NAME_LITERAL,
				ProbekitMessages._75);
		_availableDataTypes.put(DataType.METHOD_SIG_LITERAL,
				ProbekitMessages._76);
		_availableDataTypes.put(DataType.THIS_OBJECT_LITERAL,
				ProbekitMessages._77);
		_availableDataTypes.put(DataType.ARGS_LITERAL, ProbekitMessages._78);
		_availableDataTypes.put(DataType.RETURNED_OBJECT_LITERAL,
				ProbekitMessages._79);
		_availableDataTypes.put(DataType.EXCEPTION_OBJECT_LITERAL,
				ProbekitMessages._80);
		_availableDataTypes.put(DataType.IS_FINALLY_LITERAL,
				ProbekitMessages._81);
		_availableDataTypes.put(DataType.CLASS_SOURCE_FILE_LITERAL,
				ProbekitMessages._82);
		_availableDataTypes.put(DataType.METHOD_NAMES_LITERAL,
				ProbekitMessages._83);
		_availableDataTypes.put(DataType.METHOD_LINE_TABLES_LITERAL,
				ProbekitMessages._84);
		_availableDataTypes.put(DataType.METHOD_NUMBER_LITERAL,
				ProbekitMessages._85);
		_availableDataTypes.put(DataType.EXECUTABLE_UNIT_NUMBER_LITERAL,
				ProbekitMessages._86);
	}
	
	private static class Sorter implements Comparator {
		public int compare(Object a, Object b) {
			if((a == null) && (b == null)) {
				return 0;
			}
			else if(a == null) {
				return -1;
			}
			else if(b == null) {
				return 1;
			}
			
			FragmentType aType = (FragmentType)a;
			FragmentType bType = (FragmentType)b;
			return aType.getValue() - bType.getValue();
		}
	}
	
	private static class FragmentTypeMetaData {
		public FragmentTypeMetaData(String explanation, DataType[] validDataTypes) {
			_fragmentTypeExplanation = explanation;
			_dataTypes = validDataTypes;
		}
		
		public String getFragmentTypeExplanation() {
			return _fragmentTypeExplanation;
		}
		
		public DataType[] getValidDataTypes() {
			return _dataTypes;
		}
		
		private final String _fragmentTypeExplanation;
		private final DataType[] _dataTypes;
	}
}
