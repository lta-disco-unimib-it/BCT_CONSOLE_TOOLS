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
