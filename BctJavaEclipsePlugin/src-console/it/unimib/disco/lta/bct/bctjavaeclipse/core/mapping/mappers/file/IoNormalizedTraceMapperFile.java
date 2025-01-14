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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointNormalized;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointsNormalizedLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoNormalizedTraceMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import traceReaders.TraceReaderException;
import traceReaders.raw.FileTracesReader;
import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.TracesReader;
import conf.BctSettingsException;

public class IoNormalizedTraceMapperFile extends AbstractMapperFile implements IoNormalizedTraceMapper {

	public IoNormalizedTraceMapperFile(ResourceFile resource) {
		super(resource);
	}

	public ArrayList<IoNormalizedTrace> findAllTraces() throws MapperException {
		
		try {
			TracesReader reader = new FileTracesReader(resource.getDataPreprocessingDir(),resource.getIoNormalizedDtracesDirName(),resource.getInteractionNormalizedTracesDirName(),true);
			Iterator<IoTrace> it = reader.getIoTraces();
			ArrayList<IoNormalizedTrace> result = new ArrayList<IoNormalizedTrace>();
			while ( it.hasNext() ){
				IoTrace lowLevelRawTrace = it.next();
				String methodName = lowLevelRawTrace.getMethodName();
				IoNormalizedTrace trace = findTrace( methodName );
				result.add(trace);
			}
			return result;
		} catch (TraceException e) {
			throw new MapperException("Cannot open trace "+e.getMessage(),e);
		} catch (TraceReaderException e) {
			throw new MapperException("Cannot open trace "+e.getMessage(),e);
		} catch (BctSettingsException e) {
			throw new MapperException("Cannot open trace "+e.getMessage(),e);
		}
	}

	public IoNormalizedTrace findTrace(String methodName) {
		Method m = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		IoNormalizedTrace trace = new IoNormalizedTrace("-1",m);
		ValueHolder<List<ProgramPointNormalized>> valueHolder = new ValueHolder<List<ProgramPointNormalized>>(new ProgramPointsNormalizedLoader(resource.getFinderFactory().getProgramPointsNormalizedHandler(),trace) );
		trace.setProgramPoints(valueHolder);
		return trace;
	}

	public IoNormalizedTrace findTrace(Method method) throws MapperException  {
		return findTrace(method.getSignature());
	}

	
	public void update(IoNormalizedTrace entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}
}
