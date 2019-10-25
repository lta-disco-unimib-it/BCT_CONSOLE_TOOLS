package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.HashSet;
import java.util.Set;

public class AdditionalInferenceOptions {

	private Set<String> testCasesToIgnore = new HashSet<String>();
	private Set<String> actionsToIgnore = new HashSet<String>();
	private Set<String> processesToIgnore = new HashSet<String>();
	private boolean invertFiltering;

	public Set<String> getActionsToIgnore() {
		return actionsToIgnore;
	}

	public Set<String> getProcessesToIgnore() {
		return processesToIgnore;
	}

	public AdditionalInferenceOptions(){
		
	}

	public void setTestCasesToIgnore(Set<String> failingTests) {
		testCasesToIgnore = new HashSet<String>();
		testCasesToIgnore.addAll(failingTests);
	}
	
	public Set<String> getTestCasesToIgnore() {
		return testCasesToIgnore;
	}

	public void setActionsToIgnore(Set<String> failingActions) {
		actionsToIgnore = new HashSet<String>();
		actionsToIgnore.addAll(failingActions);
	}
	
	public void setProcessesToIgnore(Set<String> failingActions) {
		processesToIgnore  = new HashSet<String>();
		processesToIgnore.addAll(failingActions);
	}

	public void setInvertFiltering(boolean invertFiltering) {
		this.invertFiltering = invertFiltering;	
	}

	public boolean getInvertFiltering() {
		return invertFiltering;
	}
	
}
