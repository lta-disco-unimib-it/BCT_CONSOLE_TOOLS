/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
