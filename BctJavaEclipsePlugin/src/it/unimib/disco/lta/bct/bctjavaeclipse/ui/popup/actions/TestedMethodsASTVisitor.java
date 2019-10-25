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
