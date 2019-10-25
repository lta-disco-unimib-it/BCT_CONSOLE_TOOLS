package it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util;

import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.model.Function;
import org.eclipse.cdt.internal.core.model.TranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ProgramPointsUtil {

	public static List<String> getVariables(IFile sourceFile, int lineNumber ) throws CoreException{
		
		ICElement cElementFile = CoreModel.getDefault().create(sourceFile);
		
		System.out.println(cElementFile.getClass().getCanonicalName());
		
		if ( ! ( cElementFile instanceof TranslationUnit ) ){
			return null;
		}
		
		TranslationUnit tu = (TranslationUnit) cElementFile;
		tu.accept(new ICElementVisitor() {
			
			@Override
			public boolean visit(ICElement element) throws CoreException {
				System.out.println(element.getElementName());
				System.out.println(element.getClass().getCanonicalName());
				
				if ( element instanceof Function ){
					Function function = (Function) element;
					for ( ICElement child : function.getChildren() ){
						System.out.println(child.getElementName());
					}
				}
				return true;
			}
		});
		
		
		
		IIndex index = CCorePlugin.getDefault().getIndexManager().getIndex(cElementFile.getCProject());
		try {
			index.acquireReadLock();
			IASTTranslationUnit ast = tu.getAST();
			for ( IASTDeclaration decl : ast.getDeclarations() ){
				System.out.println(decl.getRawSignature());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			index.releaseReadLock();
		}
		
		
		return null;
		
	}
}
