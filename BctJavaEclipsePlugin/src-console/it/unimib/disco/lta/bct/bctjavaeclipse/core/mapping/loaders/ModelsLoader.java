package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

public abstract class ModelsLoader<T> implements Loader<T> {

	private Method method;

	public ModelsLoader( Method method ) {
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}
}
