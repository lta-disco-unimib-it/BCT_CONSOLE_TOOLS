package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.Collection;

public interface InteractionNormalizedTraceFinder {

	/**
	 * Returns all the interaction traces for the method monitored during BCT execution
	 * 
	 * @return
	 */
	public Collection<InteractionNormalizedTrace> findAllTraces() throws MapperException;
	
	/**
	 * Return the normalized trace associated to a single method
	 * @param method
	 * @return
	 * @throws MapperException 
	 */
	public InteractionNormalizedTrace findTrace(Method method) throws MapperException;
}
