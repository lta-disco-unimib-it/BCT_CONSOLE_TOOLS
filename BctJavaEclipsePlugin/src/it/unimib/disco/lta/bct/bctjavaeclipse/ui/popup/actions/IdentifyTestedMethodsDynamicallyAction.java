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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.JavaResourcesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaProjectSourceContainer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class IdentifyTestedMethodsDynamicallyAction  implements IObjectActionDelegate  {

	private Map<String, Set<IMethod>> testedMethodsMap;



	public IdentifyTestedMethodsDynamicallyAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}

	public void run(IAction action) {
		run(action, null);
	}

	public void run(IAction action, IJobChangeListener jobListener) {
		try {
			
			final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

			if ( selection == null ){
				throw new CoreException(new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, "Nothing was selected" ) );
			}
			
			if ( ! ( selection instanceof IStructuredSelection ) ){
				return;
			}
			
			
			
			
			List<ICompilationUnit> compilationUnits = JavaResourcesUtil.getCompilationUnitsFromSelection(selection);
			
			if ( compilationUnits.size() == 0 ){
				return;
			}
			
			IJavaProject jprj = compilationUnits.get(0).getJavaProject();
			
			File testedMethodsFile = new File( jprj.getProject().getLocation().toFile(), "TestedMethods.txt" );
			
			testedMethodsMap = load(testedMethodsFile, jprj);
			
			Set<String> testedMethods = new HashSet<>();
			IProject prj = null;
			for ( ICompilationUnit compilationUnit : compilationUnits ){
				
				
				prj = compilationUnit.getResource().getProject();
				testedMethods.addAll( processTestClass(compilationUnit) );
			}
			
			
			if ( prj != null ){
				File file = prj.getLocation().toFile();
				File dest = new File( file, "verifiedMethods.txt" );
				try {
					writeTestedMethods( dest, testedMethods );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			System.out.println(testedMethods);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	private void writeTestedMethods(File dest, Set<String> testedMethods) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
		try {
			for ( String method : testedMethods ){
				bw.write(method);
				bw.newLine();
			}
		} finally {
			bw.close();
		}
	}

	private Set<String> processTestClass(ICompilationUnit testCompilationUnit) {

		//ICompilationUnit testCompilationUnit = JavaResourcesUtil.getCompilationUnit(mc, testClassName);

		ASTParser parser = ASTParser.newParser(AST.JLS4); 
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(testCompilationUnit); // set source
		parser.setResolveBindings(true); // we need bindings later on

		CompilationUnit ast = (CompilationUnit) parser.createAST(null /* IProgressMonitor */); // parse
		
		
		
		TestedMethodsASTVisitor testAstVisitor = new TestedMethodsDynamicASTVisitor(testCompilationUnit.getElementName(), ast, testedMethodsMap );
		ast.accept(testAstVisitor);

		
		Set<String> testedMethods = testAstVisitor.getTestedMethods();

		return testedMethods;
		
	}
	
	private Map<String, Set<IMethod>> load(File dynamicData, IJavaProject jprj) throws IOException {
		

		
		
		BufferedReader r = new BufferedReader ( new FileReader(dynamicData) );
		
		Map<String,Set<IMethod>> map = new HashMap<String, Set<IMethod>>();
		
		String line;
		try {
			while ( ( line = r.readLine() ) != null ){
				String[] columns = line.split("\t");
				String testedMethod = columns[0];
				
				String testClass = columns[1];
				String testLine = columns[2];
				
				try {
					IMethod iMethod = JavaResourcesUtil.retrieveIMethod( jprj, testedMethod );
				
					if ( iMethod == null ){
						continue;
					}
					
					
					String key = testClass+":"+testLine;
					
					Set<IMethod> set = map.get(key);
					
					if ( set == null ){
						set = new HashSet<IMethod>();
						map.put(key, set);
					}
					
					set.add( iMethod );
					
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return map;
		} finally {
			r.close();
		}
		
		
	}

	public void printTestAssertionAnomalies( BufferedWriter w, Map<String, List<BctIOModelViolation>> anomalies) throws IOException {
		String SEP = "\t";

		Set<String> processed = new HashSet<String>();

		for (Entry<String, List<BctIOModelViolation>> e : anomalies.entrySet() ){
			String testAssertion = e.getKey();
			for( BctIOModelViolation v : e.getValue() ){
				String key = testAssertion+SEP+v.getViolation();

				if ( processed.contains(key) ){
					continue;
				}
				processed.add(key);

				w.append(testAssertion+SEP+v.getViolation()+SEP+v.getViolatedModel()+SEP+v.getStackTrace()[1]);	
				w.newLine();
			}

		}
	}

	public Set<String> extractTestCasesWithViolations(
			List<BctModelViolation> testAnomalies) {
		Set<String> testCases = new HashSet<String>();
		for( BctModelViolation testAnomaly : testAnomalies){
			testCases.addAll( Arrays.asList( testAnomaly.getCurrentTests() ) );
		}
		return testCases;
	}



	private void setTracesToExclude(MonitoringConfiguration mc, InferModelWizardResult result) throws ConfigurationFilesManagerException {


		boolean skipFailingTests = result.getSkipFailingTests();
		boolean skipFailingActions = result.getSkipFailingActions();
		boolean skipFailingProcesses = result.getSkipFailingProcesses();

		ModelInference.setupInferenceFilters(mc, skipFailingTests, skipFailingActions, skipFailingProcesses);
		//
	}



	public void selectionChanged(IAction action, ISelection selection) {
	}


}
