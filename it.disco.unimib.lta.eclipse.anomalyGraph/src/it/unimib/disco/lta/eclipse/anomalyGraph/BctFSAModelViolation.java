/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctFSAModelViolation.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bct FSA Model Violation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation#getCurrentStates <em>Current States</em>}</li>
 * </ul>
 * </p>
 *
 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctFSAModelViolation()
 * @model
 * @generated
 */
public interface BctFSAModelViolation extends BctModelViolation {
	/**
	 * Returns the value of the '<em><b>Current States</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current States</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current States</em>' attribute list.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getBctFSAModelViolation_CurrentStates()
	 * @model
	 * @generated
	 */
	EList<String> getCurrentStates();

} // BctFSAModelViolation
