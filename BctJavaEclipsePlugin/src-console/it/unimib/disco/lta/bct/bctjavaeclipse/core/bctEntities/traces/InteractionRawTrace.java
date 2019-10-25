package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ThreadInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

import java.util.List;

public class InteractionRawTrace extends RawTrace {

	private ThreadInfo thread;
	private ValueHolder<List<MethodCallPoint>> valueHolder;

	public InteractionRawTrace( String id, ThreadInfo threadInfo, ValueHolder<List<MethodCallPoint>> valueHolder) {
		super(id);
		this.thread = threadInfo;
		this.valueHolder = valueHolder;
	}
	
	public List<MethodCallPoint> getMethodCallPoints() throws LoaderException{
		return valueHolder.getValue();
	}

	public ThreadInfo getThread() {
		return thread;
	}
	
	
}
