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
 * $Id: ProbeImpl.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
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
import org.eclipse.hyades.models.internal.probekit.ControlKey;
import org.eclipse.hyades.models.internal.probekit.ControlName;
import org.eclipse.hyades.models.internal.probekit.Description;
import org.eclipse.hyades.models.internal.probekit.Fragment;
import org.eclipse.hyades.models.internal.probekit.Import;
import org.eclipse.hyades.models.internal.probekit.InvocationObject;
import org.eclipse.hyades.models.internal.probekit.Name;
import org.eclipse.hyades.models.internal.probekit.Probe;
import org.eclipse.hyades.models.internal.probekit.ProbekitPackage;
import org.eclipse.hyades.models.internal.probekit.StaticField;
import org.eclipse.hyades.models.internal.probekit.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probe</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getControlKey <em>Control Key</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getControlName <em>Control Name</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getImport <em>Import</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getFragmentAtClassScope <em>Fragment At Class Scope</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getFragment <em>Fragment</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getInvocationObject <em>Invocation Object</em>}</li>
 *   <li>{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl#getStaticField <em>Static Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProbeImpl extends EObjectImpl implements Probe {
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected EList<Name> name;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected EList<Description> description;

	/**
	 * The cached value of the '{@link #getControlKey() <em>Control Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlKey()
	 * @generated
	 * @ordered
	 */
	protected ControlKey controlKey;

	/**
	 * The cached value of the '{@link #getControlName() <em>Control Name</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlName()
	 * @generated
	 * @ordered
	 */
	protected EList<ControlName> controlName;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EList<Target> target;

	/**
	 * The cached value of the '{@link #getImport() <em>Import</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImport()
	 * @generated
	 * @ordered
	 */
	protected EList<Import> import_;

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
	 * The cached value of the '{@link #getFragmentAtClassScope() <em>Fragment At Class Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragmentAtClassScope()
	 * @generated
	 * @ordered
	 */
	protected String fragmentAtClassScope = FRAGMENT_AT_CLASS_SCOPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFragment() <em>Fragment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragment()
	 * @generated
	 * @ordered
	 */
	protected EList<Fragment> fragment;

	/**
	 * The cached value of the '{@link #getInvocationObject() <em>Invocation Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocationObject()
	 * @generated
	 * @ordered
	 */
	protected InvocationObject invocationObject;

	/**
	 * The cached value of the '{@link #getStaticField() <em>Static Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStaticField()
	 * @generated
	 * @ordered
	 */
	protected StaticField staticField;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProbekitPackage.Literals.PROBE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Name> getName() {
		if (name == null) {
			name = new EObjectContainmentEList<Name>(Name.class, this, ProbekitPackage.PROBE__NAME);
		}
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Description> getDescription() {
		if (description == null) {
			description = new EObjectContainmentEList<Description>(Description.class, this, ProbekitPackage.PROBE__DESCRIPTION);
		}
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlKey getControlKey() {
		return controlKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetControlKey(ControlKey newControlKey, NotificationChain msgs) {
		ControlKey oldControlKey = controlKey;
		controlKey = newControlKey;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__CONTROL_KEY, oldControlKey, newControlKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setControlKey(ControlKey newControlKey) {
		if (newControlKey != controlKey) {
			NotificationChain msgs = null;
			if (controlKey != null)
				msgs = ((InternalEObject)controlKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProbekitPackage.PROBE__CONTROL_KEY, null, msgs);
			if (newControlKey != null)
				msgs = ((InternalEObject)newControlKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProbekitPackage.PROBE__CONTROL_KEY, null, msgs);
			msgs = basicSetControlKey(newControlKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__CONTROL_KEY, newControlKey, newControlKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ControlName> getControlName() {
		if (controlName == null) {
			controlName = new EObjectContainmentEList<ControlName>(ControlName.class, this, ProbekitPackage.PROBE__CONTROL_NAME);
		}
		return controlName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Target> getTarget() {
		if (target == null) {
			target = new EObjectContainmentEList<Target>(Target.class, this, ProbekitPackage.PROBE__TARGET);
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Import> getImport() {
		if (import_ == null) {
			import_ = new EObjectContainmentEList<Import>(Import.class, this, ProbekitPackage.PROBE__IMPORT);
		}
		return import_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFragmentAtClassScope() {
		return fragmentAtClassScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragmentAtClassScope(String newFragmentAtClassScope) {
		String oldFragmentAtClassScope = fragmentAtClassScope;
		fragmentAtClassScope = newFragmentAtClassScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__FRAGMENT_AT_CLASS_SCOPE, oldFragmentAtClassScope, fragmentAtClassScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Fragment> getFragment() {
		if (fragment == null) {
			fragment = new EObjectContainmentEList<Fragment>(Fragment.class, this, ProbekitPackage.PROBE__FRAGMENT);
		}
		return fragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvocationObject getInvocationObject() {
		return invocationObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvocationObject(InvocationObject newInvocationObject, NotificationChain msgs) {
		InvocationObject oldInvocationObject = invocationObject;
		invocationObject = newInvocationObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__INVOCATION_OBJECT, oldInvocationObject, newInvocationObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvocationObject(InvocationObject newInvocationObject) {
		if (newInvocationObject != invocationObject) {
			NotificationChain msgs = null;
			if (invocationObject != null)
				msgs = ((InternalEObject)invocationObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProbekitPackage.PROBE__INVOCATION_OBJECT, null, msgs);
			if (newInvocationObject != null)
				msgs = ((InternalEObject)newInvocationObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProbekitPackage.PROBE__INVOCATION_OBJECT, null, msgs);
			msgs = basicSetInvocationObject(newInvocationObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__INVOCATION_OBJECT, newInvocationObject, newInvocationObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaticField getStaticField() {
		return staticField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStaticField(StaticField newStaticField, NotificationChain msgs) {
		StaticField oldStaticField = staticField;
		staticField = newStaticField;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__STATIC_FIELD, oldStaticField, newStaticField);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaticField(StaticField newStaticField) {
		if (newStaticField != staticField) {
			NotificationChain msgs = null;
			if (staticField != null)
				msgs = ((InternalEObject)staticField).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProbekitPackage.PROBE__STATIC_FIELD, null, msgs);
			if (newStaticField != null)
				msgs = ((InternalEObject)newStaticField).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProbekitPackage.PROBE__STATIC_FIELD, null, msgs);
			msgs = basicSetStaticField(newStaticField, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProbekitPackage.PROBE__STATIC_FIELD, newStaticField, newStaticField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProbekitPackage.PROBE__NAME:
				return ((InternalEList<?>)getName()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBE__DESCRIPTION:
				return ((InternalEList<?>)getDescription()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBE__CONTROL_KEY:
				return basicSetControlKey(null, msgs);
			case ProbekitPackage.PROBE__CONTROL_NAME:
				return ((InternalEList<?>)getControlName()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBE__TARGET:
				return ((InternalEList<?>)getTarget()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBE__IMPORT:
				return ((InternalEList<?>)getImport()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBE__FRAGMENT:
				return ((InternalEList<?>)getFragment()).basicRemove(otherEnd, msgs);
			case ProbekitPackage.PROBE__INVOCATION_OBJECT:
				return basicSetInvocationObject(null, msgs);
			case ProbekitPackage.PROBE__STATIC_FIELD:
				return basicSetStaticField(null, msgs);
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
			case ProbekitPackage.PROBE__NAME:
				return getName();
			case ProbekitPackage.PROBE__DESCRIPTION:
				return getDescription();
			case ProbekitPackage.PROBE__CONTROL_KEY:
				return getControlKey();
			case ProbekitPackage.PROBE__CONTROL_NAME:
				return getControlName();
			case ProbekitPackage.PROBE__TARGET:
				return getTarget();
			case ProbekitPackage.PROBE__IMPORT:
				return getImport();
			case ProbekitPackage.PROBE__FRAGMENT_AT_CLASS_SCOPE:
				return getFragmentAtClassScope();
			case ProbekitPackage.PROBE__FRAGMENT:
				return getFragment();
			case ProbekitPackage.PROBE__INVOCATION_OBJECT:
				return getInvocationObject();
			case ProbekitPackage.PROBE__STATIC_FIELD:
				return getStaticField();
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
			case ProbekitPackage.PROBE__NAME:
				getName().clear();
				getName().addAll((Collection<? extends Name>)newValue);
				return;
			case ProbekitPackage.PROBE__DESCRIPTION:
				getDescription().clear();
				getDescription().addAll((Collection<? extends Description>)newValue);
				return;
			case ProbekitPackage.PROBE__CONTROL_KEY:
				setControlKey((ControlKey)newValue);
				return;
			case ProbekitPackage.PROBE__CONTROL_NAME:
				getControlName().clear();
				getControlName().addAll((Collection<? extends ControlName>)newValue);
				return;
			case ProbekitPackage.PROBE__TARGET:
				getTarget().clear();
				getTarget().addAll((Collection<? extends Target>)newValue);
				return;
			case ProbekitPackage.PROBE__IMPORT:
				getImport().clear();
				getImport().addAll((Collection<? extends Import>)newValue);
				return;
			case ProbekitPackage.PROBE__FRAGMENT_AT_CLASS_SCOPE:
				setFragmentAtClassScope((String)newValue);
				return;
			case ProbekitPackage.PROBE__FRAGMENT:
				getFragment().clear();
				getFragment().addAll((Collection<? extends Fragment>)newValue);
				return;
			case ProbekitPackage.PROBE__INVOCATION_OBJECT:
				setInvocationObject((InvocationObject)newValue);
				return;
			case ProbekitPackage.PROBE__STATIC_FIELD:
				setStaticField((StaticField)newValue);
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
			case ProbekitPackage.PROBE__NAME:
				getName().clear();
				return;
			case ProbekitPackage.PROBE__DESCRIPTION:
				getDescription().clear();
				return;
			case ProbekitPackage.PROBE__CONTROL_KEY:
				setControlKey((ControlKey)null);
				return;
			case ProbekitPackage.PROBE__CONTROL_NAME:
				getControlName().clear();
				return;
			case ProbekitPackage.PROBE__TARGET:
				getTarget().clear();
				return;
			case ProbekitPackage.PROBE__IMPORT:
				getImport().clear();
				return;
			case ProbekitPackage.PROBE__FRAGMENT_AT_CLASS_SCOPE:
				setFragmentAtClassScope(FRAGMENT_AT_CLASS_SCOPE_EDEFAULT);
				return;
			case ProbekitPackage.PROBE__FRAGMENT:
				getFragment().clear();
				return;
			case ProbekitPackage.PROBE__INVOCATION_OBJECT:
				setInvocationObject((InvocationObject)null);
				return;
			case ProbekitPackage.PROBE__STATIC_FIELD:
				setStaticField((StaticField)null);
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
			case ProbekitPackage.PROBE__NAME:
				return name != null && !name.isEmpty();
			case ProbekitPackage.PROBE__DESCRIPTION:
				return description != null && !description.isEmpty();
			case ProbekitPackage.PROBE__CONTROL_KEY:
				return controlKey != null;
			case ProbekitPackage.PROBE__CONTROL_NAME:
				return controlName != null && !controlName.isEmpty();
			case ProbekitPackage.PROBE__TARGET:
				return target != null && !target.isEmpty();
			case ProbekitPackage.PROBE__IMPORT:
				return import_ != null && !import_.isEmpty();
			case ProbekitPackage.PROBE__FRAGMENT_AT_CLASS_SCOPE:
				return FRAGMENT_AT_CLASS_SCOPE_EDEFAULT == null ? fragmentAtClassScope != null : !FRAGMENT_AT_CLASS_SCOPE_EDEFAULT.equals(fragmentAtClassScope);
			case ProbekitPackage.PROBE__FRAGMENT:
				return fragment != null && !fragment.isEmpty();
			case ProbekitPackage.PROBE__INVOCATION_OBJECT:
				return invocationObject != null;
			case ProbekitPackage.PROBE__STATIC_FIELD:
				return staticField != null;
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
		result.append(" (fragmentAtClassScope: ");
		result.append(fragmentAtClassScope);
		result.append(')');
		return result.toString();
	}

} //ProbeImpl
