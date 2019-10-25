package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;
import grammarInference.Record.TraceParser;
import grammarInference.Record.kbhParser;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.MethodCallSequence;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctResourcesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.MethodFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.MethodCallSequencesLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.InteractionNormalizedTraceMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import traceReaders.normalized.NormalizedInteractionTrace;
import traceReaders.normalized.NormalizedInteractionTraceHandlerFile;
import traceReaders.normalized.NormalizedInteractionTraceIterator;

public class InteractionNormalizedTraceMapperFile extends AbstractMapperFile implements InteractionNormalizedTraceMapper {

	InteractionNormalizedTraceMapperFile(ResourceFile resource) {
		super(resource);
	}

	public Collection<InteractionNormalizedTrace> findAllTraces() {
		
		
		NormalizedInteractionTraceHandlerFile traceHandler = getHandler(); 
		NormalizedInteractionTraceIterator tIt = traceHandler.getInteractionTracesIterator();
		
		ArrayList<InteractionNormalizedTrace> traces = new ArrayList<InteractionNormalizedTrace>();
		
		while ( tIt.hasNext() ){
			NormalizedInteractionTrace trace = tIt.next();
			Method method = resource.getFinderFactory().getMethodHandler().getMethod( trace.getMethodName() );
			
			InteractionNormalizedTrace normalizedTrace = new InteractionNormalizedTrace( "-1", method, new ValueHolder<List<MethodCallSequence>>(new MethodCallSequencesLoader(method, this) ) );
			traces.add(normalizedTrace);
			
		}
		
		return traces;
	}

	private NormalizedInteractionTraceHandlerFile getHandler() {
		File tracesDir = resource.getInteractionNormalizedTracesDir();
		
		
		return new NormalizedInteractionTraceHandlerFile(tracesDir);
		
	}

	public InteractionNormalizedTrace findTrace(Method method) throws MapperException {
		InteractionNormalizedTrace normalizedTrace = new InteractionNormalizedTrace( "-1", method, new ValueHolder<List<MethodCallSequence>>(new MethodCallSequencesLoader(method, this) ) );
		return normalizedTrace;
	}

	public List<MethodCallSequence> getMethodCallSequences(Method method) throws MapperException {
		NormalizedInteractionTrace trace = getRawTrace( method );
		
		File traceFile = trace.getTraceFile();
		MethodFinder methodFinder = resource.getFinderFactory().getMethodHandler();
		
		try {
			TraceParser fileParser = new kbhParser(traceFile.getAbsolutePath());
			Iterator it = fileParser.getTraceIterator();
			
			
			ArrayList<MethodCallSequence> callSequences = new ArrayList<MethodCallSequence>();
			
			while( it.hasNext() ){
				Trace traceRaw = (Trace) it.next();
				Iterator<String> sit = traceRaw.getSymbolIterator();
				
				MethodCallSequence callSequence = new MethodCallSequence();
				
				while ( sit.hasNext() ){
					String symbol = sit.next();
					Method methodCalled = methodFinder.getMethod(symbol);
					MethodCall methodCall =  new MethodCall( "-1", methodCalled );
					callSequence.add(methodCall);
				}
				
				callSequences.add(callSequence);
			}
			
			return callSequences;
			
		} catch (FileNotFoundException e) {
			throw new MapperException("No trace for method "+method.getSignature(),e);
		}


		
	}

	private NormalizedInteractionTrace getRawTrace(Method method) throws MapperException {
		File tracesDir = resource.getInteractionNormalizedTracesDir();
		
		NormalizedInteractionTraceHandlerFile traceHandler = new NormalizedInteractionTraceHandlerFile(tracesDir);
		NormalizedInteractionTraceIterator tIt = traceHandler.getInteractionTracesIterator();
		
		ArrayList<InteractionNormalizedTrace> traces = new ArrayList<InteractionNormalizedTrace>();
		
		while ( tIt.hasNext() ){
			NormalizedInteractionTrace trace = tIt.next();
		
			if ( trace.getMethodName().equals( BctResourcesUtil.getRawMethodName(resource, method) ) ){
				return trace;
			}
			
			
		}
		throw new MapperException("No trace for method "+method.getSignature());
	}

	public void update(InteractionNormalizedTrace entity) {
		throw new RuntimeException("Not Implemented");
	}


}
