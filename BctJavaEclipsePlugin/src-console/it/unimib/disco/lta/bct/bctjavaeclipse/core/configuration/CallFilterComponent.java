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

import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;

/**
 * This class filter all the calls coming from a component to other methods of the system  
 * 
 * @author Fabrizio Pastore
 *
 */
public class CallFilterComponent extends CallFilter{
	private Component component;
	
	public CallFilterComponent(){		
	}
	
	public CallFilterComponent(Component component){
		this.component=component;		
	}
	
	public CallFilterComponent(Component component,List<MatchingRule> rules){
		super(rules);
		this.component=component;		
	}
	
	public Component getCallFilterComponent()
	{
		return component;
	}
	
	public void setCallFilterComponent(Component callFilterComponent)
	{
		component=callFilterComponent;
	}
	
	
}
