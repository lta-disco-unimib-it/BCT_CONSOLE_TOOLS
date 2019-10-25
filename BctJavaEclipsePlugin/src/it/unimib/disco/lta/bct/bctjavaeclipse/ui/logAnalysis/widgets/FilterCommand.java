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
