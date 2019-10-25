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
