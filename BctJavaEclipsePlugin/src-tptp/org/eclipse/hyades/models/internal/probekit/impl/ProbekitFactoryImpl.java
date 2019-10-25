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
 * $Id: ProbekitFactoryImpl.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.hyades.models.internal.probekit.ControlKey;
import org.eclipse.hyades.models.internal.probekit.ControlName;
import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.models.internal.probekit.Description;
import org.eclipse.hyades.models.internal.probekit.DocumentRoot;
import org.eclipse.hyades.models.internal.probekit.Fragment;
import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.models.internal.probekit.Import;
import org.eclipse.hyades.models.internal.probekit.InvocationObject;
import org.eclipse.hyades.models.internal.probekit.Label;
import org.eclipse.hyades.models.internal.probekit.Name;
import org.eclipse.hyades.models.internal.probekit.Probe;
import org.eclipse.hyades.models.internal.probekit.Probekit;
import org.eclipse.hyades.models.internal.probekit.ProbekitFactory;
import org.eclipse.hyades.models.internal.probekit.ProbekitPackage;
import org.eclipse.hyades.models.internal.probekit.StaticField;
import org.eclipse.hyades.models.internal.probekit.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProbekitFactoryImpl extends EFactoryImpl implements ProbekitFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProbekitFactory init() {
		try {
			ProbekitFactory theProbekitFactory = (ProbekitFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/org.eclipse.tptp.platform.models/src-probekit/model/probekit.xsd"); 
			if (theProbekitFactory != null) {
				return theProbekitFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProbekitFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbekitFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ProbekitPackage.CONTROL_KEY: return createControlKey();
			case ProbekitPackage.CONTROL_NAME: return createControlName();
			case ProbekitPackage.DATA_ITEM: return createDataItem();
			case ProbekitPackage.DESCRIPTION: return createDescription();
			case ProbekitPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case ProbekitPackage.FRAGMENT: return createFragment();
			case ProbekitPackage.IMPORT: return createImport();
			case ProbekitPackage.INVOCATION_OBJECT: return createInvocationObject();
			case ProbekitPackage.LABEL: return createLabel();
			case ProbekitPackage.NAME: return createName();
			case ProbekitPackage.PROBE: return createProbe();
			case ProbekitPackage.PROBEKIT: return createProbekit();
			case ProbekitPackage.STATIC_FIELD: return createStaticField();
			case ProbekitPackage.TARGET: return createTarget();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ProbekitPackage.DATA_TYPE:
				return createDataTypeFromString(eDataType, initialValue);
			case ProbekitPackage.FRAGMENT_TYPE:
				return createFragmentTypeFromString(eDataType, initialValue);
			case ProbekitPackage.DATA_TYPE_OBJECT:
				return createDataTypeObjectFromString(eDataType, initialValue);
			case ProbekitPackage.FRAGMENT_TYPE_OBJECT:
				return createFragmentTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ProbekitPackage.DATA_TYPE:
				return convertDataTypeToString(eDataType, instanceValue);
			case ProbekitPackage.FRAGMENT_TYPE:
				return convertFragmentTypeToString(eDataType, instanceValue);
			case ProbekitPackage.DATA_TYPE_OBJECT:
				return convertDataTypeObjectToString(eDataType, instanceValue);
			case ProbekitPackage.FRAGMENT_TYPE_OBJECT:
				return convertFragmentTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlKey createControlKey() {
		ControlKeyImpl controlKey = new ControlKeyImpl();
		return controlKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlName createControlName() {
		ControlNameImpl controlName = new ControlNameImpl();
		return controlName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataItem createDataItem() {
		DataItemImpl dataItem = new DataItemImpl();
		return dataItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Description createDescription() {
		DescriptionImpl description = new DescriptionImpl();
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Fragment createFragment() {
		FragmentImpl fragment = new FragmentImpl();
		return fragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Import createImport() {
		ImportImpl import_ = new ImportImpl();
		return import_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvocationObject createInvocationObject() {
		InvocationObjectImpl invocationObject = new InvocationObjectImpl();
		return invocationObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Label createLabel() {
		LabelImpl label = new LabelImpl();
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Name createName() {
		NameImpl name = new NameImpl();
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Probe createProbe() {
		ProbeImpl probe = new ProbeImpl();
		return probe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Probekit createProbekit() {
		ProbekitImpl probekit = new ProbekitImpl();
		return probekit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaticField createStaticField() {
		StaticFieldImpl staticField = new StaticFieldImpl();
		return staticField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target createTarget() {
		TargetImpl target = new TargetImpl();
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataTypeFromString(EDataType eDataType, String initialValue) {
		DataType result = DataType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FragmentType createFragmentTypeFromString(EDataType eDataType, String initialValue) {
		FragmentType result = FragmentType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFragmentTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createDataTypeFromString(ProbekitPackage.Literals.DATA_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDataTypeToString(ProbekitPackage.Literals.DATA_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FragmentType createFragmentTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createFragmentTypeFromString(ProbekitPackage.Literals.FRAGMENT_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFragmentTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertFragmentTypeToString(ProbekitPackage.Literals.FRAGMENT_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbekitPackage getProbekitPackage() {
		return (ProbekitPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProbekitPackage getPackage() {
		return ProbekitPackage.eINSTANCE;
	}

} //ProbekitFactoryImpl
