package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

public interface MethodFinder {

	/**
	 * Return the Method object given the signature
	 * 
	 * @param methodSignature
	 * @return
	 */
	public Method getMethod(String methodSignature );
}
