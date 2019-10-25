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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.dom.IName;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCapture;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTReferenceOperator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateParameter;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexBinding;
import org.eclipse.cdt.core.index.IIndexFile;
import org.eclipse.cdt.core.index.IIndexFileLocation;
import org.eclipse.cdt.core.index.IIndexFileSet;
import org.eclipse.cdt.core.index.IIndexInclude;
import org.eclipse.cdt.core.index.IIndexMacro;
import org.eclipse.cdt.core.index.IIndexName;
import org.eclipse.cdt.core.index.IndexFilter;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.CodeReaderCache;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.ICodeReaderCache;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IToken;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.AbstractCodeReaderFactory;
import org.eclipse.cdt.internal.core.dom.NullCodeReaderFactory;
import org.eclipse.cdt.internal.core.dom.parser.ASTAmbiguousNode;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.model.Function;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;
import org.eclipse.cdt.internal.core.model.Method;
import org.eclipse.cdt.internal.core.model.TranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import cpp.gdb.CSourceAnalyzer;
import cpp.gdb.LocalVariableDeclaration;
import cpp.gdb.coverage.FileUtil;

public class CDTStandaloneCFileAnalyzer implements CSourceAnalyzer {



	private static ICodeReaderFactory createReaderFactory() {
		return  new AbstractCodeReaderFactory(null){


			public CodeReader createCodeReaderForInclusion(String path) {
				try {
					if (!new File(path).isFile())
						return null;
					return new CodeReader(path);
				} catch (IOException e) {
					return null;
				}
			}

			public CodeReader createCodeReaderForTranslationUnit(String path) {
				try {
					if (!new File(path).isFile())
						return null;
					return new CodeReader(path);
				} catch (IOException e) {
					return null;
				}
			}

			public ICodeReaderCache getCodeReaderCache() {
				return null;
			}

			public int getUniqueIdentifier() {
				return 0;
			}

			@Override
			public CodeReader createCodeReaderForInclusion(IIndexFileLocation ifl, String astPath) throws CoreException, IOException {
				return createCodeReaderForInclusion(astPath);
			}
		};
	}

	public static void main(String args[]) throws IOException, CoreException{
		System.setSecurityManager(null);

		File f = new File("/home/fabrizio/Experiments/VTT_SIMPLIFIED/V0/P2P_Joints_TG3.c");

		//		List<FunctionDeclaration> funcs = getFunctionsDeclaredInFile(f);

		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();
		analyzer.retrieveFunctionsDeclaredInFile(f);

	}

	/* (non-Javadoc)
	 * @see console.CSourceAnalyzer#retrieveFunctionsDeclaredInFile(java.io.File)
	 */
	@Override
	public List<cpp.gdb.FunctionDeclaration> retrieveFunctionsDeclaredInFile(File f)
			throws FileNotFoundException {
		IParserLogService log = new DefaultLogService();

		//        String code = "class Class { public: int x,y; Class();~Class(); private: Class f(); }; int function(double parameter) { return parameter; };";
		final List<Integer> lineOffsets = new ArrayList<Integer>();
		StringBuffer sb = new StringBuffer();
		int offset = 0;
		for ( String line : util.FileUtil.getLines(f) ){
			sb.append(line);
			sb.append("\n");
			offset+=line.length()+1;
			lineOffsets.add(offset+1);
		}

		CodeReader reader = new CodeReader(sb.toString().toCharArray());
		Map definedSymbols = new HashMap();
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
		ICodeReaderFactory readerFactory = createReaderFactory();

		final List<cpp.gdb.FunctionDeclaration> result = new ArrayList<cpp.gdb.FunctionDeclaration>();

		IASTTranslationUnit translationUnit;
		try {
			translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(reader, info, readerFactory,null, log);
		} catch (CoreException e1) {
			return result;
		}




		ASTVisitor visitor = new ASTVisitor() {



			private cpp.gdb.FunctionDeclaration currentFunction;

			@Override
			public int visit(IASTExpression expression) {
				//System.out.println(expression.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(IASTStatement statement) {
				//System.out.println(statement.getRawSignature());
				//System.out.println("DECLARATION "+statement.getRawSignature()+" "+statement.getClass().getCanonicalName());

				if ( statement instanceof CPPASTDeclarationStatement ){
					CPPASTDeclarationStatement decl = (CPPASTDeclarationStatement) statement;
					IASTDeclaration declaration = decl.getDeclaration();
					if ( declaration instanceof CPPASTSimpleDeclaration ){
						CPPASTSimpleDeclaration simpleDeclaration = (CPPASTSimpleDeclaration) declaration;

						for ( IASTDeclarator declarator : simpleDeclaration.getDeclarators()){
							String name = declarator.getName().toString();
							LocalVariableDeclaration varDecl = new LocalVariableDeclaration( name, calculateLineNo( lineOffsets, simpleDeclaration.getOffset() ) );
							if ( currentFunction != null ){
								//System.out.println("CurrentFunction: "+name+" "+currentFunction.getEndLine()+" varDecl "+varDecl.getLineNo());
								if ( currentFunction.getEndLine() >= varDecl.getLineNo() ){
									currentFunction.addLocalVariable( varDecl );
								}
							}
						}

					}

				}
				return ASTVisitor.PROCESS_CONTINUE;
			}



			@Override
			public int visit(IASTPointerOperator ptrOperator) {
				//System.out.println(ptrOperator.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(ICASTDesignator designator) {
				//System.out.println(designator.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(ICPPASTBaseSpecifier baseSpecifier) {
				//System.out.println(baseSpecifier.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(IASTDeclSpecifier declSpec) {
				//System.out.println(declSpec.getRawSignature() +" " +declSpec.getClass().getCanonicalName());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(IASTTypeId typeId) {
				//System.out.println(typeId.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int leave (IASTDeclaration declaration) {
				if ( declaration instanceof CPPASTFunctionDefinition ){
					//					currentFunction = null;
					//System.out.println("DECLARATION EXIT "+declaration.getRawSignature()+" "+declaration.getClass().getCanonicalName());
				}

				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(IASTDeclaration declaration) {
				//System.out.println(declaration.getRawSignature());
				if ( declaration instanceof CPPASTFunctionDefinition ){
					//System.out.println("DECLARATION ENTER "+declaration.getRawSignature()+" "+declaration.getClass().getCanonicalName());
					CPPASTFunctionDefinition def = (CPPASTFunctionDefinition) declaration;
					try {

						String returnType = def.getSyntax().toString();
						String functionName = null;


						boolean afterType = false;

						//						System.out.println("CHILDREN ");
						for ( IASTNode node : def.getChildren() ){
							String token = node.getSyntax().toString();
							//							System.out.println("\t"+token);
							//							System.out.println(node.getLeadingSyntax());
						}

						for ( IASTNode node : def.getChildren() ){
							String token = node.getSyntax().toString();



							if ( ! afterType ){
								if ( returnType.equals(token) ){
									afterType = true;
								}
							} else {
								if ( token == null || token.equals("null") ){
									continue;
								} else if ( "*".equals(token) ){
									returnType += "*";
								} else {
									//function name found
									functionName  = token;
									break;
								}
							}


						}

						currentFunction = new cpp.gdb.FunctionDeclaration(functionName, returnType );
						result.add(currentFunction);
					} catch (ExpansionOverlapsBoundaryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//            	  System.out.println("CLASS "+declaration.getClass().getCanonicalName());
				//            	  System.out.println(declaration.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int leave(IASTDeclarator declarator) {

				if ( declarator instanceof CPPASTFunctionDeclarator ){
					//System.out.println("DECLARATOR EXIT "+declarator.getRawSignature()+" "+declarator.getClass().getCanonicalName());
					//					currentFunction = null;
				}
				return ASTVisitor.PROCESS_CONTINUE;
			}

			@Override
			public int visit(IASTDeclarator declarator) {

				if ( declarator instanceof CPPASTFunctionDeclarator ){
					//System.out.println("DECLARATOR ENTER "+declarator.getRawSignature()+" "+declarator.getClass().getCanonicalName());
					CPPASTFunctionDeclarator decl = (CPPASTFunctionDeclarator) declarator;

					String functionName = decl.getName().getRawSignature() ;

					String pointers = "";
					for ( IASTPointerOperator ptr : decl.getPointerOperators() ){
						pointers += ptr.getRawSignature();
					}

					Set<String> pointerArgs = new HashSet<String>();
					Set<String> scalarArgs = new HashSet<String>();
					Set<String> referenceArgs = new HashSet<String>();

					StringBuffer pb = new StringBuffer();
					for ( ICPPASTParameterDeclaration parameter : decl.getParameters() ){
						try {

							String parType = parameter.getSyntax().toString();

							pb.append ( parType );
							String parameterName = parameter.getDeclarator().getName().toString();

							IASTPointerOperator[] pointerOperators = parameter.getDeclarator().getPointerOperators();
							for ( IASTPointerOperator ptr : pointerOperators ){
								if ( ! ( ptr instanceof ICPPASTReferenceOperator  ) ){
									pb.append ( "*" );
									pointerArgs.add(parameterName);
								} else {
									referenceArgs.add(parameterName);
								}
							}

							if ( pointerOperators.length == 0 ){
								scalarArgs.add(parameterName);
							}

							pb.append(",");

						} catch (ExpansionOverlapsBoundaryException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if ( functionName.contains("::") ){ //CPP function, parameters required
						functionName+="(";
						if ( pb.length() > 0 ){
							pb.setLength(pb.length()-1);
							functionName += pb.toString();
						}
						functionName+=")";

					}

					IASTNode parent = decl.getParent();

					String returnType = "";
					if ( parent instanceof CPPASTFunctionDefinition ){
						CPPASTFunctionDefinition def = (CPPASTFunctionDefinition) parent;

						try {


							returnType = def.getDeclSpecifier().getRawSignature();
							if ( returnType.isEmpty() ){
								returnType = def.getSyntax().toString(); //necessary for constructors
							}
						} catch (ExpansionOverlapsBoundaryException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					returnType += pointers;


					cpp.gdb.FunctionDeclaration functionDeclaration = new cpp.gdb.FunctionDeclaration(functionName, returnType, decl.getParameters().length, null ); 
					functionDeclaration.setPointerArgs( pointerArgs );
					functionDeclaration.setScalarArgs( scalarArgs );
					functionDeclaration.setReferenceArgs( referenceArgs );

					if ( decl.takesVarArgs() ){
						functionDeclaration.setTakesVarArgs( true );
					}

					if ( parent instanceof CPPASTFunctionDefinition ){
						CPPASTFunctionDefinition def = (CPPASTFunctionDefinition) parent;
						functionDeclaration.setStartLine( calculateLineNo(lineOffsets, def.getOffset() ) );
						functionDeclaration.setEndLine( calculateLineNo(lineOffsets, def.getOffset() + def.getLength() ) );
					}

					currentFunction = functionDeclaration;
					result.add( functionDeclaration );
				}
				//				System.out.println(declarator.getRawSignature());
				return ASTVisitor.PROCESS_CONTINUE;
			}

			public int visit(IASTName name) {
				//System.out.println(name.getRawSignature()+" "+name.getClass().getCanonicalName()+" "+name.getParent().getClass().getCanonicalName());
				return ASTVisitor.PROCESS_CONTINUE;
			}
		};
		visitor.shouldVisitStatements = true;
		visitor.shouldVisitDeclarations = true;
		visitor.shouldVisitDeclarations = false;
		visitor.shouldVisitNames = false;
		visitor.shouldVisitBaseSpecifiers = false;
		visitor.shouldVisitDeclarators = true;
		visitor.shouldVisitDesignators = false;
		visitor.shouldVisitTypeIds = false;
		visitor.shouldVisitNames = false;
		
		try { 
			translationUnit.accept(visitor);
		} catch ( Exception e ){
			e.printStackTrace();
		}
		//		Iterator<cpp.gdb.FunctionDeclaration> functionIt = result.iterator();
		//		while ( functionIt.hasNext() ){
		//			cpp.gdb.FunctionDeclaration function = functionIt.next();
		//			
		//			if ( function.getStartLine() )
		//		}


		return result;
	}

	private int calculateLineNo(List<Integer> lineOffsets, int offset) {
		int currentLine = 1;

		for ( int nextLineStart : lineOffsets ){
			if ( nextLineStart > offset ){
				return currentLine;
			}
			currentLine++;
		}
		return 0;
	}
}
