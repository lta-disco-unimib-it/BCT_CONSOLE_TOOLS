/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
