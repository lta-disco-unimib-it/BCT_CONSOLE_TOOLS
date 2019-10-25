package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;

import console.ScriptsGenerator;

/**
 * This program generates configuration scripts that allow to monitor two different branches of a program.
 * The scripts monitor all the enter/exit points of all the functions implemented by a program.
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@gmail.com
 *
 */
public class GenerateBranchComparisonScripts extends ScriptsGenerator {
	
	
	public GenerateBranchComparisonScripts() {
		super();
		this.setTraceAllLinesOfMonitoredFunctions(false);
		this.setTraceAllLinesOfChildren(false);
		this.setCompleteMonitoring(true);
		
		
		String dllString = System.getProperty("dll");
		if ( dllString != null ){
			boolean dll = Boolean.parseBoolean(dllString);
			this.setDll(dll);
		}
	}

	public static void main( String args[]) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException{
		ScriptsGenerator generator = new GenerateBranchComparisonScripts();
		
		
		ScriptsGenerator.execute(args, generator);
	}

}
