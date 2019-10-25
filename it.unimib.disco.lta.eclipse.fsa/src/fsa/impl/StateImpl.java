/**
 * <copyright>
 * </copyright>
 *
 * $Id: StateImpl.java,v 1.1 2008-06-20 19:40:34 terragni Exp $
 */
package fsa.impl;

import fsa.AdditionalStateData;
import fsa.FSA;
import fsa.FsaPackage;
import fsa.State;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fsa.impl.StateImpl#getName <em>Name</em>}</li>
 *   <li>{@link fsa.impl.StateImpl#isFinal <em>Final</em>}</li>
 *   <li>{@link fsa.impl.StateImpl#getFsa <em>Fsa</em>}</li>
 *   <li>{@link fsa.impl.StateImpl#getTemporalProperties <em>Temporal Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends EObjectImpl implements State {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isFinal() <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFinal() <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean final_ = FINAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFsa() <em>Fsa</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFsa()
	 * @generated
	 * @ordered
	 */
	protected FSA fsa;

	/**
	 * The cached value of the '{@link #getTemporalProperties() <em>Temporal Properties</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporalProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<String> temporalProperties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FsaPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsaPackage.STATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFinal() {
		return final_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFinal(boolean newFinal) {
		boolean oldFinal = final_;
		final_ = newFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsaPackage.STATE__FINAL, oldFinal, final_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FSA getFsa() {
		if (fsa != null && fsa.eIsProxy()) {
			InternalEObject oldFsa = (InternalEObject)fsa;
			fsa = (FSA)eResolveProxy(oldFsa);
			if (fsa != oldFsa) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FsaPackage.STATE__FSA, oldFsa, fsa));
			}
		}
		return fsa;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FSA basicGetFsa() {
		return fsa;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFsa(FSA newFsa) {
		FSA oldFsa = fsa;
		fsa = newFsa;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsaPackage.STATE__FSA, oldFsa, fsa));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getTemporalProperties() {
		if (temporalProperties == null) {
			temporalProperties = new EDataTypeUniqueEList<String>(String.class, this, FsaPackage.STATE__TEMPORAL_PROPERTIES);
		}
		return temporalProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FsaPackage.STATE__NAME:
				return getName();
			case FsaPackage.STATE__FINAL:
				return isFinal() ? Boolean.TRUE : Boolean.FALSE;
			case FsaPackage.STATE__FSA:
				if (resolve) return getFsa();
				return basicGetFsa();
			case FsaPackage.STATE__TEMPORAL_PROPERTIES:
				return getTemporalProperties();
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
			case FsaPackage.STATE__NAME:
				setName((String)newValue);
				return;
			case FsaPackage.STATE__FINAL:
				setFinal(((Boolean)newValue).booleanValue());
				return;
			case FsaPackage.STATE__FSA:
				setFsa((FSA)newValue);
				return;
			case FsaPackage.STATE__TEMPORAL_PROPERTIES:
				getTemporalProperties().clear();
				getTemporalProperties().addAll((Collection<? extends String>)newValue);
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
			case FsaPackage.STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FsaPackage.STATE__FINAL:
				setFinal(FINAL_EDEFAULT);
				return;
			case FsaPackage.STATE__FSA:
				setFsa((FSA)null);
				return;
			case FsaPackage.STATE__TEMPORAL_PROPERTIES:
				getTemporalProperties().clear();
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
			case FsaPackage.STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FsaPackage.STATE__FINAL:
				return final_ != FINAL_EDEFAULT;
			case FsaPackage.STATE__FSA:
				return fsa != null;
			case FsaPackage.STATE__TEMPORAL_PROPERTIES:
				return temporalProperties != null && !temporalProperties.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", final: ");
		result.append(final_);
		result.append(", temporalProperties: ");
		result.append(temporalProperties);
		result.append(')');
		return result.toString();
	}

} //StateImpl
