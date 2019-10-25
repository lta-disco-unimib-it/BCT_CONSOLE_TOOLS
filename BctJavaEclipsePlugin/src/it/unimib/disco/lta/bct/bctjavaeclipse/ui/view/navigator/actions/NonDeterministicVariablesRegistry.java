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
