package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ThreadInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public interface InteractionRawTraceFinder {

	/**
	 * Returns the InteractionRawTrace with the given id
	 * @param traceId
	 * @return
	 * @throws MapperException
	 */
	public InteractionRawTrace findTrace( String traceId ) throws MapperException;
	
	/**
	 * Returns all the InteractionRawTraces recorded in a resource
	 *  
	 * @return
	 * @throws MapperException
	 */
	public List<InteractionRawTrace> findAllTraces() throws MapperException;
	
	/**
	 * Returns the method call points for a given thread
	 * 
	 * @param threadId
	 * @return
	 * @throws MapperException
	 */
	public List<MethodCallPoint> findMethodCallPointsForThread(ThreadInfo thread) throws MapperException;

	public List<MethodCallPoint> findMethodCallPointsForTrace(String traceId) throws MapperException;

	public InteractionRawTrace findTrace(String pid, String threadId) throws MapperException;
	
}
