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
 * $Id: DocumentRootImpl.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.hyades.models.internal.probekit.ControlKey;
import org.eclipse.hyades.models.internal.probekit.ControlName;
import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.Description;
import org.eclipse.hyades.models.internal.probekit.DocumentRoot;
import org.eclipse.hyades.models.internal.probekit.Fragment;
import org.eclipse.hyades.models.internal.probekit.Import;
import org.eclipse.hyades.models.internal.probekit.InvocationObject;
import org.eclipse.hyades.models.internal.probekit.Label;
import org.eclipse.hyades.models.internal.probekit.Name;
import org.eclipse.hyades.models.internal.probekit.Probe;
import org.eclipse.hyades.models.internal.probekit.Probekit;
import org.eclipse.hyades.models.internal.probekit.ProbekitPackage;
import org.eclipse.hyades.models.internal.probekit.StaticField;
import org.eclipse.hyades.models.internal.probekit.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getCode <em>Code</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getControlKey <em>Control Key</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getControlName <em>Control Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getData <em>Data</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getFragment <em>Fragment</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getFragmentAtClassScope <em>Fragment At Class Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getImport <em>Import</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getInvocationObject <em>Invocation Object</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getProbe <em>Probe</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getProbekit <em>Probekit</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getStaticField <em>Static Field</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCode()
	 * @generated
	 * @ordered
	 */
	protected static final String CODE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFragmentAtClassScope() <em>Fragment At Class Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragmentAtClassScope()
	 * @generated
	 * @ordered
	 */
	protected static final String FRAGMENT_AT_CLASS_SCOPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFragmentAtSharedScope() <em>Fragment At Shared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragmentAtSharedScope()
	 * @generated
	 * @ordered
	 */
	protected static final String FRAGMENT_AT_SHARED_SCOPE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProbekitPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, ProbekitPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ProbekitPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ProbekitPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCode() {
		return (String)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__CODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCode(String newCode) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__CODE, newCode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlKey getControlKey() {
		return (ControlKey)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__CONTROL_KEY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetControlKey(ControlKey newControlKey, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__CONTROL_KEY, newControlKey, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setControlKey(ControlKey newControlKey) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__CONTROL_KEY, newControlKey);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlName getControlName() {
		return (ControlName)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__CONTROL_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetControlName(ControlName newControlName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__CONTROL_NAME, newControlName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setControlName(ControlName newControlName) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__CONTROL_NAME, newControlName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataItem getData() {
		return (DataItem)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetData(DataItem newData, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__DATA, newData, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setData(DataItem newData) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__DATA, newData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Description getDescription() {
		return (Description)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDescription(Description newDescription, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, newDescription, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(Description newDescription) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Fragment getFragment() {
		return (Fragment)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFragment(Fragment newFragment, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT, newFragment, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragment(Fragment newFragment) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT, newFragment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFragmentAtClassScope() {
		return (String)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragmentAtClassScope(String newFragmentAtClassScope) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE, newFragmentAtClassScope);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFragmentAtSharedScope() {
		return (String)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragmentAtSharedScope(String newFragmentAtSharedScope) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE, newFragmentAtSharedScope);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Import getImport() {
		return (Import)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__IMPORT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImport(Import newImport, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__IMPORT, newImport, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImport(Import newImport) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__IMPORT, newImport);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvocationObject getInvocationObject() {
		return (InvocationObject)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__INVOCATION_OBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvocationObject(InvocationObject newInvocationObject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__INVOCATION_OBJECT, newInvocationObject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvocationObject(InvocationObject newInvocationObject) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__INVOCATION_OBJECT, newInvocationObject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Label getLabel() {
		return (Label)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__LABEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabel(Label newLabel, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__LABEL, newLabel, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(Label newLabel) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__LABEL, newLabel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Name getName() {
		return (Name)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(Name newName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__NAME, newName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(Name newName) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Probe getProbe() {
		return (Probe)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__PROBE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProbe(Probe newProbe, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__PROBE, newProbe, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProbe(Probe newProbe) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__PROBE, newProbe);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Probekit getProbekit() {
		return (Probekit)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__PROBEKIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProbekit(Probekit newProbekit, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__PROBEKIT, newProbekit, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProbekit(Probekit newProbekit) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__PROBEKIT, newProbekit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaticField getStaticField() {
		return (StaticField)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__STATIC_FIELD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStaticField(StaticField newStaticField, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__STATIC_FIELD, newStaticField, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaticField(StaticField newStaticField) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__STATIC_FIELD, newStaticField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target getTarget() {
		return (Target)getMixed().get(ProbekitPackage.Literals.DOCUMENT_ROOT__TARGET, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Target newTarget, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProbekitPackage.Literals.DOCUMENT_ROOT__TARGET, newTarget, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Target newTarget) {
		((FeatureMap.Internal)getMixed()).set(ProbekitPackage.Literals.DOCUMENT_ROOT__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProbekitPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_KEY:
				return basicSetControlKey(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_NAME:
				return basicSetControlName(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__DATA:
				return basicSetData(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__DESCRIPTION:
				return basicSetDescription(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT:
				return basicSetFragment(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__IMPORT:
				return basicSetImport(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__INVOCATION_OBJECT:
				return basicSetInvocationObject(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__LABEL:
				return basicSetLabel(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__NAME:
				return basicSetName(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__PROBE:
				return basicSetProbe(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__PROBEKIT:
				return basicSetProbekit(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__STATIC_FIELD:
				return basicSetStaticField(null, msgs);
			case ProbekitPackage.DOCUMENT_ROOT__TARGET:
				return basicSetTarget(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProbekitPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case ProbekitPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case ProbekitPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case ProbekitPackage.DOCUMENT_ROOT__CODE:
				return getCode();
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_KEY:
				return getControlKey();
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_NAME:
				return getControlName();
			case ProbekitPackage.DOCUMENT_ROOT__DATA:
				return getData();
			case ProbekitPackage.DOCUMENT_ROOT__DESCRIPTION:
				return getDescription();
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT:
				return getFragment();
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE:
				return getFragmentAtClassScope();
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE:
				return getFragmentAtSharedScope();
			case ProbekitPackage.DOCUMENT_ROOT__IMPORT:
				return getImport();
			case ProbekitPackage.DOCUMENT_ROOT__INVOCATION_OBJECT:
				return getInvocationObject();
			case ProbekitPackage.DOCUMENT_ROOT__LABEL:
				return getLabel();
			case ProbekitPackage.DOCUMENT_ROOT__NAME:
				return getName();
			case ProbekitPackage.DOCUMENT_ROOT__PROBE:
				return getProbe();
			case ProbekitPackage.DOCUMENT_ROOT__PROBEKIT:
				return getProbekit();
			case ProbekitPackage.DOCUMENT_ROOT__STATIC_FIELD:
				return getStaticField();
			case ProbekitPackage.DOCUMENT_ROOT__TARGET:
				return getTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProbekitPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__CODE:
				setCode((String)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_KEY:
				setControlKey((ControlKey)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_NAME:
				setControlName((ControlName)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__DATA:
				setData((DataItem)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__DESCRIPTION:
				setDescription((Description)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT:
				setFragment((Fragment)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE:
				setFragmentAtClassScope((String)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE:
				setFragmentAtSharedScope((String)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__IMPORT:
				setImport((Import)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__INVOCATION_OBJECT:
				setInvocationObject((InvocationObject)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__LABEL:
				setLabel((Label)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__NAME:
				setName((Name)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__PROBE:
				setProbe((Probe)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__PROBEKIT:
				setProbekit((Probekit)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__STATIC_FIELD:
				setStaticField((StaticField)newValue);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__TARGET:
				setTarget((Target)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProbekitPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case ProbekitPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case ProbekitPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case ProbekitPackage.DOCUMENT_ROOT__CODE:
				setCode(CODE_EDEFAULT);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_KEY:
				setControlKey((ControlKey)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_NAME:
				setControlName((ControlName)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__DATA:
				setData((DataItem)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__DESCRIPTION:
				setDescription((Description)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT:
				setFragment((Fragment)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE:
				setFragmentAtClassScope(FRAGMENT_AT_CLASS_SCOPE_EDEFAULT);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE:
				setFragmentAtSharedScope(FRAGMENT_AT_SHARED_SCOPE_EDEFAULT);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__IMPORT:
				setImport((Import)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__INVOCATION_OBJECT:
				setInvocationObject((InvocationObject)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__LABEL:
				setLabel((Label)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__NAME:
				setName((Name)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__PROBE:
				setProbe((Probe)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__PROBEKIT:
				setProbekit((Probekit)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__STATIC_FIELD:
				setStaticField((StaticField)null);
				return;
			case ProbekitPackage.DOCUMENT_ROOT__TARGET:
				setTarget((Target)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProbekitPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case ProbekitPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case ProbekitPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case ProbekitPackage.DOCUMENT_ROOT__CODE:
				return CODE_EDEFAULT == null ? getCode() != null : !CODE_EDEFAULT.equals(getCode());
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_KEY:
				return getControlKey() != null;
			case ProbekitPackage.DOCUMENT_ROOT__CONTROL_NAME:
				return getControlName() != null;
			case ProbekitPackage.DOCUMENT_ROOT__DATA:
				return getData() != null;
			case ProbekitPackage.DOCUMENT_ROOT__DESCRIPTION:
				return getDescription() != null;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT:
				return getFragment() != null;
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE:
				return FRAGMENT_AT_CLASS_SCOPE_EDEFAULT == null ? getFragmentAtClassScope() != null : !FRAGMENT_AT_CLASS_SCOPE_EDEFAULT.equals(getFragmentAtClassScope());
			case ProbekitPackage.DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE:
				return FRAGMENT_AT_SHARED_SCOPE_EDEFAULT == null ? getFragmentAtSharedScope() != null : !FRAGMENT_AT_SHARED_SCOPE_EDEFAULT.equals(getFragmentAtSharedScope());
			case ProbekitPackage.DOCUMENT_ROOT__IMPORT:
				return getImport() != null;
			case ProbekitPackage.DOCUMENT_ROOT__INVOCATION_OBJECT:
				return getInvocationObject() != null;
			case ProbekitPackage.DOCUMENT_ROOT__LABEL:
				return getLabel() != null;
			case ProbekitPackage.DOCUMENT_ROOT__NAME:
				return getName() != null;
			case ProbekitPackage.DOCUMENT_ROOT__PROBE:
				return getProbe() != null;
			case ProbekitPackage.DOCUMENT_ROOT__PROBEKIT:
				return getProbekit() != null;
			case ProbekitPackage.DOCUMENT_ROOT__STATIC_FIELD:
				return getStaticField() != null;
			case ProbekitPackage.DOCUMENT_ROOT__TARGET:
				return getTarget() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
