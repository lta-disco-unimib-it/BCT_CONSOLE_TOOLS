package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint.Type;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.MethodCallsUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.ProgramPointRawMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import traceReaders.TraceReaderException;
import traceReaders.raw.FileTracesReader;
import traceReaders.raw.IoInteractionMappingTrace;
import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.TracesReader;
import traceReaders.raw.IoTrace.LineIterator;
import util.componentsDeclaration.SystemElement;
import conf.BctSettingsException;
import dfmaker.core.DaikonTraceProcessor;
import dfmaker.core.DaikonTraceProcessor.DTraceListenerException;

public class ProgramPointRawMapperFile extends AbstractMapperFile implements ProgramPointRawMapper  {

	ProgramPointRawMapperFile(ResourceFile resource) {
		super(resource);
	}

	public static class ProgramPointCollector implements DaikonTraceProcessor.DTraceListener {
		private HashMap<String,ProgramPointVariable> variables = new HashMap<String, ProgramPointVariable>();
		private List<ProgramPointRaw> programPoints = new ArrayList<ProgramPointRaw>();
		private ProgramPointRaw currentProgramPoint;
		private IoRawTrace rawTrace;
		private Method method;
		
		public ProgramPointCollector( IoRawTrace rawTrace, Method method ){
			this.rawTrace = rawTrace;
			this.method = method;
		}
		
		public void entryPoint(long beginOffset, String line) throws DTraceListenerException {
			System.out.println( beginOffset+" "+line);
			currentProgramPoint = new FileProgramPointRaw("-1",rawTrace.getMethod(),Type.ENTER,beginOffset);
			programPoints.add(currentProgramPoint);
		}

		public void exitPoint(long beginOffset, String line) throws DTraceListenerException {
			System.out.println( beginOffset+" "+line);
			currentProgramPoint = new FileProgramPointRaw("-1",rawTrace.getMethod(),Type.EXIT,beginOffset);
			programPoints.add(currentProgramPoint);
		}

		private ProgramPointVariable getProgramPointVariable(String name) {
			ProgramPointVariable variable = variables.get(name);
			if ( variable == null ){
				variable = new ProgramPointVariable(method,name,ProgramPointVariable.Type.Unknown);
				variables.put(name,variable);
			}
			return variable;
		}
		
		public void newProgramVar(String varName, String varValue, String varModifier) throws DTraceListenerException {
			ProgramPointVariable ppv = getProgramPointVariable(varName);
			ModifiedInfo modified;
			if( varModifier.equals("0") ){
				modified = ModifiedInfo.NotAssigned;
			} else if ( varModifier.equals("1")){
				modified = ModifiedInfo.Assigned;
			} else {
				modified = ModifiedInfo.Nonsensical;
			}
			ProgramPointValue value = new ProgramPointValue(ppv,varValue,modified);
			try {
				currentProgramPoint.addVariableValue(value);
			} catch (ProgramPointException e) {
				throw new DTraceListenerException(e.getMessage());
			}
			
		}

		public List<ProgramPointRaw> getProgramPoints() {
			return programPoints;
		}

		public void traceEnd() throws DTraceListenerException {
			// TODO Auto-generated method stub
			//throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
		}

		@Override
		public void genericProgramPoint(long beginOffset, String line)
				throws DTraceListenerException {
			currentProgramPoint = new FileProgramPointRaw("-1",rawTrace.getMethod(),Type.POINT,beginOffset);
			programPoints.add(currentProgramPoint);
		}
		
	}
	

	
	public List<ProgramPointRaw> find(IoRawTrace trace) throws MapperException {
		String methodSignature = trace.getMethod().getSignature();
		try {
			TracesReader reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
			IoTrace lowLevelTrace;

			lowLevelTrace = reader.getIoTrace( methodSignature );
		
			LineIterator lineIterator = lowLevelTrace.getLineIterator();
			ProgramPointCollector collector = new ProgramPointCollector(trace, resource.getFinderFactory().getMethodHandler().getMethod(methodSignature));
			DaikonTraceProcessor processor = new DaikonTraceProcessor( collector );
			processor.process(lineIterator);
			return collector.getProgramPoints();
		} catch (TraceReaderException e) {
			throw new MapperException("Cannot get trace for method "+methodSignature);
			
		} catch (TraceException e) {
			throw new MapperException("Problem accessing trace. "+e.getMessage());
		} catch (DTraceListenerException e) {
			throw new MapperException("Problem readind trace. "+e.getMessage());
		} catch (BctSettingsException e) {
			throw new MapperException("Problem readind trace. ",e);
		}
	}

	public void update(ProgramPointRaw entity) {
		throw new RuntimeException("Not Implemented");
	}

	public List<ProgramPointRaw> find(List<SystemElement> components, String programPointExpression, Type ppType,
			String varNameExpr,
			ProgramPointVariable.Type varTypeExpr,
			String varValueExpr, ModifiedInfo variableModified) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public ProgramPointRaw find(MethodCallPoint methodCall) throws MapperException {
		// TODO Auto-generated method stub
		
		return find( methodCall.getMethod().getSignature() , methodCall.getMethodCallId() );
	}
	
	@Override
	public ProgramPointRaw find(String methodSignature, String methodCallId) throws MapperException {
		try {
			
			IoRawTrace lowLevelTrace = resource.getFinderFactory().getIoRawTraceHandler().findTrace(new Method( methodSignature ) );
			
			List<ProgramPointRaw> rawIoTraces = find ( lowLevelTrace );
			
			String sessionId = MethodCallsUtil.getSessionId( methodCallId );
			String threadId = MethodCallsUtil.getThreadId( methodCallId );
			Long methodCallNumber = MethodCallsUtil.getMethodCallNumber( methodCallId );

			FileTracesReader reader = new FileTracesReader(resource.getDataRecordingDir(),resource.getIoRawTracesDirName(),resource.getInteractionRawTracesDirName(),true);
					
			IoInteractionMappingTrace offsets = reader.getIoMappingsForInteractionTrace(sessionId, threadId);
			long offset = offsets.getOffsetForMethodCall((int) methodCallNumber.longValue());
			
			
			for ( ProgramPointRaw ppr : rawIoTraces){
				if ( offset == ((FileProgramPointRaw)ppr).getBeginOffset() ){
					return ppr;
				}
			}
			
			return null;
		} catch (BctSettingsException e) {
			// TODO Auto-generated catch block
			throw new MapperException("Problem loading trace reaser. ",e);
		}
		
		
	}
	



}
