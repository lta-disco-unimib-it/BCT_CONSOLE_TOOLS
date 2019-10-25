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
 * $Id: Probekit.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probekit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probekit#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probekit#getProbe <em>Probe</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probekit#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probekit#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.Probekit#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbekit()
 * @model extendedMetaData="name='probekit_._type' kind='elementOnly'"
 * @generated
 */
public interface Probekit extends EObject{
	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Label}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbekit_Label()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='label' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Label> getLabel();

	/**
	 * Returns the value of the '<em><b>Probe</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.hyades.models.internal.probekit.Probe}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Probe</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probe</em>' containment reference list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbekit_Probe()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='probe' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Probe> getProbe();

	/**
	 * Returns the value of the '<em><b>Fragment At Shared Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment At Shared Scope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment At Shared Scope</em>' attribute.
	 * @see #setFragmentAtSharedScope(String)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbekit_FragmentAtSharedScope()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='fragmentAtSharedScope' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFragmentAtSharedScope();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment At Shared Scope</em>' attribute.
	 * @see #getFragmentAtSharedScope()
	 * @generated
	 */
	void setFragmentAtSharedScope(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbekit_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getProbekit_Version()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // Probekit
