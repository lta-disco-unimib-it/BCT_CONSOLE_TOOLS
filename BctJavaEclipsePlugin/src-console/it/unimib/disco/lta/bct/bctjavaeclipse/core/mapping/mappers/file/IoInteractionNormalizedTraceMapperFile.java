package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoInteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoInteractionNormalizedTraceMapper;

import java.util.List;

public class IoInteractionNormalizedTraceMapperFile extends AbstractMapperFile
		implements IoInteractionNormalizedTraceMapper {

	IoInteractionNormalizedTraceMapperFile(ResourceFile resource) {
		super(resource);
	}

	public List<IoInteractionNormalizedTrace> findAllTraces() {
		throw new RuntimeException("Not Implemented");
	}

	public IoInteractionNormalizedTrace findTrace(Method method) {
		throw new RuntimeException("Not Implemented");
	}

	public void update(IoInteractionNormalizedTrace entity)
			throws MapperException {
		throw new RuntimeException("Not Implemented");
	}


}
