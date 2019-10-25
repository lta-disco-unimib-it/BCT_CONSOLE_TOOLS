/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctIOModelViolationTest.java,v 1.1 2008-07-07 20:09:33 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.tests;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphFactory;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Bct IO Model Violation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class BctIOModelViolationTest extends BctModelViolationTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(BctIOModelViolationTest.class);
	}

	/**
	 * Constructs a new Bct IO Model Violation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BctIOModelViolationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Bct IO Model Violation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected BctIOModelViolation getFixture() {
		return (BctIOModelViolation)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AnomalyGraphFactory.eINSTANCE.createBctIOModelViolation());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //BctIOModelViolationTest
