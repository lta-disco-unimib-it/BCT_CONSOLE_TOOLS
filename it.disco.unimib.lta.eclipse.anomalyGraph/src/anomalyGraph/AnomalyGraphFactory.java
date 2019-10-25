/**
 * <copyright>
 * </copyright>
 *
 * $Id: AnomalyGraphFactory.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package anomalyGraph;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see anomalyGraph.AnomalyGraphPackage
 * @generated
 */
public interface AnomalyGraphFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnomalyGraphFactory eINSTANCE = anomalyGraph.impl.AnomalyGraphFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graph</em>'.
	 * @generated
	 */
	Graph createGraph();

	/**
	 * Returns a new object of class '<em>Bct IO Model Violation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bct IO Model Violation</em>'.
	 * @generated
	 */
	BctIOModelViolation createBctIOModelViolation();

	/**
	 * Returns a new object of class '<em>Bct FSA Model Violation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bct FSA Model Violation</em>'.
	 * @generated
	 */
	BctFSAModelViolation createBctFSAModelViolation();

	/**
	 * Returns a new object of class '<em>Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Relationship</em>'.
	 * @generated
	 */
	Relationship createRelationship();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AnomalyGraphPackage getAnomalyGraphPackage();

} //AnomalyGraphFactory
