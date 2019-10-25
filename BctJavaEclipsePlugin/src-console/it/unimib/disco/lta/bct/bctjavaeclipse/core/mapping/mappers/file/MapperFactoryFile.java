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

import modelsFetchers.ModelsFetcher;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.DomainEntity;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoRawTraceMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.MapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.ThreadInfoMapper;

public class MapperFactoryFile extends MapperFactory<ResourceFile> {

	private IoRawTraceMapperFile ioRawTraceMapper;
	private ProgramPointsRawFinder rawProgramPointsMapper;
	private InteractionRawTraceMapperFile interactionRawTraceMapper;
	private IoInteractionRawTraceMapperFile ioInteractionRawTraceMapper;
	private IoNormalizedTraceMapperFile ioNormalizedTraceMapper;
	private InteractionNormalizedTraceMapperFile interactionNormalizedTraceMapper;
	private IoInteractionNormalizedTraceMapperFile ioInteractionNormalizedTraceMapper;
	private ProgramPointsNormalizedFinder programPointsNormalizedMapper;
	private IoModelsMapperFile ioModelsHandler;
	private FSAModelsMapperFile fsaModelsHandler;
	private EFSAModelsMapperFile efsaModelsHandler;
	private ThreadInfoMapper threadInfoMapper;
	
	public MapperFactoryFile(ResourceFile resource) {
		super(resource);
		
		rawProgramPointsMapper = new ProgramPointRawMapperFile(resource);
		programPointsNormalizedMapper = new ProgramPointsNormalizedMapperFile(resource);
		
		ioRawTraceMapper = new IoRawTraceMapperFile(resource);
		interactionRawTraceMapper = new InteractionRawTraceMapperFile(resource);
		ioInteractionRawTraceMapper = new IoInteractionRawTraceMapperFile(resource);
		
		ioNormalizedTraceMapper = new IoNormalizedTraceMapperFile(resource);
		interactionNormalizedTraceMapper = new InteractionNormalizedTraceMapperFile(resource);
		ioInteractionNormalizedTraceMapper = new IoInteractionNormalizedTraceMapperFile(resource);
		
		ioModelsHandler = new IoModelsMapperFile( resource );
		fsaModelsHandler = new FSAModelsMapperFile( resource );
		efsaModelsHandler = new EFSAModelsMapperFile( resource );
		threadInfoMapper = new ThreadInfoMapperFile( resource );
	}

	@Override
	public IoRawTraceMapper getIoRawTraceHandler() {
		return ioRawTraceMapper;
	}

	public ProgramPointsRawFinder getProgramPointsRawHandler() {
		return rawProgramPointsMapper;
	}

	public InteractionRawTraceFinder getInteractionRawTraceHandler() {
		return interactionRawTraceMapper;
	}

	public IoInteractionRawTraceFinder getIoInteractionRawTraceHandler() {
		return ioInteractionRawTraceMapper;
	}

	public IoNormalizedTraceFinder getIoNormalizedTraceHandler() {
		return ioNormalizedTraceMapper;
	}
	
	public InteractionNormalizedTraceFinder getInteractionNormalizedTraceHandler() {
		return interactionNormalizedTraceMapper;
	}
	
	public IoInteractionNormalizedTraceFinder getIoInteractionNormalizedTraceHandler() {
		return ioInteractionNormalizedTraceMapper;
	}

	public ProgramPointsNormalizedFinder getProgramPointsNormalizedHandler() {
		return programPointsNormalizedMapper;
	}

	public void update(DomainEntity entity) throws MapperException {
		System.out.println("UPDATE "+entity.getClass().getCanonicalName()+" "+IoModel.class.getCanonicalName());
		if (entity.getClass().getCanonicalName().equals(IoModel.class.getCanonicalName()) ){
			ioModelsHandler.update((IoModel) entity);
		} else {
			throw new MapperException("No persit method defined for entiry type "+entity.getClass().getCanonicalName());
		}
	}

	public EFSAModelsFinder getEFSAModelsHandler() {
		return efsaModelsHandler;
	}

	public FSAModelsFinder getFSAModelsHandler() {
		return fsaModelsHandler;
	}

	public IoModelsFinder getIoModelsHandler() {
		return ioModelsHandler;
	}

	public ThreadInfoMapper getThreadInfoHandler() {
		return threadInfoMapper;
	}

	@Override
	public ModelsFetcher getModelsFetcher() {
		return ioModelsHandler.getModelsFetcher();
	}
	

}
