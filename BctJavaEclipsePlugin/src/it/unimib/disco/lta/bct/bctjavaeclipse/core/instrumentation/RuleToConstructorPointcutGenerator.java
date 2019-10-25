package it.unimib.disco.lta.bct.bctjavaeclipse.core.instrumentation;

import util.componentsDeclaration.MatchingRule;

public class RuleToConstructorPointcutGenerator extends RuleToPointcutGenerator {

	public RuleToConstructorPointcutGenerator(boolean invert,
			MethodVisibility methodVisibility, PointcutType pointcutType) {
		super(invert, methodVisibility, pointcutType);
	}

	@Override
	protected String getMethodExpr( MatchingRule rule ) {
		return ".new(..)";
		
	}

}
