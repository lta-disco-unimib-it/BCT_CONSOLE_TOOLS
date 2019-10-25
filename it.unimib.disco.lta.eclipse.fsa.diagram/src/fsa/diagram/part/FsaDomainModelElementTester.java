package fsa.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import fsa.FsaPackage;

/**
 * @generated
 */
public class FsaDomainModelElementTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args,
			Object expectedValue) {
		if (false == receiver instanceof EObject) {
			return false;
		}
		EObject eObject = (EObject) receiver;
		EClass eClass = eObject.eClass();
		if (eClass == FsaPackage.eINSTANCE.getState()) {
			return true;
		}
		if (eClass == FsaPackage.eINSTANCE.getTransition()) {
			return true;
		}
		if (eClass == FsaPackage.eINSTANCE.getFSA()) {
			return true;
		}
		return false;
	}

}
