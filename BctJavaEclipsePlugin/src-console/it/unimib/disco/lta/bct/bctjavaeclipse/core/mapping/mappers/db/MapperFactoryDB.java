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
