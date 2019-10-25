package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;

public abstract class AbstractMapper<T extends Resource> {
	protected T resource;
	
	/**
	 * Create a Mapper and associate a resource facade to it. The Facade is useful because permit to interact with native BCT low level
	 * methods
	 *  
	 * @param resource
	 */
	protected AbstractMapper( T resource ){
		this.resource = resource;
	}

}
