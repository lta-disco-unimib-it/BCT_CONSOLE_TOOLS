package it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;
import org.eclipse.cdt.internal.core.model.TranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class CFileUtil {

	public static List<FunctionDeclaration> getFunctionsDeclaredInFile(IFile sourceFile){
		final List<FunctionDeclaration> functions = new ArrayList<FunctionDeclaration>();
		ICElement cElementFile = CoreModel.getDefault().create(sourceFile);
		
		System.out.println(cElementFile.getClass().getCanonicalName());
		
		if ( ! ( cElementFile instanceof TranslationUnit ) ){
			return null;
		}
		
		TranslationUnit tu = (TranslationUnit) cElementFile;
		try {
			tu.accept(new ICElementVisitor() {
				
				@Override
				public boolean visit(ICElement element) throws CoreException {
					System.out.println(element.getElementName());
					System.out.println(element.getClass().getCanonicalName());
					
					if ( element instanceof FunctionDeclaration ){
						FunctionDeclaration function = (FunctionDeclaration) element;
						functions.add(function);
//						
//						for ( ICElement child : function.getChildren() ){
//							System.out.println(child.getElementName());
//						}
					}
//					
//					if ( element instanceof Method ){
//						Function function = (Function) element;
//						functions.add(function);
//						for ( ICElement child : function.getChildren() ){
//							System.out.println(child.getElementName());
//						}
//					}
					return true;
				}
			});
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return functions;
	}
}
