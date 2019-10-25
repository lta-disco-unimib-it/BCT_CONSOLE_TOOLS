/**
 * <copyright>
 * </copyright>
 *
 * $Id: ViolationTypes.java,v 1.2 2008-07-08 17:56:20 pastore Exp $
 */
package it.unimib.disco.lta.eclipse.anomalyGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Violation Types</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage#getViolationTypes()
 * @model
 * @generated
 */
public enum ViolationTypes implements Enumerator {
	/**
	 * The '<em><b>Unexpected Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNEXPECTED_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	UNEXPECTED_EVENT(0, "UnexpectedEvent", "UnexpectedEvent"),

	/**
	 * The '<em><b>Premature End</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PREMATURE_END_VALUE
	 * @generated
	 * @ordered
	 */
	PREMATURE_END(0, "PrematureEnd", "PrematureEnd"),

	/**
	 * The '<em><b>Parameter Invariant Violated</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PARAMETER_INVARIANT_VIOLATED_VALUE
	 * @generated
	 * @ordered
	 */
	PARAMETER_INVARIANT_VIOLATED(0, "ParameterInvariantViolated", "ParameterInvariantViolated");

	/**
	 * The '<em><b>Unexpected Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unexpected Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNEXPECTED_EVENT
	 * @model name="UnexpectedEvent"
	 * @generated
	 * @ordered
	 */
	public static final int UNEXPECTED_EVENT_VALUE = 0;

	/**
	 * The '<em><b>Premature End</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Premature End</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PREMATURE_END
	 * @model name="PrematureEnd"
	 * @generated
	 * @ordered
	 */
	public static final int PREMATURE_END_VALUE = 0;

	/**
	 * The '<em><b>Parameter Invariant Violated</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Parameter Invariant Violated</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PARAMETER_INVARIANT_VIOLATED
	 * @model name="ParameterInvariantViolated"
	 * @generated
	 * @ordered
	 */
	public static final int PARAMETER_INVARIANT_VIOLATED_VALUE = 0;

	/**
	 * An array of all the '<em><b>Violation Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ViolationTypes[] VALUES_ARRAY =
		new ViolationTypes[] {
			UNEXPECTED_EVENT,
			PREMATURE_END,
			PARAMETER_INVARIANT_VIOLATED,
		};

	/**
	 * A public read-only list of all the '<em><b>Violation Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ViolationTypes> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Violation Types</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ViolationTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ViolationTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Violation Types</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ViolationTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ViolationTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Violation Types</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ViolationTypes get(int value) {
		switch (value) {
			case UNEXPECTED_EVENT_VALUE: return UNEXPECTED_EVENT;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ViolationTypes(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ViolationTypes
