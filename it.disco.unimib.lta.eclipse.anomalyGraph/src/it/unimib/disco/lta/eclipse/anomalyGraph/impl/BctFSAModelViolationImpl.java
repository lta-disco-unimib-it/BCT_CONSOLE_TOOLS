/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctFSAModelViolationImpl.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.impl;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bct FSA Model Violation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.impl.BctFSAModelViolationImpl#getCurrentStates <em>Current States</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BctFSAModelViolationImpl extends BctModelViolationImpl implements BctFSAModelViolation {
	/**
	 * The cached value of the '{@link #getCurrentStates() <em>Current States</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentStates()
	 * @generated
	 * @ordered
	 */
	protected EList<String> currentStates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BctFSAModelViolationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnomalyGraphPackage.Literals.BCT_FSA_MODEL_VIOLATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getCurrentStates() {
		if (currentStates == null) {
			currentStates = new EDataTypeUniqueEList<String>(String.class, this, AnomalyGraphPackage.BCT_FSA_MODEL_VIOLATION__CURRENT_STATES);
		}
		return currentStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_FSA_MODEL_VIOLATION__CURRENT_STATES:
				return getCurrentStates();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_FSA_MODEL_VIOLATION__CURRENT_STATES:
				getCurrentStates().clear();
				getCurrentStates().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_FSA_MODEL_VIOLATION__CURRENT_STATES:
				getCurrentStates().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_FSA_MODEL_VIOLATION__CURRENT_STATES:
				return currentStates != null && !currentStates.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (currentStates: ");
		result.append(currentStates);
		result.append(')');
		return result.toString();
	}

} //BctFSAModelViolationImpl
