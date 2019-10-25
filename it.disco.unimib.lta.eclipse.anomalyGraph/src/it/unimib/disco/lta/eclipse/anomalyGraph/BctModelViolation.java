/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctModelViolation.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bct Model Violation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getId <em>Id</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getViolatedModel <em>Violated Model</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getViolation <em>Violation</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getCreationTime <em>Creation Time</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getActiveActions <em>Active Actions</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getActiveTests <em>Active Tests</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getPid <em>Pid</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getThreadId <em>Thread Id</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getStackTrace <em>Stack Trace</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getViolationType <em>Violation Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation()
 * @model abstract="true"
 * @generated
 */
public interface BctModelViolation extends EObject {
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
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Violated Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Violated Model</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Violated Model</em>' attribute.
	 * @see #setViolatedModel(String)
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_ViolatedModel()
	 * @model
	 * @generated
	 */
	String getViolatedModel();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getViolatedModel <em>Violated Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Violated Model</em>' attribute.
	 * @see #getViolatedModel()
	 * @generated
	 */
	void setViolatedModel(String value);

	/**
	 * Returns the value of the '<em><b>Violation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Violation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Violation</em>' attribute.
	 * @see #setViolation(String)
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_Violation()
	 * @model
	 * @generated
	 */
	String getViolation();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getViolation <em>Violation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Violation</em>' attribute.
	 * @see #getViolation()
	 * @generated
	 */
	void setViolation(String value);

	/**
	 * Returns the value of the '<em><b>Creation Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Time</em>' attribute.
	 * @see #setCreationTime(Date)
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_CreationTime()
	 * @model
	 * @generated
	 */
	Date getCreationTime();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getCreationTime <em>Creation Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Time</em>' attribute.
	 * @see #getCreationTime()
	 * @generated
	 */
	void setCreationTime(Date value);

	/**
	 * Returns the value of the '<em><b>Active Actions</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active Actions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active Actions</em>' attribute list.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_ActiveActions()
	 * @model
	 * @generated
	 */
	EList<String> getActiveActions();

	/**
	 * Returns the value of the '<em><b>Active Tests</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active Tests</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active Tests</em>' attribute list.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_ActiveTests()
	 * @model
	 * @generated
	 */
	EList<String> getActiveTests();

	/**
	 * Returns the value of the '<em><b>Pid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pid</em>' attribute.
	 * @see #setPid(String)
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_Pid()
	 * @model
	 * @generated
	 */
	String getPid();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getPid <em>Pid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pid</em>' attribute.
	 * @see #getPid()
	 * @generated
	 */
	void setPid(String value);

	/**
	 * Returns the value of the '<em><b>Thread Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Id</em>' attribute.
	 * @see #setThreadId(String)
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_ThreadId()
	 * @model
	 * @generated
	 */
	String getThreadId();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getThreadId <em>Thread Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Id</em>' attribute.
	 * @see #getThreadId()
	 * @generated
	 */
	void setThreadId(String value);

	/**
	 * Returns the value of the '<em><b>Stack Trace</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stack Trace</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stack Trace</em>' attribute list.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_StackTrace()
	 * @model
	 * @generated
	 */
	EList<String> getStackTrace();

	/**
	 * Returns the value of the '<em><b>Violation Type</b></em>' attribute.
	 * The literals are from the enumeration {@link it.unimib.disco.lta.eclipse.anomalyGraph.ViolationTypes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Violation Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Violation Type</em>' attribute.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.ViolationTypes
	 * @see #setViolationType(ViolationTypes)
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctModelViolation_ViolationType()
	 * @model
	 * @generated
	 */
	ViolationTypes getViolationType();

	/**
	 * Sets the value of the '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation#getViolationType <em>Violation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Violation Type</em>' attribute.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.ViolationTypes
	 * @see #getViolationType()
	 * @generated
	 */
	void setViolationType(ViolationTypes value);

} // BctModelViolation
