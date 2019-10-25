package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public abstract class CallFilterRule {

	private String className;
	private String classMethod;
	private String packageName;
	
	public CallFilterRule( String packageName, String className,String classMethod )
	{
		this.classMethod=classMethod;
		this.className=className;
		this.packageName=packageName;
	}
	
	protected CallFilterRule(){
		
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}
	
	public String getClassMethod() {
		return classMethod;
	}
	
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
	
//	public void setRulesType(String[] rulesType) {
//		this.rulesType = rulesType;
//	}
//	
//	public String[] getRulesType() {
//		return rulesType;
//	}
	
	public abstract boolean isInclude();
	
	public boolean isExclude(){
		return !isInclude();
	}
	
}
