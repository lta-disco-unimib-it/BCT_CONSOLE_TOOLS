/**
 * <copyright>
 * </copyright>
 *
 * $Id: BctFSAModelViolationTest.java,v 1.1 2008-07-07 20:09:33 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.tests;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphFactory;
import it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Bct FSA Model Violation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class BctFSAModelViolationTest extends BctModelViolationTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(BctFSAModelViolationTest.class);
	}

	/**
	 * Constructs a new Bct FSA Model Violation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BctFSAModelViolationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Bct FSA Model Violation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected BctFSAModelViolation getFixture() {
		return (BctFSAModelViolation)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AnomalyGraphFactory.eINSTANCE.createBctFSAModelViolation());
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

} //BctFSAModelViolationTest
