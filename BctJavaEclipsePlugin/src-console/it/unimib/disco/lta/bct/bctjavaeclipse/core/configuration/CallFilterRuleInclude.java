package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public class CallFilterRuleInclude extends CallFilterRule {

	
	public CallFilterRuleInclude(String packageName, String className, String classMethod)
	{
		super(packageName, className, classMethod);
	}
	
	public CallFilterRuleInclude(){}
	
	
	public boolean isInclude() {
		
		return true;
	}

}
