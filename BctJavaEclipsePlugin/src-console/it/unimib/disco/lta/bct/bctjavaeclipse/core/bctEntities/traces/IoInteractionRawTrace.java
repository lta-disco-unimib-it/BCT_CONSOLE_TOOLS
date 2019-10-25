package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;

import java.util.List;


public class IoInteractionRawTrace extends RawTrace {

	IoInteractionRawTrace(String id) {
		super(id);
	}
	
	public List<MethodCall> getMethodCalls(){
		throw new RuntimeException("Not Implemented");
	}

	public InteractionRawTrace getInteractionTrace() {
		throw new RuntimeException("Not Implemented");
	}



}
