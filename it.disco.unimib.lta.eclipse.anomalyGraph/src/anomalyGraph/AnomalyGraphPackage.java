/**
 * <copyright>
 * </copyright>
 *
 * $Id: AnomalyGraphPackage.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package anomalyGraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see anomalyGraph.AnomalyGraphFactory
 * @model kind="package"
 * @generated
 */
public interface AnomalyGraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "anomalyGraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "it.unimib.disco.lta.eclipse.anomalyGraph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "it.unimib.disco.lta.eclipse.anomalyGraph";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnomalyGraphPackage eINSTANCE = anomalyGraph.impl.AnomalyGraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link anomalyGraph.impl.GraphImpl <em>Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see anomalyGraph.impl.GraphImpl
	 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getGraph()
	 * @generated
	 */
	int GRAPH = 0;

	/**
	 * The feature id for the '<em><b>Violations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__VIOLATIONS = 0;

	/**
	 * The feature id for the '<em><b>Relationships</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__RELATIONSHIPS = 1;

	/**
	 * The number of structural features of the '<em>Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link anomalyGraph.impl.BctModelViolationImpl <em>Bct Model Violation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see anomalyGraph.impl.BctModelViolationImpl
	 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getBctModelViolation()
	 * @generated
	 */
	int BCT_MODEL_VIOLATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Violated Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__VIOLATED_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Violation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__VIOLATION = 2;

	/**
	 * The feature id for the '<em><b>Creation Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__CREATION_TIME = 3;

	/**
	 * The feature id for the '<em><b>Active Actions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__ACTIVE_ACTIONS = 4;

	/**
	 * The feature id for the '<em><b>Active Tests</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__ACTIVE_TESTS = 5;

	/**
	 * The feature id for the '<em><b>Pid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__PID = 6;

	/**
	 * The feature id for the '<em><b>Thread Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__THREAD_ID = 7;

	/**
	 * The feature id for the '<em><b>Stack Trace</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__STACK_TRACE = 8;

	/**
	 * The feature id for the '<em><b>Violation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION__VIOLATION_TYPE = 9;

	/**
	 * The number of structural features of the '<em>Bct Model Violation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_MODEL_VIOLATION_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link anomalyGraph.impl.BctIOModelViolationImpl <em>Bct IO Model Violation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see anomalyGraph.impl.BctIOModelViolationImpl
	 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getBctIOModelViolation()
	 * @generated
	 */
	int BCT_IO_MODEL_VIOLATION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__ID = BCT_MODEL_VIOLATION__ID;

	/**
	 * The feature id for the '<em><b>Violated Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__VIOLATED_MODEL = BCT_MODEL_VIOLATION__VIOLATED_MODEL;

	/**
	 * The feature id for the '<em><b>Violation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__VIOLATION = BCT_MODEL_VIOLATION__VIOLATION;

	/**
	 * The feature id for the '<em><b>Creation Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__CREATION_TIME = BCT_MODEL_VIOLATION__CREATION_TIME;

	/**
	 * The feature id for the '<em><b>Active Actions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__ACTIVE_ACTIONS = BCT_MODEL_VIOLATION__ACTIVE_ACTIONS;

	/**
	 * The feature id for the '<em><b>Active Tests</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__ACTIVE_TESTS = BCT_MODEL_VIOLATION__ACTIVE_TESTS;

	/**
	 * The feature id for the '<em><b>Pid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__PID = BCT_MODEL_VIOLATION__PID;

	/**
	 * The feature id for the '<em><b>Thread Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__THREAD_ID = BCT_MODEL_VIOLATION__THREAD_ID;

	/**
	 * The feature id for the '<em><b>Stack Trace</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__STACK_TRACE = BCT_MODEL_VIOLATION__STACK_TRACE;

	/**
	 * The feature id for the '<em><b>Violation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__VIOLATION_TYPE = BCT_MODEL_VIOLATION__VIOLATION_TYPE;

	/**
	 * The feature id for the '<em><b>Actual Parameters States</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES = BCT_MODEL_VIOLATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bct IO Model Violation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_IO_MODEL_VIOLATION_FEATURE_COUNT = BCT_MODEL_VIOLATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link anomalyGraph.impl.BctFSAModelViolationImpl <em>Bct FSA Model Violation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see anomalyGraph.impl.BctFSAModelViolationImpl
	 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getBctFSAModelViolation()
	 * @generated
	 */
	int BCT_FSA_MODEL_VIOLATION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__ID = BCT_MODEL_VIOLATION__ID;

	/**
	 * The feature id for the '<em><b>Violated Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__VIOLATED_MODEL = BCT_MODEL_VIOLATION__VIOLATED_MODEL;

	/**
	 * The feature id for the '<em><b>Violation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__VIOLATION = BCT_MODEL_VIOLATION__VIOLATION;

	/**
	 * The feature id for the '<em><b>Creation Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__CREATION_TIME = BCT_MODEL_VIOLATION__CREATION_TIME;

	/**
	 * The feature id for the '<em><b>Active Actions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__ACTIVE_ACTIONS = BCT_MODEL_VIOLATION__ACTIVE_ACTIONS;

	/**
	 * The feature id for the '<em><b>Active Tests</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__ACTIVE_TESTS = BCT_MODEL_VIOLATION__ACTIVE_TESTS;

	/**
	 * The feature id for the '<em><b>Pid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__PID = BCT_MODEL_VIOLATION__PID;

	/**
	 * The feature id for the '<em><b>Thread Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__THREAD_ID = BCT_MODEL_VIOLATION__THREAD_ID;

	/**
	 * The feature id for the '<em><b>Stack Trace</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__STACK_TRACE = BCT_MODEL_VIOLATION__STACK_TRACE;

	/**
	 * The feature id for the '<em><b>Violation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__VIOLATION_TYPE = BCT_MODEL_VIOLATION__VIOLATION_TYPE;

	/**
	 * The feature id for the '<em><b>Current State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION__CURRENT_STATE = BCT_MODEL_VIOLATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bct FSA Model Violation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCT_FSA_MODEL_VIOLATION_FEATURE_COUNT = BCT_MODEL_VIOLATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link anomalyGraph.impl.RelationshipImpl <em>Relationship</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see anomalyGraph.impl.RelationshipImpl
	 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getRelationship()
	 * @generated
	 */
	int RELATIONSHIP = 4;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__TO = 1;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__WEIGHT = 2;

	/**
	 * The number of structural features of the '<em>Relationship</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link anomalyGraph.ViolationTypes <em>Violation Types</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see anomalyGraph.ViolationTypes
	 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getViolationTypes()
	 * @generated
	 */
	int VIOLATION_TYPES = 5;


	/**
	 * Returns the meta object for class '{@link anomalyGraph.Graph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph</em>'.
	 * @see anomalyGraph.Graph
	 * @generated
	 */
	EClass getGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link anomalyGraph.Graph#getViolations <em>Violations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Violations</em>'.
	 * @see anomalyGraph.Graph#getViolations()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Violations();

	/**
	 * Returns the meta object for the containment reference list '{@link anomalyGraph.Graph#getRelationships <em>Relationships</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Relationships</em>'.
	 * @see anomalyGraph.Graph#getRelationships()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Relationships();

	/**
	 * Returns the meta object for class '{@link anomalyGraph.BctModelViolation <em>Bct Model Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bct Model Violation</em>'.
	 * @see anomalyGraph.BctModelViolation
	 * @generated
	 */
	EClass getBctModelViolation();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see anomalyGraph.BctModelViolation#getId()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_Id();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getViolatedModel <em>Violated Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Violated Model</em>'.
	 * @see anomalyGraph.BctModelViolation#getViolatedModel()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_ViolatedModel();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getViolation <em>Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Violation</em>'.
	 * @see anomalyGraph.BctModelViolation#getViolation()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_Violation();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getCreationTime <em>Creation Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Time</em>'.
	 * @see anomalyGraph.BctModelViolation#getCreationTime()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_CreationTime();

	/**
	 * Returns the meta object for the attribute list '{@link anomalyGraph.BctModelViolation#getActiveActions <em>Active Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Active Actions</em>'.
	 * @see anomalyGraph.BctModelViolation#getActiveActions()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_ActiveActions();

	/**
	 * Returns the meta object for the attribute list '{@link anomalyGraph.BctModelViolation#getActiveTests <em>Active Tests</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Active Tests</em>'.
	 * @see anomalyGraph.BctModelViolation#getActiveTests()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_ActiveTests();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getPid <em>Pid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pid</em>'.
	 * @see anomalyGraph.BctModelViolation#getPid()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_Pid();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getThreadId <em>Thread Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thread Id</em>'.
	 * @see anomalyGraph.BctModelViolation#getThreadId()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_ThreadId();

	/**
	 * Returns the meta object for the attribute list '{@link anomalyGraph.BctModelViolation#getStackTrace <em>Stack Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Stack Trace</em>'.
	 * @see anomalyGraph.BctModelViolation#getStackTrace()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_StackTrace();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctModelViolation#getViolationType <em>Violation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Violation Type</em>'.
	 * @see anomalyGraph.BctModelViolation#getViolationType()
	 * @see #getBctModelViolation()
	 * @generated
	 */
	EAttribute getBctModelViolation_ViolationType();

	/**
	 * Returns the meta object for class '{@link anomalyGraph.BctIOModelViolation <em>Bct IO Model Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bct IO Model Violation</em>'.
	 * @see anomalyGraph.BctIOModelViolation
	 * @generated
	 */
	EClass getBctIOModelViolation();

	/**
	 * Returns the meta object for the attribute list '{@link anomalyGraph.BctIOModelViolation#getActualParametersStates <em>Actual Parameters States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Actual Parameters States</em>'.
	 * @see anomalyGraph.BctIOModelViolation#getActualParametersStates()
	 * @see #getBctIOModelViolation()
	 * @generated
	 */
	EAttribute getBctIOModelViolation_ActualParametersStates();

	/**
	 * Returns the meta object for class '{@link anomalyGraph.BctFSAModelViolation <em>Bct FSA Model Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bct FSA Model Violation</em>'.
	 * @see anomalyGraph.BctFSAModelViolation
	 * @generated
	 */
	EClass getBctFSAModelViolation();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.BctFSAModelViolation#getCurrentState <em>Current State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current State</em>'.
	 * @see anomalyGraph.BctFSAModelViolation#getCurrentState()
	 * @see #getBctFSAModelViolation()
	 * @generated
	 */
	EAttribute getBctFSAModelViolation_CurrentState();

	/**
	 * Returns the meta object for class '{@link anomalyGraph.Relationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relationship</em>'.
	 * @see anomalyGraph.Relationship
	 * @generated
	 */
	EClass getRelationship();

	/**
	 * Returns the meta object for the reference '{@link anomalyGraph.Relationship#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see anomalyGraph.Relationship#getFrom()
	 * @see #getRelationship()
	 * @generated
	 */
	EReference getRelationship_From();

	/**
	 * Returns the meta object for the reference '{@link anomalyGraph.Relationship#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see anomalyGraph.Relationship#getTo()
	 * @see #getRelationship()
	 * @generated
	 */
	EReference getRelationship_To();

	/**
	 * Returns the meta object for the attribute '{@link anomalyGraph.Relationship#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see anomalyGraph.Relationship#getWeight()
	 * @see #getRelationship()
	 * @generated
	 */
	EAttribute getRelationship_Weight();

	/**
	 * Returns the meta object for enum '{@link anomalyGraph.ViolationTypes <em>Violation Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Violation Types</em>'.
	 * @see anomalyGraph.ViolationTypes
	 * @generated
	 */
	EEnum getViolationTypes();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AnomalyGraphFactory getAnomalyGraphFactory();

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
		 * The meta object literal for the '{@link anomalyGraph.impl.GraphImpl <em>Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see anomalyGraph.impl.GraphImpl
		 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getGraph()
		 * @generated
		 */
		EClass GRAPH = eINSTANCE.getGraph();

		/**
		 * The meta object literal for the '<em><b>Violations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__VIOLATIONS = eINSTANCE.getGraph_Violations();

		/**
		 * The meta object literal for the '<em><b>Relationships</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__RELATIONSHIPS = eINSTANCE.getGraph_Relationships();

		/**
		 * The meta object literal for the '{@link anomalyGraph.impl.BctModelViolationImpl <em>Bct Model Violation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see anomalyGraph.impl.BctModelViolationImpl
		 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getBctModelViolation()
		 * @generated
		 */
		EClass BCT_MODEL_VIOLATION = eINSTANCE.getBctModelViolation();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__ID = eINSTANCE.getBctModelViolation_Id();

		/**
		 * The meta object literal for the '<em><b>Violated Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__VIOLATED_MODEL = eINSTANCE.getBctModelViolation_ViolatedModel();

		/**
		 * The meta object literal for the '<em><b>Violation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__VIOLATION = eINSTANCE.getBctModelViolation_Violation();

		/**
		 * The meta object literal for the '<em><b>Creation Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__CREATION_TIME = eINSTANCE.getBctModelViolation_CreationTime();

		/**
		 * The meta object literal for the '<em><b>Active Actions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__ACTIVE_ACTIONS = eINSTANCE.getBctModelViolation_ActiveActions();

		/**
		 * The meta object literal for the '<em><b>Active Tests</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__ACTIVE_TESTS = eINSTANCE.getBctModelViolation_ActiveTests();

		/**
		 * The meta object literal for the '<em><b>Pid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__PID = eINSTANCE.getBctModelViolation_Pid();

		/**
		 * The meta object literal for the '<em><b>Thread Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__THREAD_ID = eINSTANCE.getBctModelViolation_ThreadId();

		/**
		 * The meta object literal for the '<em><b>Stack Trace</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__STACK_TRACE = eINSTANCE.getBctModelViolation_StackTrace();

		/**
		 * The meta object literal for the '<em><b>Violation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_MODEL_VIOLATION__VIOLATION_TYPE = eINSTANCE.getBctModelViolation_ViolationType();

		/**
		 * The meta object literal for the '{@link anomalyGraph.impl.BctIOModelViolationImpl <em>Bct IO Model Violation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see anomalyGraph.impl.BctIOModelViolationImpl
		 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getBctIOModelViolation()
		 * @generated
		 */
		EClass BCT_IO_MODEL_VIOLATION = eINSTANCE.getBctIOModelViolation();

		/**
		 * The meta object literal for the '<em><b>Actual Parameters States</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES = eINSTANCE.getBctIOModelViolation_ActualParametersStates();

		/**
		 * The meta object literal for the '{@link anomalyGraph.impl.BctFSAModelViolationImpl <em>Bct FSA Model Violation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see anomalyGraph.impl.BctFSAModelViolationImpl
		 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getBctFSAModelViolation()
		 * @generated
		 */
		EClass BCT_FSA_MODEL_VIOLATION = eINSTANCE.getBctFSAModelViolation();

		/**
		 * The meta object literal for the '<em><b>Current State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCT_FSA_MODEL_VIOLATION__CURRENT_STATE = eINSTANCE.getBctFSAModelViolation_CurrentState();

		/**
		 * The meta object literal for the '{@link anomalyGraph.impl.RelationshipImpl <em>Relationship</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see anomalyGraph.impl.RelationshipImpl
		 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getRelationship()
		 * @generated
		 */
		EClass RELATIONSHIP = eINSTANCE.getRelationship();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATIONSHIP__FROM = eINSTANCE.getRelationship_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATIONSHIP__TO = eINSTANCE.getRelationship_To();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATIONSHIP__WEIGHT = eINSTANCE.getRelationship_Weight();

		/**
		 * The meta object literal for the '{@link anomalyGraph.ViolationTypes <em>Violation Types</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see anomalyGraph.ViolationTypes
		 * @see anomalyGraph.impl.AnomalyGraphPackageImpl#getViolationTypes()
		 * @generated
		 */
		EEnum VIOLATION_TYPES = eINSTANCE.getViolationTypes();

	}

} //AnomalyGraphPackage
