/**
 * <copyright>
 * </copyright>
 *
 * $Id: Graph.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.Graph#getViolations <em>Violations</em>}</li>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.Graph#getRelationships <em>Relationships</em>}</li>
 * </ul>
 * </p>
 *
 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getGraph()
 * @model
 * @generated
 */
public interface Graph extends EObject {
	/**
	 * Returns the value of the '<em><b>Violations</b></em>' containment reference list.
	 * The list contents are of type {@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Violations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Violations</em>' containment reference list.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getGraph_Violations()
	 * @model containment="true"
	 * @generated
	 */
	EList<BctModelViolation> getViolations();

	/**
	 * Returns the value of the '<em><b>Relationships</b></em>' containment reference list.
	 * The list contents are of type {@link it.unimib.disco.lta.eclipse.anomalyGraph.Relationship}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relationships</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relationships</em>' containment reference list.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getGraph_Relationships()
	 * @model containment="true"
	 * @generated
	 */
	EList<Relationship> getRelationships();

} // Graph
