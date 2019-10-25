/**
 * <copyright>
 * </copyright>
 *
 * $Id: FsaFactoryImpl.java,v 1.2 2010-04-15 01:31:32 pastore Exp $
 */
package fsa.impl;

import fsa.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FsaFactoryImpl extends EFactoryImpl implements FsaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FsaFactory init() {
		try {
			FsaFactory theFsaFactory = (FsaFactory)EPackage.Registry.INSTANCE.getEFactory("fsa"); 
			if (theFsaFactory != null) {
				return theFsaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FsaFactoryImpl();
	}


	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FsaFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FsaPackage.STATE: return createState();
			case FsaPackage.TRANSITION: return createTransition();
			case FsaPackage.FSA: return createFSA();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Creates a state that belogs to a given FSA
	 * 
	 * <!-- end-user-doc -->
	 * @generated false
	 */
	public State createState(FSA fsa) {
		StateImpl state = new StateImpl();
		state.setFsa(fsa);
		fsa.getStates().add(state);
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Creates a transition associated to a given FSA
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition(FSA fsa) {
		TransitionImpl transition = new TransitionImpl();
		fsa.getTransitions().add(transition);
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FSA createFSA() {
		FSAImpl fsa = new FSAImpl();
		return fsa;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FsaPackage getFsaPackage() {
		return (FsaPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FsaPackage getPackage() {
		return FsaPackage.eINSTANCE;
	}

} //FsaFactoryImpl
