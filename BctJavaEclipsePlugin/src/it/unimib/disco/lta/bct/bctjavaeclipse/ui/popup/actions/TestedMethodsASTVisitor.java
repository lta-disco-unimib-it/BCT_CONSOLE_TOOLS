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



import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public abstract class TestedMethodsASTVisitor extends ASTVisitor {

	public static class TestMethod {

		private IMethodBinding node;

		public TestMethod(MethodDeclaration node) {
			this.node = node.resolveBinding();
		}
		
		public String getFullSignature(){
			String s = node.getKey();
			return node.getDeclaringClass().getBinaryName()+"."+node.getName();
		}
	}


	protected TestMethod currentTest;
	protected CompilationUnit cu;
	protected String testClass;
	public TestedMethodsASTVisitor(String testClass, CompilationUnit ast) {
		this.testClass = testClass;
		this.cu = ast;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		currentTest = new TestMethod( node );
		return true;
	}
	
	public abstract Set<String> getTestedMethods();
}
