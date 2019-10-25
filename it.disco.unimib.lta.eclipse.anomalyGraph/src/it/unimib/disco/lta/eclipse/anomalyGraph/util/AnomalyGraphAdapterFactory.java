/**
 * <copyright>
 * </copyright>
 *
 * $Id: AnomalyGraphAdapterFactory.java,v 1.1 2008-07-07 20:07:51 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.util;

import it.unimib.disco.lta.eclipse.anomalyGraph.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage
 * @generated
 */
public class AnomalyGraphAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AnomalyGraphPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnomalyGraphAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AnomalyGraphPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnomalyGraphSwitch<Adapter> modelSwitch =
		new AnomalyGraphSwitch<Adapter>() {
			@Override
			public Adapter caseGraph(Graph object) {
				return createGraphAdapter();
			}
			@Override
			public Adapter caseBctModelViolation(BctModelViolation object) {
				return createBctModelViolationAdapter();
			}
			@Override
			public Adapter caseBctIOModelViolation(BctIOModelViolation object) {
				return createBctIOModelViolationAdapter();
			}
			@Override
			public Adapter caseBctFSAModelViolation(BctFSAModelViolation object) {
				return createBctFSAModelViolationAdapter();
			}
			@Override
			public Adapter caseRelationship(Relationship object) {
				return createRelationshipAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link it.unimib.disco.lta.eclipse.anomalyGraph.Graph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.Graph
	 * @generated
	 */
	public Adapter createGraphAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation <em>Bct Model Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation
	 * @generated
	 */
	public Adapter createBctModelViolationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation <em>Bct IO Model Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation
	 * @generated
	 */
	public Adapter createBctIOModelViolationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation <em>Bct FSA Model Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation
	 * @generated
	 */
	public Adapter createBctFSAModelViolationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.unimib.disco.lta.eclipse.anomalyGraph.Relationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.unimib.disco.lta.eclipse.anomalyGraph.Relationship
	 * @generated
	 */
	public Adapter createRelationshipAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AnomalyGraphAdapterFactory
