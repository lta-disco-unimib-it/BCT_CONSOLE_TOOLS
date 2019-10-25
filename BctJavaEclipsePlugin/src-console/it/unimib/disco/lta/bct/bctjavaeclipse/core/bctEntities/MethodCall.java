package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

public class MethodCall extends DomainEntity {

	private Method method;

	public MethodCall( String id, Method method ){
		super(id);
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}
}
