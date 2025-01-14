/**
 * <copyright>
 * </copyright>
 *
 * $Id: GraphTest.java,v 1.1 2008-07-07 20:09:34 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph.tests;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphFactory;
import it.unimib.disco.lta.eclipse.anomalyGraph.Graph;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GraphTest extends TestCase {

	/**
	 * The fixture for this Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Graph fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GraphTest.class);
	}

	/**
	 * Constructs a new Graph test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GraphTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Graph fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Graph getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AnomalyGraphFactory.eINSTANCE.createGraph());
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

} //GraphTest
