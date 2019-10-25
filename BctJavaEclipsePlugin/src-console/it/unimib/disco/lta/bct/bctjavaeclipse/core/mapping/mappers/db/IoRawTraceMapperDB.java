package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceDB;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoRawTraceMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.Collection;


public class IoRawTraceMapperDB extends AbstractMapperDB implements IoRawTraceMapper {

	protected IoRawTraceMapperDB(ResourceDB resource) {
		super(resource);
	}

	public IoRawTrace findTrace(Method method) {
		throw new RuntimeException("Not Implemented");
	}

	public Collection<IoRawTrace> findAllTraces() throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	public void update(IoRawTrace entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}



}
