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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

public class ProgramPointRawLoader implements Loader<ProgramPointRaw> {

	private ResourceFile resource;
	private String methodCallId;
	private String methodSignature;

	public ProgramPointRawLoader(ResourceFile resource, String methodSignature, String methodCallId) {
		this.resource = resource;
		this.methodCallId = methodCallId;
		this.methodSignature = methodSignature;
	}

	@Override
	public ProgramPointRaw load() throws LoaderException {
		try {
			return resource.getFinderFactory().getProgramPointsRawHandler().find(methodSignature, methodCallId);
		} catch (MapperException e) {
			throw new LoaderException("Problem ivoking finder",e);
		}
		
	}

}
