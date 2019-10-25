package it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AdvancedViolationsUtil;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import cpp.gdb.DemangledNamesUtils;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.GlobalVarsDetector.GlobalVariable;

public class FunctionsUtil {

	public static String extractMethodReturnType(FunctionMonitoringData functionData) {
		FunctionDeclaration function = getFunctionDeclaration(functionData);
		
		if ( function == null ){
			return null;
		}
		
		return function.getReturnType();
	}

	public static FunctionDeclaration getFunctionDeclaration(
			FunctionMonitoringData functionData) {
		IFile sourceFile = AdvancedViolationsUtil.getFileForLocation(functionData.getAbsoluteFile().getAbsolutePath());
		if ( sourceFile == null ){
			return null;
		}
		
		List<FunctionDeclaration> functions = CFileUtil.getFunctionsDeclaredInFile(sourceFile);
		
		String demangled = functionData.getDemangledName();
		
		boolean isCpp = DemangledNamesUtils.isCppSignature(demangled);
		
		for ( FunctionDeclaration function : functions ){
			try {
				boolean match = false;			
				String signature = function.getSignature();
				
				
				
				if ( isCpp ){
					
					//FIXME: check if this implementation works
					if ( signature.equals(demangled) ) {
						match = true;
					}
				} else {
					String functionName = DemangledNamesUtils.getFunctionNameOnly(signature);
					if ( demangled.equals(functionName) ) {
						match = true;
					}
				}
				
				if ( match ){
					return function;
					
				}
				System.out.println(signature);
			} catch (CModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("restriction")
	public static List<GlobalVariable> retrieveParameters(FunctionDeclaration function) {
		List<GlobalVariable> vars = new ArrayList<GlobalVariable>();
		String[] parameterTypes = function.getParameterTypes();
		try {
			
			
			int parStart = function.getSignature().indexOf('(');
			if ( parStart<=0){
				return vars;
			}
			int end = function.getSignature().indexOf(')');
			
			String parDecls = function.getSignature().substring(parStart+1, end);
			parDecls = parDecls.trim();
			
			if ( parDecls.isEmpty() ){
				return vars;
			}
			
			int c = -1;
			for ( String parAndType : parDecls.split(",") ){
				c++;
				String[] tokens = parAndType.trim().split(" ");
				if ( tokens.length == 0 ){
					continue;
				}
				String type = parameterTypes[c].replace("*", "");
				boolean isPointer = type.contains("*");
				GlobalVariable v = new GlobalVariable(tokens[tokens.length-1], parameterTypes[c], isPointer);
				vars.add(v);
			}
			

			
		} catch (CModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vars;
	}

	public static boolean isVoid(FunctionDeclaration function) {
		return function.getReturnType().equals("void");
	}

	
}
