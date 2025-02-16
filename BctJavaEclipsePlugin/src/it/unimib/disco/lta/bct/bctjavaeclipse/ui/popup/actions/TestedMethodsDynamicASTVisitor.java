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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class TestedMethodsDynamicASTVisitor extends TestedMethodsStaticASTVisitor {

	private Map<String, Set<IMethod>> testedMethodsMap;

	public TestedMethodsDynamicASTVisitor(String testClass, CompilationUnit ast, Map<String, Set<IMethod>> testedMethodsMap) {
		super(testClass, ast);
		this.testedMethodsMap = testedMethodsMap;
	}
	


	@Override
	public boolean visit(MethodInvocation node) {
		if ( withinAssert(node) ){
			
			Collection<IMethod> methodsInvoked = extractMethodInvocationsInExpression(node);
			if ( methodsInvoked != null ){
				testedInvocations.addAll(methodsInvoked);
			}
		}

		return true;
	}

	private Set<IMethod> retrieveMethodsInvokedInLine(String fullSignature, int line) {
		int dot = fullSignature.lastIndexOf('.');
		fullSignature = fullSignature.substring(0,dot);
//		if ( line == 1095 ){
//			for ( String keys : testedMethodsMap.keySet() ){
//				System.out.println(testedMethodsMap.get(keys));
//			}
//			System.out.println("");
//		}
		return this.testedMethodsMap.get(fullSignature+":"+line);
	}

	@Override
	protected Collection<IMethod> extractMethodInvocationsInExpression(Expression rhs) {
		
		int lineNo = cu.getLineNumber(rhs.getStartPosition());
		String testSignature = currentTest.getFullSignature();
		
		return retrieveMethodsInvokedInLine(testSignature,lineNo);
		
	}




}
