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
