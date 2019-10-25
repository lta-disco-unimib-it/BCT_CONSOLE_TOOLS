package it.unimib.disco.lta.bct.bctjavaeclipse.core.instrumentation;

import util.componentsDeclaration.MatchingRule;

public class RuleToMethodPointcutGenerator extends RuleToPointcutGenerator {

	public RuleToMethodPointcutGenerator(boolean invert,
			MethodVisibility methodVisibility, PointcutType pointcutType) {
		super(invert, methodVisibility, pointcutType);
	}

	@Override
	protected String getMethodExpr( MatchingRule rule ) {
		StringBuffer result = new StringBuffer();
		
		result.append(".");
		
		result.append( getFormattedExpr ( rule.getMethodExpr() ) );
		
		result.append("(..)");
		
		return result.toString();
		
	}

}
