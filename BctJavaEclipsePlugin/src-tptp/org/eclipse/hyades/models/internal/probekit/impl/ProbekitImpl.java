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
 * $Id: ProbekitImpl.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.hyades.models.internal.probekit.Label;
import org.eclipse.hyades.models.internal.probekit.Probe;
import org.eclipse.hyades.models.internal.probekit.Probekit;
import org.eclipse.hyades.models.internal.probekit.ProbekitPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probekit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl#getProbe <em>Probe</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProbekitImpl extends EObjectImpl implements Probekit {
	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected EList<Label> label;

	/**
	 * The cached value of the '{@link #getProbe() <em>Probe</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbe()
	 * @generated
	 * @ordered
	 */
	protected EList<Probe> probe;

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
	 * The cached value of the '{@link #getFragmentAtSharedScope() <em>Fragment At Shared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragmentAtSharedScope()
	 * @generated
	 * @ordered
	 */
	protected String fragmentAtSharedScope = FRAGMENT_AT_SHARED_SCOPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbekitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProbekitPackage.Literals.PROBEKIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Label> getLabel() {
		if (label == null) {
			label = new EObjectContainmentEList<Label>(Label.class, this, ProbekitPackage.PROBEKIT__LABEL);
		}
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Probe> getProbe() {
		if (probe == null) {
			probe = new EObjectContainmentEList<Probe>(Probe.class, this, ProbekitPackage.PROBEKIT__PROBE);
		}
		return probe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFragmentAtSharedScope() {
		return fragmentAtSharedScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragmentAtSharedScope(String newFragmentAtSharedScope) {
		String oldFragmentAtSharedScope = fragmentAtSharedScope;
		fragmentAtSharedScope = newFragmentAtSharedScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBEKIT__FRAGMENT_AT_SHARED_SCOPE, oldFragmentAtSharedScope, fragmentAtSharedScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBEKIT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBEKIT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProbekitPackage.PROBEKIT__LABEL:
				return ((InternalEList<?>)getLabel()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBEKIT__PROBE:
				return ((InternalEList<?>)getProbe()).basicRemove(otherEnd, msgs);
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
			case ProbekitPackage.PROBEKIT__LABEL:
				return getLabel();
			case ProbekitPackage.PROBEKIT__PROBE:
				return getProbe();
			case ProbekitPackage.PROBEKIT__FRAGMENT_AT_SHARED_SCOPE:
				return getFragmentAtSharedScope();
			case ProbekitPackage.PROBEKIT__ID:
				return getId();
			case ProbekitPackage.PROBEKIT__VERSION:
				return getVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProbekitPackage.PROBEKIT__LABEL:
				getLabel().clear();
				getLabel().addAll((Collection<? extends Label>)newValue);
				return;
			case ProbekitPackage.PROBEKIT__PROBE:
				getProbe().clear();
				getProbe().addAll((Collection<? extends Probe>)newValue);
				return;
			case ProbekitPackage.PROBEKIT__FRAGMENT_AT_SHARED_SCOPE:
				setFragmentAtSharedScope((String)newValue);
				return;
			case ProbekitPackage.PROBEKIT__ID:
				setId((String)newValue);
				return;
			case ProbekitPackage.PROBEKIT__VERSION:
				setVersion((String)newValue);
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
			case ProbekitPackage.PROBEKIT__LABEL:
				getLabel().clear();
				return;
			case ProbekitPackage.PROBEKIT__PROBE:
				getProbe().clear();
				return;
			case ProbekitPackage.PROBEKIT__FRAGMENT_AT_SHARED_SCOPE:
				setFragmentAtSharedScope(FRAGMENT_AT_SHARED_SCOPE_EDEFAULT);
				return;
			case ProbekitPackage.PROBEKIT__ID:
				setId(ID_EDEFAULT);
				return;
			case ProbekitPackage.PROBEKIT__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case ProbekitPackage.PROBEKIT__LABEL:
				return label != null && !label.isEmpty();
			case ProbekitPackage.PROBEKIT__PROBE:
				return probe != null && !probe.isEmpty();
			case ProbekitPackage.PROBEKIT__FRAGMENT_AT_SHARED_SCOPE:
				return FRAGMENT_AT_SHARED_SCOPE_EDEFAULT == null ? fragmentAtSharedScope != null : !FRAGMENT_AT_SHARED_SCOPE_EDEFAULT.equals(fragmentAtSharedScope);
			case ProbekitPackage.PROBEKIT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ProbekitPackage.PROBEKIT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (fragmentAtSharedScope: ");
		result.append(fragmentAtSharedScope);
		result.append(", id: ");
		result.append(id);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //ProbekitImpl
