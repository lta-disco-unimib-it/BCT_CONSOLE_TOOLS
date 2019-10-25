package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MethodCallSequence {
	List<MethodCall> methodCalls = new ArrayList<MethodCall>();
	
	
	
	MethodCallSequence( Collection<MethodCall> methodCalls ){
		this.methodCalls.addAll(methodCalls);
	}

	public MethodCallSequence() {
		// TODO Auto-generated constructor stub
	}

	public void add(int index, MethodCall element) {
		methodCalls.add(index, element);
	}

	public boolean add(MethodCall o) {
		return methodCalls.add(o);
	}

	public MethodCall get(int index) {
		return methodCalls.get(index);
	}

	public boolean isEmpty() {
		return methodCalls.isEmpty();
	}

	public Iterator<MethodCall> iterator() {
		return methodCalls.iterator();
	}
	
}
