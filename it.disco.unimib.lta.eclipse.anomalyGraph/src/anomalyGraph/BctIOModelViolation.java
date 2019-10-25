/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctIOModelViolation.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package anomalyGraph;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bct IO Model Violation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link anomalyGraph.BctIOModelViolation#getActualParametersStates <em>Actual Parameters States</em>}</li>
 * </ul>
 * </p>
 *
 * @see anomalyGraph.AnomalyGraphPackage#getBctIOModelViolation()
 * @model
 * @generated
 */
public interface BctIOModelViolation extends BctModelViolation {
	/**
	 * Returns the value of the '<em><b>Actual Parameters States</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual Parameters States</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual Parameters States</em>' attribute list.
	 * @see anomalyGraph.AnomalyGraphPackage#getBctIOModelViolation_ActualParametersStates()
	 * @model
	 * @generated
	 */
	EList<String> getActualParametersStates();

} // BctIOModelViolation
