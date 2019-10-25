package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;

/**
 * This class represent a point of execution of a method. It is the basic block of an interaction trace.
 * 
 * @author Fabrizio Pastore
 *
 */
public abstract class MethodCallPoint {

	private Method method;
	private String methodCallId;
	private ProgramPointRawLoader programPointRawLoader;

	public enum Types { ENTER, EXIT, GENERIC }
	private Types type;
	
	/**
	 * Constructor
	 * 
	 * @param method the method this call point refers to
	 * @param methodCallId 
	 * @param programPointRawLoader 
	 */
	public MethodCallPoint( Method method, String methodCallId, ProgramPointRawLoader programPointRawLoader, Types type ){
		this.method = method;
		this.methodCallId = methodCallId;
		this.programPointRawLoader = programPointRawLoader;
		this.type = type;
	}

	public Method getMethod() {
		return method;
	}

	public String getMethodCallId() {
		return methodCallId;
	}
	
	public final boolean isExit() {
		return type == Types.EXIT;
	}

	public final boolean isEnter(){
		return type == Types.ENTER;
	}
	
	public ProgramPoint getCorrespondingProgramPoint() {
		try {
			return programPointRawLoader.load();
		} catch (LoaderException e) {
			return null;
		}
	}

	public final boolean isGeneric() {
		return type == Types.GENERIC;
	}
}
