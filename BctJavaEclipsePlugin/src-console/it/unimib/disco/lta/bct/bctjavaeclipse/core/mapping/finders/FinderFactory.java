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
