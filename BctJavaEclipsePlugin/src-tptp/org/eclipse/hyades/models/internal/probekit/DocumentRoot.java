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
 * $Id: DocumentRoot.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getCode <em>Code</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlKey <em>Control Key</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlName <em>Control Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getData <em>Data</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragment <em>Fragment</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtClassScope <em>Fragment At Class Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getImport <em>Import</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getInvocationObject <em>Invocation Object</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbe <em>Probe</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbekit <em>Probekit</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getStaticField <em>Static Field</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject{
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code</em>' attribute.
	 * @see #setCode(String)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Code()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='code' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCode();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 * @generated
	 */
	void setCode(String value);

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
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_ControlKey()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='controlKey' namespace='##targetNamespace'"
	 * @generated
	 */
	ControlKey getControlKey();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlKey <em>Control Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Control Key</em>' containment reference.
	 * @see #getControlKey()
	 * @generated
	 */
	void setControlKey(ControlKey value);

	/**
	 * Returns the value of the '<em><b>Control Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Control Name</em>' containment reference.
	 * @see #setControlName(ControlName)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_ControlName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='controlName' namespace='##targetNamespace'"
	 * @generated
	 */
	ControlName getControlName();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlName <em>Control Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Control Name</em>' containment reference.
	 * @see #getControlName()
	 * @generated
	 */
	void setControlName(ControlName value);

	/**
	 * Returns the value of the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' containment reference.
	 * @see #setData(DataItem)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Data()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='data' namespace='##targetNamespace'"
	 * @generated
	 */
	DataItem getData();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getData <em>Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data</em>' containment reference.
	 * @see #getData()
	 * @generated
	 */
	void setData(DataItem value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' containment reference.
	 * @see #setDescription(Description)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Description()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	Description getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getDescription <em>Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' containment reference.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(Description value);

	/**
	 * Returns the value of the '<em><b>Fragment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment</em>' containment reference.
	 * @see #setFragment(Fragment)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Fragment()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='fragment' namespace='##targetNamespace'"
	 * @generated
	 */
	Fragment getFragment();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragment <em>Fragment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment</em>' containment reference.
	 * @see #getFragment()
	 * @generated
	 */
	void setFragment(Fragment value);

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
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_FragmentAtClassScope()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='fragmentAtClassScope' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFragmentAtClassScope();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtClassScope <em>Fragment At Class Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment At Class Scope</em>' attribute.
	 * @see #getFragmentAtClassScope()
	 * @generated
	 */
	void setFragmentAtClassScope(String value);

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
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_FragmentAtSharedScope()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='fragmentAtSharedScope' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFragmentAtSharedScope();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment At Shared Scope</em>' attribute.
	 * @see #getFragmentAtSharedScope()
	 * @generated
	 */
	void setFragmentAtSharedScope(String value);

	/**
	 * Returns the value of the '<em><b>Import</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import</em>' containment reference.
	 * @see #setImport(Import)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Import()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='import' namespace='##targetNamespace'"
	 * @generated
	 */
	Import getImport();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getImport <em>Import</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import</em>' containment reference.
	 * @see #getImport()
	 * @generated
	 */
	void setImport(Import value);

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
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_InvocationObject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='invocationObject' namespace='##targetNamespace'"
	 * @generated
	 */
	InvocationObject getInvocationObject();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getInvocationObject <em>Invocation Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invocation Object</em>' containment reference.
	 * @see #getInvocationObject()
	 * @generated
	 */
	void setInvocationObject(InvocationObject value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Label()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='label' namespace='##targetNamespace'"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getLabel <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(Name)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Name()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	Name getName();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(Name value);

	/**
	 * Returns the value of the '<em><b>Probe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Probe</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probe</em>' containment reference.
	 * @see #setProbe(Probe)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Probe()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='probe' namespace='##targetNamespace'"
	 * @generated
	 */
	Probe getProbe();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbe <em>Probe</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probe</em>' containment reference.
	 * @see #getProbe()
	 * @generated
	 */
	void setProbe(Probe value);

	/**
	 * Returns the value of the '<em><b>Probekit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Probekit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probekit</em>' containment reference.
	 * @see #setProbekit(Probekit)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Probekit()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='probekit' namespace='##targetNamespace'"
	 * @generated
	 */
	Probekit getProbekit();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbekit <em>Probekit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probekit</em>' containment reference.
	 * @see #getProbekit()
	 * @generated
	 */
	void setProbekit(Probekit value);

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
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_StaticField()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='staticField' namespace='##targetNamespace'"
	 * @generated
	 */
	StaticField getStaticField();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getStaticField <em>Static Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Field</em>' containment reference.
	 * @see #getStaticField()
	 * @generated
	 */
	void setStaticField(StaticField value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Target)
	 * @see org.eclipse.hyades.models.internal.probekit.ProbekitPackage#getDocumentRoot_Target()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='target' namespace='##targetNamespace'"
	 * @generated
	 */
	Target getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Target value);

} // DocumentRoot
