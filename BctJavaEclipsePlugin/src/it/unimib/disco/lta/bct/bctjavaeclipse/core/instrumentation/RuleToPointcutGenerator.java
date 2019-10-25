package it.unimib.disco.lta.bct.bctjavaeclipse.core.instrumentation;

import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;

public abstract class RuleToPointcutGenerator {

	public enum MethodVisibility { Public, Protected, Private };
	public enum PointcutType { execution, call, withincode, staticinitialization };
	
	private boolean normal;
	private MethodVisibility methodVisibility;
	private PointcutType pointcutType; 

	public PointcutType getPointcutType() {
		return pointcutType;
	}

	public RuleToPointcutGenerator( boolean invert, MethodVisibility methodVisibility, PointcutType pointcutType ){
		this.normal = ! invert;
		this.methodVisibility = methodVisibility;
		this.pointcutType = pointcutType;
	}
	
	public String createPointcut(Component component) {
		//result.append( "( " );
		return createPointcut(component.getRules());
	}
	
	public String createPointcut(List<MatchingRule> rules) {

		StringBuffer result = new StringBuffer();

		
		boolean first = true;
		
		
		for ( MatchingRule rule : rules){
			
			if ( first ){
				first = false;
			} else {
				result.append(" AND ");
			}
			
			
//			Normal	MatchRuleExclude	Negate  
//			
//			T  		T  					T
//			T		F					F
//			F		T					F
//			F		F					T
			
			boolean negate = normal ^ ( rule instanceof MatchingRuleExclude ); 
			
			
			
			
			if ( negate ){
				result.append(" ! ");
			}
			
			result.append(getPointcutType().name()+"( ");
			
			result.append(getComponentEnterAccessLevel());
			
			//return type
			result.append(" * ");
			
			String packageExpr = getFormattedExpr ( rule.getPackageExpr() );
			
			result.append(packageExpr);
			
			if ( ! packageExpr.endsWith("..") ){
				result.append(".");
			}
			
			//class expr
			result.append( getFormattedExpr ( rule.getClassExpr() ) );
			
			
			
			//method expr
			result.append(getMethodExpr(rule));
			
			result.append(" )");
			
		}
		
		return result.toString();
	}
	
	protected abstract String getMethodExpr(MatchingRule rule);

	private String getComponentEnterAccessLevel() {
		switch ( methodVisibility ){
		case Private:
				return "*";
		case Protected:
				return "!private";
		case Public:
				return "public";
		}
		return null;
	}
	
	protected String getFormattedExpr(String matchingExpression) {
		if ( ! matchingExpression.endsWith("*") ){
			return matchingExpression;
		}
		
		matchingExpression = matchingExpression.substring( 0, matchingExpression.length()-1);
		
		return matchingExpression+"..";
	}
}
