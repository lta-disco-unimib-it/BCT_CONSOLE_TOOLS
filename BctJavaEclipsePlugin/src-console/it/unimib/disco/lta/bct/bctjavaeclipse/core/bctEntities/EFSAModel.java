package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import automata.fsa.FiniteStateAutomaton;

public class EFSAModel extends DomainEntity {
	private Method method;
	private FiniteStateAutomaton fsa;
	
	/**
	 * Create an Interaction model
	 * 
	 * @param id		id of the 
	 * @param method	
	 * @param fsa
	 */
	protected EFSAModel(String id,Method method, FiniteStateAutomaton fsa) {
		super(id);
		this.method = method;
		this.fsa = fsa;
	}
}
