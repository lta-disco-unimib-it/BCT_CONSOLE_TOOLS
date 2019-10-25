package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.List;

import util.componentsDeclaration.MatchingRule;

/**
 * This class represent a call filter that operates on all the Components of the system
 * 
 * @author Fabrizio Pastore
 *
 */
public class CallFilterGlobal extends CallFilter {

	public CallFilterGlobal(){
		
	}
	
	public CallFilterGlobal(List<MatchingRule> rules){
		super(rules);
	}
	
}
