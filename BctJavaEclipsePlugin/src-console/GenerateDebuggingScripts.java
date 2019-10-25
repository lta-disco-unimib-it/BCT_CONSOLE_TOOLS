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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;


public class GenerateDebuggingScripts {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException {
		
		if ( args.length != 5  && args.length != 3 ){
			System.out.println("Usage: " +
					"\n\t"+GenerateDebuggingScripts.class.getCanonicalName()+" <BctOutputDir> <originalSrc> <originalExec> <modifiedSrc> <modifiedExec>"+
					"\n\t"+GenerateDebuggingScripts.class.getCanonicalName()+" <BctOutputDir> <src> <exec>"
					);
			return;
		}
		

		
		console.DebuggingScriptsGenerator.main(args);
	}

}
