package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.Collection;

public interface IoRawTraceFinder {

	/**
	 * Returns all the traces recorded
	 * @return
	 * @throws MapperException
	 */
	public Collection<IoRawTrace> findAllTraces() throws MapperException;

	/**
	 * Returns the trace associated with the given method
	 * @param method
	 * @return
	 * @throws MapperException 
	 */
	public IoRawTrace findTrace( Method method ) throws MapperException;

}
