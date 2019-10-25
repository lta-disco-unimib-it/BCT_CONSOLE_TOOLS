package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoInteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoInteractionRawTraceMapper;

public class IoInteractionRawTraceMapperFile extends AbstractMapperFile
		implements IoInteractionRawTraceMapper {

	IoInteractionRawTraceMapperFile(ResourceFile resource) {
		super(resource);
	}

	public void update(IoInteractionRawTrace entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}


	
}
