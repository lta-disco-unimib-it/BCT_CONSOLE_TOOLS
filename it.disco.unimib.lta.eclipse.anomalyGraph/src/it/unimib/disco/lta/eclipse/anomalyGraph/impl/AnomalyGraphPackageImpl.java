/**
 * <copyright>
 * </copyright>
 *
 * $Id: AnomalyGraphPackageImpl.java,v 1.2 2008-07-08 17:56:20 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.impl;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphFactory;
import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation;
import it.unimib.disco.lta.eclipse.anomalyGraph.Graph;
import it.unimib.disco.lta.eclipse.anomalyGraph.Relationship;
import it.unimib.disco.lta.eclipse.anomalyGraph.ViolationTypes;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnomalyGraphPackageImpl extends EPackageImpl implements AnomalyGraphPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass graphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bctModelViolationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bctIOModelViolationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bctFSAModelViolationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationshipEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum violationTypesEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AnomalyGraphPackageImpl() {
		super(eNS_URI, AnomalyGraphFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AnomalyGraphPackage init() {
		if (isInited) return (AnomalyGraphPackage)EPackage.Registry.INSTANCE.getEPackage(AnomalyGraphPackage.eNS_URI);

		// Obtain or create and register package
		AnomalyGraphPackageImpl theAnomalyGraphPackage = (AnomalyGraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof AnomalyGraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new AnomalyGraphPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theAnomalyGraphPackage.createPackageContents();

		// Initialize created meta-data
		theAnomalyGraphPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAnomalyGraphPackage.freeze();

		return theAnomalyGraphPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGraph() {
		return graphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraph_Violations() {
		return (EReference)graphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraph_Relationships() {
		return (EReference)graphEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBctModelViolation() {
		return bctModelViolationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_Id() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_ViolatedModel() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_Violation() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_CreationTime() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_ActiveActions() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_ActiveTests() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_Pid() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_ThreadId() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_StackTrace() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctModelViolation_ViolationType() {
		return (EAttribute)bctModelViolationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBctIOModelViolation() {
		return bctIOModelViolationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctIOModelViolation_ActualParametersStates() {
		return (EAttribute)bctIOModelViolationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBctFSAModelViolation() {
		return bctFSAModelViolationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBctFSAModelViolation_CurrentStates() {
		return (EAttribute)bctFSAModelViolationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelationship() {
		return relationshipEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationship_From() {
		return (EReference)relationshipEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationship_To() {
		return (EReference)relationshipEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRelationship_Weight() {
		return (EAttribute)relationshipEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getViolationTypes() {
		return violationTypesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnomalyGraphFactory getAnomalyGraphFactory() {
		return (AnomalyGraphFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		graphEClass = createEClass(GRAPH);
		createEReference(graphEClass, GRAPH__VIOLATIONS);
		createEReference(graphEClass, GRAPH__RELATIONSHIPS);

		bctModelViolationEClass = createEClass(BCT_MODEL_VIOLATION);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__ID);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__VIOLATED_MODEL);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__VIOLATION);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__CREATION_TIME);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__ACTIVE_ACTIONS);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__ACTIVE_TESTS);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__PID);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__THREAD_ID);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__STACK_TRACE);
		createEAttribute(bctModelViolationEClass, BCT_MODEL_VIOLATION__VIOLATION_TYPE);

		bctIOModelViolationEClass = createEClass(BCT_IO_MODEL_VIOLATION);
		createEAttribute(bctIOModelViolationEClass, BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES);

		bctFSAModelViolationEClass = createEClass(BCT_FSA_MODEL_VIOLATION);
		createEAttribute(bctFSAModelViolationEClass, BCT_FSA_MODEL_VIOLATION__CURRENT_STATES);

		relationshipEClass = createEClass(RELATIONSHIP);
		createEReference(relationshipEClass, RELATIONSHIP__FROM);
		createEReference(relationshipEClass, RELATIONSHIP__TO);
		createEAttribute(relationshipEClass, RELATIONSHIP__WEIGHT);

		// Create enums
		violationTypesEEnum = createEEnum(VIOLATION_TYPES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		bctIOModelViolationEClass.getESuperTypes().add(this.getBctModelViolation());
		bctFSAModelViolationEClass.getESuperTypes().add(this.getBctModelViolation());

		// Initialize classes and features; add operations and parameters
		initEClass(graphEClass, Graph.class, "Graph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGraph_Violations(), this.getBctModelViolation(), null, "violations", null, 0, -1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGraph_Relationships(), this.getRelationship(), null, "relationships", null, 0, -1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bctModelViolationEClass, BctModelViolation.class, "BctModelViolation", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBctModelViolation_Id(), ecorePackage.getEString(), "id", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_ViolatedModel(), ecorePackage.getEString(), "violatedModel", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_Violation(), ecorePackage.getEString(), "violation", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_CreationTime(), ecorePackage.getEDate(), "creationTime", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_ActiveActions(), ecorePackage.getEString(), "activeActions", null, 0, -1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_ActiveTests(), ecorePackage.getEString(), "activeTests", null, 0, -1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_Pid(), ecorePackage.getEString(), "pid", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_ThreadId(), ecorePackage.getEString(), "threadId", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_StackTrace(), ecorePackage.getEString(), "stackTrace", null, 0, -1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBctModelViolation_ViolationType(), this.getViolationTypes(), "violationType", null, 0, 1, BctModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bctIOModelViolationEClass, BctIOModelViolation.class, "BctIOModelViolation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBctIOModelViolation_ActualParametersStates(), ecorePackage.getEString(), "actualParametersStates", null, 0, -1, BctIOModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bctFSAModelViolationEClass, BctFSAModelViolation.class, "BctFSAModelViolation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBctFSAModelViolation_CurrentStates(), ecorePackage.getEString(), "currentStates", null, 0, -1, BctFSAModelViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relationshipEClass, Relationship.class, "Relationship", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelationship_From(), this.getBctModelViolation(), null, "from", null, 1, 1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationship_To(), this.getBctModelViolation(), null, "to", null, 1, 1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationship_Weight(), ecorePackage.getEDouble(), "weight", null, 0, 1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(violationTypesEEnum, ViolationTypes.class, "ViolationTypes");
		addEEnumLiteral(violationTypesEEnum, ViolationTypes.UNEXPECTED_EVENT);
		addEEnumLiteral(violationTypesEEnum, ViolationTypes.PREMATURE_END);
		addEEnumLiteral(violationTypesEEnum, ViolationTypes.PARAMETER_INVARIANT_VIOLATED);

		// Create resource
		createResource(eNS_URI);
	}

} //AnomalyGraphPackageImpl
