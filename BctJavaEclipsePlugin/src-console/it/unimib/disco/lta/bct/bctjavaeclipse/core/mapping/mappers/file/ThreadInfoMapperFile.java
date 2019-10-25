package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import java.util.HashMap;

import conf.BctSettingsException;
import database.Session;

import traceReaders.TraceReaderException;
import traceReaders.raw.FileTracesReader;
import traceReaders.raw.InteractionTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.TracesReader;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ThreadInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.ThreadInfoMapper;

public class ThreadInfoMapperFile implements ThreadInfoMapper {

	private HashMap<String,ThreadInfo> map = new HashMap<String, ThreadInfo>();
	private ResourceFile resource;
	
	public ThreadInfoMapperFile(ResourceFile resource) {
		this.resource = resource;
	}

	public void update(ThreadInfo entity) throws MapperException {
		
	}

	public ThreadInfo findThreadInfo(String sessionId, String threadId) throws MapperException {
		TracesReader reader;
		try {
			reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
			
			InteractionTrace id = reader.getInteractionTrace(sessionId, threadId);
			
			return createThreadInfo(id, reader.getSessionName(sessionId));
			
		} catch (BctSettingsException e) {
			throw new MapperException(e);
		} finally {
			
		}
		
		
		
		
	}

	private ThreadInfo createThreadInfo(InteractionTrace trace, String sessionName) throws MapperException {
		try{
			
			return new ThreadInfo(trace.getTraceId(),trace.getSessionId(),trace.getThreadId(), sessionName );
		} catch (TraceException e) {
			throw new MapperException(e);
		}
	}

	public ThreadInfo findThreadInfo(String traceId) throws MapperException {
		FileTracesReader reader;
		try {
			reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
			
			InteractionTrace trace = reader.getInteractionTrace(traceId);
			
			
			return createThreadInfo(trace, reader.getSessionName(trace.getSessionId()));
			
		} catch (BctSettingsException e) {
			throw new MapperException(e);
		} catch (TraceReaderException e) {
			throw new MapperException(e);
		}
		
		
		
	}


}
