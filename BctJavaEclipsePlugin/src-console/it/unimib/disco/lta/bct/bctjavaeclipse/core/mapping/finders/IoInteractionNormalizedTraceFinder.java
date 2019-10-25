package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoInteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

import java.util.List;

public interface IoInteractionNormalizedTraceFinder {

	/**
	 * Return all the IoInteractionNormalizedTraces of the resource
	 * 
	 * @return
	 */
	public List<IoInteractionNormalizedTrace> findAllTraces();
	
	/**
	 * Return the IoInteractionNormalizedTrace associated to a method
	 * 
	 * @param method
	 * @return
	 */
	public IoInteractionNormalizedTrace findTrace( Method method );
	
	
}
