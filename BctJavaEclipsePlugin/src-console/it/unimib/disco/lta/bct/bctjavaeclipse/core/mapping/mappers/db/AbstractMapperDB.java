package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceDB;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.AbstractMapper;

public abstract class AbstractMapperDB extends AbstractMapper<ResourceDB> {

	protected AbstractMapperDB(ResourceDB resource) {
		super(resource);
	}

}
