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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionRawTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public class MethodCallPointLoader implements Loader<List<MethodCallPoint>> {

	private InteractionRawTraceFinder finder;
	private String traceId;

	/**
	 * Loader of method call point values.
	 * 
	 * @param finder
	 * @param threadId
	 * @param threadId2 
	 */
	public MethodCallPointLoader( InteractionRawTraceFinder finder, String traceId ) {
		this.finder = finder;
		this.traceId = traceId;
	}

	public List<MethodCallPoint> load() throws LoaderException {
		try {
			return finder.findMethodCallPointsForTrace(traceId);
		} catch (MapperException e) {
			throw new LoaderException("Cannot load call points for thread "+traceId,e);
		}
	}

}
