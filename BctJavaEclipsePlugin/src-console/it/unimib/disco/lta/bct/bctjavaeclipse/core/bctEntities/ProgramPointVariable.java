package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

/**
 * This class represent a program point variable.
 * 
 * @author Fabrizio Pastore
 *
 */
public class ProgramPointVariable {
	public enum Type { Unknown, Int, Hash, String, Double };
	private String name;
	private Method method;
	private Type type;

	/**
	 * Consructor
	 * 
	 * @param method Method to wich the variable is associated
	 * @param name name of the variable
	 * @param type variable type
	 */
	public ProgramPointVariable(Method method, String name, Type type) {
		this.method = method;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Method getMethod() {
		return method;
	}

	public Type getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
