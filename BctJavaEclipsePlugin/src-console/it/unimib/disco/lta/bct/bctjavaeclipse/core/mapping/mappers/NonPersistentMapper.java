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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This absract mapper implements a factory methods that instatiate only one instance for objects identified by a key.
 * @author Fabrizio Pastore
 *
 * @param <K>
 * @param <V>
 */
public abstract class NonPersistentMapper <K,V> {
	private ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K,V>();
	
	protected V getElement(K key){
		V element = map.get(key);
		if ( element == null ){
			element = newElement(key);
			map.put(key, element);
		}
		return element;
	}

	protected abstract V newElement(K key);
	
}
