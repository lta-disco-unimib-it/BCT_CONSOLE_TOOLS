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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class TestedMethodsStaticASTVisitor extends TestedMethodsASTVisitor {

	
	public TestedMethodsStaticASTVisitor(String testClass, CompilationUnit ast) {
		super(testClass, ast);
	}




	Map<IVariableBinding, Collection<IMethod>> bindingsToMethodsMap = new HashMap<IVariableBinding, Collection<IMethod>>();
	Map<IVariableBinding, List<IVariableBinding>> bindingsToVariablesMap = new HashMap<IVariableBinding, List<IVariableBinding>>();
	
	

	protected boolean withinAssert(ASTNode node) {

		ASTNode p = node.getParent();			

		while ( p != null ){
			if ( p instanceof MethodInvocation ){
				MethodInvocation mi = (MethodInvocation) p;

				if ( mi.getName().getIdentifier().startsWith("assert") ){
					return true;
				}
			}
			p = p.getParent();
		}

		return false;
	}



	
	
	@Override
	public boolean visit(Assignment node) {
		Expression lhs = node.getLeftHandSide();
		System.out.println("ASSIGNMENT"+lhs+" "+lhs.getClass().getCanonicalName());
		
		Expression rhs = node.getRightHandSide();
		System.out.println("ASSIGNMENT"+rhs+" "+rhs.getClass().getCanonicalName());
		
		if ( lhs instanceof SimpleName ){
			IBinding binding = ((SimpleName) lhs).resolveBinding();
			
			updateBindingsToExpressionMembers((IVariableBinding) binding, rhs);
		}
		
		return super.visit(node);
	}

	public void updateBindingsToExpressionMembers(IVariableBinding binding,
			Expression rhs) {
		Collection<IMethod> methodInvocations = extractMethodInvocationsInExpression( rhs );
		List<IVariableBinding> variablesUsed = extractVariablesUsedInExpression(rhs);
		
		if ( methodInvocations != null && methodInvocations.size() > 0  ){
			bindingsToMethodsMap.put((IVariableBinding)binding, methodInvocations );
		}
		
		if ( variablesUsed.size() > 0 ){
			bindingsToVariablesMap.put((IVariableBinding)binding, variablesUsed);
		}
	}


	protected Collection<IMethod> extractMethodInvocationsInExpression( Expression rhs ) {
		List<IMethod> methodInvocations = new ArrayList<>();
		rhs.getStartPosition();
		if ( rhs instanceof MethodInvocation ){
			IMethod invokedMethod = (IMethod)((MethodInvocation)rhs).resolveMethodBinding().getJavaElement();
			if ( invokedMethod != null ){
				methodInvocations.add(invokedMethod);
			}
		} else 	if ( rhs instanceof ClassInstanceCreation ){
			IMethod invokedMethod = (IMethod) ((ClassInstanceCreation) rhs).resolveConstructorBinding().getJavaElement();
			if ( invokedMethod != null ){
				methodInvocations.add( invokedMethod );
			}
		} else if ( rhs instanceof InfixExpression ){
			InfixExpression exp = (InfixExpression) rhs;
			
			methodInvocations.addAll( extractMethodInvocationsInExpression( exp.getLeftOperand() ) );
			methodInvocations.addAll( extractMethodInvocationsInExpression( exp.getRightOperand() ) );
		} else if ( rhs instanceof PostfixExpression ){
			PostfixExpression exp = (PostfixExpression) rhs;
			
			methodInvocations.addAll( extractMethodInvocationsInExpression( exp.getOperand() ) );
		} else if ( rhs instanceof ParenthesizedExpression ){
			ParenthesizedExpression exp = (ParenthesizedExpression) rhs;
			
			methodInvocations.addAll( extractMethodInvocationsInExpression(  exp.getExpression() ) );
		}
		
		return methodInvocations;
	}
	
	private List<IVariableBinding> extractVariablesUsedInExpression( Expression rhs ) {
		List<IVariableBinding> variablesUsed = new ArrayList<>();
		
		if ( rhs instanceof SimpleName ){
			SimpleName sn = (SimpleName) rhs;
			IBinding binding = sn.resolveBinding();
			
			if ( binding instanceof IVariableBinding ){
				variablesUsed.add((IVariableBinding) binding);
			}
			
		} else if ( rhs instanceof InfixExpression ){
			InfixExpression exp = (InfixExpression) rhs;
			variablesUsed.addAll( extractVariablesUsedInExpression( exp.getLeftOperand() ) );
			variablesUsed.addAll( extractVariablesUsedInExpression( exp.getRightOperand() ) );
			for ( Object extendedOperand : exp.extendedOperands() ){
				variablesUsed.addAll( extractVariablesUsedInExpression( (Expression) extendedOperand ) );
			}
		} else if ( rhs instanceof PostfixExpression ){
			PostfixExpression exp = (PostfixExpression) rhs;
			
			variablesUsed.addAll( extractVariablesUsedInExpression( exp.getOperand() ) );
		} else if ( rhs instanceof ParenthesizedExpression ){
			ParenthesizedExpression exp = (ParenthesizedExpression) rhs;
			
			variablesUsed.addAll( extractVariablesUsedInExpression(  exp.getExpression() ) );
		}
		
		return variablesUsed;
	}
	


	@Override
	public boolean visit(VariableDeclarationExpression node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		return super.visit(node);
	}

	
	
	
	Map<Expression, IVariableBinding> pendingBindings = new HashMap<>();
	protected Set<IMethod> testedInvocations = new HashSet<IMethod>();
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		System.out.println("VAR-DECL: "+node.toString());

		for ( Object f : node.fragments() ){
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) f;
			Expression initializer = fragment.getInitializer();
			IVariableBinding binding = fragment.resolveBinding();

			if ( initializer != null ){
				updateBindingsToExpressionMembers(binding, initializer);
			}

		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {

		if ( withinAssert(node) ){
			IMethodBinding mb = node.resolveMethodBinding();
			IJavaElement jEl = mb.getJavaElement();
			if ( jEl instanceof IMethod ){
				testedInvocations.add((IMethod) jEl);
			} else {
				throw new IllegalArgumentException();
			}
		}

		return true;
	}



	@Override
	public boolean visit(Block node) {
		//		System.out.println(node.toString());
		return true;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(TextElement node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		System.out.println(node.toString());
		return true;
	}



	/**
	 * This method finds variables checked in assertions, and put the method assigned to this variable in the list of tested method
	 * @param node
	 * @return
	 */
	@Override
	public boolean visit(SimpleName node) {

		System.out.println(node);
		IBinding binding = node.resolveBinding();

		System.out.println("BINDING for "+node.getIdentifier()+" "+binding.getName());

		if ( withinAssert ( node ) ){
			Set<IMethod> invokedMethods = retrieveUsedMethods ( binding ) ;		
			if ( invokedMethods == null ){
				return true;
			}

			testedInvocations.addAll( invokedMethods );	
		}

		return super.visit(node);
	}

	private Set<IMethod> retrieveUsedMethods(IBinding binding) {
		
		Set<IMethod> usedMethods = new HashSet<IMethod>();
		
		if ( bindingsToMethodsMap.containsKey(binding) ){
			usedMethods.addAll( bindingsToMethodsMap.get(binding) );
		}
		
		
		LinkedList<IVariableBinding> variablesToProcess = new LinkedList<>();
		if ( bindingsToVariablesMap.containsKey(binding) ){
			variablesToProcess.addAll( bindingsToVariablesMap.get(binding) );
		}
		
		while( ! variablesToProcess.isEmpty() ){
			IVariableBinding current = variablesToProcess.pop();
			
			if ( bindingsToMethodsMap.containsKey(current) ){
				usedMethods.addAll( bindingsToMethodsMap.get(current) );
			}
			
			if ( bindingsToVariablesMap.containsKey(current) ){
				variablesToProcess.addAll( bindingsToVariablesMap.get(current) );
			}
		}
		
		return usedMethods;
	}
	
	
	public Set<String> getTestedMethods() {
		Set<String> result = new HashSet<String>();
		
		for ( IMethod e : testedInvocations ){
			try {
				result.add(e.getDeclaringType().getFullyQualifiedName()+"."+e.getElementName()+e.getSignature());
			} catch (JavaModelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;
	}
	
}
