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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;

import console.ScriptsGenerator;
import cpp.gdb.FunctionMonitoringDataSerializer;

/**
 * This program generates configuration scripts that allow to monitor modified functions and callers/callees.
 * The scripts monitor all the enter/exit points of all the functions modified.
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@gmail.com
 *
 */
public class GenerateModifiedFunctionsAndCallersScripts extends ScriptsGenerator {
	
	
	public GenerateModifiedFunctionsAndCallersScripts() {
		super();
		this.setTraceAllLinesOfMonitoredFunctions(false);
		this.setTraceAllLinesOfChildren(false);
		this.setCompleteMonitoring(false);
		this.setMonitorAllIfNoChange(false);
		
		String dllString = System.getProperty("dll");
		if ( dllString != null ){
			boolean dll = Boolean.parseBoolean(dllString);
			this.setDll(dll);
		}
	}

	public static void main( String args[]) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException{
		ScriptsGenerator generator = new GenerateModifiedFunctionsAndCallersScripts();
		
		
		ScriptsGenerator.execute(args, generator);
		
		
	}

}
