package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint.Type;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class ProgramPoint extends DomainEntity {
	public enum Type  { ENTER, EXIT, POINT }
	/**
	 * @uml.annotations  for <code>values</code> 
	 *    collection_type="it.unimib.disco.lta.bct.bctjavaeclipse.core.businessLogic.domainEntities.ProgramPointValue"
	 */
	private HashMap<ProgramPointVariable,ProgramPointValue> values = new HashMap<ProgramPointVariable, ProgramPointValue>();
	private Collection<ProgramPointValue> orderedValues = new ArrayList<ProgramPointValue>();
	
	private Method method;
	private Type type;

	public ProgramPoint(String id, Method method, Type type ) {
		super(id);
		this.type = type;
		this.method = method;
	}
	
	public boolean isEnter(){
		return type == Type.ENTER;
	}

	/**
	 * Returns the name of the program point with the ENTER/EXIT1/POINT specifier.
	 * e.g.
	 * 	myPackage.myClass.myMethod(int,int):::ENTER
	 * 
	 * 	myPackage.myClass.myMethod(int,int):::EXIT1
	 * 
	 * @return
	 */
	public String getProgramPointName(){
		switch ( type ){
		case ENTER:
			return method.getSignature()+":::ENTER";
		case EXIT:
			return method.getSignature()+":::EXIT1";
		case POINT:
			return method.getSignature()+":::POINT";
		}
		return method.getSignature();

	}

	
	/**
	 * Returns the method this program point refers to.
	 * 
	 * @return
	 */
	public Method getMethod(){
		return method;
	}

	/**
	 * Returns the values assciated 
	 * 
	 * @return
	 */
	public List<ProgramPointValue> getProgramPointVariableValues() {
		return new ArrayList<ProgramPointValue>(orderedValues);
	}

	/**
	 * Add a variable value to a program point. Throws a ProhramPointException exception if the value for taht variable was already defined.
	 * 
	 * @param value
	 * @return
	 * @throws ProgramPointException 
	 */
	public ProgramPointValue addVariableValue(ProgramPointValue value) throws ProgramPointException{
		if ( values.containsKey(value.getVariable()) )
			throw new ProgramPointException("Variable has already a value: "+value.getVariable().getName()+" new: "+value.getValue()+" old: "+values.get(value).getValue());
		
		orderedValues.add(value);
		return values.put(value.getVariable(), value);
	}

	public boolean isExit() {
		return type == Type.EXIT;
	}

	public boolean isGeneric() {
		return type == Type.POINT;
	}
	
	public ProgramPointValue removeVariableValue(ProgramPointValue value) {
		return values.remove(value.getVariable());
	}
}
