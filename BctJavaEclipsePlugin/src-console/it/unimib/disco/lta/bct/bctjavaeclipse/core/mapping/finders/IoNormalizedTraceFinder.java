package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.Collection;

public interface IoNormalizedTraceFinder {

	public Collection<IoNormalizedTrace> findAllTraces() throws MapperException;
	
	public IoNormalizedTrace findTrace(String methodName) throws MapperException;
	
	public IoNormalizedTrace findTrace(Method method) throws MapperException;
	
}
