package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public class CallFilterRuleExclude extends CallFilterRule {

	public CallFilterRuleExclude(String packageName, String className, String classMethod) {
		super(packageName, className, classMethod);
	}
	
	public CallFilterRuleExclude(){
		
	}

	@Override
	public boolean isInclude() {
		return false;
	}

}
