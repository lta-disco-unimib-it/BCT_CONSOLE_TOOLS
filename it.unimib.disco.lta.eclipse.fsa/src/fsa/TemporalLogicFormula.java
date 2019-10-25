/**
 * <copyright>
 * </copyright>
 *
 * $Id: TemporalLogicFormula.java,v 1.1 2008-06-20 19:40:34 terragni Exp $
 */
package fsa;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Temporal Logic Formula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fsa.TemporalLogicFormula#getFormula <em>Formula</em>}</li>
 * </ul>
 * </p>
 *
 * @see fsa.FsaPackage#getTemporalLogicFormula()
 * @model
 * @generated
 */
public interface TemporalLogicFormula extends AdditionalFsaData {
	/**
	 * Returns the value of the '<em><b>Formula</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formula</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formula</em>' attribute.
	 * @see #setFormula(String)
	 * @see fsa.FsaPackage#getTemporalLogicFormula_Formula()
	 * @model
	 * @generated
	 */
	String getFormula();

	/**
	 * Sets the value of the '{@link fsa.TemporalLogicFormula#getFormula <em>Formula</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formula</em>' attribute.
	 * @see #getFormula()
	 * @generated
	 */
	void setFormula(String value);

} // TemporalLogicFormula
