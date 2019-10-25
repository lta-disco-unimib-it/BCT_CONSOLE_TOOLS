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
