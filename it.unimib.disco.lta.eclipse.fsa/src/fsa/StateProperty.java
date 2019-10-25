/**
 * <copyright>
 * </copyright>
 *
 * $Id: StateProperty.java,v 1.1 2008-06-20 19:40:34 terragni Exp $
 */
package fsa;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fsa.StateProperty#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see fsa.FsaPackage#getStateProperty()
 * @model
 * @generated
 */
public interface StateProperty extends AdditionalStateData {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see #setProperty(String)
	 * @see fsa.FsaPackage#getStateProperty_Property()
	 * @model
	 * @generated
	 */
	String getProperty();

	/**
	 * Sets the value of the '{@link fsa.StateProperty#getProperty <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(String value);

} // StateProperty
