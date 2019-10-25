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
