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

import java.util.List;

import automata.fsa.FiniteStateAutomaton;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.EFSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.EFSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.EFSAModelsMapper;

public class EFSAModelsMapperFile extends AbstractModelsMapperFile implements
		EFSAModelsMapper {
	
	

	EFSAModelsMapperFile(ResourceFile resource) {
		super(resource);
	}



	public List<EFSAModel> getEFSAModels() {
		throw new RuntimeException("Not Implemented");
	}
	
	

	public EFSAModel getEFSAModel(Method m) {
		throw new RuntimeException("Not Implemented");
	}
	

	public FiniteStateAutomaton getRawEFSA(Method m) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}



	public void update(EFSAModel entity) {
		throw new RuntimeException("Not Implemented");
	}



}
