package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointsRawLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoRawTraceMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils.TraceMapperUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import recorders.FileDataRecorder;
import traceReaders.TraceReaderException;
import traceReaders.raw.FileTracesReader;
import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.TracesReader;
import conf.BctSettingsException;

public class IoRawTraceMapperFile extends AbstractMapperFile implements IoRawTraceMapper {

	IoRawTraceMapperFile(ResourceFile resource) {
		super(resource);
	}

	public Collection<IoRawTrace> findAllTraces() throws MapperException {
		
		try {
			TracesReader reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
			Iterator<IoTrace> it = reader.getIoTraces();
			ArrayList<IoRawTrace> result = new ArrayList<IoRawTrace>();
			while ( it.hasNext() ){
				IoTrace lowLevelRawTrace = it.next();
				String methodName = lowLevelRawTrace.getMethodName();
				IoRawTrace trace = findTrace( methodName );
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

	public IoRawTrace findTrace(String methodName) {
		Method m = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		IoRawTrace trace = new IoRawTrace("-1",m);
		ValueHolder<List<ProgramPointRaw>> valueHolder = new ValueHolder<List<ProgramPointRaw>>(new ProgramPointsRawLoader(resource.getFinderFactory().getProgramPointsRawHandler(),trace) );
		trace.setProgramPoints(valueHolder);
		return trace;
	}

	public IoRawTrace findTrace(Method method) throws MapperException  {
		return findTrace(method.getSignature());
	}

	public void update(IoRawTrace entity) throws MapperException {
		
		FileDataRecorder recorder = new FileDataRecorder(resource.getDataDirFile());
		
		TraceMapperUtil.recordIoRawTrace(recorder,entity);
		
		
	}
	

}
