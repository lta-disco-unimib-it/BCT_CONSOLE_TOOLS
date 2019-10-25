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
