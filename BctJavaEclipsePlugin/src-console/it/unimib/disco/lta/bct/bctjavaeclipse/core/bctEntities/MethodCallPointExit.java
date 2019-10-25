package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;

/**
 * This class represent an exit from a method call.
 * 
 * @author fabrizio
 *
 */
public class MethodCallPointExit extends MethodCallPoint {

	public MethodCallPointExit(Method method, String methodCallId, ProgramPointRawLoader programPointRawLoader) {
		super(method, methodCallId, programPointRawLoader, Types.EXIT );
	}



}
