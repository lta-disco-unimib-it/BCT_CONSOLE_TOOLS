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
