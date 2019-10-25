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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import java.util.HashMap;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

public class NonDeterministicVariablesRegistry {

	private static NonDeterministicVariablesRegistry instance;
	private HashMap<String,Integer> nonDetVars = new HashMap<String,Integer>();
	
	private NonDeterministicVariablesRegistry(){
		
	}
	
	public synchronized static NonDeterministicVariablesRegistry getInstance() {
		if ( instance == null ){
			instance = new NonDeterministicVariablesRegistry();
		}
		return instance;
	}

	public void addNonDetermnisticVariable(Method method, String name) {
		nonDetVars.put(getKey(method, name), 1 );
	}
	
	private String getKey(Method method, String name) {
		// TODO Auto-generated method stub
		return method.getSignature()+"."+name;
	}

	public boolean isNonDeterministic( Method method, String name ){
		Integer value = nonDetVars.get(getKey(method, name));
		
		if ( value == null ){
			return false;
		}
		
		return true;
	}

}
