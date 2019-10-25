package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils;


/**
 * Loader for a lazy list
 * 
 * @author Fabrizio Pastore
 *
 * @param <T>
 */
public interface LazyListElementsLoader<T> {

	/**
	 * Returns true if another element is available
	 * 
	 * @return
	 */
	public boolean hasNext()  throws LazyListElementsLoaderException;
	
	/**
	 * Returns the next available element
	 * 
	 * @return
	 * @throws LazyListElementsLoaderException 
	 */
	public T next() throws LazyListElementsLoaderException;

	/**
	 * Returns how many elements have been loaded
	 * @return
	 */
	public int getLoadedElementsCount();

	/**
	 * Return the total number of elements that this loader will load
	 * 
	 * @return
	 * @throws LazyListElementsLoaderException 
	 */
	public int getTotalElementsCount() throws LazyListElementsLoaderException;
}
