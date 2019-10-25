package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;



public interface Loader<T> {

	public T load() throws LoaderException;

}
