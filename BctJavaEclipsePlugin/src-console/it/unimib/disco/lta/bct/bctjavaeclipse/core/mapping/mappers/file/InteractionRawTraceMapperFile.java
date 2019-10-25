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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ThreadInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.MethodCallPointLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.InteractionRawTraceMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils.LazyLoadingList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import traceReaders.TraceReaderException;
import traceReaders.raw.FileReaderException;
import traceReaders.raw.FileTracesReader;
import traceReaders.raw.InteractionTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.TracesReader;
import conf.BctSettingsException;
import conf.EnvironmentalSetter;

/**
 * This is the mapper that gets information on traces from file
 * 
 * @author Fabrizio Pastore
 *
 */
public class InteractionRawTraceMapperFile extends AbstractMapperFile implements InteractionRawTraceMapper {

	InteractionRawTraceMapperFile(ResourceFile resource) {
		super(resource);
	}

	public List<InteractionRawTrace> findAllTraces() throws MapperException {
		try {
			
			System.out.println("Data Recording dir "+resource.getDataRecordingDir());
			List<InteractionRawTrace> traces = new ArrayList<InteractionRawTrace>();
			TracesReader reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
			Iterator<InteractionTrace> it = reader.getInteractionTraces();
			while ( it.hasNext() ){
				InteractionTrace originalTrace = it.next();
				traces.add( findTrace(originalTrace.getTraceId() ) );
			}
			
			return traces;
		} catch (BctSettingsException e) {
			
			throw new MapperException("Cannot open trace "+e.getMessage());
		} catch (TraceException e) {
			System.out.println(resource.getDataRecordingDir());
			throw new MapperException("Cannot open trace "+e.getMessage());
		}
	}

	public InteractionRawTrace findTrace(String traceId) throws MapperException {
		ThreadInfo threadInfo = resource.getFinderFactory().getThreadInfoHandler().findThreadInfo(traceId);
		ValueHolder<List<MethodCallPoint>> valueHolder = new ValueHolder<List<MethodCallPoint>>( new MethodCallPointLoader(this, traceId));
		return new InteractionRawTrace(threadInfo.getId(),threadInfo,valueHolder);
	}

	public List<MethodCallPoint> findMethodCallPointsForTrace(String traceId) throws MapperException {

		
		try {
			TracesReader reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);


			InteractionTrace originalTrace;
			try {
				originalTrace = reader.getInteractionTrace(traceId);
			} catch (TraceReaderException e) {
				throw new MapperException(e);
			}

			MethodCallPointListLoaderFile loader = new MethodCallPointListLoaderFile(resource,originalTrace);
			return new LazyLoadingList<MethodCallPoint>(loader);

		} catch (BctSettingsException e) {
			throw new MapperException(e);
		}

		
	}

	public List<MethodCallPoint> findMethodCallPointsForThread(ThreadInfo thread) throws MapperException {
		return findMethodCallPointsForTrace(thread.getId());
	}

	public void update(InteractionRawTrace entity) throws MapperException {
		throw new RuntimeException("Not implemented");
		//FIXME: has to be fixed
//		FileDataRecorder recorder = new FileDataRecorder(resource.getDataDirFile());
//		try {
//			recorder.deleteInteractionTraceForThread( Integer.valueOf(entity.getThread().getThreadId()) );
//			for ( MethodCallPoint callPoint : entity.getMethodCallPoints() ){
//				
//				if ( callPoint.isEnter() ){
//					recorder.recordInteractionEnter(callPoint.getMethod().getSignature(), Integer.valueOf(entity.getThread().getThreadId()) );
//				} else if ( callPoint.isExit() ) {
//					recorder.recordInteractionExit(callPoint.getMethod().getSignature(), Integer.valueOf(entity.getThread().getThreadId()) );
//				} else {
//					throw new MapperException("Unmanaged MethodCallPoint type.");
//				}
//				
//			}
//		} catch (LoaderException e) {
//			throw new MapperException("Cannot save status ",e);
//		} catch (NumberFormatException e) {
//			throw new MapperException("Cannot save status ",e);
//		} catch (RecorderException e) {
//			throw new MapperException("Cannot save status ",e);
//		}
//		
	}

	@Override
	public InteractionRawTrace findTrace(String pid, String threadId) throws MapperException {
		
		TracesReader reader;
		try {
			reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
			String sessiondId = reader.getSessionIdFromName(pid);
			InteractionTrace interactionTrace = reader.getInteractionTrace(sessiondId, threadId);
			if ( interactionTrace == null ){
				return null;
			}
			return findTrace( interactionTrace.getTraceId() );
		} catch (BctSettingsException e) {
			throw new MapperException(e);
		} catch (TraceException e) {
			throw new MapperException(e);
		} catch (FileReaderException e) {
			throw new MapperException(e);
		}

		
	}

}
