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
 * $Id: ProbekitPackage.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 **********************************************************************/
 
/*
 * generated using Hyades customized JET templates
 */

package org.eclipse.hyades.models.internal.probekit;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 *      See http://www.w3.org/XML/1998/namespace.html and     http://www.w3.org/TR/REC-xml for information about this namespace.        This schema document describes the XML namespace, in a form      suitable for import by other schema documents.          Note that local names in this namespace are intended to be defined      only by the World Wide Web Consortium or its subgroups.  The      following names are currently defined in this namespace and should      not be used with conflicting semantics by any Working Group,      specification, or document instance:        base (as an attribute name): denotes an attribute whose value           provides a URI to be used as the base for interpreting any           relative URIs in the scope of the element on which it           appears; its value is inherited.  This name is reserved           by virtue of its definition in the XML Base specification.        id   (as an attribute name): denotes an attribute whose value           should be interpreted as if declared to be of type ID.           The xml:id specification is not yet a W3C Recommendation,           but this attribute is included here to facilitate experimentation           with the mechanisms it proposes.  Note that it is _not_ included           in the specialAttrs attribute group.        lang (as an attribute name): denotes an attribute whose value           is a language code for the natural language of the content of           any element; its value is inherited.  This name is reserved           by virtue of its definition in the XML specification.          space (as an attribute name): denotes an attribute whose           value is a keyword indicating what whitespace processing           discipline is intended for the content of the element; its           value is inherited.  This name is reserved by virtue of its           definition in the XML specification.        Father (in any context at all): denotes Jon Bosak, the chair of            the original XML Working Group.  This name is reserved by            the following decision of the W3C XML Plenary and            XML Coordination groups:                 In appreciation for his vision, leadership and dedication               the W3C XML Plenary on this 10th day of February, 2000               reserves for Jon Bosak in perpetuity the XML name               xml:Father      This schema defines attributes and an attribute group          suitable for use by          schemas wishing to allow xml:base, xml:lang or xml:space attributes          on elements they define.            To enable this, such a schema must import this schema          for the XML namespace, e.g. as follows:          &lt;schema . . .&gt;           . . .           &lt;import namespace="http://www.w3.org/XML/1998/namespace"                      schemaLocation="http://www.w3.org/2001/03/xml.xsd"/&gt;            Subsequently, qualified reference to any of the attributes          or the group defined below will have the desired effect, e.g.            &lt;type . . .&gt;           . . .           &lt;attributeGroup ref="xml:specialAttrs"/&gt;              will define a type which will schema-validate an instance           element with any of those attributes  In keeping with the XML Schema WG's standard versioning     policy, this schema document will persist at     http://www.w3.org/2004/10/xml.xsd.     At the date of issue it can also be found at     http://www.w3.org/2001/xml.xsd.     The schema document at that URI may however change in the future,     in order to remain compatible with the latest version of XML Schema     itself, or with the XML namespace itself.  In other words, if the XML     Schema or XML namespaces change, the version of this document at     http://www.w3.org/2001/xml.xsd will change     accordingly; the version at     http://www.w3.org/2004/10/xml.xsd will not change.    
 * <!-- end-model-doc -->
 * @see org.eclipse.hyades.models.internal.probekit.ProbekitFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface ProbekitPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "probekit";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.eclipse.tptp.platform.models/src-probekit/model/probekit.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "probekit";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProbekitPackage eINSTANCE = org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ControlKeyImpl <em>Control Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ControlKeyImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getControlKey()
	 * @generated
	 */
	int CONTROL_KEY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_KEY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_KEY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Control Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_KEY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ControlNameImpl <em>Control Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ControlNameImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getControlName()
	 * @generated
	 */
	int CONTROL_NAME = 1;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_NAME__LANG = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_NAME__TEXT = 1;

	/**
	 * The number of structural features of the '<em>Control Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_NAME_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.DataItemImpl <em>Data Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.DataItemImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDataItem()
	 * @generated
	 */
	int DATA_ITEM = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ITEM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ITEM__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Data Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ITEM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.DescriptionImpl <em>Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.DescriptionImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDescription()
	 * @generated
	 */
	int DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__LANG = 1;

	/**
	 * The number of structural features of the '<em>Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 4;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CODE = 3;

	/**
	 * The feature id for the '<em><b>Control Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONTROL_KEY = 4;

	/**
	 * The feature id for the '<em><b>Control Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONTROL_NAME = 5;

	/**
	 * The feature id for the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DESCRIPTION = 7;

	/**
	 * The feature id for the '<em><b>Fragment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FRAGMENT = 8;

	/**
	 * The feature id for the '<em><b>Fragment At Class Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE = 9;

	/**
	 * The feature id for the '<em><b>Fragment At Shared Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE = 10;

	/**
	 * The feature id for the '<em><b>Import</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPORT = 11;

	/**
	 * The feature id for the '<em><b>Invocation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INVOCATION_OBJECT = 12;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LABEL = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME = 14;

	/**
	 * The feature id for the '<em><b>Probe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROBE = 15;

	/**
	 * The feature id for the '<em><b>Probekit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROBEKIT = 16;

	/**
	 * The feature id for the '<em><b>Static Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATIC_FIELD = 17;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TARGET = 18;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.FragmentImpl <em>Fragment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.FragmentImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getFragment()
	 * @generated
	 */
	int FRAGMENT = 5;

	/**
	 * The feature id for the '<em><b>Data</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAGMENT__DATA = 0;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAGMENT__CODE = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAGMENT__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAGMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ImportImpl <em>Import</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ImportImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getImport()
	 * @generated
	 */
	int IMPORT = 6;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT__TEXT = 0;

	/**
	 * The number of structural features of the '<em>Import</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.InvocationObjectImpl <em>Invocation Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.InvocationObjectImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getInvocationObject()
	 * @generated
	 */
	int INVOCATION_OBJECT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION_OBJECT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Invocation Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.LabelImpl <em>Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.LabelImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 8;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__LANG = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__NAME = 2;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.NameImpl <em>Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.NameImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getName_()
	 * @generated
	 */
	int NAME = 9;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__LANG = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__TEXT = 1;

	/**
	 * The number of structural features of the '<em>Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl <em>Probe</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getProbe()
	 * @generated
	 */
	int PROBE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Control Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__CONTROL_KEY = 2;

	/**
	 * The feature id for the '<em><b>Control Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__CONTROL_NAME = 3;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__TARGET = 4;

	/**
	 * The feature id for the '<em><b>Import</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__IMPORT = 5;

	/**
	 * The feature id for the '<em><b>Fragment At Class Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__FRAGMENT_AT_CLASS_SCOPE = 6;

	/**
	 * The feature id for the '<em><b>Fragment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__FRAGMENT = 7;

	/**
	 * The feature id for the '<em><b>Invocation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__INVOCATION_OBJECT = 8;

	/**
	 * The feature id for the '<em><b>Static Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__STATIC_FIELD = 9;

	/**
	 * The number of structural features of the '<em>Probe</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl <em>Probekit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getProbekit()
	 * @generated
	 */
	int PROBEKIT = 11;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBEKIT__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Probe</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBEKIT__PROBE = 1;

	/**
	 * The feature id for the '<em><b>Fragment At Shared Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBEKIT__FRAGMENT_AT_SHARED_SCOPE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBEKIT__ID = 3;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBEKIT__VERSION = 4;

	/**
	 * The number of structural features of the '<em>Probekit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBEKIT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.StaticFieldImpl <em>Static Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.StaticFieldImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getStaticField()
	 * @generated
	 */
	int STATIC_FIELD = 12;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FIELD__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Static Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FIELD_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.impl.TargetImpl
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 13;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__CLASS_NAME = 0;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__METHOD = 1;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__PACKAGE = 2;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__SIGNATURE = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.DataType <em>Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.DataType
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.hyades.models.internal.probekit.FragmentType <em>Fragment Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.hyades.models.internal.probekit.FragmentType
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getFragmentType()
	 * @generated
	 */
	int FRAGMENT_TYPE = 15;

	/**
	 * The meta object id for the '<em>Data Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.AbstractEnumerator
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDataTypeObject()
	 * @generated
	 */
	int DATA_TYPE_OBJECT = 16;

	/**
	 * The meta object id for the '<em>Fragment Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.AbstractEnumerator
	 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getFragmentTypeObject()
	 * @generated
	 */
	int FRAGMENT_TYPE_OBJECT = 17;


	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.ControlKey <em>Control Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control Key</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.ControlKey
	 * @generated
	 */
	EClass getControlKey();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.ControlKey#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.ControlKey#getName()
	 * @see #getControlKey()
	 * @generated
	 */
	EAttribute getControlKey_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.ControlKey#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.ControlKey#getValue()
	 * @see #getControlKey()
	 * @generated
	 */
	EAttribute getControlKey_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.ControlName <em>Control Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.ControlName
	 * @generated
	 */
	EClass getControlName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.ControlName#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.ControlName#getLang()
	 * @see #getControlName()
	 * @generated
	 */
	EAttribute getControlName_Lang();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.ControlName#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.ControlName#getText()
	 * @see #getControlName()
	 * @generated
	 */
	EAttribute getControlName_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.DataItem <em>Data Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Item</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DataItem
	 * @generated
	 */
	EClass getDataItem();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.DataItem#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DataItem#getName()
	 * @see #getDataItem()
	 * @generated
	 */
	EAttribute getDataItem_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.DataItem#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DataItem#getType()
	 * @see #getDataItem()
	 * @generated
	 */
	EAttribute getDataItem_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Description <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Description</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Description
	 * @generated
	 */
	EClass getDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Description#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Description#getValue()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Description#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Description#getLang()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Lang();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getCode()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Code();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlKey <em>Control Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Control Key</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlKey()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ControlKey();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlName <em>Control Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Control Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getControlName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ControlName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getData()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Data();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Description</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getDescription()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Description();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragment <em>Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fragment</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragment()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Fragment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtClassScope <em>Fragment At Class Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment At Class Scope</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtClassScope()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_FragmentAtClassScope();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment At Shared Scope</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getFragmentAtSharedScope()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_FragmentAtSharedScope();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getImport <em>Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Import</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getImport()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Import();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getInvocationObject <em>Invocation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invocation Object</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getInvocationObject()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_InvocationObject();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getLabel()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Label();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbe <em>Probe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Probe</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbe()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Probe();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbekit <em>Probekit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Probekit</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getProbekit()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Probekit();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getStaticField <em>Static Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Static Field</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getStaticField()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_StaticField();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.DocumentRoot#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DocumentRoot#getTarget()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Fragment <em>Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fragment</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Fragment
	 * @generated
	 */
	EClass getFragment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Fragment#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Fragment#getData()
	 * @see #getFragment()
	 * @generated
	 */
	EReference getFragment_Data();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Fragment#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Fragment#getCode()
	 * @see #getFragment()
	 * @generated
	 */
	EAttribute getFragment_Code();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Fragment#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Fragment#getType()
	 * @see #getFragment()
	 * @generated
	 */
	EAttribute getFragment_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Import <em>Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Import
	 * @generated
	 */
	EClass getImport();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Import#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Import#getText()
	 * @see #getImport()
	 * @generated
	 */
	EAttribute getImport_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.InvocationObject <em>Invocation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Invocation Object</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.InvocationObject
	 * @generated
	 */
	EClass getInvocationObject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.InvocationObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.InvocationObject#getName()
	 * @see #getInvocationObject()
	 * @generated
	 */
	EAttribute getInvocationObject_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Label <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Label
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Label#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Label#getDescription()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Label#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Label#getLang()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Lang();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Label#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Label#getName()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Name <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Name
	 * @generated
	 */
	EClass getName_();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Name#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Name#getLang()
	 * @see #getName_()
	 * @generated
	 */
	EAttribute getName_Lang();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Name#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Name#getText()
	 * @see #getName_()
	 * @generated
	 */
	EAttribute getName_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Probe <em>Probe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probe</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe
	 * @generated
	 */
	EClass getProbe();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probe#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getName()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probe#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getDescription()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_Description();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.Probe#getControlKey <em>Control Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Control Key</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getControlKey()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_ControlKey();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probe#getControlName <em>Control Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Control Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getControlName()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_ControlName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probe#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Target</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getTarget()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_Target();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probe#getImport <em>Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Import</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getImport()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_Import();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Probe#getFragmentAtClassScope <em>Fragment At Class Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment At Class Scope</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getFragmentAtClassScope()
	 * @see #getProbe()
	 * @generated
	 */
	EAttribute getProbe_FragmentAtClassScope();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probe#getFragment <em>Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fragment</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getFragment()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_Fragment();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.Probe#getInvocationObject <em>Invocation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invocation Object</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getInvocationObject()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_InvocationObject();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.hyades.models.internal.probekit.Probe#getStaticField <em>Static Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Static Field</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probe#getStaticField()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_StaticField();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Probekit <em>Probekit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probekit</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probekit
	 * @generated
	 */
	EClass getProbekit();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Label</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probekit#getLabel()
	 * @see #getProbekit()
	 * @generated
	 */
	EReference getProbekit_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getProbe <em>Probe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Probe</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probekit#getProbe()
	 * @see #getProbekit()
	 * @generated
	 */
	EReference getProbekit_Probe();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getFragmentAtSharedScope <em>Fragment At Shared Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment At Shared Scope</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probekit#getFragmentAtSharedScope()
	 * @see #getProbekit()
	 * @generated
	 */
	EAttribute getProbekit_FragmentAtSharedScope();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probekit#getId()
	 * @see #getProbekit()
	 * @generated
	 */
	EAttribute getProbekit_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Probekit#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Probekit#getVersion()
	 * @see #getProbekit()
	 * @generated
	 */
	EAttribute getProbekit_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.StaticField <em>Static Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Field</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.StaticField
	 * @generated
	 */
	EClass getStaticField();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.StaticField#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.StaticField#getType()
	 * @see #getStaticField()
	 * @generated
	 */
	EAttribute getStaticField_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.hyades.models.internal.probekit.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Target
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Target#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Target#getClassName()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_ClassName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Target#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Target#getMethod()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Method();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Target#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Target#getPackage()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Package();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Target#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signature</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Target#getSignature()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Signature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.hyades.models.internal.probekit.Target#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.Target#getType()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Type();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.hyades.models.internal.probekit.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.DataType
	 * @generated
	 */
	EEnum getDataType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.hyades.models.internal.probekit.FragmentType <em>Fragment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Fragment Type</em>'.
	 * @see org.eclipse.hyades.models.internal.probekit.FragmentType
	 * @generated
	 */
	EEnum getFragmentType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.AbstractEnumerator <em>Data Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Data Type Object</em>'.
	 * @see org.eclipse.emf.common.util.AbstractEnumerator
	 * @model instanceClass="org.eclipse.emf.common.util.AbstractEnumerator"
	 *        extendedMetaData="name='dataType:Object' baseType='dataType'"
	 * @generated
	 */
	EDataType getDataTypeObject();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.AbstractEnumerator <em>Fragment Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Fragment Type Object</em>'.
	 * @see org.eclipse.emf.common.util.AbstractEnumerator
	 * @model instanceClass="org.eclipse.emf.common.util.AbstractEnumerator"
	 *        extendedMetaData="name='fragmentType:Object' baseType='fragmentType'"
	 * @generated
	 */
	EDataType getFragmentTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProbekitFactory getProbekitFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ControlKeyImpl <em>Control Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ControlKeyImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getControlKey()
		 * @generated
		 */
		EClass CONTROL_KEY = eINSTANCE.getControlKey();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTROL_KEY__NAME = eINSTANCE.getControlKey_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTROL_KEY__VALUE = eINSTANCE.getControlKey_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ControlNameImpl <em>Control Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ControlNameImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getControlName()
		 * @generated
		 */
		EClass CONTROL_NAME = eINSTANCE.getControlName();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTROL_NAME__LANG = eINSTANCE.getControlName_Lang();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTROL_NAME__TEXT = eINSTANCE.getControlName_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.DataItemImpl <em>Data Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.DataItemImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDataItem()
		 * @generated
		 */
		EClass DATA_ITEM = eINSTANCE.getDataItem();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_ITEM__NAME = eINSTANCE.getDataItem_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_ITEM__TYPE = eINSTANCE.getDataItem_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.DescriptionImpl <em>Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.DescriptionImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDescription()
		 * @generated
		 */
		EClass DESCRIPTION = eINSTANCE.getDescription();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__VALUE = eINSTANCE.getDescription_Value();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__LANG = eINSTANCE.getDescription_Lang();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.DocumentRootImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CODE = eINSTANCE.getDocumentRoot_Code();

		/**
		 * The meta object literal for the '<em><b>Control Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CONTROL_KEY = eINSTANCE.getDocumentRoot_ControlKey();

		/**
		 * The meta object literal for the '<em><b>Control Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CONTROL_NAME = eINSTANCE.getDocumentRoot_ControlName();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DATA = eINSTANCE.getDocumentRoot_Data();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DESCRIPTION = eINSTANCE.getDocumentRoot_Description();

		/**
		 * The meta object literal for the '<em><b>Fragment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FRAGMENT = eINSTANCE.getDocumentRoot_Fragment();

		/**
		 * The meta object literal for the '<em><b>Fragment At Class Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__FRAGMENT_AT_CLASS_SCOPE = eINSTANCE.getDocumentRoot_FragmentAtClassScope();

		/**
		 * The meta object literal for the '<em><b>Fragment At Shared Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__FRAGMENT_AT_SHARED_SCOPE = eINSTANCE.getDocumentRoot_FragmentAtSharedScope();

		/**
		 * The meta object literal for the '<em><b>Import</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IMPORT = eINSTANCE.getDocumentRoot_Import();

		/**
		 * The meta object literal for the '<em><b>Invocation Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__INVOCATION_OBJECT = eINSTANCE.getDocumentRoot_InvocationObject();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LABEL = eINSTANCE.getDocumentRoot_Label();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NAME = eINSTANCE.getDocumentRoot_Name();

		/**
		 * The meta object literal for the '<em><b>Probe</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROBE = eINSTANCE.getDocumentRoot_Probe();

		/**
		 * The meta object literal for the '<em><b>Probekit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROBEKIT = eINSTANCE.getDocumentRoot_Probekit();

		/**
		 * The meta object literal for the '<em><b>Static Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STATIC_FIELD = eINSTANCE.getDocumentRoot_StaticField();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TARGET = eINSTANCE.getDocumentRoot_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.FragmentImpl <em>Fragment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.FragmentImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getFragment()
		 * @generated
		 */
		EClass FRAGMENT = eINSTANCE.getFragment();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FRAGMENT__DATA = eINSTANCE.getFragment_Data();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FRAGMENT__CODE = eINSTANCE.getFragment_Code();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FRAGMENT__TYPE = eINSTANCE.getFragment_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ImportImpl <em>Import</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ImportImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getImport()
		 * @generated
		 */
		EClass IMPORT = eINSTANCE.getImport();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT__TEXT = eINSTANCE.getImport_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.InvocationObjectImpl <em>Invocation Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.InvocationObjectImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getInvocationObject()
		 * @generated
		 */
		EClass INVOCATION_OBJECT = eINSTANCE.getInvocationObject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVOCATION_OBJECT__NAME = eINSTANCE.getInvocationObject_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.LabelImpl <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.LabelImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL__DESCRIPTION = eINSTANCE.getLabel_Description();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL__LANG = eINSTANCE.getLabel_Lang();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL__NAME = eINSTANCE.getLabel_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.NameImpl <em>Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.NameImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getName_()
		 * @generated
		 */
		EClass NAME = eINSTANCE.getName_();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME__LANG = eINSTANCE.getName_Lang();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME__TEXT = eINSTANCE.getName_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl <em>Probe</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbeImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getProbe()
		 * @generated
		 */
		EClass PROBE = eINSTANCE.getProbe();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__NAME = eINSTANCE.getProbe_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__DESCRIPTION = eINSTANCE.getProbe_Description();

		/**
		 * The meta object literal for the '<em><b>Control Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__CONTROL_KEY = eINSTANCE.getProbe_ControlKey();

		/**
		 * The meta object literal for the '<em><b>Control Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__CONTROL_NAME = eINSTANCE.getProbe_ControlName();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__TARGET = eINSTANCE.getProbe_Target();

		/**
		 * The meta object literal for the '<em><b>Import</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__IMPORT = eINSTANCE.getProbe_Import();

		/**
		 * The meta object literal for the '<em><b>Fragment At Class Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBE__FRAGMENT_AT_CLASS_SCOPE = eINSTANCE.getProbe_FragmentAtClassScope();

		/**
		 * The meta object literal for the '<em><b>Fragment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__FRAGMENT = eINSTANCE.getProbe_Fragment();

		/**
		 * The meta object literal for the '<em><b>Invocation Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__INVOCATION_OBJECT = eINSTANCE.getProbe_InvocationObject();

		/**
		 * The meta object literal for the '<em><b>Static Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__STATIC_FIELD = eINSTANCE.getProbe_StaticField();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl <em>Probekit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getProbekit()
		 * @generated
		 */
		EClass PROBEKIT = eINSTANCE.getProbekit();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBEKIT__LABEL = eINSTANCE.getProbekit_Label();

		/**
		 * The meta object literal for the '<em><b>Probe</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBEKIT__PROBE = eINSTANCE.getProbekit_Probe();

		/**
		 * The meta object literal for the '<em><b>Fragment At Shared Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBEKIT__FRAGMENT_AT_SHARED_SCOPE = eINSTANCE.getProbekit_FragmentAtSharedScope();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBEKIT__ID = eINSTANCE.getProbekit_Id();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBEKIT__VERSION = eINSTANCE.getProbekit_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.StaticFieldImpl <em>Static Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.StaticFieldImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getStaticField()
		 * @generated
		 */
		EClass STATIC_FIELD = eINSTANCE.getStaticField();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATIC_FIELD__TYPE = eINSTANCE.getStaticField_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.impl.TargetImpl
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__CLASS_NAME = eINSTANCE.getTarget_ClassName();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__METHOD = eINSTANCE.getTarget_Method();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__PACKAGE = eINSTANCE.getTarget_Package();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__SIGNATURE = eINSTANCE.getTarget_Signature();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__TYPE = eINSTANCE.getTarget_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.DataType <em>Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.DataType
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDataType()
		 * @generated
		 */
		EEnum DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.hyades.models.internal.probekit.FragmentType <em>Fragment Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.hyades.models.internal.probekit.FragmentType
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getFragmentType()
		 * @generated
		 */
		EEnum FRAGMENT_TYPE = eINSTANCE.getFragmentType();

		/**
		 * The meta object literal for the '<em>Data Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.AbstractEnumerator
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getDataTypeObject()
		 * @generated
		 */
		EDataType DATA_TYPE_OBJECT = eINSTANCE.getDataTypeObject();

		/**
		 * The meta object literal for the '<em>Fragment Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.AbstractEnumerator
		 * @see org.eclipse.hyades.models.internal.probekit.impl.ProbekitPackageImpl#getFragmentTypeObject()
		 * @generated
		 */
		EDataType FRAGMENT_TYPE_OBJECT = eINSTANCE.getFragmentTypeObject();

	}

} //ProbekitPackage
