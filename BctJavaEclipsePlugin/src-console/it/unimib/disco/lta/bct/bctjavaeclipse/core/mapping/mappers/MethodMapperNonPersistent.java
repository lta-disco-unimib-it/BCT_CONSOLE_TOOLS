package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

/**
 * This mapper return Method objects taht are not stored on File/DB, this is used to maintain unicity of methods among a resource.
 * 
 * @author Fabrizio Pastore
 *
 */
public class MethodMapperNonPersistent extends NonPersistentMapper<String, Method> implements MethodMapper {
	
	public Method getMethod(String signature) {
		return getElement(signature);
	}

	protected Method newElement(String signature) {
		return new Method(signature);
	}
}
