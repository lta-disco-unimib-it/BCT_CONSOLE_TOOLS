package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.DomainEntity;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FinderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

public interface Resource {

	public FinderFactory getFinderFactory();

	public void saveEntity( DomainEntity entity ) throws MapperException;

	public String getName();

}
