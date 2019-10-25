/**
 * <copyright>
 * </copyright>
 *
 * $Id: Relationship.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package anomalyGraph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relationship</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link anomalyGraph.Relationship#getFrom <em>From</em>}</li>
 *   <li>{@link anomalyGraph.Relationship#getTo <em>To</em>}</li>
 *   <li>{@link anomalyGraph.Relationship#getWeight <em>Weight</em>}</li>
 * </ul>
 * </p>
 *
 * @see anomalyGraph.AnomalyGraphPackage#getRelationship()
 * @model
 * @generated
 */
public interface Relationship extends EObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(BctModelViolation)
	 * @see anomalyGraph.AnomalyGraphPackage#getRelationship_From()
	 * @model required="true"
	 * @generated
	 */
	BctModelViolation getFrom();

	/**
	 * Sets the value of the '{@link anomalyGraph.Relationship#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(BctModelViolation value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(BctModelViolation)
	 * @see anomalyGraph.AnomalyGraphPackage#getRelationship_To()
	 * @model required="true"
	 * @generated
	 */
	BctModelViolation getTo();

	/**
	 * Sets the value of the '{@link anomalyGraph.Relationship#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(BctModelViolation value);

	/**
	 * Returns the value of the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weight</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weight</em>' attribute.
	 * @see #setWeight(double)
	 * @see anomalyGraph.AnomalyGraphPackage#getRelationship_Weight()
	 * @model
	 * @generated
	 */
	double getWeight();

	/**
	 * Sets the value of the '{@link anomalyGraph.Relationship#getWeight <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weight</em>' attribute.
	 * @see #getWeight()
	 * @generated
	 */
	void setWeight(double value);

} // Relationship
