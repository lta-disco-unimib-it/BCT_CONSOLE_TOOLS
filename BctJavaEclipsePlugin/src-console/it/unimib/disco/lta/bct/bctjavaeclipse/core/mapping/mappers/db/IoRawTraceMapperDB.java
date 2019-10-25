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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceDB;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoRawTraceMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.Collection;


public class IoRawTraceMapperDB extends AbstractMapperDB implements IoRawTraceMapper {

	protected IoRawTraceMapperDB(ResourceDB resource) {
		super(resource);
	}

	public IoRawTrace findTrace(Method method) {
		throw new RuntimeException("Not Implemented");
	}

	public Collection<IoRawTrace> findAllTraces() throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	public void update(IoRawTrace entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}



}
