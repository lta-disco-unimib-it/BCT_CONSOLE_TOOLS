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
package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import modelsFetchers.ModelsFetcherException;

import org.eclipse.core.runtime.CoreException;

import tools.InvariantGenerator;
import tools.fshellExporter.CBMCAssertionsInjector;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.PropertyUtil;

public class RunUnitChecking {
	


	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws ModelsFetcherException 
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws DefaultOptionsManagerException 
	 * @throws CoreException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
		
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS, "true");
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_EXPAND_EQUIVALENCES, "false" );
		
		ArrayList<String> argsArr = new ArrayList<String>();
		
		
		
		for ( int i = 0; i < args.length; i++ ){
			if ( args[i].equals("--executable") ){
				System.setProperty(CBCMRegressionsDetector.BCT_GOTO_PROGRAM, args[++i] );
			} else if ( args[i].equals("--compile-cmd") ){
				System.setProperty(CBCMRegressionsDetector.BCT_COMPILE_COMMAND, args[++i] );
			} else if ( args[i].equals("--testFunctions") ){
				System.setProperty(CBMCAssertionsInjector.BCT_TEST_FUNCTIONS, args[++i] );
			} else if ( args[i].equals("--unwind") ){
				System.setProperty(CBCMRegressionsDetector.BCT_CBMC_UNWIND, args[++i] );
			} else if ( args[i].equals("--includeOnly") ){
				//--includeOnly x,y:z  include all the assertions that contain var x plus all the assertions that include both y and z
				System.setProperty(CBCMRegressionsDetector.BCT_EXPORTER_INCLUDE_ONLY, args[++i] );
			} else if ( args[i].equals("--excludeVars") ){
				System.setProperty(CBCMRegressionsDetector.BCT_EXPORTER_INCLUDE_ONLY, args[++i] );
			} else if ( args[i].equals("--exportLines") ){
				System.setProperty(CBCMRegressionsDetector.BCT_EXPORT_LINES, args[++i] );
			} else if ( args[i].equals("--programPoints") ){
				System.setProperty("bct.programPointsToInclude", args[++i] );
			} else if ( args[i].equals("--minimal") ){
//				System.setProperty(CBMCAssertionsInjector.COMMENT_OUT_LINES_NOT_MONITORED, "true" );
				System.setProperty(CBCMRegressionsDetector.BCT_FILTER_REDUNDANT_MODELS, "true" );
			} else if ( args[i].equals("--pointers") ){
				System.setProperty("bct.processPointers", "true");
			} else {
				argsArr.add(args[i]);
			}
		}
		
		
		
		RegressionsDetector.execute(argsArr.toArray(new String[args.length]));
	}

}
