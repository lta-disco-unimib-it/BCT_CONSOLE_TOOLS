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

import java.util.NoSuchElementException;

import traceReaders.raw.InteractionTrace;
import traceReaders.raw.Token;
import traceReaders.raw.TraceException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPointEnter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPointExit;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.MethodCallsUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils.LazyListElementsLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils.LazyListElementsLoaderException;

public class MethodCallPointListLoaderFile implements LazyListElementsLoader<MethodCallPoint> {

	private InteractionTrace originalTrace;
	private int loadedElementsCount;
	private boolean dataReady;
	private Token nextToken;
	private ResourceFile resource;

	public MethodCallPointListLoaderFile(ResourceFile resource, InteractionTrace originalTrace) {
		this.resource = resource;
		this.originalTrace = originalTrace;
	}

	public int getLoadedElementsCount() {
		return loadedElementsCount;
	}

	public boolean hasNext() throws LazyListElementsLoaderException {
		prepareData();
		return ( nextToken != null );
	}

	private void prepareData() throws LazyListElementsLoaderException {
		if ( dataReady ){
			return;
		}
		try {
			nextToken = originalTrace.getNextToken();
		} catch (TraceException e) {
			throw new LazyListElementsLoaderException(e);
		}
		dataReady=true;
	}

	public MethodCallPoint next() throws LazyListElementsLoaderException {
		if ( ! hasNext() ){
			throw new NoSuchElementException();
		}
		prepareData();
		Token token = nextToken;
		dataConsumed();
		
		String tokenValue = token.getTokenValue();
		
		int tokenId = loadedElementsCount;
		loadedElementsCount++;
		
		String methodCallId = MethodCallsUtil.getMethodCallId( originalTrace.getSessionId(), originalTrace.getThreadId(), tokenId );

		
		Method method = resource.getFinderFactory().getMethodHandler().getMethod(token.getMethodSignature());
		if ( tokenValue.endsWith("B") ){
			return new MethodCallPointEnter(method, methodCallId, new ProgramPointRawLoader( resource, method.getSignature(), methodCallId) );
		} else if (tokenValue.endsWith("E")) {
			return new MethodCallPointExit(method, methodCallId, new ProgramPointRawLoader( resource, method.getSignature(), methodCallId) );
		} else if (tokenValue.endsWith("P")) {
			return new MethodCallPointGeneric(method, methodCallId, new ProgramPointRawLoader( resource, method.getSignature(), methodCallId) );	
		} else {
			throw new LazyListElementsLoaderException("Not correct trace, call point neither ENTER or EXIT");
		}
		
		
	}

	private void dataConsumed() {
		nextToken = null;
		dataReady=false;
	}

	public int getTotalElementsCount() throws LazyListElementsLoaderException {
		try {
			return originalTrace.getTokensCount();
		} catch (TraceException e) {
			throw new LazyListElementsLoaderException(e);
		}
	}

	

}
