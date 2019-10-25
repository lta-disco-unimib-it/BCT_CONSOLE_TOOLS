/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctFSAModelViolation.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package anomalyGraph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bct FSA Model Violation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link anomalyGraph.BctFSAModelViolation#getCurrentState <em>Current State</em>}</li>
 * </ul>
 * </p>
 *
 * @see anomalyGraph.AnomalyGraphPackage#getBctFSAModelViolation()
 * @model
 * @generated
 */
public interface BctFSAModelViolation extends BctModelViolation {
	/**
	 * Returns the value of the '<em><b>Current State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current State</em>' attribute.
	 * @see #setCurrentState(String)
	 * @see anomalyGraph.AnomalyGraphPackage#getBctFSAModelViolation_CurrentState()
	 * @model
	 * @generated
	 */
	String getCurrentState();

	/**
	 * Sets the value of the '{@link anomalyGraph.BctFSAModelViolation#getCurrentState <em>Current State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current State</em>' attribute.
	 * @see #getCurrentState()
	 * @generated
	 */
	void setCurrentState(String value);

} // BctFSAModelViolation
