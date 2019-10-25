package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.Loader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

public class ValueHolder<T> {

	private Loader<T> loader;
	private T value = null;
	
	public ValueHolder( Loader<T> loader ){
		this.loader = loader;
	}
	
	public T getValue() throws LoaderException{
		if ( value == null )
			value = loader.load();
		return value;
	}

}
