package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

public class AnomaliesBindingsCollectorASTVisitor extends ASTVisitor {

	private String testClass;
	private TestMethod currentTest;
	private List<BctModelViolation> testAnomalies;
	private CompilationUnit cu;
	
	private Map<Integer,List<BctIOModelViolation>> lineAnomalies = new HashMap<Integer, List<BctIOModelViolation>>();
	private Map<String,List<BctIOModelViolation>> assertionAnomalies = new HashMap<String, List<BctIOModelViolation>>();
	private boolean acceptAllViolations = false;
	
	
	public boolean isAcceptAllViolations() {
		return acceptAllViolations;
	}

	public void setAcceptAllViolations(boolean acceptAllViolations) {
		this.acceptAllViolations = acceptAllViolations;
	}

	public Map<String, List<BctIOModelViolation>> getAssertionAnomalies() {
		return assertionAnomalies;
	}

	public static class TestMethod {

		public TestMethod(SimpleName name, int startPosition, int i) {
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public AnomaliesBindingsCollectorASTVisitor(String testClass, List<BctModelViolation> testAnomalies, CompilationUnit ast){
		this.testClass = testClass;
		this.testAnomalies = testAnomalies;
		for ( BctModelViolation ta : testAnomalies ){
			int line = retrieveTestLine( ta );
			if ( line > 0 ){
				addAnomaly( line, (BctIOModelViolation)ta );
			}
		}
		
		this.cu = ast;
	}
	
	private void addAnomaly(int line, BctIOModelViolation ta) {
		List<BctIOModelViolation> anomalies = lineAnomalies.get(line);
		if ( anomalies == null ){
			anomalies = new ArrayList<BctIOModelViolation>();
			
			lineAnomalies.put(line, anomalies);
		}
		
		if ( mayImpactOnTestAssertions(ta) ){
			anomalies.add( ta );
		}
	}

	private boolean mayImpactOnTestAssertions(BctIOModelViolation ta) {
		ViolationData vd = ViolationsUtil.getViolationData(ta);
		for( VariableData varData : vd.getViolatedVariables() ){
			if ( acceptAllViolations || varData.getVariableName().contains("returnValue" ) ) {
				return true;
			}
		}
		
		return false;
	}

	private int retrieveTestLine(BctModelViolation ta) {
		if ( ! ( ta instanceof BctIOModelViolation ) ){
			return -1;
		}
		
		BctIOModelViolation ioViol = (BctIOModelViolation) ta;
		
		String[] st = ta.getStackTrace();
		for ( int i = 0; i < st.length; i++ ){
			String stackEl = st[i];
			if ( stackEl.startsWith(testClass) ){
				int lineSep = stackEl.lastIndexOf(':');
				if ( lineSep > 0 ){

					String afterColon = stackEl.substring(lineSep+1);
					return Integer.valueOf(afterColon);
				}
			}
		}
		return -1;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
//		System.out.println("METHOD "+node.getName());
		currentTest = new TestMethod( node.getName(), node.getStartPosition(), node.getStartPosition()+node.getLength() );
		
		
		return true;
	}

	
	
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	Map<IVariableBinding,List<BctIOModelViolation>> bindingsMap = new HashMap<IVariableBinding,List<BctIOModelViolation>>();
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		System.out.println("VAR-DECL: "+node.toString());
		
		for ( Object f : node.fragments() ){
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) f;
			
			IVariableBinding binding = fragment.resolveBinding();
			
			int curLine = cu.getLineNumber(node.getStartPosition());
			
			List<BctIOModelViolation> anomalies = this.lineAnomalies.get(curLine);

			System.out.println("FRAG "+f.getClass().getCanonicalName() +" "+binding);
			if ( anomalies != null ){
				System.out.println("Anomalies");
				bindingsMap.put(binding, anomalies);
			}
			

		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		SimpleName nodeName = node.getName();
		String fqn = nodeName.getFullyQualifiedName();
		String key = node.resolveMethodBinding().getKey();
		if( key.startsWith("Lorg/junit/Assert;.assert") ) {
			int line = cu.getLineNumber(node.getStartPosition());
			List<BctIOModelViolation> anomalies = this.lineAnomalies.get(line);
			if ( anomalies != null ){
				assertionAnomalies.put(testClass+":"+line,anomalies);
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

	@Override
	public boolean visit(Assignment node) {
		Expression lhs = node.getLeftHandSide();
		System.out.println(lhs+" "+lhs.getClass().getCanonicalName());
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleName node) {
		
		IBinding binding = node.resolveBinding();
		
		List<BctIOModelViolation> anomalies = bindingsMap.get(binding);
		if ( anomalies != null && binding != null ){
			System.out.println("ANOMALIES for "+node.getIdentifier()+" "+binding.getName()+" "+anomalies);
			
			ASTNode p = node.getParent();
			while ( p != null ){
//				System.out.println("PARENT " +p);
				if ( p instanceof MethodInvocation ){
					MethodInvocation mi = (MethodInvocation) p;
					
					if ( mi.getName().getIdentifier().startsWith("assert") ){
						System.out.println("ASSERT");
						int line = cu.getLineNumber(node.getStartPosition());
						assertionAnomalies.put(testClass+":"+line,anomalies);
					}
				}
				p = p.getParent();
			}
			
		}
		
		return super.visit(node);
	}
	
	
	

}
