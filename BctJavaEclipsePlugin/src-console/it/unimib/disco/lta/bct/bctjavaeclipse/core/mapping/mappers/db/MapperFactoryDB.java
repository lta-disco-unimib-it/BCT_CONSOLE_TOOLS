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

import modelsFetchers.ModelsFetcher;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.EFSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionRawTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoInteractionNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoInteractionRawTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ProgramPointsNormalizedFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ProgramPointsRawFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ThreadInfoFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoRawTraceMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.MapperFactory;

public class MapperFactoryDB extends MapperFactory {

	MapperFactoryDB(Resource resource) {
		super(resource);
		
	}

	@Override
	public IoRawTraceMapper getIoRawTraceHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public ProgramPointsRawFinder getProgramPointsRawHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public InteractionRawTraceFinder getInteractionRawTraceHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public IoInteractionRawTraceFinder getIoInteractionRawTraceHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public ProgramPointsNormalizedFinder getProgramPointsNormalizedHandler() {
		throw new RuntimeException("Not Implemented");
	}

	

	public InteractionNormalizedTraceFinder getInteractionNormalizedTraceHandler() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not Implemented");
	}

	public IoInteractionNormalizedTraceFinder getIoInteractionNormalizedTraceHandler() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not Implemented");
	}

	public IoNormalizedTraceFinder getIoNormalizedTraceHandler() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not Implemented");
	}

	public EFSAModelsFinder getEFSAModelsHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public FSAModelsFinder getFSAModelsHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public IoModelsFinder getIoModelsHandler() {
		throw new RuntimeException("Not Implemented");
	}

	public ThreadInfoFinder getThreadInfoHandler() {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public ModelsFetcher getModelsFetcher() {
		throw new RuntimeException("Not Implemented");
	}

}
