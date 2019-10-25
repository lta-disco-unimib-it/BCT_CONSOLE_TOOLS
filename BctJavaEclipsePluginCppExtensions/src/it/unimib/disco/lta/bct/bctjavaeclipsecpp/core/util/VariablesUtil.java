package it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.ASTCompletionNode;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class VariablesUtil {

	public static List<String> getVariables(IFile sourceFile, int lineNumber)
			throws CoreException {
		

		ITranslationUnit tu = (ITranslationUnit) CoreModel.getDefault().create(
				sourceFile);
		return getVariables(tu, lineNumber);
	}
		public static List<String> getVariables(ITranslationUnit tu , int lineNumber) throws CoreException{
			List<String> variables = new ArrayList<String>();
		IASTTranslationUnit ast = tu.getAST(null,
				ITranslationUnit.AST_SKIP_ALL_HEADERS);
		// IASTTranslationUnit ast = tu.getAST();

		ast.accept(new ASTVisitor(true) {
			@Override
			public int visit(IASTName name) {
				IBinding binding = name.resolveBinding();
				if (binding == null)
					System.out.println("## Null binding for " + name);
				else {
					// System.out.println("!! " + binding.getName());
					if (binding instanceof IVariable) {
						IVariable variable = (IVariable) binding;
						IASTFileLocation location = name.getFileLocation();

						if (location != null)
							System.out.println(location.getFileName() + " @ "
									+ location.getStartingLineNumber() + ": "
									+ variable.getType() + " "
									+ variable.getName() + " with owner "
									+ variable.getOwner());
						else
							System.out.println("null @ null: "
									+ variable.getType() + " "
									+ variable.getName() + " with owner "
									+ variable.getOwner());
						try {
							IScope scope = variable.getScope();
							if (scope != null)
								System.out.println("\tScope: "
										+ scope.getScopeName());
						} catch (DOMException e) {
							System.out.println("!!! Exception: "
									+ e.getLocalizedMessage());
						}

					}
				}

				return super.visit(name);
			}
		});

		ast.getChildren();

		return variables;
	}

	public static List<String> getVariables2(IFile sourceFile, int lineNumber) throws CoreException {
		List<String> variables = new ArrayList<String>();

		ITranslationUnit tu = (ITranslationUnit) CoreModel.getDefault().create(sourceFile);
		final IASTTranslationUnit ast = tu.getAST(null,
				ITranslationUnit.AST_SKIP_ALL_HEADERS);

		ast.accept(new ASTVisitor(true) {

			@Override
			public int visit(IASTName name) {
				//System.out.println("Visiting " + name.resolveBinding().getName());
				
				IASTFileLocation fl = name.getFileLocation();
				if (fl != null) {
					int actualLine = fl.getStartingLineNumber();
					
					if (actualLine == 13) {
						
						ASTCompletionNode compNode = new ASTCompletionNode(null, ast);
						IASTName[] names = compNode.getNames();
						System.out.println(names.length);
						
						for (int i = 0; i < names.length; i++) {
							System.out.println(names[i]);
						}
					}
				}

				// IBinding binding = name.resolveBinding();
				// if (binding == null)
				// System.out.println("## Null binding for " + name);
				// else {
				// System.out.println("!! " + binding.getName());
				/*
				 * if (binding instanceof IVariable) { IVariable variable =
				 * (IVariable) binding; IASTFileLocation location =
				 * name.getFileLocation(); }
				 */
				// }
				return super.visit(name);
			}
		});
		ast.getChildren(); // Force the execution of method ASTVisitor.visit

		return variables;
	}
}
