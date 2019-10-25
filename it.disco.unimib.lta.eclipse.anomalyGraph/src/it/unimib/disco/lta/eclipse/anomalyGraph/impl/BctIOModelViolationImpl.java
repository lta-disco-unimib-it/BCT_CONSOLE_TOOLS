/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctIOModelViolationImpl.java,v 1.1 2008-07-07 20:07:50 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.impl;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bct IO Model Violation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link it.unimib.disco.lta.eclipse.anomalyGraph.impl.BctIOModelViolationImpl#getActualParametersStates <em>Actual Parameters States</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BctIOModelViolationImpl extends BctModelViolationImpl implements BctIOModelViolation {
	/**
	 * The cached value of the '{@link #getActualParametersStates() <em>Actual Parameters States</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualParametersStates()
	 * @generated
	 * @ordered
	 */
	protected EList<String> actualParametersStates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BctIOModelViolationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnomalyGraphPackage.Literals.BCT_IO_MODEL_VIOLATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getActualParametersStates() {
		if (actualParametersStates == null) {
			actualParametersStates = new EDataTypeUniqueEList<String>(String.class, this, AnomalyGraphPackage.BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES);
		}
		return actualParametersStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnomalyGraphPackage.BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES:
				return getActualParametersStates();
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
			case AnomalyGraphPackage.BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES:
				getActualParametersStates().clear();
				getActualParametersStates().addAll((Collection<? extends String>)newValue);
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
			case AnomalyGraphPackage.BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES:
				getActualParametersStates().clear();
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
			case AnomalyGraphPackage.BCT_IO_MODEL_VIOLATION__ACTUAL_PARAMETERS_STATES:
				return actualParametersStates != null && !actualParametersStates.isEmpty();
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
		result.append(" (actualParametersStates: ");
		result.append(actualParametersStates);
		result.append(')');
		return result.toString();
	}

} //BctIOModelViolationImpl
