package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.DomainEntity;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

/**
 * This interface presents all the methods that Entity Mappers must implement
 * 
 * @author Fabrizio Pastore
 *
 * @param <E>
 */
public interface EntityMapper <E extends DomainEntity> {

	/**
	 * Update a persisted domain entity
	 *  
	 * @param entity
	 * @throws MapperException 
	 */
	public void update ( E entity ) throws MapperException;
}
