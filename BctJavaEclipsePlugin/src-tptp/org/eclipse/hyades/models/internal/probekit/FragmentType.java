/**********************************************************************
 * Copyright (c) 2003, 2009 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 *
 * $Id: FragmentType.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
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
 * A representation of the literals of the enumeration '<em><b>Fragment Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getFragmentType()
 * @model extendedMetaData="name='fragmentType'"
 * @generated
 */
public enum FragmentType implements Enumerator
{
	/**
	 * The '<em><b>Entry</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Entry</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ENTRY
	 * @generated
	 * @ordered
	 */
	ENTRY_LITERAL(0, "entry", "entry"),
	/**
	 * The '<em><b>Catch</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Catch</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CATCH
	 * @generated
	 * @ordered
	 */
	CATCH_LITERAL(1, "catch", "catch"),
	/**
	 * The '<em><b>Exit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Exit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXIT
	 * @generated
	 * @ordered
	 */
	EXIT_LITERAL(2, "exit", "exit"),
	/**
	 * The '<em><b>Before Call</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Before Call</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BEFORE_CALL
	 * @generated
	 * @ordered
	 */
	BEFORE_CALL_LITERAL(3, "beforeCall", "beforeCall"),
	/**
	 * The '<em><b>After Call</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>After Call</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AFTER_CALL
	 * @generated
	 * @ordered
	 */
	AFTER_CALL_LITERAL(4, "afterCall", "afterCall"),
	/**
	 * The '<em><b>Static Initializer</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Static Initializer</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATIC_INITIALIZER
	 * @generated
	 * @ordered
	 */
	STATIC_INITIALIZER_LITERAL(5, "staticInitializer", "staticInitializer"),
	/**
	 * The '<em><b>Executable Unit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Executable Unit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXECUTABLE_UNIT
	 * @generated
	 * @ordered
	 */
	EXECUTABLE_UNIT_LITERAL(6, "executableUnit", "executableUnit");
	/**
	 * The '<em><b>Entry</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ENTRY_LITERAL
	 * @model name="entry"
	 * @generated
	 * @ordered
	 */
	public static final int ENTRY = 0;

	/**
	 * The '<em><b>Catch</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CATCH_LITERAL
	 * @model name="catch"
	 * @generated
	 * @ordered
	 */
	public static final int CATCH = 1;

	/**
	 * The '<em><b>Exit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXIT_LITERAL
	 * @model name="exit"
	 * @generated
	 * @ordered
	 */
	public static final int EXIT = 2;

	/**
	 * The '<em><b>Before Call</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BEFORE_CALL_LITERAL
	 * @model name="beforeCall"
	 * @generated
	 * @ordered
	 */
	public static final int BEFORE_CALL = 3;

	/**
	 * The '<em><b>After Call</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AFTER_CALL_LITERAL
	 * @model name="afterCall"
	 * @generated
	 * @ordered
	 */
	public static final int AFTER_CALL = 4;

	/**
	 * The '<em><b>Static Initializer</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATIC_INITIALIZER_LITERAL
	 * @model name="staticInitializer"
	 * @generated
	 * @ordered
	 */
	public static final int STATIC_INITIALIZER = 5;

	/**
	 * The '<em><b>Executable Unit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXECUTABLE_UNIT_LITERAL
	 * @model name="executableUnit"
	 * @generated
	 * @ordered
	 */
	public static final int EXECUTABLE_UNIT = 6;

	/**
	 * An array of all the '<em><b>Fragment Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FragmentType[] VALUES_ARRAY =
		new FragmentType[] {
			ENTRY_LITERAL,
			CATCH_LITERAL,
			EXIT_LITERAL,
			BEFORE_CALL_LITERAL,
			AFTER_CALL_LITERAL,
			STATIC_INITIALIZER_LITERAL,
			EXECUTABLE_UNIT_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Fragment Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FragmentType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Fragment Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FragmentType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FragmentType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Fragment Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FragmentType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FragmentType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Fragment Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FragmentType get(int value) {
		switch (value) {
			case ENTRY: return ENTRY_LITERAL;
			case CATCH: return CATCH_LITERAL;
			case EXIT: return EXIT_LITERAL;
			case BEFORE_CALL: return BEFORE_CALL_LITERAL;
			case AFTER_CALL: return AFTER_CALL_LITERAL;
			case STATIC_INITIALIZER: return STATIC_INITIALIZER_LITERAL;
			case EXECUTABLE_UNIT: return EXECUTABLE_UNIT_LITERAL;
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
	private FragmentType(int value, String name, String literal) {
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
