/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
