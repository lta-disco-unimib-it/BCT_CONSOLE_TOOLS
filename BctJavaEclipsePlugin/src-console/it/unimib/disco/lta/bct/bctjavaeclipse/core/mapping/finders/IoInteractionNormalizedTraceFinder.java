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
