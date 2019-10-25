/**********************************************************************
 * Copyright (c) 2003, 2010 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 *
 * $Id: DataType.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Data Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDataType()
 * @model extendedMetaData="name='dataType'"
 * @generated
 */
public enum DataType implements Enumerator
{
	/**
	 * The '<em><b>Class Name</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Class Name</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CLASS_NAME
	 * @generated
	 * @ordered
	 */
	CLASS_NAME_LITERAL(0, "className", "className"),
	/**
	 * The '<em><b>Method Name</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Method Name</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_NAME
	 * @generated
	 * @ordered
	 */
	METHOD_NAME_LITERAL(1, "methodName", "methodName"),
	/**
	 * The '<em><b>Method Sig</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Method Sig</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_SIG
	 * @generated
	 * @ordered
	 */
	METHOD_SIG_LITERAL(2, "methodSig", "methodSig"),
	/**
	 * The '<em><b>This Object</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>This Object</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #THIS_OBJECT
	 * @generated
	 * @ordered
	 */
	THIS_OBJECT_LITERAL(3, "thisObject", "thisObject"),
	/**
	 * The '<em><b>Args</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Args</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ARGS
	 * @generated
	 * @ordered
	 */
	ARGS_LITERAL(4, "args", "args"),
	/**
	 * The '<em><b>Returned Object</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Returned Object</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RETURNED_OBJECT
	 * @generated
	 * @ordered
	 */
	RETURNED_OBJECT_LITERAL(5, "returnedObject", "returnedObject"),
	/**
	 * The '<em><b>Is Finally</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Is Finally</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IS_FINALLY
	 * @generated
	 * @ordered
	 */
	IS_FINALLY_LITERAL(6, "isFinally", "isFinally"),
	/**
	 * The '<em><b>Exception Object</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Exception Object</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXCEPTION_OBJECT
	 * @generated
	 * @ordered
	 */
	EXCEPTION_OBJECT_LITERAL(7, "exceptionObject", "exceptionObject"),
	/**
	 * The '<em><b>Static Field</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Static Field</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATIC_FIELD
	 * @generated
	 * @ordered
	 */
	STATIC_FIELD_LITERAL(8, "staticField", "staticField"),
	/**
	 * The '<em><b>Class Source File</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Class Source File</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CLASS_SOURCE_FILE
	 * @generated
	 * @ordered
	 */
	CLASS_SOURCE_FILE_LITERAL(9, "classSourceFile", "classSourceFile"),
	/**
	 * The '<em><b>Method Names</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Method Names</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_NAMES
	 * @generated
	 * @ordered
	 */
	METHOD_NAMES_LITERAL(10, "methodNames", "methodNames"),
	/**
	 * The '<em><b>Method Line Tables</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Method Line Tables</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_LINE_TABLES
	 * @generated
	 * @ordered
	 */
	METHOD_LINE_TABLES_LITERAL(11, "methodLineTables", "methodLineTables"),
	/**
	 * The '<em><b>Method Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Method Number</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_NUMBER
	 * @generated
	 * @ordered
	 */
	METHOD_NUMBER_LITERAL(12, "methodNumber", "methodNumber"),
	/**
	 * The '<em><b>Executable Unit Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Executable Unit Number</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXECUTABLE_UNIT_NUMBER
	 * @generated
	 * @ordered
	 */
	EXECUTABLE_UNIT_NUMBER_LITERAL(13, "executableUnitNumber", "executableUnitNumber"),
	/**
	 * The '<em><b>Number of executable units defined in previous methods</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Number of executable units defined in previous methods</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PREVIOUS_UNIT_NUMBER
	 * @generated
	 * @ordered
	 */	
	PREVIOUS_UNIT_NUMBER_LITERAL(14, "numPreviousUnits", "numPreviousUnits");
	/**
	 * The '<em><b>Class Name</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLASS_NAME_LITERAL
	 * @model name="className"
	 * @generated
	 * @ordered
	 */
	public static final int CLASS_NAME = 0;

	/**
	 * The '<em><b>Method Name</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_NAME_LITERAL
	 * @model name="methodName"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_NAME = 1;

	/**
	 * The '<em><b>Method Sig</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_SIG_LITERAL
	 * @model name="methodSig"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_SIG = 2;

	/**
	 * The '<em><b>This Object</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THIS_OBJECT_LITERAL
	 * @model name="thisObject"
	 * @generated
	 * @ordered
	 */
	public static final int THIS_OBJECT = 3;

	/**
	 * The '<em><b>Args</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ARGS_LITERAL
	 * @model name="args"
	 * @generated
	 * @ordered
	 */
	public static final int ARGS = 4;

	/**
	 * The '<em><b>Returned Object</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RETURNED_OBJECT_LITERAL
	 * @model name="returnedObject"
	 * @generated
	 * @ordered
	 */
	public static final int RETURNED_OBJECT = 5;

	/**
	 * The '<em><b>Is Finally</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IS_FINALLY_LITERAL
	 * @model name="isFinally"
	 * @generated
	 * @ordered
	 */
	public static final int IS_FINALLY = 6;

	/**
	 * The '<em><b>Exception Object</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXCEPTION_OBJECT_LITERAL
	 * @model name="exceptionObject"
	 * @generated
	 * @ordered
	 */
	public static final int EXCEPTION_OBJECT = 7;

	/**
	 * The '<em><b>Static Field</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATIC_FIELD_LITERAL
	 * @model name="staticField"
	 * @generated
	 * @ordered
	 */
	public static final int STATIC_FIELD = 8;

	/**
	 * The '<em><b>Class Source File</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLASS_SOURCE_FILE_LITERAL
	 * @model name="classSourceFile"
	 * @generated
	 * @ordered
	 */
	public static final int CLASS_SOURCE_FILE = 9;

	/**
	 * The '<em><b>Method Names</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_NAMES_LITERAL
	 * @model name="methodNames"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_NAMES = 10;

	/**
	 * The '<em><b>Method Line Tables</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_LINE_TABLES_LITERAL
	 * @model name="methodLineTables"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_LINE_TABLES = 11;

	/**
	 * The '<em><b>Method Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_NUMBER_LITERAL
	 * @model name="methodNumber"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_NUMBER = 12;

	/**
	 * The '<em><b>Executable Unit Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXECUTABLE_UNIT_NUMBER_LITERAL
	 * @model name="executableUnitNumber"
	 * @generated
	 * @ordered
	 */
	public static final int EXECUTABLE_UNIT_NUMBER = 13;
	
	/**
	 * The '<em><b>Previously Unit Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PREVIOUS_UNIT_NUMBER_LITERAL
	 * @model name="numPreviousUnits"
	 * @generated
	 * @ordered
	 */
	public static final int PREVIOUS_UNIT_NUMBER = 14;

	/**
	 * An array of all the '<em><b>Data Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DataType[] VALUES_ARRAY =
		new DataType[] {
			CLASS_NAME_LITERAL,
			METHOD_NAME_LITERAL,
			METHOD_SIG_LITERAL,
			THIS_OBJECT_LITERAL,
			ARGS_LITERAL,
			RETURNED_OBJECT_LITERAL,
			IS_FINALLY_LITERAL,
			EXCEPTION_OBJECT_LITERAL,
			STATIC_FIELD_LITERAL,
			CLASS_SOURCE_FILE_LITERAL,
			METHOD_NAMES_LITERAL,
			METHOD_LINE_TABLES_LITERAL,
			METHOD_NUMBER_LITERAL,
			EXECUTABLE_UNIT_NUMBER_LITERAL,
			PREVIOUS_UNIT_NUMBER_LITERAL
		};

	/**
	 * A public read-only list of all the '<em><b>Data Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DataType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Data Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DataType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DataType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Data Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DataType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DataType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Data Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DataType get(int value) {
		switch (value) {
			case CLASS_NAME: return CLASS_NAME_LITERAL;
			case METHOD_NAME: return METHOD_NAME_LITERAL;
			case METHOD_SIG: return METHOD_SIG_LITERAL;
			case THIS_OBJECT: return THIS_OBJECT_LITERAL;
			case ARGS: return ARGS_LITERAL;
			case RETURNED_OBJECT: return RETURNED_OBJECT_LITERAL;
			case IS_FINALLY: return IS_FINALLY_LITERAL;
			case EXCEPTION_OBJECT: return EXCEPTION_OBJECT_LITERAL;
			case STATIC_FIELD: return STATIC_FIELD_LITERAL;
			case CLASS_SOURCE_FILE: return CLASS_SOURCE_FILE_LITERAL;
			case METHOD_NAMES: return METHOD_NAMES_LITERAL;
			case METHOD_LINE_TABLES: return METHOD_LINE_TABLES_LITERAL;
			case METHOD_NUMBER: return METHOD_NUMBER_LITERAL;
			case EXECUTABLE_UNIT_NUMBER: return EXECUTABLE_UNIT_NUMBER_LITERAL;
			case PREVIOUS_UNIT_NUMBER: return PREVIOUS_UNIT_NUMBER_LITERAL;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DataType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
}
