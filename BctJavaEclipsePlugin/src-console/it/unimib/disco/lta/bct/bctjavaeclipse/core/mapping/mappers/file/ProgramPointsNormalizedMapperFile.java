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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint.Type;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointNormalized;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ProgramPointsNormalizedFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import traceReaders.TraceReaderException;
import traceReaders.raw.FileTracesReader;
import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import traceReaders.raw.TracesReader;
import traceReaders.raw.IoTrace.LineIterator;
import conf.BctSettingsException;
import dfmaker.core.DaikonTraceProcessor;
import dfmaker.core.DaikonTraceProcessor.DTraceListenerException;

public class ProgramPointsNormalizedMapperFile extends AbstractMapperFile implements
		ProgramPointsNormalizedMapper {

	public static class ProgramPointCollector implements DaikonTraceProcessor.DTraceListener {
		private HashMap<String,ProgramPointVariable> variables = new HashMap<String, ProgramPointVariable>();
		private List<ProgramPointNormalized> programPoints = new ArrayList<ProgramPointNormalized>();
		private ProgramPointNormalized currentProgramPoint;
		private IoNormalizedTrace rawTrace;
		private Method method;
		
		public ProgramPointCollector( IoNormalizedTrace rawTrace, Method method ){
			this.rawTrace = rawTrace;
			this.method = method;
		}
		
		public void entryPoint(long offset,String line) throws DTraceListenerException {
			currentProgramPoint = new ProgramPointNormalized("-1",rawTrace.getMethod(),Type.ENTER);
			programPoints.add(currentProgramPoint);
		}

		public void exitPoint(long offset,String line) throws DTraceListenerException {
			currentProgramPoint = new ProgramPointNormalized("-1",rawTrace.getMethod(),Type.EXIT);
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

		public List<ProgramPointNormalized> getProgramPoints() {
			return programPoints;
		}

		public void traceEnd() throws DTraceListenerException {
			// TODO Auto-generated method stub
			//throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
		}

		@Override
		public void genericProgramPoint(long beginOffset, String line)
				throws DTraceListenerException {
			currentProgramPoint = new ProgramPointNormalized("-1",rawTrace.getMethod(),Type.POINT);
			programPoints.add(currentProgramPoint);
		}
	
	}
	
	ProgramPointsNormalizedMapperFile(ResourceFile resource) {
		super(resource);
	}

	public List<ProgramPointNormalized> find(IoNormalizedTrace trace) throws MapperException {
		String methodSignature = trace.getMethod().getSignature();
		try {
			TracesReader reader = new FileTracesReader(resource.getDataPreprocessingDir(),resource.getIoNormalizedDtracesDirName(),resource.getInteractionNormalizedTracesDirName(),true);
			IoTrace lowLevelTrace;

			lowLevelTrace = reader.getIoTrace( methodSignature );
		
			LineIterator lineIterator = lowLevelTrace.getLineIterator();
			ProgramPointCollector collector = new ProgramPointCollector(trace, resource.getFinderFactory().getMethodHandler().getMethod(methodSignature));
			DaikonTraceProcessor processor = new DaikonTraceProcessor( collector );
			processor.process(lineIterator);
			return collector.getProgramPoints();
			
			
		} catch (TraceReaderException e) {
			throw new MapperException("Cannot get normalized trace for method "+methodSignature);
		} catch (TraceException e) {
			throw new MapperException("Problem accessing normalized  trace. "+e.getMessage());
		} catch (DTraceListenerException e) {
			throw new MapperException("Problem reading normalized  trace. "+e.getMessage());
		} catch (BctSettingsException e) {
			throw new MapperException("Problem reading normalized  trace. ",e);
		}
	}

	public void update(ProgramPointNormalized entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}


}