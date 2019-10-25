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
 * $Id: Probe.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probe</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getControlKey <em>Control Key</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getControlName <em>Control Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getImport <em>Import</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getFragmentAtClassScope <em>Fragment At Class Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getFragment <em>Fragment</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getInvocationObject <em>Invocation Object</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probe#getStaticField <em>Static Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe()
 * @model extendedMetaData="name='probe_._type' kind='elementOnly'"
 * @generated
 */
public interface Probe extends EObject{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Name}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_Name()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Name> getName();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Description}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_Description()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Description> getDescription();

	/**
	 * Returns the value of the '<em><b>Control Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Control Key</em>' containment reference.
	 * @see #setControlKey(ControlKey)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_ControlKey()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='controlKey' namespace='##targetNamespace'"
	 * @generated
	 */
	ControlKey getControlKey();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probe#getControlKey <em>Control Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Control Key</em>' containment reference.
	 * @see #getControlKey()
	 * @generated
	 */
	void setControlKey(ControlKey value);

	/**
	 * Returns the value of the '<em><b>Control Name</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.ControlName}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control Name</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Control Name</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_ControlName()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='controlName' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ControlName> getControlName();

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Target}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_Target()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='target' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Target> getTarget();

	/**
	 * Returns the value of the '<em><b>Import</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Import}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_Import()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='import' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Import> getImport();

	/**
	 * Returns the value of the '<em><b>Fragment At Class Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment At Class Scope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment At Class Scope</em>' attribute.
	 * @see #setFragmentAtClassScope(String)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_FragmentAtClassScope()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='fragmentAtClassScope' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFragmentAtClassScope();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probe#getFragmentAtClassScope <em>Fragment At Class Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment At Class Scope</em>' attribute.
	 * @see #getFragmentAtClassScope()
	 * @generated
	 */
	void setFragmentAtClassScope(String value);

	/**
	 * Returns the value of the '<em><b>Fragment</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Fragment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_Fragment()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='fragment' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Fragment> getFragment();

	/**
	 * Returns the value of the '<em><b>Invocation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invocation Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invocation Object</em>' containment reference.
	 * @see #setInvocationObject(InvocationObject)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_InvocationObject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='invocationObject' namespace='##targetNamespace'"
	 * @generated
	 */
	InvocationObject getInvocationObject();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probe#getInvocationObject <em>Invocation Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invocation Object</em>' containment reference.
	 * @see #getInvocationObject()
	 * @generated
	 */
	void setInvocationObject(InvocationObject value);

	/**
	 * Returns the value of the '<em><b>Static Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Static Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Static Field</em>' containment reference.
	 * @see #setStaticField(StaticField)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbe_StaticField()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='staticField' namespace='##targetNamespace'"
	 * @generated
	 */
	StaticField getStaticField();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probe#getStaticField <em>Static Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Field</em>' containment reference.
	 * @see #getStaticField()
	 * @generated
	 */
	void setStaticField(StaticField value);

} // Probe
