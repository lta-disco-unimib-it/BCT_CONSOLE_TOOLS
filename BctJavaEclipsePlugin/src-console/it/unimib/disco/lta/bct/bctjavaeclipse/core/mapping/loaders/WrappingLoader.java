package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;


/**
 * This loader wraps an object, it does not implement any loading functionality but its load 
 * method just returns the wrapped object.
 * It is useful to store new values in value holders.
 *  
 * @author Fabrizio Pastore
 *
 * @param <T>
 */
public class WrappingLoader<T> implements Loader<T> {

	protected T data;

	public WrappingLoader(T data){
		this.data = data;
	}
	
	public T load() throws LoaderException {
		return data;
	}

}
