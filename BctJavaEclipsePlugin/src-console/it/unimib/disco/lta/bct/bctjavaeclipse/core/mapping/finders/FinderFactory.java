package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import modelsFetchers.ModelsFetcher;


public interface FinderFactory {

	//RAW
	public IoRawTraceFinder getIoRawTraceHandler();
	
	public InteractionRawTraceFinder getInteractionRawTraceHandler();
	
	public IoInteractionRawTraceFinder getIoInteractionRawTraceHandler();
	
	public ProgramPointsRawFinder getProgramPointsRawHandler();
	
	
	//NORMALIZED
	public IoNormalizedTraceFinder getIoNormalizedTraceHandler();
	
	public InteractionNormalizedTraceFinder getInteractionNormalizedTraceHandler();
	
	public IoInteractionNormalizedTraceFinder getIoInteractionNormalizedTraceHandler();
	
	public ProgramPointsNormalizedFinder getProgramPointsNormalizedHandler();
	

	//MODELS
	
	public IoModelsFinder getIoModelsHandler();
	
	public FSAModelsFinder getFSAModelsHandler();
	
	public EFSAModelsFinder getEFSAModelsHandler();
	
	
	
	
	public MethodFinder getMethodHandler();
	
	public ThreadInfoFinder getThreadInfoHandler();

	public ModelsFetcher getModelsFetcher();
	
	
	
}
