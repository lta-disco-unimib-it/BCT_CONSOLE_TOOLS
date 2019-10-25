package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

import java.util.List;

public class InteractionNormalizedTrace extends NormalizedTrace {
	private Method method;
	private ValueHolder<List<MethodCallSequence>> callSequencesLoader;

	
	public InteractionNormalizedTrace(String id, Method method, ValueHolder<List<MethodCallSequence>> callSequencesLoader ) {
		super(id);
		this.method = method;
		this.callSequencesLoader = callSequencesLoader;
	}
	
	public Method getMethod() {
		return method;
	}


	public List<MethodCallSequence> getCallSequences() throws LoaderException{
		return callSequencesLoader.getValue();
	}

}
