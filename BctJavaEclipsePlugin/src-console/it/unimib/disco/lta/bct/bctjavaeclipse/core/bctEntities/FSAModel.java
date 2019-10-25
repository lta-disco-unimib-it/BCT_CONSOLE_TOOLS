package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import automata.fsa.FiniteStateAutomaton;

public class FSAModel extends DomainEntity {

	private Method method;
	private ValueHolder<FiniteStateAutomaton> fsaHolder;
	
	

	public FSAModel(String id, Method method, ValueHolder<FiniteStateAutomaton> valueHolder) {
		super(id);
		this.method = method;
		this.fsaHolder = valueHolder;
	}

	public Method getMethod() {
		return method;
	}

	public FiniteStateAutomaton getFSA() throws LoaderException{
		return fsaHolder.getValue();
	}
	
}
