package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.ArrayList;
import java.util.List;

import util.componentsDeclaration.MatchingRule;



public abstract class CallFilter {

	/**
	 * @uml.annotations  for <code>callToRules</code> 
	 *    collection_type="it.unimib.disco.lta.bct.bctjavaeclipse.core.businessLogic.CallToRule"
	 */
//	private List<String> callToRules;
	
	private List<MatchingRule> callToRules = new ArrayList<MatchingRule>();
	
	
	
	public CallFilter(List<MatchingRule> rules)
	{
		
		this.callToRules.addAll(rules);
	}
	
	public CallFilter()
	{
		
	}
	
		
	public void addRule(MatchingRule rules)
	{
		
		callToRules.add(rules);
	}

	public List<MatchingRule> getCallToRules() {
		return callToRules;
	}

	public void setCallToRules(List<MatchingRule> callToRules) {
		this.callToRules = callToRules;
	}
	
	public boolean isCallFilterGlobal(){
		return this instanceof CallFilterGlobal;
	}
	
	public boolean isCallFilterComponent(){
		return this instanceof CallFilterComponent;
	}
}
