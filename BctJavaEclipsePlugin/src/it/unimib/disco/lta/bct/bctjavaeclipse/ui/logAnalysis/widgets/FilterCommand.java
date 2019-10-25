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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import modelsViolations.BctModelViolation;

public class FilterCommand {

	private String value;
	private FilteringAttribute attribute;
	public FilteringAttribute getAttribute() {
		return attribute;
	}

	public enum FilteringAttribute { Process, Action, Test};
	
	public FilterCommand ( String regex, FilteringAttribute attribute ){
		this.value = regex;
		this.attribute = attribute;
	}
	
	public String getFilterRegex(){
		return value;
	}

	public boolean match(BctModelViolation violation) {
		
		if ( attribute == FilteringAttribute.Process ){
			return violation.getPid().matches(value);
		} else if ( attribute == FilteringAttribute.Action ){
			String[] actions = violation.getCurrentActions();
			for ( String action :  actions ){
				if ( action.matches(value) ){
					return true;
				}
			}
		} else if ( attribute == FilteringAttribute.Test ){
			String[] tests = violation.getCurrentTests();
			if ( tests == null ){
				return false;
			}
			for ( String action : tests ){
				//System.out.println(action+" "+value);
				if ( action.equals(value) ){
					//System.out.println("MATCH");
					return true;
				}
			}
		}
		//System.out.println("NO MATCH");
		return false;
	}
}
